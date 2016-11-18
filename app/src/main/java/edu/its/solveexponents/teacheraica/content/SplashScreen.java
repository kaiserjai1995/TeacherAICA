package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import edu.its.solveexponents.teacheraica.R;

/**
 * Created by jairus on 8/9/16.
 */

public class SplashScreen extends Activity {

    private ProgressBar mProgress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);

        Thread timerThread = new Thread(){
            public void run(){
                doWork();
                startApp();
                finish();
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private void doWork() {
        for(int progress = 0; progress < 100; progress += 10)
            try{
                Thread.sleep(500);
                mProgress.setProgress(progress);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void startApp() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
