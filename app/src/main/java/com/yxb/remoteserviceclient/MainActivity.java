package com.yxb.remoteserviceclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import com.yxb.remoteserviceserver.AidlInterface;

/**
 * @author : yuxibing
 * @date : 2020-09-07
 * Describe :远程Service的客户端
 */
public class MainActivity extends AppCompatActivity {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AidlInterface aidlInterface = AidlInterface.Stub.asInterface(service);
            try {
                //通过该对象调用在AidlInterface.aidl文件中定义的接口方法,从而实现跨进程通信
                aidlInterface.aidlService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.yxb.remoteserviceserver.AidlInterface");
                intent.setPackage("com.yxb.remoteserviceserver");
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });
    }
}
