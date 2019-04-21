package uno;

import static java.lang.Thread.sleep;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import static uno.Uno.checkUno;
import static uno.Uno.getComputerChoice;
import static uno.Uno.wildColor;
import static uno.Uno.wildComputerColor;

/**
 * Reference Code: https://github.com/chillhumanoid/uno.git
 * @author MapleLeaf
 */
public class Uno {

    static Random rn = new Random();
    static deal play1 = new deal();//
    static deal computer1 = new deal();
    static deal computer2 = new deal();
    static deal computer3 = new deal(); 
    static cardHandler decks = new cardHandler();
    static Scanner s = new Scanner(System.in);
    static Scanner si = new Scanner(System.in);
    static deal discardPile = new deal();
    static int numComp = 0;
    static boolean reverse = false; 
    static boolean skip = false; 
    static boolean gameEnd = false; 
    static boolean draw2 = false;
    static boolean draw4 = false; 

    public static void main(String[] args) throws InterruptedException {
        decks.shuffleDecks();
        decks.shuffleDecks();
        boolean comNum = false;
        do {
            try {
                System.out.print("How many computers players?(1-3): ");
                numComp = si.nextInt();
                si.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                si.nextLine();
            }
            if (numComp <= 3 && numComp >= 1) {
                comNum = true;
            } else {
                System.out.println("Please enter a number between 1 and 3");
                System.out.println();
                comNum = false;
            }
        } while (!comNum);
        if (numComp == 1) {
            for (int i = 0; i <= 6; i++) { 
                play1.addCard(decks);
                computer1.addCard(decks);
            }
        } else if (numComp == 2) {
            for (int i = 0; i <= 6; i++) {
                play1.addCard(decks);
                computer1.addCard(decks);
                computer2.addCard(decks);
            }
        } else if (numComp == 3) {
            for (int i = 0; i <= 6; i++) {
                play1.addCard(decks);
                computer1.addCard(decks);
                computer2.addCard(decks);
                computer3.addCard(decks);
            }
        }
        discardPile.addCard(decks);//
        if (Card.getCardNum(discardPile.getLast()) == 14) { 
            discardPile.addCard(wildColor(14));
        } else if (Card.getCardNum(discardPile.getLast()) == 13) {
            discardPile.addCard(wildColor(13));
        }
        int currentPlayer = 1; 
        boolean uno = false; 
        do { 
            while (currentPlayer == 1) { 
                skip = false; 
                draw2 = false; 
                draw4 = false; 
                sleep(1500);
                boolean unoCalled = false; 
                int cardPlayed = 0; 
                System.out.println();
                int choice = 0; 
                Boolean drawCard = false; 
                do {
                    uno = checkUno(play1); 
                    System.out.println(); 
                    printHand(play1, drawCard, uno, unoCalled); 
                    System.out.println(); 
                    printDiscard();
                    choice = getCardNumber();
                    while (choice > play1.getSize() + 2 || choice < 0) { 
                        System.out.println("Invalid Input");
                        choice = getCardNumber();
                    }
                    int elem = choice - 1; 
                    if (choice == (play1.getSize() + 1)) {
                        if (!drawCard) { 
                            play1.addCard(decks);
                            drawCard = true; 
                        } else if (drawCard) {
                            cardPlayed = 1;
                        }
                    } else if (choice == (play1.getSize() + 2)) {
                        if (!unoCalled) { 
                            System.out.println(); 
                            System.out.println("Player 1 Calls Uno");
                            unoCalled = true;
                        } else if (unoCalled || !uno) {
                            System.out.println();
                            System.out.println("Please Select a Valid Card");
                        }
                    } else if (Card.getCardCol(play1.getCard(elem)) == 'a'
                            && Card.getCardNum(play1.getCard(elem)) == 13) { 
                        discardPile.addCard(wildColor(13)); 
                        play1.removeCard(elem);
                        cardPlayed = 1;
                    } else if (Card.getCardCol(play1.getCard(elem)) == 'a' && Card.getCardNum(play1.getCard(elem)) == 14) { 
                        discardPile.addCard(wildColor(14));
                        draw4 = true;
                        play1.removeCard(elem);
                        cardPlayed = 1;
                    } else if (Card.getCardCol(play1.getCard(elem)) == Card.getCardCol
        (discardPile.getLast()) || Card.getCardNum(play1.getCard(elem)) == 
                            Card.getCardNum(discardPile.getLast())) {
                        discardPile.addCard(play1.getCard(elem));
                        switch (Card.getCardNum(play1.getCard((elem)))) {
                            case 10:
                                skip = true;
                                break;
                            case 11:
                                draw2 = true;
                                break;
                            case 12:
                                if (reverse) {
                                    reverse = false;
                                } else if (!reverse) {
                                    reverse = true;
                                }
                                break;
                            default:
                                break;
                        }
                        play1.removeCard(elem);
                        cardPlayed = 1;
                    } else {
                        System.out.println();
                        System.out.println("Please Select a valid card");
                    }
                } while (cardPlayed == 0);
                if (play1.getSize() == 1 && !unoCalled) {
                    System.out.println("Player 1 did not call uno. +2");
                    play1.addCard(decks);
                    play1.addCard(decks);
                }
                if (play1.getSize() > 1 && unoCalled) {
                    System.out.println("Player 1 falsely called uno. +2");
                    play1.addCard(decks);
                    play1.addCard(decks);
                }
                if (play1.getSize() == 0) {
                    System.out.println();
                    System.out.println("Player 1 won");
                    gameEnd = true;
                    System.exit(0);
                }
                if (numComp == 1) {
                    if (reverse && skip || !reverse && skip) {
                        currentPlayer = 1;
                    } else if (reverse && !skip || !reverse && !skip) {
                        currentPlayer = 2;
                        if (draw2) {
                            draw2(computer1);
                        }
                        if (draw4) {
                            draw4(computer1);
                        }
                    }
                } else if (numComp == 2) { //PLAYER 1 CURRENTLY
                    if (reverse && skip) {
                        currentPlayer = 2;
                    } else if (!reverse && skip) {
                        currentPlayer = 3;
                    } else if (reverse && !skip) {
                        currentPlayer = 3;
                        if (draw2) {
                            draw2(computer2);
                        }
                        if (draw4) {
                            draw4(computer2);
                        }
                    } else if (!reverse && !skip) {
                        currentPlayer = 2;
                        if (draw2) {
                            draw2(computer1);
                        }
                        if (draw4) {
                            draw4(computer1);
                        }
                    }
                } else if (numComp == 3) { //PLAYER 1
                    if (reverse && skip || !reverse && skip) {
                        currentPlayer = 3;
                    } else if (!reverse && !skip) {
                        currentPlayer = 2;
                        if (draw2) {
                            draw2(computer1);
                        }
                        if (draw4) {
                            draw4(computer1);
                        }
                    } else if (reverse && !skip) {
                        currentPlayer = 4;
                        if (draw2) {
                            draw2(computer3);
                        }
                        if (draw4) {
                            draw4(computer3);
                        }
                    } else {
                        System.out.println("ERROR");
                    }
                }
                checkDraw(decks, discardPile);
                System.out.println();
                break;
            }
            while (currentPlayer == 2) {
                int compNumber = 1;
                computerPlay(computer1, skip, draw2, draw4, uno, reverse, gameEnd, compNumber);
                if (numComp == 1) { //PLAYER 2
                    if (reverse && skip || !reverse && skip) {
                        currentPlayer = 2;
                    } else if (reverse && !skip || !reverse && !skip) {
                        currentPlayer = 1;
                        if (draw2) {
                            draw2(play1);
                        }
                        if (draw4) {
                            draw4(play1);
                        }
                    }
                } else if (numComp == 2) { //PLAYER 2 CURRENTLY
                    if (reverse && skip) {
                        currentPlayer = 3;
                    } else if (!reverse && skip) {
                        currentPlayer = 1;
                    } else if (reverse && !skip) {
                        currentPlayer = 1;
                        if (draw2) {
                            draw2(play1);
                        }
                        if (draw4) {
                            draw4(play1);
                        }
                    } else if (!reverse && !skip) {
                        currentPlayer = 3;
                        if (draw2) {
                            draw2(computer2);
                        }
                        if (draw4) {
                            draw4(computer2);
                        }
                    }
                } else if (numComp == 3) {
                    if (reverse && skip || !reverse && skip) {
                        currentPlayer = 4;
                    } else if (!reverse && !skip) {
                        currentPlayer = 3;
                        if (draw2) {
                            draw2(computer2);
                        }
                        if (draw4) {
                            draw4(computer2);
                        }
                    } else if (reverse && !skip) {
                        currentPlayer = 1;
                        if (draw2) {
                            draw2(play1);
                        }
                        if (draw4) {
                            draw4(play1);
                        }
                    }
                }
                checkDraw(decks, discardPile);
                break;

            }
            while (currentPlayer == 3) {
                int compNumber = 2;
                computerPlay(computer2, skip, draw2, draw4, uno, reverse, gameEnd, compNumber);
                if (numComp == 2) { //COMPUTER 2 CURRENTLY
                    if (reverse && skip) {
                        currentPlayer = 3;
                    } else if (!reverse && skip) {
                        currentPlayer = 1;
                    } else if (reverse && !skip) {
                        currentPlayer = 2;
                        if (draw2) {
                            draw2(computer1);
                        }
                        if (draw4) {
                            draw4(computer1);
                        }
                    } else if (!reverse && !skip) {
                        currentPlayer = 1;
                        if (draw2) {
                            draw2(computer3);
                        }
                        if (draw4) {
                            draw4(computer3);
                        }
                    }
                } else if (numComp == 3) { //player 3
                    if (reverse && skip || !reverse && skip) {
                        currentPlayer = 1;
                    } else if (!reverse && !skip) {
                        currentPlayer = 4;
                        if (draw2) {
                            draw2(computer3);
                        }
                        if (draw4) {
                            draw4(computer3);
                        }
                    } else if (reverse && !skip) {
                        currentPlayer = 2;
                        if (draw2) {
                            draw2(computer1);
                        }
                        if (draw4) {
                            draw4(computer1);
                        }
                    }
                }
                checkDraw(decks, discardPile);
                break;

            }
            while (currentPlayer == 4) {
                int compNumber = 3;
                computerPlay(computer3, skip, draw2, draw4, uno, reverse, gameEnd, compNumber);
                if (reverse && skip || !reverse && skip) {
                    currentPlayer = 2;
                } else if (!reverse && !skip) {
                    currentPlayer = 1;
                    if (draw2) {
                        draw2(computer1);
                    }
                    if (draw4) {
                        draw4(computer1);
                    }
                } else if (reverse && !skip) {
                    currentPlayer = 3;
                    if (draw2) {
                        draw2(computer3);
                    }
                    if (draw4) {
                        draw4(computer3);
                    }
                }
                checkDraw(decks, discardPile);
                break;

            }
        } while (!gameEnd);
    }

