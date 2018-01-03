package bannedesing.keeplife.OnceProccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/3.
 */

public class OneceStartServiceReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,OnceServiceDemo.class);
        System.out.println("输出OnceServiceDemo:"+i.getStringExtra("message"));
        Toast.makeText(context,"OnceServiceDemo",Toast.LENGTH_LONG).show();
        context.startService(i);
    }
}
