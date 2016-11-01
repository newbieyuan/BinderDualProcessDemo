package com.yy.binderdualprocessdemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

public class RemoteService extends Service {
    private MyBinder binder;
    private MyConn myConn;

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (null == myConn){
            myConn = new MyConn();
        }
        this.bindService(new Intent(this,LocalService.class),myConn,BIND_IMPORTANT);
    }

    class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public void getString(String name) throws RemoteException {
        }
    }


    class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //绑定进程异常结束时调用
            Toast.makeText(RemoteService.this,"本地进程被杀死",Toast.LENGTH_SHORT).show();
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),myConn,BIND_IMPORTANT);
        }
    }

}