    public static void printHand(deal play1, boolean drawCard, boolean uno, boolean unoCalled) {
        int display = 0;
        for (int x = 0; x < play1.getSize(); x++) {
            display = x + 1;
            System.out.println(display + ". " + play1.getCard(x));
        }
        display++;
        if (!drawCard) {
            System.out.println(display + ". Draw Card");
        } else if (drawCard) {
            System.out.println(display + ". End Turn");
        }
        display++;
        if (uno && !unoCalled) {
            System.out.println(display + ". Uno");
        } else if (uno && unoCalled) {
            System.out.println("Uno Called");
        }
    }

    public static boolean checkUno(deal play) {
        boolean uno = false;
        if (play.getSize() == 2) {
            uno = true;
        } else {
            uno = false;
        }
        return uno;
    }

    public static Card wildColor(int cardNumber) {
        System.out.print("What color do you want the decks to be?: ");
        String input = s.nextLine();
        char color = 'a';
        input = input.toLowerCase();
        switch (input.charAt(0)) {
            case 'b':
                color = 'b';
                break;
            case 'r':
                color = 'r';
                break;
            case 'g':
                color = 'g';
                break;
            case 'y':
                color = 'y';
                break;
            default:
                System.out.println("Type: (b)lue, (g)reen, (r)ed or (y)ellow");
                wildColor(cardNumber);
                break;
        }
        return new Card(cardNumber, color);
    }

