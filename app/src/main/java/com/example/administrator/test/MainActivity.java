package com.example.administrator.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * 撕衣服小案例
 * 2017年12月11日16:46:21
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Bitmap mBitmap_copy;
    private ImageView iv_pre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化iv_pre控件
        iv_pre = (ImageView) findViewById(R.id.iv_pre);

        // 加载原图
        Bitmap bitmap_src = BitmapFactory.decodeResource(this.getResources(), R.drawable.pre19);

        // 创建原图的副本，可以用来作画
        mBitmap_copy = Bitmap.createBitmap(bitmap_src.getWidth(), bitmap_src.getHeight(),
                bitmap_src.getConfig());
        Canvas mCanvas = new Canvas(mBitmap_copy);
        Paint paint = new Paint();
        mCanvas.drawBitmap(bitmap_src, new Matrix(), paint);

        // 将副本加载到iv_pre上
        iv_pre.setImageBitmap(mBitmap_copy);

        // 设置iv_pre的触摸事件，让手指所到之处变透明，显示出底层图片即可
        iv_pre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        for (int i = -30; i <= 30; i++) {
                            for (int j = -30; j <= 30; j++) {
                                if (Math.sqrt(i * i + j * j) < 30) {
                                    try {
                                        mBitmap_copy.setPixel((int) event.getX() + i, (int) event.getY() + j,
                                                Color.TRANSPARENT);
                                    } catch (Exception e) {
                                        // java.lang.IllegalArgumentException: x must be >= 0 不打印异常
                                    }
                                }
                            }
                        }
                        // 图片做了改变，需要更新到iv_pre控件上
                        iv_pre.setImageBitmap(mBitmap_copy);
                        break;
                }
                return true;
            }
        });
    }
}
