import java.util.ArrayList;


/**
 * Models a standard 52 card deck
 * @author Nic Ferencko
 *
 */
public class DeckOfCards
{
    ArrayList <Card> cardDeck;

    /**
     * Creates a 52 card unshuffled deck
     */
    public DeckOfCards()
    {
        this.cardDeck = new ArrayList(52);

        for(int i = Card.CLUBS; i <= Card.SPADES; i++)
        {
            for(int j = Card.ACE; j <= Card.KING; j++)
            {
                Card c = new Card(j,i);
                this.cardDeck.add(c);
            }
        }
    }

    /**
     * Resets deck back to full 52 card unshuffled deck
     */
    public void resetDeck()
    {
        this.cardDeck = new ArrayList(52);

        for(int i = Card.CLUBS; i <= Card.SPADES; i++)
        {
            for(int j = Card.ACE; j <= Card.KING; j++)
            {
                Card c = new Card(j,i);
                this.cardDeck.add(c);
            }
        }
    }

    /**
     * Removes top card from the deck and returns it
     * @return Card at the top of the deck
     */
    public Card takeCard()
    {
        Card c = cardDeck.remove(0);
        return c;
    }

    /**
     * Resets the deck and shuffles it
     */
    public void shuffle()
    {
        this.resetDeck();

        // Create a new array list shuffleDeck

        ArrayList <Card> shuffledDeck = new ArrayList(52);


        // pick a card from a random location from this.cardDeck and add it to shuffleDeck
        while(this.cardDeck.size()>0)
        {
            double r = Math.random()*(this.cardDeck.size());
            int indexToRemove = (int)r;

            shuffledDeck.add(this.cardDeck.remove(indexToRemove));
        }
        this.cardDeck = shuffledDeck;
    }










    public static void main(String[] args)
    {
        DeckOfCards d = new DeckOfCards();

        for(int i = Card.CLUBS; i <= Card.SPADES; i++)
        {
            for(int j = Card.ACE; j <= Card.KING; j++)
            {
                System.out.print(d.takeCard()+" ");
            }
            System.out.println();
        }
        System.out.println();
        d = new DeckOfCards();

        d.shuffle();

        for(int i = Card.CLUBS; i <= Card.SPADES; i++)
        {
            for(int j = Card.ACE; j <= Card.KING; j++)
            {
                System.out.print(d.takeCard()+" ");
            }
            System.out.println();
        }
    }

    /*

     /*
    //method for testing blackjack
    public void createPlayerBlackJack(){
        cardDeck.add(0, new Card(Card.ACE, Card.CLUBS));
        cardDeck.add(2, new Card(Card.KING, Card.CLUBS));

    }

    public void createDealerBlackJack(){
        cardDeck.add(1, new Card(Card.ACE, Card.HEARTS));
        cardDeck.add(3, new Card(Card.KING, Card.HEARTS));
    }

    public void createBothBlackJack(){
        cardDeck.add(0, new Card(Card.ACE, Card.CLUBS));
        cardDeck.add(2, new Card(Card.KING, Card.CLUBS));
        cardDeck.add(1, new Card(Card.ACE, Card.HEARTS));
        cardDeck.add(3, new Card(Card.KING, Card.HEARTS));
    }
    */

}