    public static void draw2(deal play) {
        for (int i = 0; i <= 1; i++) {
            play.addCard(decks);
        }
    }

    public static void draw4(deal play) {
        for (int i = 0; i <= 3; i++) {
            play.addCard(decks);
        }
    }

    public static void printDiscard() {
        System.out.print("Discard Pile: ");
        System.out.println(discardPile.getLast());
    }

    public static void checkDraw(cardHandler decks, deal discardPile) {
        if (decks.getSize() <= 4) {
            System.out.println();
            System.out.println("Shuffling Deck");
            System.out.println();
            for (int i = 0; i < discardPile.getSize(); i++) {
                if (Card.getCardNum(discardPile.getLast()) == 13 && Card.getCardCol(discardPile.getLast()) != 'a') {
                    discardPile.removeCard(discardPile.getSize() - 1);
                    discardPile.addCard(new Card(13, 'a'));
                } else if (Card.getCardNum(discardPile.getLast()) == 14 && Card.getCardCol(discardPile.getLast()) != 'a') {
                    discardPile.removeCard(discardPile.getSize() - 1);
                    discardPile.addCard(new Card(14, 'a'));
                } else {
                    decks.addCard(discardPile.getLast());
                    discardPile.removeCard(discardPile.getSize() - 1);
                }
            }
            decks.shuffleDecks();
        } else {

        }

    }

