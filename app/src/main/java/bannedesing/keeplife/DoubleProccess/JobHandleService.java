package bannedesing.keeplife.DoubleProccess;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandleService extends JobService {
    private int kJobId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyJobDaemonService", "jobService启动");
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("MyJobDaemonService", "执行了onStartJob方法");
        boolean isLocalServiceWork = isServiceWork(this, "bannedesing.keeplife.DoubleProccess.LocalService");
        boolean isRemoteServiceWork = isServiceWork(this, "bannedesing.keeplife.DoubleProccess.RemotelService");
        if(!isLocalServiceWork||
                !isRemoteServiceWork){


        }
        this.startService(new Intent(this,LocalService.class));
        this.startService(new Intent(this,RemotelService.class));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("MyJobDaemonService", "执行了onStopJob方法");
        scheduleJob(getJobInfo());
        return true;
    }

    //将任务作业发送到作业调度中去
    public void scheduleJob(JobInfo t) {
        Log.i("MyJobDaemonService", "调度job");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo(){
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, JobHandleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        //间隔100毫秒
        builder.setPeriodic(10);
        return builder.build();
    }

    // 判断服务是否正在运行
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(200);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


}