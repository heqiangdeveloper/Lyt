package com.cimcitech.lyt.activity.main;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.register.IdCardBackVo;
import com.cimcitech.lyt.bean.register.IdCardFrontVo;
import com.cimcitech.lyt.bean.register.VerificationCodeVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.DateTool;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.TypeConverter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterFirstActivity2 extends BaseActivity {
    @Bind(R.id.font_side_fl)
    FrameLayout font_side_Fl;
    @Bind(R.id.behind_side_fl)
    FrameLayout behind_side_Fl;
    @Bind(R.id.font_side_iv)
    ImageView font_side_Iv;
    @Bind(R.id.behind_side_iv)
    ImageView behind_side_Iv;
    @Bind(R.id.upload_tv1)
    TextView upload_Tv1;
    @Bind(R.id.upload_tv2)
    TextView upload_Tv2;
    @Bind(R.id.agree_tv)
    TextView agree_Tv;
    @Bind(R.id.register_bt)
    Button register_Bt;
    @Bind(R.id.show_agree_cb)
    CheckBox show_agree_Cb;

    private VerificationCodeVo verificationCodeVo;
    private IdCardFrontVo idCardFrontVo;
    private IdCardBackVo idCardBackVo;
    private SharedPreferences sp;
    private boolean isRegister = true;

    private PopupWindow pop;
    private final Context context = RegisterFirstActivity2.this;
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private boolean isFontSelected = true;
    private String imgPath_front = "";
    private String imgPath_behind = "";

    private String name = "";
    private String idcard = "";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first2);
        ButterKnife.bind(this);

        isRegister = getIntent().getStringExtra("type").equals("Forget") ? false : true;

        sp = this.getSharedPreferences(Config.KEY_LOGIN_AUTO, MODE_PRIVATE);//如果存在则打开它，否则创建新的Preferences
    }

    public void textWatcher(EditText tv){
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addWatcher();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void registerBtnOn(){
        register_Bt.setBackgroundResource(R.drawable.shape_login_button_on);
        register_Bt.setClickable(true);
        register_Bt.setTextColor(getResources().getColor(R.color.white));
    }

    public void registerBtnOff(){
        register_Bt.setBackgroundResource(R.drawable.shape_login_button_off);
        register_Bt.setClickable(false);
        register_Bt.setTextColor(getResources().getColor(R.color.login_off_color));
    }


    @OnClick({R.id.register_bt,R.id.back,R.id.agree_tv,R.id.font_side_fl,R.id.behind_side_fl,
              R.id.show_agree_cb})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.register_bt:
                //判断是否需要调用OCR识别身份证照片
                if(Config.isNeedVerify){
                    if(register_Bt.isClickable()){
                        mCommittingDialog.show();
                        //先检查身份证正面，若成功，再检查身份证反面
                        verifyFront();
                    }else {
                        Toast.makeText(RegisterFirstActivity2.this,getResources().getString(R.string
                                .register_warning_msg),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Intent i = new Intent(RegisterFirstActivity2.this,RegisterSecondActivity.class);
                    i.putExtra("name","");
                    i.putExtra("idCard","");
                    startActivity(i);
                }
                break;
            case R.id.agree_tv:
                startActivity(new Intent(RegisterFirstActivity2.this,AgreementActivity.class));
                break;
            case R.id.font_side_fl:
                isFontSelected = true;
                showContactUsPopWin(context);
                pop.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case R.id.behind_side_fl:
                isFontSelected = false;
                showContactUsPopWin(context);
                pop.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case R.id.show_agree_cb:
                addWatcher();
                break;
        }
    }

    public void showContactUsPopWin(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.dialog_my_info_view, null);
        view.getBackground().setAlpha(230);//0-255,0完全透明
        // 创建PopupWindow对象
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        View pop_reward_view = view.findViewById(R.id.pop_reward_view);
        TextView take_photo_Tv = view.findViewById(R.id.take_photo_tv);
        TextView upload_photo_Tv = view.findViewById(R.id.upload_photo_tv);
        TextView cancel_Tv = view.findViewById(R.id.cancel_tv);
        take_photo_Tv.setOnClickListener(new RegisterFirstActivity2.Listener());
        upload_photo_Tv.setOnClickListener(new RegisterFirstActivity2.Listener());
        cancel_Tv.setOnClickListener(new RegisterFirstActivity2.Listener());
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
    }

    class Listener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.take_photo_tv://拍照
                    pop.dismiss();
                    xiangjiClick();
                    break;
                case R.id.upload_photo_tv://从相册选取
                    pop.dismiss();
                    xiangceClick();
                    break;
                case R.id.cancel_tv:
                    pop.dismiss();
                    break;
            }
        }
    }

    /**
     * 打开相机
     */
    public void xiangjiClick() {
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission
                .CAMERA) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(RegisterFirstActivity2.this,permissions,1);
        }else {
            take_photo();//已经授权了就调用打开相机的方法
        }
    }

    /**
     * 打开相册
     */
    public void xiangceClick() {
        select_photo();
    }

    /**
     * 拍照获取图片
     **/
    public void take_photo() {
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            outputImagepath = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(outputImagepath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, outputImagepath.getAbsolutePath());
                Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 从相册中获取图片
     */
    public void select_photo() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            openAlbum();
        }
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    /**
     * 4.4以下系统处理图片的方法
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 4.4及以上系统处理图片的方法
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("uri=intent.getData :", "" + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);        //数据表里指定的行
            Log.d("getDocumentId(uri) :", "" + docId);
            Log.d("uri.getAuthority() :", "" + uri.getAuthority());
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);
    }

    /**
     * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 拍完照和从相册获取玩图片都要执行的方法(根据图片路径显示图片)
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //orc_bitmap = BitmapFactory.decodeFile(imagePath);//获取图片
            orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图片
            ImgUpdateDirection(imagePath);//显示图片,并且判断图片显示的方向,如果不正就放正
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_LONG).show();
        }
    }

    //比例压缩
    private Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    //改变拍完照后图片方向不正的问题
    private void ImgUpdateDirection(String filepath) {
        Log.d("filepath","filepath is: " + filepath);
        int digree = 0;//图片旋转的角度
        //根据图片的URI获取图片的绝对路径
        Log.i("tag", ">>>>>>>>>>>>>开始");
        //String filepath = ImgUriDoString.getRealFilePath(getApplicationContext(), uri);
        Log.i("tag", "》》》》》》》》》》》》》》》" + filepath);
        //根据图片的filepath获取到一个ExifInterface的对象
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
            Log.i("tag", "exif》》》》》》》》》》》》》》》" + exif);
            if (exif != null) {

                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;

                }

            }
            //如果图片不为0
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                orc_bitmap = Bitmap.createBitmap(orc_bitmap, 0, 0, orc_bitmap.getWidth(),
                        orc_bitmap.getHeight(), m, true);
            }
            if (orc_bitmap != null) {
                if(isFontSelected){
                    upload_Tv1.setVisibility(View.GONE);
                    font_side_Iv.setImageBitmap(orc_bitmap);
                    imgPath_front = filepath;
                }else{
                    upload_Tv2.setVisibility(View.GONE);
                    behind_side_Iv.setImageBitmap(orc_bitmap);
                    imgPath_behind = filepath;
                }
                addWatcher();
            }
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
            imgPath_front = imgPath_behind = "";
        }
    }

    public void addWatcher(){
//        if(name_Et.getText().toString().trim().length() != 0 &&
//                idcard_Et.getText().toString().trim().length() != 0 &&
//                imgPath_front.trim().length() != 0 &&
//                imgPath_behind.trim().length() != 0 &&
//                show_agree_Cb.isChecked()){
        if(imgPath_front.trim().length() != 0 &&
                imgPath_behind.trim().length() != 0 &&
                show_agree_Cb.isChecked()){
            registerBtnOn();
        }else{
            registerBtnOff();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //打开相机后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    /**
                     * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                     */
                    displayImage(outputImagepath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImgeOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1://相机权限
                if(grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(context, "必须同意所有的权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    take_photo();
                }else {
                    Toast.makeText(this, "你需要许可", Toast.LENGTH_LONG).show();
                }
                break;
            case 2://相册的权限：容许访问内存
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();//打开相册
                } else {
                    Toast.makeText(this, "你需要许可", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public boolean isUserNameValid(String s) {
        return Pattern.matches(REGEX_MOBILE,s) || Pattern.matches(REGEX_EMAIL,s) ? true : false;
    }

    public void verifyFront() {
        String key = Config.APPKEY;
        //将图片转成Base64格式
        String image = TypeConverter.GetImageStr(imgPath_front);

        //先检测身份证正面，若成功，再检测反面
        OkHttpUtils
                .post()
                .url(Config.IDCARD_IDENTIFY_URL)
                .addParams("key", key)
                .addParams("image", image)
                .addParams("side", "front")
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mCommittingDialog.dismiss();
                                Toast.makeText(RegisterFirstActivity2.this, "网络错误，请检查网络",Toast
                                        .LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.d("ocr","front is: " + response);
                                try {
                                    idCardFrontVo = GjsonUtil.parseJsonWithGson(response, IdCardFrontVo.class);
                                    if (idCardFrontVo != null) {
                                        if (idCardFrontVo.getError_code() == 0) {//成功
                                            //姓名和身份证号码都正确，再验证身份证反面
                                            name = idCardFrontVo.getResult().getRealname();
                                            idcard = idCardFrontVo.getResult().getIdcard();
                                            verifyBehind();
                                        }else{//失败
                                            mCommittingDialog.dismiss();
                                            Toast.makeText(RegisterFirstActivity2.this,
                                                    idCardFrontVo.getError_code(),Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        mCommittingDialog.dismiss();
                                        Toast.makeText(RegisterFirstActivity2.this, "接口访问失败",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    mCommittingDialog.dismiss();
                                    e.printStackTrace();
                                }
                            }
                        }
                );
    }

    public void verifyBehind() {
        //将图片转成Base64格式
        String image = TypeConverter.GetImageStr(imgPath_behind);
        String key = Config.APPKEY;

        OkHttpUtils
                .post()
                .url(Config.IDCARD_IDENTIFY_URL)
                .addParams("key", key)
                .addParams("image", image)
                .addParams("side", "back")
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mCommittingDialog.dismiss();
                                Toast.makeText(RegisterFirstActivity2.this, "网络错误，请检查网络",Toast
                                        .LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.d("ocr","behind is: " + response);
                                try {
                                    idCardBackVo = GjsonUtil.parseJsonWithGson(response, IdCardBackVo.class);
                                    if (idCardBackVo != null) {
                                        if (idCardBackVo.getError_code() == 0) {//成功
                                            //验证身份证的有效期
                                            String todayStr = DateTool.getSystemDateStr();

                                            //将20180321格式转为2018-03-21
                                            String endStr = idCardBackVo.getResult().getEnd();//身份证失效时间

                                            if(Integer.parseInt(todayStr) < Integer.parseInt(endStr)){//有效
                                                mCommittingDialog.dismiss();

                                                //存储相关的信息，并进行下一步
                                                Intent i = new Intent(RegisterFirstActivity2.this,RegisterSecondActivity.class);
                                                i.putExtra("name",name);
                                                i.putExtra("idCard",idcard);
                                                startActivity(i);
                                            }else {//失效
                                                mCommittingDialog.dismiss();
                                                Toast.makeText(RegisterFirstActivity2.this,
                                                        "身份证不在有效期内！", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{//失败
                                            mCommittingDialog.dismiss();
                                            Toast.makeText(RegisterFirstActivity2.this,
                                                    idCardBackVo.getError_code(),Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        mCommittingDialog.dismiss();
                                        Toast.makeText(RegisterFirstActivity2.this, "接口访问失败",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    mCommittingDialog.dismiss();
                                    e.printStackTrace();
                                }
                            }
                        }
                );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterFirstActivity2.this,LoginActivity.class));
        finish();
    }
}
