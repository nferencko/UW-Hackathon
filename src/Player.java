public class Player {

    private double myBalance;  // balance amount
    private String myName;
    private BlackJackHand myHand;
    private double myBet;

    private boolean myCanHit;

    public Player(double initBalance, String theName){
        myBalance = initBalance;
        myName = theName;
        myHand = new BlackJackHand();
        myBet = 0.0;

        myCanHit = true;

    }

    public Player(){
        final double defaultBalance = 100.0;
        final String defaultName = "Player";

        this(defaultBalance, defaultName);
    }

    public String getName(){
        return myName;
    }

    public double getBalance(){
        return myBalance;
    }

    public BlackJackHand getHand(){
        return myHand;
    }

    public void setBalance(double newBalance){
        myBalance = newBalance;
    }

    public void incrementBalance(double deltaBalance){
        if( myBalance + deltaBalance < 0){
            throw new IllegalArgumentException();
        }
        myBalance = myBalance + deltaBalance;
    }

    public boolean setBet(double theBet){
        if( theBet > myBalance){
            return false;
        }

        myBalance -= theBet;
        myBet = theBet;

        return true;
    }

    public double getBet(){
        return myBet;
    }


    public boolean canHit(){
        return myCanHit;
    }

    public boolean isBust(){
        return myHand.getValue() > 21;
    }

    public boolean hasBlackJack(){
        return myHand.isBlackJack();
    }

    public void hit(Card c){
        myHand.add(c);
    }

    public void stand(){
        myCanHit = false;
    }

    public boolean doubleDown(){
        if(myBet > myBalance){
            return false;
        }

        myBalance -= myBet;
        myBet += myBet;
        return true;
    }

    public void resetPlayer(){
        myHand = new BlackJackHand();
        myBet = 0;
        myCanHit = true;

    }

    @Override
    public String toString(){
        return "[Balance = " + myBalance + ", Bet = " + myBet + "]";
    }


}
