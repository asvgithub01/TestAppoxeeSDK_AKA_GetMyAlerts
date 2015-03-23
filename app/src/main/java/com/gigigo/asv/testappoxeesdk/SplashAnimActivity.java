package com.gigigo.asv.testappoxeesdk;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gigigo.asv.testappoxeesdk.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashAnimActivity extends Activity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_anim);

        image =(ImageView)findViewById(R.id.imgAnim);
        Animation traslate;


        traslate = AnimationUtils.loadAnimation(this, R.anim.animation);

        traslate.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                image.animate().y(200).setDuration(100).setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        image.animate().y(0).setDuration(100).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                MediaPlayer player  = MediaPlayer.create(SplashAnimActivity.this,R.raw.fin);
                                player.start();
                                //asv redirige al inicio
                                Intent goHome = new Intent(SplashAnimActivity.this, SuscriptionActivity.class);
                                startActivity(goHome) ;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                });
            }
        });
        traslate.reset();
     //   image.setAnimation(traslate);
        image.startAnimation(traslate);


    }

}
