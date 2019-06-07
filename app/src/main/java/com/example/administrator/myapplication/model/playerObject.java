package com.example.administrator.myapplication.model;

import java.util.ArrayList;

public class playerObject
{
    public playerObject()
    {
        cardsInHand =new ArrayList<card>();
        playerId = 0;
       //无参构造方法给bot继承
    }
    public playerObject(String name)
    {
        //为填入玩家姓名预留接口
        playerName = name;
    }

    public String playerName;
    public ArrayList<card> cardsInHand;
    public int playerId;


}
