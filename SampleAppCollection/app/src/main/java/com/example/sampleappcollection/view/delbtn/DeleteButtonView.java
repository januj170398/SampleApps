package com.example.sampleappcollection.view.delbtn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * 描述：长按删除按钮
 *
 * @author zhangqin
 * @date 2018/5/9
 */
public class DeleteButtonView extends AppCompatTextView {

    private EditText mEditText;
    private Context mContext;
    private ObservableEmitter<Integer> mEmitter;
    private Timer mTimer;

    public DeleteButtonView(Context context) {
        this(context, null);
    }

    public DeleteButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mTimer = new Timer();
        Observable<Integer> observable = Observable.create(e -> mEmitter = e);
        Disposable subscribe = observable
                .subscribe(integer -> {
                    if (integer == 1) {
                        int index = mEditText.getSelectionStart();
                        if (index > 0) {
                            Editable editable = mEditText.getText();
                            editable.delete(index - 1, index);
                        }
                    } else {
                        mTimer.cancel();
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEmitter.onNext(1);
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mEmitter.onNext(1);
                    }
                }, 1000, 200);
                break;
            case MotionEvent.ACTION_UP:
                mEmitter.onNext(2);
                break;
        }
        return true;
    }

    /**
     * 传入外部的 EditText
     *
     * @param editText
     */
    public void setEditText(EditText editText) {
        mEditText = editText;
    }
}
