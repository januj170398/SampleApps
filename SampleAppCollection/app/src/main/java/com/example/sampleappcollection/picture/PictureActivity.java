package com.example.sampleappcollection.picture;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.sampleappcollection.Config;
import com.example.sampleappcollection.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 描述：拍照与图片剪裁
 *
 * @author zhangqin
 * @date 2017/9/14
 */
public class PictureActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int PERMISSIONS_CODE_1 = 101;
    public static final int RESULT_CODE_1 = 201;
    public static final int RESULT_CODE_2 = 202;

    @BindView(R.id.camera_pz)
    Button mCameraPz;
    @BindView(R.id.camera_cc)
    Button mCameraCc;
    @BindView(R.id.camera_img)
    ImageView mCameraImg;
    @BindView(R.id.camera_tv)
    TextView mCameraTv;

    /**
     * 需要的权限
     */
    private String[] mPerms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    /**
     * 7.0 以上的uri
     */
    private Uri mProviderUri;
    /**
     * 7.0 以下的uri
     */
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        // 判断权限
        if (EasyPermissions.hasPermissions(this, mPerms)) {
        } else {
            // 如果用户拒绝权限，第二次打开才会显示提示文字
            EasyPermissions.requestPermissions(this, "使用拍照功能需要拍照权限！", PERMISSIONS_CODE_1, mPerms);
        }
    }

    @OnClick({R.id.camera_pz, R.id.camera_cc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.camera_pz:
                camera();
                break;
            case R.id.camera_cc:
                selectImg();
                break;
            default:
                break;
        }
    }

    /**
     * 相册选图
     */
    private void selectImg() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, RESULT_CODE_2);
    }

    /**
     * 拍照
     */
    private void camera() {

        File file = new File(Config.SAVE_REAL_PATH, System.currentTimeMillis() + ".jpg");
        LogUtils.e(file.getPath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            mProviderUri = FileProvider.getUriForFile(this, "com.sdwfqin.sample.fileprovider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mProviderUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            mUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        }
        try {
            startActivityForResult(intent, RESULT_CODE_1);
        } catch (ActivityNotFoundException anf) {
            ToastUtils.showShort("摄像头未准备好！");
        }
    }

    /**
     * 使用UCrop进行图片剪裁
     *
     * @param uri
     */
    public void cropRawPhoto(Uri uri) {

        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
        options.setFreeStyleCropEnabled(true);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 圆
        options.setCircleDimmedLayer(true);
        // 不显示网格线
        options.setShowCropGrid(false);

        FileUtils.createOrExistsDir(Config.SAVE_REAL_PATH);

        // 设置源uri及目标uri
        UCrop.of(uri, Uri.fromFile(new File(Config.SAVE_REAL_PATH, System.currentTimeMillis() + ".jpg")))
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(200, 200)
                // 配置参数
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UCrop.RESULT_ERROR) {
            mCameraTv.setText(UCrop.getError(data) + "");
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CODE_1:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        cropRawPhoto(mProviderUri);
                    } else {
                        cropRawPhoto(mUri);
                    }
                    break;
                case RESULT_CODE_2:
                    LogUtils.i("onActivityResult: " + data.getData());
                    cropRawPhoto(data.getData());
                    break;
                case UCrop.REQUEST_CROP:
                    LogUtils.i("onActivityResult: " + UCrop.getOutput(data));
                    mCameraTv.setText(UCrop.getOutput(data) + "");
                    Glide.with(this)
                            .load(UCrop.getOutput(data))
                            .into(mCameraImg);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.i("onPermissionsGranted: " + "同意授权");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "没有权限可能会引起程序崩溃", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
