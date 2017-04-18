package com.example.face;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by 雪无痕 on 2017/4/18.
 */

public class FaceView extends ImageView{
    private Paint mPaint;
    private Rect mRect;//矩形
    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.mRect!=null){
            canvas.drawRect(mRect,mPaint);
            Log.d("jjy", "onDraw: ");
        }
    }
    public void drawFace(Rect rect){
        this.mRect=rect;
        invalidate();
    }
}
