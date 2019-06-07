package com.example.administrator.myapplication.model;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class cardImageView extends View implements cardImage {
    //屏幕的宽高
    private int width;
    private int height;
    private Paint paint;
    private Bitmap mbitmap;
    private card mcard;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Rect src;
    private RectF des;
    int num;
    boolean isClicked;
    public cardImageView(Context context,card c, int num) {
        super(context);
        this.mcard = c;
        this.num = num;
        Resources r = this.getResources();
        DisplayMetrics dm = r.getDisplayMetrics();
        paint = new Paint();
        src = new Rect();
        des = new RectF();
        isClicked = false;
        mbitmap = BitmapFactory.decodeResource(context.getResources(), cardImage[mcard.getCardNumber()-3][mcard.getCardColor()]);
        left = 300+100*(num-1);
        top = 800;
        right = left + mbitmap.getWidth();
        bottom = top + mbitmap.getHeight();
        System.out.println("created");
    }
    public card getMcard(){
        return mcard;
    }
    public int getleft(){

        return left;
    }
    public int getright(){
        return right;
    };
    public int gettop(){
        return top;
    }
    public int getbottom(){
        return bottom;
    }
    public boolean getIsClicked(){
        return isClicked;
    }

    //选取卡牌
    public void whenClick() {
            if(isClicked)//已经被点取
            {
                //向下移动
                scrollBy(0,-20);
                //
                isClicked = !isClicked;
                mcard.isSelected = false;
            }
            else
            {
                //向上移动
                scrollBy(0,20);
                isClicked = !isClicked;
                mcard.isSelected = true;
            }


    }
    //出牌时将卡牌移到中间
    public void moveToCenter(int num){
       scrollBy(left -500-(num*100),400);
    }

      //画出卡牌

    @Override
    protected void onDraw(Canvas canvas) {
        src = new Rect(0,0,mbitmap.getWidth(),mbitmap.getHeight());
        des = new RectF(left,top,right,bottom);
        canvas.drawRoundRect(des, 5, 5, paint);
    canvas.drawBitmap(mbitmap,src,des,paint);
    }

}