    public static int getComputerChoice(Card dCard, deal Computer, boolean unoCalled) {
        int choice = 0;
        boolean hWild = false;
        boolean hSkip = false;
        boolean hReverse = false;
        boolean hDTwo = false;
        boolean hDFour = false;
        boolean hPlayable = false;
        hWild = hasWild(dCard, Computer);
        hSkip = hasSkip(dCard, Computer);
        hReverse = hasReverse(dCard, Computer, 0);
        hDTwo = hasDrawTwo(dCard, Computer, 0);
        hDFour = hasDrawFour(dCard, Computer, 0);
        hPlayable = hasPlayable(dCard, Computer);
        if (Computer.getSize() == 2 && !unoCalled) {
            choice = Computer.getSize() + 2;
        } else if (hDTwo) {
            choice = findDTwo(dCard, Computer);
            choice++;
        } else if (hSkip) {
            choice = findSkip(Computer, dCard);
            choice++;
        } else if (hReverse) {
            choice = findReverse(dCard, Computer);
            choice++;
        } else if (hPlayable) {
            choice = findPlayable(dCard, Computer);
            choice++;
        } else if (hWild && !hDTwo && !hSkip && !hReverse && !hPlayable) {
            choice = findWild(Computer);
            choice++;
        } else if (hDFour && !hWild && !hDTwo && !hSkip && !hReverse && !hPlayable) {
            choice = findDrawFour(Computer);
            choice++;
        } else {
            choice = Computer.getSize() + 1;
        }
        return choice;
    }

