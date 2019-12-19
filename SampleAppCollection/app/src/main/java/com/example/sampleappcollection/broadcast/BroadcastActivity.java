package com.example.sampleappcollection.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;
import com.example.sampleappcollection.broadcast.broadcast.LocalBroadcastReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：Broadcast广播
 *
 * @author zhangqin
 * @date 2016/12/10
 */
public class BroadcastActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mBroadcastList;

    private BroadcastReceiver mReceiver1;
    private BroadcastReceiver mReceiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        String[] strings = new String[]{"普通广播", "有序广播", "本地广播"};
        mBroadcastList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, strings));

        mBroadcastList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    ordinaryBroadcast();
                    break;
                case 1:
                    orderBroadcast();
                    break;
                case 2:
                    localBroadcast();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 注册普通广播
        mReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String str = intent.getStringExtra("name");
                LogUtils.i("onReceive(普通广播，动态注册): " + str);
            }
        };
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.sdwfqin.broadcastdemo");
        registerReceiver(mReceiver1, filter1);

        // ===================================================
        // 注册本地广播
        mReceiver2 = new LocalBroadcastReceiver();
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("com.sdwfqin.broadcastdemo3");
        LocalBroadcastManager.getInstance(BroadcastActivity.this).registerReceiver(mReceiver2, filter2);
    }

    private void ordinaryBroadcast() {
        final Intent intent = new Intent();
        //广播内容
        intent.setAction("com.sdwfqin.broadcastdemo");
        intent.putExtra("name", "values");

        // 发送普通广播
        sendBroadcast(intent);
    }

    private void orderBroadcast() {
        final Intent intent = new Intent();
        //广播内容
        intent.setAction("com.sdwfqin.broadcastdemo2");

        // 发送有序广播
        sendOrderedBroadcast(intent, null);
        // 终结广播
//        endOrderedBroadcast(intent, null, new OrderBroadcastReceiver.LowPriority(),
//                new Handler(), 0, null, null);

    }

    private void localBroadcast() {
        final Intent intent = new Intent();
        intent.setAction("com.sdwfqin.broadcastdemo3");
        intent.putExtra("name", "values");
        // 发送异步广播
        LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcast(intent);
        // 发送同步广播
        // LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcastSync(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 反注册广播
        unregisterReceiver(mReceiver1);
        LocalBroadcastManager.getInstance(BroadcastActivity.this).unregisterReceiver(mReceiver2);
    }
}
