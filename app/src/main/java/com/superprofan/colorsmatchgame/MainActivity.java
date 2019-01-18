package com.superprofan.colorsmatchgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView iv_button, iv_arrow;
    TextView tv_points;

    Handler handler;
    Runnable runnable;
    ProgressBar progressBar;

    Random r;

    private final static int STATE_BLUE =1;
    private final static int STATE_RED =2;
    private final static int STATE_YELLOW =3;
    private final static int STATE_GREEN =4;

    int buttonState = STATE_BLUE;
    int arrowState = STATE_BLUE;

    int currentTime = 4000;
    int startTime = 4000;

    int currentPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_button = findViewById(R.id.iv_button);
        iv_arrow = findViewById(R.id.iv_arrow);
        tv_points = findViewById(R.id.tv_points);
        progressBar = findViewById(R.id.progressBar);



        //set the initial progressbar time to 4 seconds
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText("Points: " + currentPoints);

        //generate random arrow color at the start of the game
        r = new Random();
        arrowState = r.nextInt(4) + 1;
        setArrowImage(arrowState);

        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rotate the button with the colors
                setButtonImage(setButtonPosition(buttonState));
            }
        });

        //main game loop
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentTime = currentTime-100;
                progressBar.setProgress(currentTime);
                //show progress
                if(currentTime >0){
                    handler.postDelayed(runnable,100);
                }else{
                    //check if the colors are the same
                    if (buttonState == arrowState){
                        //increase and show poits
                        currentPoints +=1;
                        tv_points.setText("Points: "+ currentPoints);

                        //make the speed higher after every turn, make it 2 seconds when it's 1 second
                        startTime = startTime-100;
                        if(startTime <1000){
                            startTime = 2000;
                        }
                        progressBar.setMax(startTime);
                        currentTime = startTime;
                        progressBar.setProgress(currentTime);

                        //generate new color of the arrow
                        arrowState = r.nextInt(4)+1;
                        setArrowImage(arrowState);

                        handler.postDelayed(runnable, 100);
                    }else{
                        iv_button.setEnabled(false);
                        Toast.makeText(MainActivity.this, "GAMEOVER!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        handler.postDelayed(runnable,100);

    }
//display the arrow color according to the generated nubmer
    private void setArrowImage(int state) {
        switch(state){
            case STATE_BLUE:
                iv_arrow.setImageResource(R.drawable.ic_blue);
                arrowState = STATE_BLUE;
                break;
            case STATE_RED:
                iv_arrow.setImageResource(R.drawable.ic_red);
                arrowState = STATE_RED;
                break;
            case STATE_YELLOW:
                iv_arrow.setImageResource(R.drawable.ic_yellow);
                arrowState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                iv_arrow.setImageResource(R.drawable.ic_green);
                arrowState = STATE_GREEN;
                break;
        }
    }
//rotate animation of the button when clicked
   private void setRotation(final ImageView image, final int drawable){
        //rotate 90 degrees
       RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
       rotateAnimation.setDuration(100);
       rotateAnimation.setInterpolator(new LinearInterpolator());
       rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
            image.setImageResource(drawable);
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
       image.startAnimation(rotateAnimation);
   }
   //set button colors position 1-4
   private int setButtonPosition(int position){
       position = position+1;
       if(position==5){
           position = 1;
       }
       return position;

   }
   //display the button colors positions
   private void setButtonImage (int state){
       switch(state){
           case STATE_BLUE:
               setRotation(iv_button, R.drawable.ic_button_blue);
               buttonState = STATE_BLUE;
               break;
           case STATE_RED:
               setRotation(iv_button, R.drawable.ic_button_purple);
               buttonState = STATE_RED;
               break;
           case STATE_YELLOW:
               setRotation(iv_button, R.drawable.ic_button_yellow);
               buttonState = STATE_YELLOW;
               break;
           case STATE_GREEN:
               setRotation(iv_button, R.drawable.ic_button_green);
               buttonState = STATE_GREEN;
               break;
       }
   }
}
