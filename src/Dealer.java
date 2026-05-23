/**
 * Models a BlackJack Dealer.  The dealer is like a player, but doesn't have
 * money, doesn't make bets, etc.
 *
 * @author Nic Ferencko
 */
public class Dealer {

    /** the Dealer's BlackJack hand */
    private BlackJackHand myHand;

    /** True when the dealer's hand is in a state that allows them to take another card */
    private boolean myCanHit;

    /**
     * Create a Dealer with an empty BlackJack Hand
     */
    public Dealer(){
        myHand = new BlackJackHand();
        myCanHit = myHand.getValue() < 17;
    }

    /** Get this Dealer's Hand */
    public BlackJackHand getHand(){
        return myHand;
    }

    /**
     *
     * @return true if this Dealer can take another card, false otherwise
     */
    public boolean canHit(){
        return myCanHit;
    }

    /**
     * Reset this Dealer's Hand
     */
    public void resetDealer(){
        myHand = new BlackJackHand();
        myCanHit = true;
    }

    /**
     * Add a Card to this Dealer's hand
     * @param c the Card being added
     */

    public void hit(Card c){

        if( !myCanHit ){
            throw new IllegalCallerException("Dealer in state where they cannot hit.");
        }

        myHand.add(c);
        myCanHit = myHand.getValue() < 17;
    }

    /**
     *
     * @return true if this Dealer's hand is BlackJack
     */
    public boolean hasBlackJack(){
        return myHand.isBlackJack();
    }

    /**
     *
     * @return true if this Dealer's hand is a bust (greater than 21)
     */
    public boolean isBust(){
        return myHand.getValue() > 21;
    }
}
