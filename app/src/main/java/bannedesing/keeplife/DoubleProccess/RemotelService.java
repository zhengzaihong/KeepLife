package bannedesing.keeplife.DoubleProccess;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import bannedesing.keeplife.IMyAidlInterface;
import bannedesing.keeplife.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class RemotelService extends Service {
    private RemotelService.RemoteServiceConn remoteServiceConn;
    private RemotelService.MyLocalBinder localBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        remoteServiceConn = new RemotelService.RemoteServiceConn();
        localBinder = new RemotelService.MyLocalBinder();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(RemotelService.this);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setContentTitle("ssss");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentInfo("14");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,2000,intent,startId);
        builder.setContentIntent(pendingIntent);
        startForeground(startId,builder.build());
        startlog();
        return START_STICKY;
    }
    class MyLocalBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }


    class RemoteServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            RemotelService.this.startService(new Intent(RemotelService.this,LocalService.class));
            RemotelService.this.bindService(new Intent(RemotelService.this,LocalService.class),remoteServiceConn, Context.BIND_IMPORTANT);


        }
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
                    System.out.println("输出：RemotelService"+count);
                }
            }
        }).start();
    }
}
