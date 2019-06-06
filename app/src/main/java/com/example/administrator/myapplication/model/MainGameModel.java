package com.example.administrator.myapplication.model;

import java.util.ArrayList;
import java.util.Random;

enum BotType
{
    PASSIVE, AGGRESSIVE
}
public class MainGameModel {
    //属性
    static private MainGameModel mainGameModelInstance;
    public ArrayList<card> cardDeck;//用于存储卡牌的牌堆
    public playerObject playerInstance;//人类玩家的玩家对象
    public botObject[] botInstance;//电脑玩家



    //方法
    private MainGameModel()
    {
        cardDeck = new ArrayList<card>();
        botInstance = new botObject[3];
        cardDeck = new ArrayList<>();
        //创建玩家与电脑玩家的对象
        playerInstance = new playerObject();
        for(int i=0;i<3;i++)
            botInstance[i] = new botObject(i);

    }
    public void ini(){
        //生成牌堆
        createNewCardDeck();
        //发牌
        distributeCards();
    }
    private void createNewCardDeck()
    {

        cardDeck.clear();
        card.color c = card.color.club;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c= card.color.diamond;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c= card.color.heart;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
        c= card.color.spade;
        for(int i=3;i<=15;i++)
            cardDeck.add(new card(c,i));
    }
    private void distributeCards()
    {
        Random rand = new Random();
        //给玩家发牌
        for(int i=0;i<13;i++)
        {
            int currentCardsLeft = cardDeck.size();
            int index = rand.nextInt(currentCardsLeft);
            playerInstance.cardsInHand.add(cardDeck.get(index));
            cardDeck.remove(index);
        }
        //给BOT发牌
        for(int i=0;i<3;i++)
        {
            for (int j = 0; j < 13; j++) {
                int currentCardsLeft = cardDeck.size();
                int index = rand.nextInt(currentCardsLeft);
                botInstance[i].cardsInHand.add(cardDeck.get(index));
                cardDeck.remove(index);
            }
        }
    }
    public static MainGameModel getMainGameModel()
    {
        if(mainGameModelInstance == null)
        {
            mainGameModelInstance = new MainGameModel();
        }
        return mainGameModelInstance;
    }
}


class botObject extends playerObject
{    private int botID;
    botObject(int id)
    {
        //懒得实现AI了，全被动（只会pass）完事了
        super();
        botID = id;
        botType = BotType.PASSIVE;
    }

    BotType botType;
}