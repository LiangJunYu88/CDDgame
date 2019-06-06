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
    //点击事件
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if(e.getAction() == MotionEvent.ACTION_UP)
        {
            if(isClicked)//已经被点取
            {
                //向下移动
                scrollBy(0,-20);
                //
                isClicked = false;
            }
            else
            {
                //向上移动
                scrollBy(0,20);
                isClicked = true;
            }
        }


        return true;
    }

      //画出卡牌

    @Override
    protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(mbitmap,200+100*(num-1),50,paint);
    }

}
