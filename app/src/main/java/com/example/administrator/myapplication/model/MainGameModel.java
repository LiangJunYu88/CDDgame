package com.example.administrator.myapplication.model;

import java.util.ArrayList;

enum color{
    club,diamond,heart,spade
}
public class MainGameModel {
    static private MainGameModel mainGameModelInstance;
    private MainGameModel()
    {
        //生成牌堆
        createNewCardDeck();
    }


    public ArrayList<card> cardDeck;//用于存储卡牌的牌堆
    private void createNewCardDeck()
    {
        cardDeck.clear();
        color c = color.club;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c=color.diamond;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c=color.heart;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c=color.spade;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
    }
    private void distributeCards()
    {
        //TODO:分发手牌到三位玩家手上
    }
    public MainGameModel getMainGameModel()
    {
        if(mainGameModelInstance == null)
        {
            mainGameModelInstance = new MainGameModel();
        }
        return mainGameModelInstance;
    }
}


class card
{
    private color cardColor;
    private int cardNumber;


    public color getCardColor() {
        return cardColor;
    }


    public int getCardNumber() {
        return cardNumber;
    }

    public card(color c, int n) //设置为public以便显示图像时读取
    {
        cardNumber = n;
        cardColor = c;
    }
}

class playerObject
{
    //TODO:创建玩家的类型
}