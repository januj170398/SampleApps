package com.example.sampleappcollection.view.paypwdinput;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：验证码
 *
 * @author zhangqin
 */
public class PayPwdInputActivity extends AppCompatActivity {

    @BindView(R.id.password)
    PayPwdInputView mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pwd_input);
        ButterKnife.bind(this);

        mPassword.setComparePassword("123456", new PayPwdInputView.OnPasswordListener() {
            @Override
            public void onSuccess(String psd) {
                Toast.makeText(PayPwdInputActivity.this, "匹配成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(PayPwdInputActivity.this, "匹配失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
