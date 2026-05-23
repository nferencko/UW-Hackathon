import java.util.*;

/**
 * This models a black jack hand
 * @author Nic Ferencko
 *
 */
public class BlackJackHand
{
    private ArrayList <Card> hand;

    /**
     * Create an empty black jack hand
     */
    public BlackJackHand()
    {
        this.hand = new ArrayList<>();
    }

    /**
     * @return number of cards in the black jack hand
     */
    public int size()
    {
        return this.hand.size();
    }


    /**
     * This class takes Card c and adds it to players hand
     * @param c Takes a new card
     */
    public void add(Card c)
    {
        this.hand.add(c);
    }

    /**
     * @return The value of this black jack hand
     */
    public int getValue()
    {
        boolean hasAce = false;
        int value = 0;
        // Go through the hand and up the cards
        for(int i=0; i<this.size(); i++)
        {
            Card c = this.hand.get(i);
            int cardValue = 0;
            // If it's a face card, the value is 10. Otherwse value is the rank
            if(c.getRank()>10)
            {
                cardValue = 10;
            }
            else
            {
                cardValue = c.getRank();
            }
            // Check if we have an ace
            if(c.getRank()==Card.ACE)
            {
                hasAce = true;
            }
            value += cardValue;
        }
        // If we have an ace, check if it should be counted as an 11
        if(hasAce == true && value+10<=21)
        {
            value = value+10;  // 1+10 = 11
        }

        return value;
    }

    /**
     * @return True if this hand is black jack (exactly 2 card adds up to 21)
     */
    public boolean isBlackJack()
    {
        if(this.size()!=2)
        {
            return false;
        }
        if(this.getValue()==21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * If you call this method on a hand with less than two cards, an error will happen
     * @return a string representation of the dealer's first two cards with the first
     * card hidden
     */
    public String dealerView()
    {
        String s ="[*-*] " + this.hand.get(1);

        return s;
    }

    /**
     * @return A string representation of this black jack hand
     */
    public String toString()
    {
        String s = "";
        for(int i=0; i<this.size(); i++)
        {
            Card c = this.hand.get(i);
            s = s + c + " ";
        }
        return s;
    }

    /**
     * @return The list of cards currently in this hand.
     */
    public ArrayList<Card> getCards() {
        return this.hand;
    }



    // Tester for this class
    public static void main(String[] args)
    {
        BlackJackHand b = new BlackJackHand();
        b.add(new Card(5, Card.DIAMONDS));
        b.add(new Card(Card.ACE, Card.HEARTS));
        b.add(new Card(Card.ACE, Card.CLUBS));

        System.out.print(b + " " + b.getValue());
        System.out.println(": isBlackJack = " + b.isBlackJack());

        BlackJackHand c = new BlackJackHand();

        System.out.println("Testing Empty Hand");
        System.out.print(c + " " + c.getValue());
        System.out.println(": isBlackJack = " + c.isBlackJack());
        System.out.println("End Test");

        c.add(new Card(Card.ACE, Card.CLUBS));
        //c.add(new Card(10, Card.DIAMONDS));
        //System.out.print(c + " " + c.getValue());
        //System.out.println(": isBlackJack = " + c.isBlackJack());

        //System.out.println(c.dealerView());
        //System.out.println(b.dealerView());

        c.add(new Card(5, Card.HEARTS));
        System.out.println(c);
        System.out.println(c.getValue());

    }
}