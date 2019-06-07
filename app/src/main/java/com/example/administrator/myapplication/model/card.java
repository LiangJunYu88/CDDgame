package com.example.administrator.myapplication.model;

public class card implements Comparable
{
    private color cardColor;
    private int cardNumber;
    public boolean isSelected;

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
    @Override
    public int compareTo(Object c)
    {
        if(c instanceof card)
        {
            if(this.getCardNumber()>((card) c).getCardNumber())return 1;
            else if(this.getCardNumber()<((card) c).getCardNumber())return -1;
            else if(this.getCardNumber()==((card) c).getCardNumber())
            {
                if(this.getCardColor()<((card) c).getCardColor())
                    return 1;
                if(this.getCardColor()>((card) c).getCardColor())
                    return -1;
            }
        }
        return 0;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public card(color c, int n) //设置为public以便显示图像时读取
    {
        cardNumber = n;
        cardColor = c;
        isSelected = false;
    }

  public  enum color{
        club,diamond,heart,spade
    }
}
