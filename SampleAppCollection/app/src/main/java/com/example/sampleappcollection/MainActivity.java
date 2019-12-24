package com.example.sampleappcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sampleappcollection.activitytransition.T1Activity;
import com.example.sampleappcollection.asynctask.AsyncTaskActivity;
import com.example.sampleappcollection.biometrics.BiometricsMainActivity;
import com.example.sampleappcollection.bottomsheet.BottomSheetActivity;
import com.example.sampleappcollection.broadcast.BroadcastActivity;
import com.example.sampleappcollection.canvas.CanvasActivity;
import com.example.sampleappcollection.eventbus.EventBusActivity;
import com.example.sampleappcollection.gridview.GridViewActivity;
import com.example.sampleappcollection.handler.HandlerActivity;
import com.example.sampleappcollection.notification.NotificationActivity;
import com.example.sampleappcollection.picture.PictureActivity;
import com.example.sampleappcollection.popupwindow.PopupMainActivity;
import com.example.sampleappcollection.recyclerview.RecyclerActivity;
import com.example.sampleappcollection.retrofit.RetrofitActivity;
import com.example.sampleappcollection.rxjava.RxJavaMainActivity;
import com.example.sampleappcollection.service.ServiceActivity;
import com.example.sampleappcollection.spannablestring.SpannableActivity;
import com.example.sampleappcollection.sqlitetable.SqliteTableActivity;
import com.example.sampleappcollection.view.ViewActivity;
import com.example.sampleappcollection.webview.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.list)
    ListView list;

    private final int RESULT_CODE_1 = 100;
    private long exitTime = 0;

    private String[] mTitle = new String[]
            {"View",
            "Notification",
            "Recycler list",
            "Activity jump animation",
            "BottomSheet",
            "PopupWindow",
            "SQLite database and dynamic tables",
            "GridView",
            "Handler",
            "Retrofit",
            "Broadcast",
            "SpannableString rich text",
            "Canvas",
            "AsyncTask",
            "Service",
            "RxJava",
            "Eventbus",
            "WebView interaction",
            "Photo and album",
            "Biometrics"};
    private Class[] mClasses = new Class[]
            {ViewActivity.class,
            NotificationActivity.class,
            RecyclerActivity.class,
            T1Activity.class,
            BottomSheetActivity.class,
            PopupMainActivity.class,
            SqliteTableActivity.class,
            GridViewActivity.class,
            HandlerActivity.class,
            RetrofitActivity.class,
            BroadcastActivity.class,
            SpannableActivity.class,
            CanvasActivity.class,
            AsyncTaskActivity.class,
            ServiceActivity.class,
            RxJavaMainActivity.class,
            EventBusActivity.class,
            WebViewActivity.class,
            PictureActivity.class,
            BiometricsMainActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        list.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list, R.id.tv_items, mTitle));

        // 判断权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            initListener();
        } else {
            // 如果用户拒绝权限，第二次打开才会显示提示文字
            EasyPermissions.requestPermissions(this, "Store permissions are required to maintain the normal operation of the app", RESULT_CODE_1, perms);
        }
    }

    private void initListener() {
        list.setOnItemClickListener((adapterView, view, i, l) ->
                startActivity(new Intent(MainActivity.this, mClasses[i])
                ));
    }

    // 双击退出
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(getApplicationContext(), "Press again to exit the program", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 同意授权
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initListener();
    }

    /**
     * 拒绝授权
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "Authority", Toast.LENGTH_SHORT).show();
        finish();
    }
}
