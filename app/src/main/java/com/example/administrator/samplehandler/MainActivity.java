package com.example.administrator.samplehandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * 스레드를 이용해 프로그레스바를 보여주는 방법에 대해 알 수 있습니다.
 * 별도로 만든 스레드에서 메인 스레드를 접근할 때 핸들러를 사용해야 한다는 것을 알 수 있습니다.
 *
 * @author Mike
 */public class MainActivity extends Activity {
    private int i = 0;
    private TextView textViewForindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewForindex = (TextView) findViewById(R.id.itee);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateThread();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Throwable t) {
                    }
                }
            }
        });

        myThread.start();
    }

    private void updateThread() {
        i++;
        textViewForindex.setText(String.valueOf(i));
    }
}

