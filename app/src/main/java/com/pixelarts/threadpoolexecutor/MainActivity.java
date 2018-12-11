package com.pixelarts.threadpoolexecutor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    private TextView textView1, textView2, textView3, textView4;
    private UIHandler uiHandler;
    private CustomThreadPoolManager customThreadPoolManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.tvThread1);
        textView2 = findViewById(R.id.tvThread2);
        textView3 = findViewById(R.id.tvThread3);
        textView4 = findViewById(R.id.tvThread4);

        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        progressBar3 = findViewById(R.id.progress3);
        progressBar4 = findViewById(R.id.progress4);
        uiHandler = new UIHandler(
                getMainLooper(),
                textView1, textView2, textView3, textView4,
                progressBar1, progressBar2, progressBar3, progressBar4);

        customThreadPoolManager = CustomThreadPoolManager.getInstance();
        customThreadPoolManager.setHandler(uiHandler);

    }

    public void addCallable(View view) {
        customThreadPoolManager.addRunnable();
    }

    public  static class UIHandler extends Handler{
        private WeakReference<TextView> tvFirstThread, tvSecondThread, tvThirdThread, tvForthThread;
        private WeakReference<ProgressBar> firstProgress, secondProgress, thirdProgress, forthProgress;

        public UIHandler(Looper looper, TextView tvFirstThread, TextView tvSecondThread, TextView tvThirdThread, TextView tvForthThread,
                         ProgressBar firstProgress, ProgressBar secondProgress, ProgressBar thirdProgress, ProgressBar forthProgress){

            super(looper);
            this.tvFirstThread = new WeakReference<>(tvFirstThread);
            this.tvSecondThread = new WeakReference<>(tvSecondThread);
            this.tvThirdThread = new WeakReference<>(tvThirdThread);
            this.tvForthThread = new WeakReference<>(tvForthThread);

            this.firstProgress = new WeakReference<>(firstProgress);
            this.secondProgress = new WeakReference<>(secondProgress);
            this.thirdProgress = new WeakReference<>(thirdProgress);
            this.forthProgress = new WeakReference<>(forthProgress);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    setUpUI(msg, tvFirstThread.get(), firstProgress.get());
                break;

                case 2:
                    setUpUI(msg, tvSecondThread.get(), secondProgress.get());
                    break;

                case 3:
                    setUpUI(msg, tvThirdThread.get(), thirdProgress.get());
                    break;

                case 4:
                    setUpUI(msg, tvForthThread.get(), forthProgress.get());
                    break;
            }
        }

        private void setUpUI(Message msg, TextView textView, ProgressBar progressBar){
            textView.setText(msg.getData().getString("message"));
            progressBar.setProgress(msg.getData().getInt("int"));
        }
    }
}
