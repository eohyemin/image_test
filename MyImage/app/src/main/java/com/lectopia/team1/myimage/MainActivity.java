package com.lectopia.team1.myimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentPosition = 0;
    int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iv1 = (ImageView) findViewById(R.id.imageView); // 익명 inner 클래스에서 접근 가능 -final

        Button btnPrev = (Button) findViewById(R.id.btnPrev);
        Button btnNext = (Button) findViewById(R.id.btnNext);


        final int[] images = {R.drawable.contents1, R.drawable.contents2, R.drawable.contents3, R.drawable.contents4,
                R.drawable.contents5, R.drawable.contents6, R.drawable.contents7, R.drawable.content8, R.drawable.contents9};


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition == 0) {
                    currentPosition = images.length - 1;
                }
                else {
                    currentPosition --;
                }
                iv1.setImageResource(images[currentPosition]);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition == images.length - 1) {
                    currentPosition = 0;
                }
                else {
                    currentPosition ++;
                }
                iv1.setImageResource(images[currentPosition]);
            }
        });

        iv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getX() > view.getX() + view.getWidth() / 2) {
                    if (num < images.length - 1) {
                        num++;
                    } else {
                        num = 0;
                    }
                    iv1.setImageResource(images[num]);
                } else {
                    if (num > 0) {
                        num--;
                    } else {
                        num = images.length - 1;
                    }
                    iv1.setImageResource(images[num]);
                }
                return false;

            }
        });

    }
}
