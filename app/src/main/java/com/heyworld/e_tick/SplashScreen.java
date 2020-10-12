package com.heyworld.e_tick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.heyworld.e_tick.Login.LoginPage;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";
    private int SLEEP_TIME = 3;

    //onCreate
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);

        requestWindowFeature (Window.FEATURE_NO_TITLE);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**Because we want to run the full screen before showing the content*/
        setContentView (R.layout.splash_screen);
        getSupportActionBar ().hide ();

        LogoLauncher logoLauncher = new LogoLauncher ();
        logoLauncher.start ();
    }
    //onCreate

    //logo launcher class
    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep (1000 * SLEEP_TIME);
            }catch (InterruptedException e){
                e.printStackTrace ();
            }
            startActivity (new Intent (SplashScreen.this, LoginPage.class));
            SplashScreen.this.finish ();
        }
    }
    //logo launcher class


}//Main