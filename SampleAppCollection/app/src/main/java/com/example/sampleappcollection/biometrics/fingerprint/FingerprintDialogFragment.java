package com.example.sampleappcollection.biometrics.fingerprint;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sampleappcollection.R;

import javax.crypto.Cipher;

/**
 * 描述：指纹识别弹窗
 *
 * @author 张钦
 * @date 2018/10/15
 */
@TargetApi(23)
public class FingerprintDialogFragment extends DialogFragment {

    /**
     * 指纹管理器
     */
    private FingerprintManager fingerprintManager;
    /**
     * 提供取消正在进行的操作的功能
     */
    private CancellationSignal mCancellationSignal;
    /**
     * 加密类
     */
    private Cipher mCipher;
    /**
     * 上下文对象
     */
    private Activity mActivity;

    private TextView errorMsg;

    private OnDialogResultListener mOnDialogResultListener;

    /**
     * 标识是否是用户主动取消的认证。
     */
    private boolean isSelfCancelled;

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        errorMsg = v.findViewById(R.id.error_msg);
        TextView cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(v1 -> {
            dismiss();
            stopListening();
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始指纹认证监听
        startListening(mCipher);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止指纹认证监听
        stopListening();
    }

    private void startListening(Cipher cipher) {
        isSelfCancelled = false;
        mCancellationSignal = new CancellationSignal();
        /**
         * crypto 这是一个加密类的对象，指纹扫描器会使用这个对象来判断认证结果的合法性。这个对象可以是null，但是这样的话，就意味这app无条件信任认证的结果，虽然从理论上这个过程可能被攻击，数据可以被篡改，这是app在这种情况下必须承担的风险。因此，建议这个参数不要置为null。这个类的实例化有点麻烦，主要使用javax的security接口实现，后面我的demo程序中会给出一个helper类（CryptoObjectHelper.java），这个类封装内部实现的逻辑，开发者可以直接使用我的类简化实例化的过程。
         * cancel 这个是CancellationSignal类的一个对象，这个对象用来在指纹识别器扫描用户指纹的是时候取消当前的扫描操作，如果不取消的话，那么指纹扫描器会移植扫描直到超时（一般为30s，取决于具体的厂商实现），这样的话就会比较耗电。建议这个参数不要置为null。
         * flags 标识位，根据上图的文档描述，这个位暂时应该为0，这个标志位应该是保留将来使用的。
         * callback 这个是FingerprintManager.AuthenticationCallback类的对象，这个是这个接口中除了第一个参数之外最重要的参数了，稍后我们详细来介绍。这个参数不能为NULL。
         * handler 这是Handler类的对象，如果这个参数不为null的话，那么FingerprintManager将会使用这个handler中的looper来处理来自指纹识别硬件的消息。通常来讲，开发这不用提供这个参数，可以直接置为null，因为FingerprintManager会默认使用app的main looper来处理。
         */
        fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                if (!isSelfCancelled) {
                    errorMsg.setText(errString);
                    // 指纹验证失败，不可再验
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }

            /**
             * 指纹验证失败，可再验，可能手指过脏，或者移动过快等原因。
             */
            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                errorMsg.setText(helpString);
            }

            /**
             * 成功
             */
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                if (mOnDialogResultListener != null){
                    mOnDialogResultListener.success();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                errorMsg.setText("指纹认证失败，请再试一次");
            }
        }, null);
    }

    private void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }

    /**
     * 监听接口
     */
    public interface OnDialogResultListener {

        /**
         * 成功
         */
        void success();
    }

    /**
     * 设置监听
     */
    public void setOnClickListener(OnDialogResultListener onDialogResultListener) {
        mOnDialogResultListener = onDialogResultListener;
    }

}