package com.example.administrator.myapplication.model;

import java.util.ArrayList;
import java.util.Random;

enum BotType
{
    PASSIVE, AGGRESSIVE
}
enum CardsType
{
    Single,Double,Triple,Quad,
    String,SameColor,ThreePlusTwo,FourPlusOne,SameColorString, //五张
    None
}

public class MainGameModel {
    //属性
    static private MainGameModel mainGameModelInstance;
    public ArrayList<card> cardDeck;//用于存储卡牌的牌堆
    public ArrayList<card> cardOnDesk;//存储桌上的牌
    public int lastShownPlayerIndex;
    public CardsType lastShownCardsType;
    public playerObject playerInstance;//人类玩家的玩家对象
    public botObject[] botInstance;//电脑玩家


    //方法
    private MainGameModel() {
        cardDeck = new ArrayList<>();
        botInstance = new botObject[3];
        cardDeck = new ArrayList<>();
        cardOnDesk = new ArrayList<>();
        //创建玩家与电脑玩家的对象
        playerInstance = new playerObject();
        for (int i = 1; i <= 3; i++)
            botInstance[i] = new botObject(i);
    }

    public void ini() {
        //生成牌堆
        createNewCardDeck();
        //发牌
        distributeCards();
        //@TODO:判断第一个出牌的玩家，如果不是人类玩家出牌，则应先执行BOT出牌
    }

    private void createNewCardDeck() {

        cardDeck.clear();
        card.color c = card.color.club;
        for (int i = 3; i <= 15; i++)
            cardDeck.add(new card(c, i));
        c = card.color.diamond;
        for (int i = 3; i <= 15; i++)
            cardDeck.add(new card(c, i));
        c = card.color.heart;
        for (int i = 3; i <= 15; i++)
            cardDeck.add(new card(c, i));
        c = card.color.spade;
        for (int i = 3; i <= 15; i++)
            cardDeck.add(new card(c, i));
    }

    private void distributeCards() {
        Random rand = new Random();
        //给玩家发牌
        for (int i = 0; i < 13; i++) {
            int currentCardsLeft = cardDeck.size();
            int index = rand.nextInt(currentCardsLeft);
            if (cardDeck.get(index).getCardColor() == 0 && cardDeck.get(index).getCardNumber() == 3)
                lastShownPlayerIndex = 0;
            playerInstance.cardsInHand.add(cardDeck.get(index));
            cardDeck.remove(index);
        }
        //给BOT发牌
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 13; j++) {
                int currentCardsLeft = cardDeck.size();
                int index = rand.nextInt(currentCardsLeft);
                if (cardDeck.get(index).getCardColor() == 0 && cardDeck.get(index).getCardNumber() == 3)
                    lastShownPlayerIndex = i + 1;
                botInstance[i].cardsInHand.add(cardDeck.get(index));
                cardDeck.remove(index);
            }
        }
    }

    public static MainGameModel getMainGameModel() {
        if (mainGameModelInstance == null) {
            mainGameModelInstance = new MainGameModel();
        }
        return mainGameModelInstance;
    }

    public ArrayList<card> showCards(playerObject playerInstance, int[] indexArray, int cardsSelected) {
        ArrayList<card> cardArrayList = new ArrayList<>();
        int playerCardsLeft = playerInstance.cardsInHand.size();


        boolean isAbleToShow = false;

        for (int i = 0; i < cardsSelected; i++)
            cardArrayList.add(playerInstance.cardsInHand.get(indexArray[i]));


        switch (cardsSelected) {
            case 1:

                if (lastShownPlayerIndex == playerInstance.playerId || lastShownCardsType == CardsType.None) {
                    isAbleToShow = true;
                    lastShownCardsType = CardsType.Single;
                } else if (lastShownCardsType == CardsType.Single) {
                    if (cardArrayList.get(0).compareTo(cardOnDesk.get(0)) == 1) {
                        isAbleToShow = true;
                        lastShownCardsType = CardsType.Single;
                    }
                }
                break;
            case 2:
                if (lastShownPlayerIndex == playerInstance.playerId || lastShownCardsType == CardsType.None) {
                    isAbleToShow = true;
                    lastShownCardsType = CardsType.Double;
                } else if (lastShownCardsType == CardsType.Double) {
                    card newCompareCard;
                    card oldCompareCard;
                    if (cardArrayList.get(0).compareTo(cardArrayList.get(1)) == 1)
                        newCompareCard = cardArrayList.get(0);
                    else newCompareCard = cardArrayList.get(1);
                    if (cardOnDesk.get(0).compareTo(cardOnDesk.get(1)) == 1) oldCompareCard = cardOnDesk.get(0);
                    else oldCompareCard = cardOnDesk.get(1);
                    if (newCompareCard.compareTo(oldCompareCard) == 1) {
                        isAbleToShow = true;
                        lastShownCardsType = CardsType.Double;
                    }
                }
                break;
            case 3:
                if (lastShownPlayerIndex == playerInstance.playerId || lastShownCardsType == CardsType.None) {
                    isAbleToShow = true;
                    lastShownCardsType = CardsType.Triple;
                } else if (lastShownCardsType == CardsType.Triple) {
                    if (cardArrayList.get(0).compareTo(cardOnDesk.get(0)) == 1) {
                        isAbleToShow = true;
                        lastShownCardsType = CardsType.Triple;
                    }
                }
                break;
            case 4:
                if (lastShownPlayerIndex == playerInstance.playerId || lastShownCardsType == CardsType.None) {
                    isAbleToShow = true;
                    lastShownCardsType = CardsType.Quad;
                } else if (lastShownCardsType == CardsType.Quad) {
                    if (cardArrayList.get(0).compareTo(cardOnDesk.get(0)) == 1) {
                        isAbleToShow = true;
                        lastShownCardsType = CardsType.Quad;
                    }
                }
                break;
            case 5:
                if(lastShownPlayerIndex==playerInstance.playerId ||lastShownCardsType == CardsType.None)
                {
                    //同花顺>铁支>葫芦>同花>顺子
                    //所以优先判断同花顺
                    //TODO：判断五张牌牌型
                }
                break;
            default:
                break;
        }
        if (isAbleToShow) {
            cardOnDesk = cardArrayList;
            return cardArrayList;
        } else return null;
    }
}


class botObject extends playerObject
{
    botObject(int id)
    {
        //懒得实现AI了，全被动（只会pass）完事了
        super();
        playerId = id;
        botType = BotType.PASSIVE;
    }

    BotType botType;
}