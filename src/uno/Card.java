package uno;

/**
 *
 * @author MapleLeaf
 * Reference Code: https://github.com/chillhumanoid/uno.git
 */
public class Card {
    private int cardNum;
    private char cardCol;
    
    public Card(int cNumber, char cColor){
        cardNum = cNumber;
        cardCol = cColor;
    }
    public int getCardNum(){
        return cardNum;
    }
    public char getCardCol(){
        return cardCol;
    }
    public static char getCardCol(Card cards){
        return cards.getCardCol();
    }
    public static int getCardNum(Card cards){
        return cards.getCardNum();
    }
    @Override
    public String toString(){
        String cColor = null;
        String cNumber = null;
        if(cardCol == 'r'){
            cColor = "Red";
        }else if(cardCol == 'b'){
            cColor = "Blue";
        }else if(cardCol == 'y'){
            cColor = "Yellow";
        }else if(cardCol == 'g'){
            cColor = "Green";
        }else if(cardCol =='a'){
            cColor = "Any";
        }
        if(cardNum <= 9){
            cNumber = Integer.toString(cardNum);
        }else if(cardNum == 10){
            cNumber = "Skip";
        }else if(cardNum == 11){
            cNumber = "Draw Two";
        }else if(cardNum == 12){
            cNumber = "Reverse";
        }else if(cardNum == 13){
            cNumber = "Wild";
        }else if(cardNum == 14){
            cNumber = "Wild Draw Four";
        }
        return (cColor + " - " + cNumber);
    }
}
