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
    private ArrayList<cardImageView> deskCards;//牌桌上打出的牌
    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
        layout = findViewById(R.id.layout_game);
        playerHandCards = new ArrayList<>();
        deskCards = new ArrayList<>();
        MainGameModel.getMainGameModel().ini();
        //画出玩家手牌
        for (int i=0;i<13;i++){
            paintHandCards(MainGameModel.getMainGameModel().playerInstance.cardsInHand.get(i),i);
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
    //在牌桌上画出手牌
    private void paintHandCards(card mcard,int num){
        playerHandCards.add(new cardImageView(this,mcard,num));
        layout.addView(playerHandCards.get(playerHandCards.size()-1));

    }
    //画打出的牌
    private void paintShowCards(card mcard,int num){
       deskCards.add(new cardImageView(this,mcard,num));
        layout.addView(deskCards.get(deskCards.size()-1));
        deskCards.get(deskCards.size()-1).moveToCenter(num);
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
        //清空打出的牌
        for (int i = 0; i < deskCards.size(); i++) {
            deskCards.get(i).setVisibility(View.GONE);
        }
        deskCards.clear();
        ArrayList<card> selectedCards = new ArrayList<>();
        ArrayList<Integer> selectedCardsIndex = new ArrayList<>();
        for (int i=0;i < playerHandCards.size();i++){
            if (playerHandCards.get(i).getIsClicked()){
                selectedCards.add(MainGameModel.getMainGameModel().playerInstance.cardsInHand.get(i));
                selectedCardsIndex.add(i);
            }
        }
        if (MainGameModel.getMainGameModel().showCards(MainGameModel.getMainGameModel().playerInstance,selectedCards,selectedCardsIndex)) {

            //重新画手牌
            for (int i = 0; i < playerHandCards.size(); i++) {
                playerHandCards.get(i).setVisibility(View.GONE);
            }
            playerHandCards.clear();
            for (int i = 0; i < MainGameModel.getMainGameModel().playerInstance.cardsInHand.size(); i++) {
                paintHandCards(MainGameModel.getMainGameModel().playerInstance.cardsInHand.get(i), i);
            }
            for (int i=0;i < MainGameModel.getMainGameModel().cardOnDesk.size();i++){
                paintShowCards(MainGameModel.getMainGameModel().cardOnDesk.get(i),i);
            }
        }
        return true;
    }

        //出牌逻辑
    //返回菜单按钮
    public void backMenu(View view){
      finish();
    }
}
