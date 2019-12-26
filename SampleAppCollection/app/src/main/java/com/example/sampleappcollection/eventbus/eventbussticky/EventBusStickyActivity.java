package com.example.sampleappcollection.eventbus.eventbussticky;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Send a sticky event via postSticky, this event will not disappear after only being consumed once, but will always exist in the system
 * Until deleted by removeStickyEvent. Then as long as all methods of the sticky event are subscribed,
 * As long as it is registered, it will be detected and executed.
 * The subscription method needs to add the sticky = true attribute
 *
 * @author zhangqin
 */
public class EventBusStickyActivity extends AppCompatActivity {

    @BindView(R.id.btn_event2_send)
    Button mBtnEvent2Send;
    @BindView(R.id.btn_event2_remove)
    Button mBtnEvent2Remove;
    @BindView(R.id.tv_event2_msg)
    TextView mTvEvent2Msg;

    private String mString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_sticky);
        ButterKnife.bind(this);

        mBtnEvent2Send.setOnClickListener(v -> {
            mString = "Sticky events emitted by EventBus2Activity，Exit this activity without deleting or come in or go to EventBus1Activity can still see me";
            EventBus.getDefault().postSticky(mString);
        });
        mBtnEvent2Remove.setOnClickListener(v -> {
            // Remove all sticky events
            EventBus.getDefault().removeAllStickyEvents();
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN, priority = 100)
    public <T> void onMessageEvent(T t) {
        try {
            mTvEvent2Msg.setText(t.toString());
        } catch (Exception e) {
            LogUtils.e("onMessageEvent: ", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        // 反注册
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
