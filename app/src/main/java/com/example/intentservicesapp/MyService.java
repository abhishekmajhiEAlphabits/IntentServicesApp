package com.example.intentservicesapp;

import static android.app.Service.START_STICKY;
import static android.content.ContentValues.TAG;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends IntentService {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN = 0;
    private final int MAX = 100;

    public MyService() {
        super(MyService.class.getSimpleName());
    }

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder mBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    /*new thread is automatically created and service runs on it.
    No need to explicitly create a separate thread.--Feature of intent services which diffrentiates it from normal services

     */
    //UI Thread is allowed to do other ui and small tasks
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mIsRandomGeneratorOn = true;
        startRandomNumberGenerator();
    }

    //onDestroy method of service calls stopRandomNumberGenerator() to stop generating number
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.i(TAG, "Service Destroyed");
    }


    //random number generator method
    private void startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000);
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;
                    Log.i(TAG, "Random number : " + mRandomNumber);

                }
            } catch (InterruptedException e) {
                Log.i(TAG, "Thread Interrupted");
            }
        }
    }

    //stop number generator method
    private void stopRandomNumberGenerator() {
        mIsRandomGeneratorOn = false;
    }

    public int getRandomNumber() {
        return mRandomNumber;
    }
}
