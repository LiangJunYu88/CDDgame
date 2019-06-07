package com.example.administrator.myapplication.model;

import java.util.ArrayList;
import java.util.Collections;
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
    public  ArrayList<card> cardOnDesk;//存储桌上的牌
    public  int lastShownPlayerIndex;//上一位出牌的玩家
    public CardsType lastShownCardsType;//上一轮打出的牌型
    public playerObject playerInstance;//人类玩家的玩家对象
    public botObject[] botInstance;//电脑玩家


    //方法
    private MainGameModel() {
        cardDeck = new ArrayList<>();
        botInstance = new botObject[3];
        cardDeck = new ArrayList<>();
        cardOnDesk = new ArrayList<>();
        //创建玩家与电脑玩家的对象
        lastShownCardsType = CardsType.None;
        playerInstance = new playerObject();
        for (int i = 1; i <= 3; i++)
            botInstance[i - 1] = new botObject(i);
    }

    public static void destroy(){
        mainGameModelInstance = null;
    }

    public void ini() {
        //生成牌堆
        createNewCardDeck();
        //发牌
        distributeCards();
        //控制轮次
        if (lastShownPlayerIndex!=0){
            cardOnDesk.add(new card(card.color.diamond,3));
            lastShownCardsType=CardsType.Single;
        }

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

    //playerInstance 用来判定玩家出牌还是bot出牌
    public boolean showCards(playerObject playerInstance, ArrayList<card> selectCards, ArrayList<Integer> selectCardsIndex) {
        //手牌大于5重选
        if (selectCards.size() > 5) return false;
        //第一个出牌者的牌要有方块三
        if (lastShownCardsType == CardsType.None) {
            boolean hasThree = false;
            for (int i = 0; i < selectCards.size(); i++) {
                if (selectCards.get(i).getCardColor() == 0 && selectCards.get(i).getCardNumber() == 3)
                    hasThree = true;
            }
            if (!hasThree)
                return false;
        }
        //给selectCards排序
        for (int i = 0; i < selectCards.size() - 1; i++)
            for (int j = 0; j < selectCards.size() - i - 1; j++) {
                if (selectCards.get(j).getCardNumber() > selectCards.get(j + 1).getCardNumber()) {
                    Collections.swap(selectCards, j, j + 1);
                    Collections.swap(selectCardsIndex, j, j + 1);
                }
                //数字相同排花色
                if (selectCards.get(j).getCardNumber() == selectCards.get(j + 1).getCardNumber()) {
                    if (selectCards.get(j).getCardColor() > selectCards.get(j + 1).getCardColor()) {
                        Collections.swap(selectCards, j, j + 1);
                        Collections.swap(selectCardsIndex, j, j + 1);
                    }
                }
            }
        //判断牌型
        switch (selectCards.size()) {
            case 1:
                if (lastShownCardsType == CardsType.None || lastShownPlayerIndex == playerInstance.playerId) {
                    lastShownCardsType = CardsType.Single;
                    playerInstance.cardsInHand.remove(selectCards.get(0));//移除手牌
                    cardOnDesk.clear();
                    cardOnDesk.add(selectCards.get(0));
                    lastShownPlayerIndex = this.playerInstance.playerId;//修改上一个出牌玩家
                    return true;
                } else if (lastShownCardsType == CardsType.Single) {
                    if (selectCards.get(0).compareTo(cardOnDesk.get(0)) == 1) {

                        lastShownCardsType = CardsType.Single;
                        playerInstance.cardsInHand.remove(selectCards.get(0));//移除手牌
                        cardOnDesk.clear();
                        cardOnDesk.add(selectCards.get(0));
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        return true;
                    }
                }
                break;
            case 2:
                if (lastShownCardsType == CardsType.None || lastShownPlayerIndex == playerInstance.playerId) {
                    if (selectCards.get(0).getCardNumber() != selectCards.get(1).getCardNumber())
                        return false;
                    lastShownCardsType = CardsType.Double;
                    cardOnDesk.clear();
                    for (int i = 0; i < 2; i++) {
                        playerInstance.cardsInHand.remove( selectCards.get(i));
                        cardOnDesk.add(selectCards.get(i));
                    }
                    lastShownPlayerIndex = this.playerInstance.playerId;

                    return true;
                } else if (lastShownCardsType == CardsType.Double) {
                    if (selectCards.get(0).getCardNumber() != selectCards.get(1).getCardNumber())
                        return false;
                    if (selectCards.get(1).compareTo(cardOnDesk.get(1)) == 1) {
                        cardOnDesk.clear();
                        for (int i = 0; i < 2; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        lastShownPlayerIndex = this.playerInstance.playerId;

                        return true;
                    }
                }
                break;
            case 3:
                if (lastShownCardsType == CardsType.None || lastShownPlayerIndex == playerInstance.playerId) {
                    if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber()) {
                        lastShownCardsType = CardsType.Triple;
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        cardOnDesk.clear();
                        for (int i = 0; i < 3; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                } else if (lastShownCardsType == CardsType.Triple) {
                    if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber()) {
                        if (selectCards.get(2).compareTo(cardOnDesk.get(2)) == 1) {
                            cardOnDesk.clear();
                            lastShownPlayerIndex = this.playerInstance.playerId;
                            for (int i = 0; i < 3; i++) {
                                playerInstance.cardsInHand.remove(selectCards.get(i));
                                cardOnDesk.add(selectCards.get(i));
                            }
                            return true;
                        }
                    }
                }
                break;
            case 4:
                if (lastShownCardsType == CardsType.None || lastShownPlayerIndex == playerInstance.playerId) {
                    if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(3).getCardNumber()) {
                        lastShownCardsType = CardsType.Quad;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 4; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                } else if (lastShownCardsType == CardsType.Quad) {
                    if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber() &&
                            selectCards.get(0).getCardNumber() == selectCards.get(3).getCardNumber()) {
                        if (selectCards.get(3).compareTo(cardOnDesk.get(3)) == 1) {
                            cardOnDesk.clear();
                            lastShownPlayerIndex = this.playerInstance.playerId;
                            for (int i = 0; i < 4; i++) {
                                playerInstance.cardsInHand.remove( selectCards.get(i));
                                cardOnDesk.add(selectCards.get(i));
                            }
                            return true;
                        }
                    }
                }
                break;
            case 5:
                if (lastShownCardsType == CardsType.None || lastShownPlayerIndex == playerInstance.playerId) {
                    if (isString(selectCards) && isSameColor(selectCards)) {
                        lastShownCardsType = CardsType.SameColorString;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 5; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                    if (isFourPlusOne(selectCards)) {
                        lastShownCardsType = CardsType.FourPlusOne;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 5; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                    if (isThreePlusTwo(selectCards)) {
                        lastShownCardsType = CardsType.ThreePlusTwo;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 5; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                    if (isSameColor(selectCards)) {
                        lastShownCardsType = CardsType.SameColor;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 5; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                    if (isString(selectCards)) {
                        lastShownCardsType = CardsType.String;
                        cardOnDesk.clear();
                        lastShownPlayerIndex = this.playerInstance.playerId;
                        for (int i = 0; i < 5; i++) {
                            playerInstance.cardsInHand.remove( selectCards.get(i));
                            cardOnDesk.add(selectCards.get(i));
                        }
                        return true;
                    }
                } else switch (lastShownCardsType) {
                    case SameColorString:
                        if (isString(selectCards) && isSameColor(selectCards)) {
                            if (selectCards.get(4).compareTo(cardOnDesk.get(4)) == 1) {
                                cardOnDesk.clear();
                                lastShownCardsType = CardsType.SameColorString;
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove(selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                                return true;
                            }
                        }
                        break;
                    case FourPlusOne:
                        if (isFourPlusOne(selectCards)) {
                            if (selectCards.get(2).compareTo(cardOnDesk.get(2)) == 1) {
                                cardOnDesk.clear();
                                lastShownCardsType = CardsType.FourPlusOne;
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove( selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                                return true;
                            }
                        }
                        break;
                    case ThreePlusTwo:
                        if (isThreePlusTwo(selectCards)) {
                            if (selectCards.get(2).compareTo(cardOnDesk.get(2)) == 1) {
                                cardOnDesk.clear();
                                lastShownCardsType = CardsType.ThreePlusTwo;
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove( selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                                return true;
                            }
                        }
                        break;
                    case SameColor:
                        if (isThreePlusTwo(selectCards)) {
                            if (selectCards.get(4).getCardColor() > cardOnDesk.get(4).getCardColor()) {
                                cardOnDesk.clear();
                                lastShownCardsType = CardsType.ThreePlusTwo;
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove( selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                                return true;
                            }
                            //花色相同比最大的牌
                            if (selectCards.get(4).getCardColor() == cardOnDesk.get(4).getCardColor() && selectCards.get(4).getCardNumber() > cardOnDesk.get(4).getCardNumber()) {
                                lastShownCardsType = CardsType.ThreePlusTwo;
                                cardOnDesk.clear();
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove( selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                                return true;
                            }
                        }
                        break;
                    case String:
                        if (isString(selectCards)) {
                            cardOnDesk.clear();
                            if (selectCards.get(4).compareTo(cardOnDesk.get(4).getCardNumber()) == 1) {
                                lastShownPlayerIndex = this.playerInstance.playerId;
                                lastShownCardsType = CardsType.String;
                                for (int i = 0; i < 5; i++) {
                                    playerInstance.cardsInHand.remove( selectCards.get(i));
                                    cardOnDesk.add(selectCards.get(i));
                                }
                            }
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }

    public boolean isString(ArrayList<card> selectCards) {
        int start = selectCards.get(0).getCardNumber();
        //牌为A2345
        if (selectCards.get(4).getCardNumber() == 15 &&
                selectCards.get(0).getCardNumber() == 3 &&
                selectCards.get(1).getCardNumber() == 4 &&
                selectCards.get(2).getCardNumber() == 5 &&
                selectCards.get(3).getCardNumber() == 14
        ) return true;
        //A不能出现在头尾之外的地方
        if (selectCards.get(1).getCardNumber() == 14 || selectCards.get(2).getCardNumber() == 14 || selectCards.get(3).getCardNumber() == 14)
            return false;

        for (int i = 1; i < 5; i++) {
            if ((++start) != selectCards.get(i).getCardNumber())
                return false;
        }
        return true;
    }
    public void pass(){

    }
    public boolean isSameColor(ArrayList<card> selectCards) {
        int color = selectCards.get(0).getCardColor();
        for (int i = 1; i < 5; i++) {
            if (color != selectCards.get(i).getCardColor())
                return false;
        }
        return true;
    }

    //葫芦比第三张牌大小
    public boolean isThreePlusTwo(ArrayList<card> selectCards) {
        if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber())
            if (selectCards.get(3).getCardNumber() == selectCards.get(4).getCardNumber()) {
                return true;
            }
        if (selectCards.get(2).getCardNumber() == selectCards.get(3).getCardNumber() &&
                selectCards.get(4).getCardNumber() == selectCards.get(2).getCardNumber())
            if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber()) {
                return true;
            }
        return false;
    }

    public boolean isFourPlusOne(ArrayList<card> selectCards) {
        if (selectCards.get(0).getCardNumber() == selectCards.get(1).getCardNumber() &&
                selectCards.get(0).getCardNumber() == selectCards.get(2).getCardNumber() &&
                selectCards.get(0).getCardNumber() == selectCards.get(3).getCardNumber())
            return true;
        if (selectCards.get(4).getCardNumber() == selectCards.get(1).getCardNumber() &&
                selectCards.get(4).getCardNumber() == selectCards.get(2).getCardNumber() &&
                selectCards.get(4).getCardNumber() == selectCards.get(3).getCardNumber())
            return true;
        return false;
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
    public int getBotCards(){
        return this.cardsInHand.size();
    }
    BotType botType;
}