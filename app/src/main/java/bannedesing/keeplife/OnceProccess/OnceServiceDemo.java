package bannedesing.keeplife.OnceProccess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/1/3.
 */

public class OnceServiceDemo extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startlog();
        return START_STICKY;
    }
    private int count = 0;
    public void startlog(){

        new Thread(new Runnable() {
            @Override
            public void run() {
              while (true){
                  count++;
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  System.out.println("输出："+count);
              }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("输出：onDestroy");
        Intent intent = new Intent();
        getApplication().sendBroadcast(intent);
    }



}
