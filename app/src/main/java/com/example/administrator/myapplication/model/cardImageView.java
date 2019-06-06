package com.example.administrator.myapplication.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.AttributedCharacterIterator;

public class cardImageView extends View implements cardImage {
    private Paint paint;
    private Bitmap mbitmap;
    private card  mcard;
    private Rect src;
    private RectF des;
    boolean isClicked;
    public cardImageView(Context contest,card c) {
        super(contest);
        this.mcard = c;
        paint = new Paint();
        src = new Rect();
        des = new RectF();
        isClicked = false;
        mbitmap = BitmapFactory.decodeResource(contest.getResources(), cardImage[mcard.getCardColor()][mcard.getCardNumber()-3]);
    }

    @Override
        protected void onDraw(Canvas canvas) {
    src.set(0,0,mbitmap.getWidth(),mbitmap.getHeight());
    des.set(0,0,100,100);
    canvas.drawBitmap(mbitmap,src,des,paint);
    }
}
