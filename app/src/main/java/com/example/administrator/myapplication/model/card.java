package com.example.administrator.myapplication.model;

public class card
{
    private color cardColor;
    private int cardNumber;


    public int getCardColor() {
        int temp=0;
        switch (this.cardColor){
            case diamond:
                temp = 0;
                break;
            case club:
                temp = 1;
                break;
            case heart:
                temp = 2;
                break;
            case spade:
                temp = 3;
                break;
        }
        return temp;
    }


    public int getCardNumber() {
        return cardNumber;
    }

    public card(color c, int n) //设置为public以便显示图像时读取
    {
        cardNumber = n;
        cardColor = c;
    }

  public  enum color{
        club,diamond,heart,spade
    }
}
