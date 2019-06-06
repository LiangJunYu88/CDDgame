package com.example.administrator.myapplication.model;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


public class cardImageView extends View implements cardImage {
    private Paint paint;
    private Bitmap mbitmap;
    private card  mcard;
    private int top = 50;
    private Rect src;
    private RectF des;
    int num;
    boolean isClicked;
    public cardImageView(Context context,card c, int num) {
        super(context);
        this.mcard = c;
        this.num = num;
        paint = new Paint();
        src = new Rect();
        des = new RectF();
        isClicked = false;
        mbitmap = BitmapFactory.decodeResource(context.getResources(), cardImage[mcard.getCardNumber()-3][mcard.getCardColor()]);
        System.out.println("created");
    }
    public void click(){
        if (isClicked) {
            top -= 50;
            this.isClicked = !this.isClicked;
        }
        else {
            top += 50;
            this.isClicked = !this.isClicked;
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(mbitmap,200+100*(num-1),top,paint);
    }

}
