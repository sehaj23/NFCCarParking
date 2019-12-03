package com.example.sehaj.nfccarparking;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static ProgressBar p1;
    private static TextView t1;
    private ImageView i1, i2;
    LinearLayout one,two;
    Animation uptodown,downtoup;
    private static int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheme(R.style.main);

        one=(LinearLayout) findViewById(R.id.one);
        two=(LinearLayout) findViewById(R.id.two);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        one.setAnimation(uptodown);
        two.setAnimation(downtoup);


//        animation();
//        setAnimationn();
//        animatior();
//
//
        p1 = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    // Update the progress status
                    progressStatus += 1;

                    // Try to sleep the thread for 20 milliseconds
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            p1.setProgress(progressStatus);
                            // Show the progress on TextView
                            t1 = (TextView) findViewById(R.id.textView);
                            t1.setText(progressStatus + "%");
                            if (progressStatus == 100) {
                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                            }


                        }
                    });
                }
            }
        }).start();


    }
}

//    }
//    public void animatior() {
//        ObjectAnimator animatorx = ObjectAnimator.ofFloat(i2, "x", 1200f);
//        animatorx.setDuration(2000);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatorx);
//        animatorSet.start();
//    }
//
//
//    public void animation() {
//        AlphaAnimation animation1 = new AlphaAnimation(0.00f, 1.00f);
//        animation1.setDuration(2000);
//        i1.setAnimation(animation1);
//        i1.startAnimation(animation1);
//    }
//
//    public void setAnimationn() {
//        ((AnimationDrawable) i2.getBackground()).start();
//    }
//}
