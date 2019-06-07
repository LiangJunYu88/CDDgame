package com.example.administrator.myapplication;

import android.content.pm.ActivityInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.example.administrator.myapplication.model.*;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


import com.example.administrator.myapplication.model.MainGameModel;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ArrayList<cardImageView> playerHandCards;
    public ArrayList<cardImageView> cardOnDesk;//牌桌上打出的牌
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
        layout = findViewById(R.id.layout_game);
        playerHandCards = new ArrayList<>();
        cardOnDesk = new ArrayList<>();
        MainGameModel.getMainGameModel().ini();
        //画出玩家手牌
        for (int i=0;i<13;i++){
            paintCards(MainGameModel.getMainGameModel().playerInstance.cardsInHand.get(i),i);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断是否有焦点

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
    //禁用返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) return true;
            return super.onKeyDown(keyCode,event);
    }
    //每张牌的事件
    @Override
    public boolean onTouchEvent(MotionEvent e){
        if (e.getAction() == MotionEvent.ACTION_UP){
            float x = e.getX();
            float y = e.getY();
            for (int i=0; i<playerHandCards.size();i++){
                if (playerHandCards.get(i).getleft()<x && playerHandCards.get(i).getright()>x && playerHandCards.get(i).gettop()<y && playerHandCards.get(i).getbottom()>y){
                playerHandCards.get(i).whenClick();
                break;
                }
            }
        }
        return true;
    }
    //在牌桌上画出扑克
    private void paintCards(card mCard,int num){
        playerHandCards.add(new cardImageView(this,mCard,num));
        layout.addView(playerHandCards.get(playerHandCards.size()-1));
    }
    //重选按钮
    public void reSelect(View view){
        for (int i=0;i<playerHandCards.size();i++){
            if (playerHandCards.get(i).getIsClicked())
                playerHandCards.get(i).whenClick();
        }
    }
    //出牌按钮
    public boolean showCards(View view) {
        cardImageView temp;
        for (int i = 0; i < cardOnDesk.size(); i++) {
            cardOnDesk.get(i).setVisibility(View.GONE);
        }
        cardOnDesk.clear();

        //计算已选中的手牌数量

        //如果手牌数量数量大于5直接判断无法出牌
        int cardsSelected = 0;
        int[] clickedCardsIndexArray = new int[13];
        for (int i = 0; i < playerHandCards.size(); i++) {
            if (playerHandCards.get(i).getIsClicked()) {
                clickedCardsIndexArray[cardsSelected] = i;
                cardsSelected++;
            }
        }
        if (cardsSelected > 5) return false;

        ArrayList<card> cardsShouldBeOnDesk;


        cardsShouldBeOnDesk = MainGameModel.getMainGameModel().showCards(
                MainGameModel.getMainGameModel().playerInstance, clickedCardsIndexArray, cardsSelected);
        if(cardsShouldBeOnDesk == null) return false;

        //TODO：打出的手牌已经存在GameModel的cardOnDesk数组中，重新绘制
        /*
        for (int i = 0; i < playerHandCards.size(); i++) {
            if (playerHandCards.get(i).getIsClicked()) {
                cardOnDesk.add(playerHandCards.get(i));
                playerHandCards.get(i).moveToCenter(cardOnDesk.size());
                playerHandCards.remove(i);
                i = i - 1;
            }
        }
        */
        return true;
    }

        //出牌逻辑
    //返回菜单按钮
    public void backMenu(View view){
      finish();
    }
}
