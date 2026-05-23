/**
 * This class models a single playing card from a standard deck. Cards have one of four
 * suits(0-3), and a rank(ace = 1, king = 13).
 *
 * @author Nic Ferencko
 *
 */
public class Card {
    private int rank;
    private int suit;

    // Class constants for the suit
    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    // Class constants for the value of the non-numeric cards
    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    /**
     * Creates a Card with a rank and suit
     * @param rank rank of card. Must be 1-13
     * @param suit suit of card. Must be 0-3
     */
    public Card (int rank, int suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return rank of the Card
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * @return suit of the Card
     */
    public int getSuit()
    {
        return suit;
    }

    /**
     * @return String representation of a Card. Can be used to print on the console.
     *
     */
    public String toString()
    {
        String valueString = null;
        String suitString = null;

        if(this.rank == Card.ACE)
        {
            valueString = "A";
        }
        else if (this.rank == Card.JACK)
        {
            valueString = "J";
        }
        else if (this.rank == Card.QUEEN)
        {
            valueString = "Q";
        }
        else if (this.rank == Card.KING)
        {
            valueString = "K";
        }
        else if(this.rank >= 2 && this.rank <= 10)
        {
            valueString = this.rank + "";
        }


        if(this.suit == Card.CLUBS)
        {
            suitString = "C";
        }
        else if(this.suit == Card.DIAMONDS)
        {
            suitString = "D";
        }
        else if(this.suit == Card.HEARTS)
        {
            suitString = "H";
        }
        else if(this.suit == Card.SPADES)
        {
            suitString = "S";
        }

        return "[" + valueString + "-" + suitString + "]";
    }
}