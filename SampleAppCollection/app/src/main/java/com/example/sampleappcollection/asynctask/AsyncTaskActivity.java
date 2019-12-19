package com.example.sampleappcollection.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：AsyncTask
 *
 * @author zhangqin
 */
public class AsyncTaskActivity extends AppCompatActivity {

    @BindView(R.id.async_tv)
    TextView mAsyncTv;
    @BindView(R.id.async_progress)
    ProgressBar mAsyncProgress;
    @BindView(R.id.async_btn)
    Button mAsyncBtn;
    @BindView(R.id.async_t)
    Button mAsyncT;

    private MyAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

        mAsyncBtn.setOnClickListener(v -> {
            mAsyncTask = new MyAsyncTask();
            // 使用指定的Params参数执行任务
            mAsyncTask.execute(100, 50);
        });

        mAsyncT.setOnClickListener(v -> {
            // 停止任务
            // asyncTask.cancel(true);
        });
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        /**
         * 这里的Integer参数对应AsyncTask中的第一个参数
         * 这里的String返回值对应AsyncTask的第三个参数
         * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
         * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
         */
        @Override
        protected String doInBackground(Integer... params) {
            int count = params.length;
            LogUtils.e("doInBackground: " + count);
            int i = 0;
            for (i = 10; i <= params[0].intValue(); i += 10) {
                try {
                    //休眠1秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 更新进度条（0-100）
                publishProgress(i);
            }
            return (i - 10) + "";
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            mAsyncTv.setText("异步操作执行结束" + result);
        }


        //该方法运行在UI线程当中,并且运行在UI线程当中可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
            mAsyncTv.setText("开始执行异步线程，请稍等");
        }


        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            int vlaue = values[0];
            mAsyncProgress.setProgress(vlaue);
        }

    }
}
