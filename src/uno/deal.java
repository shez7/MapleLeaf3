package uno;

import java.util.ArrayList;

/**
 *
 * @author MapleLeaf
 * 
 * Reference Code: https://github.com/chillhumanoid/uno.git
 */
public class deal extends cardHandler{
    private ArrayList<Card> hand;
    public deal(){
        hand = new ArrayList<>();
    }
    public void addCard(cardHandler deck){
        hand.add(0,deck.getLast());
        deck.removeLast();
    }
    public void addCard(uno.Card addCard){
        hand.add(addCard);
    }
    public void removeCard(int elem){
        hand.remove(elem);
    }
    public Card getCard(int elem){
        return hand.get(elem);
    }
    @Override
    public int getSize(){
        return hand.size();
    }
    public void printArray(){
        System.out.println(hand.toString());
    }
    public Card getLast(){
        return hand.get(hand.size()-1);
    }
}