    public static int findWild(deal Computer) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 13) {
                elem = i;
            }
        }
        return elem;
    }

    public static int findDTwo(Card dCard, deal Computer) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 11 && Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                elem = i;
            }
        }
        return elem;
    }

    public static int findSkip(deal Computer, Card dCard) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 10 && Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                elem = i;
            }
        }
        return elem;
    }

    public static int findReverse(Card dCard, deal Computer) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 12 && Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                elem = i;
            }
        }
        return elem;
    }

    public static int findDrawFour(deal Computer) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 14) {
                elem = i;
            }
        }
        return elem;
    }

    public static int findPlayable(Card dCard, deal Computer) {
        int elem = 0;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) < 10 && 
                    Card.getCardCol(Computer.getCard(i)) 
                    == Card.getCardCol(dCard)) {
                elem = i;
            }
        }
        return elem;
    }

    public static boolean hasWild(Card dCard, deal Computer) {
        boolean hWild = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 13) {
                hWild = true;
            }
        }
        return hWild;
    }

    public static boolean hasSkip(Card dCard, deal Computer) {
        boolean hSkip = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 10 && Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                hSkip = true;
            }
        }
        return hSkip;
    }

    public static boolean hasReverse(Card dCard, deal Computer, int x) {
        boolean hReverse = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 12 && Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                hReverse = true;
            }
        }
        return hReverse;
    }

    public static boolean hasDrawTwo(Card dCard, deal Computer, int x) {
        boolean hDTwo = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 11 && 
                    Card.getCardCol(Computer.getCard(i)) == Card.getCardCol(dCard)) {
                hDTwo = true;
            }
        }
        return hDTwo;
    }

    public static boolean hasDrawFour(Card dCard, deal Computer, int x) {
        boolean hDrawFour = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) == 14) {
                hDrawFour = true;
            }
        }
        return hDrawFour;
    }

    public static boolean hasPlayable(Card dCard, deal Computer) {
        boolean hPlayable = false;
        for (int i = 0; i < Computer.getSize(); i++) {
            if (Card.getCardNum(Computer.getCard(i)) < 10 &&
                    Card.getCardCol(Computer.getCard(i)) == 
                    Card.getCardCol(dCard)) {
                hPlayable = true;
            }
        }
        return hPlayable;
    }

    public static Card wildComputerColor(int cardNumber, deal computer) {
        int blue = 0;
        int red = 0;
        int yellow = 0;
        int green = 0;
        char cColor = 'a';
        for (int i = 0; i < computer.getSize(); i++) {
            if (Card.getCardCol(computer.getCard(i)) == 'b') {
                blue++;
            } else if (Card.getCardCol(computer.getCard(i)) == 'r') {
                red++;
            } else if (Card.getCardCol(computer.getCard(i)) == 'y') {
                yellow++;
            } else if (Card.getCardCol(computer.getCard(i)) == 'g') {
                green++;
            }
        }
        if (blue > green && blue > yellow && blue > red) {
            cColor = 'b';
        } else if (green > blue && green > yellow && green > red) {
            cColor = 'g';
        } else if (yellow > green && yellow > blue && yellow > red) {
            cColor = 'y';
        } else if (red > green && red > yellow && red > blue) {
            cColor = 'r';
        } else {
            cColor = 'b';
        }
        return new Card(cardNumber, cColor);
    }

    public static void computerPlay(deal comp, boolean skip,
            boolean draw2, boolean draw4, boolean uno, boolean reverse,
            boolean gameEnd, int compNumber) throws InterruptedException {
        skip = false;
        int special = 0;
        draw2 = false;
        draw4 = false;
        int choice = 0;
        int cardPlayed = 0; //this is similar to a boolean. 1's and 0's ya know. 
        boolean drawCard = false;
        boolean unoCalled = false;
        do {//do this while card played = 0
            sleep(2000);
            uno = checkUno(comp);
            Card dCard = discardPile.getLast();
            choice = getComputerChoice(dCard, comp, unoCalled);
            int elem = choice - 1;
            if (choice == (comp.getSize() + 1)) {
                if (!drawCard) {
                    System.out.println("Computer " + compNumber + " has drawn a card");
                    comp.addCard(decks);
                    drawCard = true;
                } else if (drawCard) {
                    System.out.println("Computer " + compNumber + " has ended their turn without playing a card");
                    cardPlayed = 1;
                }
            } else if (choice == (comp.getSize() + 2)) {
                if (!unoCalled) {
                    System.out.println("Computer " + compNumber + " Calls Uno");
                    unoCalled = true;
                }
            } else if (Card.getCardNum(comp.getCard(elem)) == 13) {
                discardPile.addCard(wildComputerColor(13, comp));
                System.out.println("Computer " + compNumber + " played " + discardPile.getLast());
                comp.removeCard(elem);
                cardPlayed = 1;
            } else if (Card.getCardNum(comp.getCard(elem)) == 14) {
                discardPile.addCard(wildComputerColor(14, comp));
                System.out.println("Computer " + compNumber + " played " + discardPile.getLast());
                draw4 = true;
                comp.removeCard(choice - 1);
                cardPlayed = 1;
            } else if (Card.getCardCol(comp.getCard(elem)) ==
                    Card.getCardCol(discardPile.getLast()) || 
                    Card.getCardNum(comp.getCard(choice - 1)) == 
                    Card.getCardNum(discardPile.getLast())) {
                discardPile.addCard(comp.getCard(elem));
                switch (Card.getCardNum(comp.getCard(elem))) {
                    case 10:
                        skip = true;
                        break;
                    case 11:
                        draw2 = true;
                        break;
                    case 12:
                        if (reverse) {
                            reverse = false;
                        } else if (!reverse) {
                            reverse = true;
                        } else {

                        }
                        break;
                    default:
                        break;
                }
                System.out.println("Computer " + compNumber + " played " + discardPile.getLast());
                comp.removeCard(choice - 1);
                cardPlayed = 1;
            }
        } while (cardPlayed == 0);
        if (comp.getSize() == 1 && !unoCalled) {
            System.out.println("Computer " + compNumber + " did not call uno. +2");
            comp.addCard(decks);
            comp.addCard(decks);
        }
        if (comp.getSize() == 0) {
            System.out.println();
            System.out.println("Computer " + compNumber + " won");
            gameEnd = true;
            System.exit(0);
        }
        if (reverse && skip) {
            special = 1;
        } else if (!reverse && skip) {
            special = 2;
        } else if (reverse && !skip) {
            if (draw2) {
                special = 4;
            } else if (draw4) {
                special = 5;
            } else {
                special = 3;
            }
        } else if (!reverse && !skip) {
            if (draw2) {
                special = 7;
            } else if (draw4) {
                special = 8;
            } else {
                special = 6;
            }
        }
    }

    public static int getCardNumber() {
        int choice = 0;
        do {
            try {
                System.out.print("Which card do you want to play: ");
                choice = si.nextInt();
                si.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                si.nextLine();
            }
        } while (choice == 0);
        return choice;
    }
}
