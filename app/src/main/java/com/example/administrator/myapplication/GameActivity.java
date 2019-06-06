package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.administrator.myapplication.model.*;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.myapplication.model.MainGameModel;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ArrayList<cardImageView> playerHandCards;
    private FrameLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
        layout = findViewById(R.id.cardLayout);
        playerHandCards = new ArrayList<>();
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


    private void paintCards(card mcard,int num){
        playerHandCards.add(new cardImageView(this,mcard,num));
        layout.addView(playerHandCards.get(playerHandCards.size()-1));
    }

    public void backMenu(View view){
      finish();
    }
}
