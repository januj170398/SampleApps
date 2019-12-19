package com.example.sampleappcollection.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;
import com.sdwfqin.sample.service.service.InteractiveService;
import com.sdwfqin.sample.service.service.MyIntentService;
import com.sdwfqin.sample.service.service.NotInteractiveService;
import com.sdwfqin.sample.service.service.StageService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：服务
 *
 * @author zhangqin
 * @date 2016/12/8
 */
public class ServiceActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mServiceList;

    MyServiceConnection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        final Intent intent = new Intent(ServiceActivity.this, NotInteractiveService.class);
        final Intent intent2 = new Intent(ServiceActivity.this, InteractiveService.class);
        final Intent intent3 = new Intent(ServiceActivity.this, StageService.class);
        final Intent intent4 = new Intent(ServiceActivity.this, MyIntentService.class);

        String[] strings = new String[]{"启动不可交互服务", "停止不可交互服务", "启动可交互服务",
                "停止可交互服务", "启动前台服务", "IntentService（耗时服务）"};
        mServiceList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, strings));

        mServiceList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    // 启动不可交互服务
                    startService(intent);
                    break;
                case 1:
                    // 关闭不可交互服务
                    stopService(intent);
                    break;
                case 2:
                    // 绑定可交互服务
                    conn = new MyServiceConnection();
                    bindService(intent2, conn, BIND_AUTO_CREATE);
                    break;
                case 3:
                    // 解绑可交互服务
                    try {
                        unbindService(conn);
                    } catch (Exception e) {
                        Toast.makeText(ServiceActivity.this, "呦，崩溃了耶，亲，你绑定了吗", Toast.LENGTH_SHORT).show();
                        LogUtils.i("onItemClick: ", e);
                    }
                    break;
                case 4:
                    // 启动前台服务
                    startService(intent3);
                    break;
                case 5:
                    // 启动耗时服务
                    startService(intent4);
                    break;
                default:
                    break;
            }
        });
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.i("onServiceConnected: " + "绑定回调");
            InteractiveService.MyBind myBind = (InteractiveService.MyBind) iBinder;
            myBind.showLog();
            myBind.show("测试传输数据");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.i("onServiceDisconnected: ");
        }
    }
}
