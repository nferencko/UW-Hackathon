import javax.swing.JOptionPane;

public class BlackJackController {
    private BlackJackView view;
    private DeckOfCards deck;
    private Player player;
    private Dealer dealer;

    public BlackJackController(BlackJackView view, Player player, Dealer dealer) {
        this.view = view;
        this.player = player;
        this.dealer = dealer;
        this.deck = new DeckOfCards();

        // Attach event listeners
        this.view.getDealButton().addActionListener(e -> startNewHand());
        this.view.getHitButton().addActionListener(e -> playerHit());
        this.view.getStandButton().addActionListener(e -> playerStand());
        this.view.getDoubleButton().addActionListener(e -> playerDoubleDown());

        // Initial UI state
        this.view.setTurnState(false);
        updateView(true); // show initial balance
    }

    private void startNewHand() {

        // Setup models
        deck.shuffle();
        player.resetPlayer();
        dealer.resetDealer();
        // Notice: Your Player/Dealer classes instantiate new BlackJackHands inside constructors,
        // but to reset them perfectly per hand, you'll need to call player.resetPlayer()
        // and adjust Dealer to have a reset method, or instantiate new Player/Dealer objects.

        String betStr = JOptionPane.showInputDialog(view, "Enter your bet:", "Place Bet", JOptionPane.QUESTION_MESSAGE);
        if (betStr == null) return;

        try {
            double bet = Double.parseDouble(betStr);
            if (!player.setBet(bet)) {
                JOptionPane.showMessageDialog(view, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println(player);


        // End of testing methods

        // Deal initial cards
        player.hit(deck.takeCard());
        dealer.hit(deck.takeCard());
        player.hit(deck.takeCard());
        dealer.hit(deck.takeCard());

        // checkForBlackJack();

        if( !checkForBlackJack()){
            view.setTurnState(true);
            view.setGameStatus("Your turn. Hit or Stand?");
            updateView(false); // keep dealer card hidden
        }
        else{
            view.setTurnState(false);
            updateView(true);
        }

    }

    private void playerHit() {
        player.hit(deck.takeCard());
        if (player.isBust()) {
            view.setGameStatus("You busted! Dealer wins.");
            endHand(0); // Player loses bet
        } else {
            updateView(false);
        }
        this.view.getDoubleButton().setEnabled(false);
    }

    private void playerStand() {
        view.setTurnState(false);
        // Dealer logic loop
        while (dealer.canHit()) {
            dealer.hit(deck.takeCard());
        }
        determineWinner();
    }

    private void playerDoubleDown() {
        if (player.doubleDown()) {
            player.hit(deck.takeCard());
            if (player.isBust()) {
                view.setGameStatus("Busted on Double Down! Dealer wins.");
                endHand(0);
            } else {
                playerStand(); // Automatically stands after 1 hit on double down
            }
        } else {
            JOptionPane.showMessageDialog(view, "Not enough money to double down!");
        }
    }

    /**
     * Check if dealer or player has BlackJack and if so, call endHand.
     *
     * @return true if either player or dealer has Blackjack
     */
    private boolean checkForBlackJack(){

        boolean isBlackJack = false;

        if(player.hasBlackJack() && dealer.hasBlackJack()){
            view.setGameStatus("Push! Both dealer and player have blackjack");
            endHand(1.0);
            isBlackJack = true;
        }
        else if(player.hasBlackJack()){
            view.setGameStatus("You have blackjack!");
            endHand(2.5);
            isBlackJack = true;
        }
        else if(dealer.hasBlackJack()){
            view.setGameStatus("Dealer has blackjack.");
            endHand(0.0);
            isBlackJack = true;
        }

        return isBlackJack;
    }

    private void determineWinner() {
        int pValue = player.getHand().getValue();
        int dValue = dealer.getHand().getValue();

        if (dealer.isBust()) {
            view.setGameStatus("Dealer busts! You win!");
            endHand(2.0); // Wins 2x bet back
        } else if (pValue > dValue) {
            view.setGameStatus("You win!");
            endHand(2.0);
        } else if (pValue < dValue) {
            view.setGameStatus("Dealer wins.");
            endHand(0);
        } else {
            view.setGameStatus("Push! (Tie)");
            endHand(1.0); // Gets original bet back
        }
    }

    private void endHand(double payoutMultiplier) {
        view.setTurnState(false);
        // Assuming you track current bet to calculate payout increment
        player.incrementBalance(payoutMultiplier*player.getBet());
        // player.incrementBalance(...) logic goes here
        updateView(true); // reveal dealer cards
    }

    private void updateView(boolean revealDealer) {
        view.setPlayerStats(player.getBalance(), player.getBet()); // Update with actual bet tracking variable
        view.updatePlayerCards(player.getHand().toString());
        view.setScores(
                revealDealer ? String.valueOf(dealer.getHand().getValue()) : "??",
                player.getHand().getValue()
        );

        if (revealDealer) {
            view.updateDealerCards(dealer.getHand().toString());
        } else {
            view.updateDealerCards(dealer.getHand().dealerView());
        }
    }
}
