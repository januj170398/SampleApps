package com.example.sampleappcollection.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.sampleappcollection.MainActivity;
import com.example.sampleappcollection.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：通知栏
 *
 * @author zhangqin
 * @date 2018/6/19
 */
public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView list;

    private Context mContext;
    final String CHANNEL_ID = "channel_id_1";
    final String CHANNEL_NAME = "channel_name_1";

    private String[] mTitle = new String[]{"普通通知", "自定义通知", "进度条"};
    private Map<Integer, NotificationCompat.Builder> mMap;
    private NotificationManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        mContext = this;

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        initNotification();

        mMap = new HashMap<>();

        list.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mTitle));
        initListener();
    }

    private void initListener() {
        list.setOnItemClickListener((adapterView, view, i, l) -> {
                    switch (i) {
                        case 0:
                            classicNotification();
                            break;
                        case 1:
                            customNotification();
                            break;
                        case 2:
                            barNotification();
                            break;
                        default:
                    }
                }
        );
    }

    /**
     * 创建渠道
     */
    private void initNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            /**
             * IMPORTANCE_UNSPECIFIED（值为-1）意味着用户没有表达重要性的价值。此值用于保留偏好设置，不应与实际通知关联。
             * IMPORTANCE_NONE（值为0）不重要的通知：不会在阴影中显示。
             * IMPORTANCE_MIN（值为1）最低通知重要性：只显示在阴影下，低于折叠。这不应该与Service.startForeground一起使用，因为前台服务应该是用户关心的事情，所以它没有语义意义来将其通知标记为最低重要性。如果您从Android版本O开始执行此操作，系统将显示有关您的应用在后台运行的更高优先级通知。
             * IMPORTANCE_LOW（值为2）低通知重要性：无处不在，但不侵入视觉。
             * IMPORTANCE_DEFAULT （值为3）：默认通知重要性：随处显示，产生噪音，但不会在视觉上侵入。
             * IMPORTANCE_HIGH（值为4）更高的通知重要性：随处显示，造成噪音和窥视。可以使用全屏的Intent。
             */
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            // 配置通知渠道的属性
            notificationChannel.setDescription("渠道的描述");
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            notificationChannel.enableVibration(true);
            //如上设置使手机：静止1秒，震动2秒，静止1秒，震动3秒
            notificationChannel.setVibrationPattern(new long[]{1000, 2000, 1000, 3000});
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            mManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * 经典通知栏
     */
    private void classicNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder
                .setContentTitle("这是通知标题")
                .setContentText("这是通知内容")
                .setWhen(System.currentTimeMillis())
                // 点击通知后自动消失
                .setAutoCancel(true)
                // 通知图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 小图标只能用alpha图层进行绘制，不能用rgb图层
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_launcher));


        // ===========  设置声音文件，这里的文件是res/raw/miui_notice.mp3  ===========
        // Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.miui_notice);
        // setSound(uri);

        // ======================  点击通知后进入的活动  ======================
        Intent resultIntent = new Intent(this, MainActivity.class);
        // 这两句非常重要，使之前的活动不出栈
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //允许更新
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent)
                // 浮动通知
                .setFullScreenIntent(resultPendingIntent, true);


        mManager.notify(0x01, builder.build());
    }

    /**
     * 自定义布局
     */
    private void customNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder
                // 通知图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 小图标只能用alpha图层进行绘制，不能用rgb图层
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_launcher));

        // ======================  点击通知后进入的活动  ======================
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        // 这两句非常重要，使之前的活动不出栈
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //允许更新
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        // 设置通知的显示视图
        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(),
                R.layout.notifit_bar);

        builder.setCustomContentView(remoteViews);

        Notification notification = builder.build();
        // 通知点击不消失
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        mManager.notify(0x02, notification);
        mMap.put(0x02, builder);

        // 更新自定义布局
        updateCustom(0x02, 80);
    }

    /**
     * 进度条
     */
    private void barNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder
                .setContentTitle("这是通知标题")
                .setContentText("这是通知内容")
                .setWhen(System.currentTimeMillis())
                // 通知图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 小图标只能用alpha图层进行绘制，不能用rgb图层
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_launcher))
                .setProgress(100, 0, false);

        // ======================  点击通知后进入的活动  ======================
        Intent resultIntent = new Intent(this, NotificationActivity.class);
        // 这两句非常重要，使之前的活动不出栈
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        //允许更新
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        // 通知点击不消失
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        mManager.notify(0x03, notification);
        mMap.put(0x03, builder);


        // 更新进度条
        updateProgress(0x03, 80);
    }

    public void updateProgress(int notificationId, int progress) {
        NotificationCompat.Builder builder = mMap.get(notificationId);
        if (null != builder) {
            // 修改进度条
            builder.setProgress(100, progress, false);
            mManager.notify(notificationId, builder.build());
        }
    }

    public void updateCustom(int notificationId, int progress) {
        NotificationCompat.Builder builder = mMap.get(notificationId);
        if (null != builder) {
            // 修改进度条
            @SuppressLint("RestrictedApi")
            RemoteViews contentView = builder.getContentView();
            contentView.setProgressBar(R.id.pBar, 100, progress, false);
            builder.setCustomContentView(contentView);
            mManager.notify(notificationId, builder.build());
        }
    }

}
