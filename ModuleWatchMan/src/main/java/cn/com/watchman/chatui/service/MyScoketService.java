package cn.com.watchman.chatui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.com.watchman.chatui.mysocket.WebSocket;
import cn.com.watchman.chatui.mysocket.WebSocketCall;
import cn.com.watchman.chatui.mysocket.WebSocketListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by 志强 on 2017.6.8.
 */

public class MyScoketService extends Service {
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    Log.i("接收到的消息:", "" + msg.obj.toString());
                    String result=msg.obj.toString();
                    Intent intent=new Intent();
                    intent.setAction("myChatActionFlag");
                    intent.putExtra("myChat",result);
                    sendBroadcast(intent);
                    break;
                case -1:
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        initData();
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        //创建Intent
//        Intent intent = new Intent(this, PushReciver.class);
//        intent.setAction("com.example.clock");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//        //周期触发
//        manager.setRepeating(AlarmManager.RTC, 0, 5 * 1000, pendingIntent);
        super.onCreate();
        Log.d("un", "Service onCreate");


    }

    final static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
            .build();
    String url = "ws://123.56.96.237:12005";

    private void initData() {
        Request request = new Request.Builder().url(url).build();
        WebSocketCall webSocketCall = WebSocketCall.create(mOkHttpClient, request);
        webSocketCall.enqueue(new WebSocketListener() {
            private final ExecutorService sendExecutor = Executors.newSingleThreadExecutor();
            private WebSocket mWebSocket;

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                this.mWebSocket = webSocket;
            }

            /**
             * 连接失败
             * @param e
             * @param response Present when the failure is a direct result of the response (e.g., failed
             * upgrade, non-101 response code, etc.). {@code null} otherwise.
             */
            @Override
            public void onFailure(IOException e, Response response) {
                Log.d("WebSocketCall", "连接失败");
            }

            /**
             * 接收到消息
             * @param message
             * @throws IOException
             */
            @Override
            public void onMessage(final ResponseBody message) throws IOException {
                final String state = message.source().readByteString().utf8();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Message message = new Message();
                        message.obj = state;
                        message.what = 1000;
                        handler2.sendMessage(message);
                    }
                });
                thread.start();
                message.source().close();
            }

            @Override
            public void onPong(Buffer payload) {
                Log.d("WebSocketCall", "onPong:");
            }

            @Override
            public void onClose(int code, String reason) {
                sendExecutor.shutdown();
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }


    @Override
    public void onDestroy() {
        Log.d("un", "Service onDestory");
        Intent i = new Intent("com.rance.chatui");
        sendBroadcast(i);
        super.onDestroy();
    }


}
