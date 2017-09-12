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
 *
 *
 *
 *
 */

/**
 * obtain Message -> send Message -> handle Message 순으로 흘러감.
 * handleMessage 에서 UI의 변경이 가능하다. 메인 스레드가 일을 할거거든!
 *
 */



public class MainActivity extends Activity {
    private static final int UPDATE_NUMBER = 0;
    private static final int UPDATE_NUMBER_BY2 = 1;


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
            switch(msg.what) {
                case UPDATE_NUMBER:
                    i++;
                    textViewForindex.setText(String.valueOf(i));
                    break;
                case UPDATE_NUMBER_BY2:
                    i+=2;
                    textViewForindex.setText(String.valueOf(i));
                    break;
                default :
                    //default condition
                    break;
            }

        }


    };

    @Override
    protected void onStart() {
        super.onStart();

        Thread myThread = new Thread(new Runnable() {
            public void run() {
                for (i = 0; ;){

                    // 이 안에서 아무리 setText를 넣어도 안된다! 핸들러의 메시지를 통해서만 전달 가능.

                    // obtainMessage에서 받은 메시지 코드에 따라서 핸들메시지에서 다르게 행동하게 된다.!!
                    try {
                        Message msg = handler.obtainMessage(UPDATE_NUMBER_BY2);
                        handler.sendMessage(msg);

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

