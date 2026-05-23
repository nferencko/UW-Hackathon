import javax.swing.JOptionPane;

/** Controller for the BlackJack game */
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

        // Attach event listeners to the buttons
        this.view.getDealButton().addActionListener(e -> startNewHand());
        this.view.getHitButton().addActionListener(e -> playerHit());
        this.view.getStandButton().addActionListener(e -> playerStand());
        this.view.getDoubleButton().addActionListener(e -> playerDoubleDown());

        // Initial UI state
        this.view.setTurnState(false);
        updateView(true); // show initial balance
    }

    private void startNewHand() {

        // Setup models for a new game
        deck.shuffle();
        player.resetPlayer();
        dealer.resetDealer();

        String betStr = JOptionPane.showInputDialog(view, "Enter your bet:",
                "Place Bet", JOptionPane.QUESTION_MESSAGE);

        if (betStr == null) return;

        try {
            double bet = Double.parseDouble(betStr);
            if (!player.setBet(bet)) {
                JOptionPane.showMessageDialog(view, "Invalid Bet Amount!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Deal initial cards to player and dealer
        player.hit(deck.takeCard());
        dealer.hit(deck.takeCard());
        player.hit(deck.takeCard());
        dealer.hit(deck.takeCard());

        // If nobody has BlackJack, player can choose to hit or stand, otherwise game is
        // over and update the view.
        if( !checkForBlackJack()){
            view.setTurnState(true);
            view.setGameStatus("Your turn. Hit, Stand, or Double Down?");
            updateView(false); // keep dealer card hidden
        }
        else{
            view.setTurnState(false);
            updateView(true);
        }

    }

    // Action when Player chooses to Hit
    private void playerHit() {
        player.hit(deck.takeCard());
        if (player.isBust()) {
            view.setGameStatus("You busted! Dealer wins.");
            endHand(0); // Player loses bet
        } else {
            updateView(false);
        }
        // Once player hits, they can no longer double down
        this.view.getDoubleButton().setEnabled(false);
    }

    // Action when player chooses to stand
    private void playerStand() {
        view.setTurnState(false);

        // Dealer takes cards as prescribed by canHit method
        while (dealer.canHit()) {
            dealer.hit(deck.takeCard());
        }
        determineWinner();
    }

    // Action when player chooses to double down
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
            view.setGameStatus("Push! You and the dealer both have BlackJack.");
            endHand(1.0);
            isBlackJack = true;
        }
        else if(player.hasBlackJack()){
            view.setGameStatus("You have BlackJack!");
            endHand(2.5);
            isBlackJack = true;
        }
        else if(dealer.hasBlackJack()){
            view.setGameStatus("Dealer has BlackJack.");
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

    // when game is over, update balances
    private void endHand(double payoutMultiplier) {
        view.setTurnState(false);
        // Assuming you track current bet to calculate payout increment
        player.incrementBalance(payoutMultiplier*player.getBet());
        // player.incrementBalance(...) logic goes here
        updateView(true); // reveal dealer cards
    }

    private void updateView(boolean revealDealer) {

        view.setPlayerStats(player.getBalance(), player.getBet());

        // Pass the actual card lists rather than strings
        view.updatePlayerCards(player.getHand().getCards());

        view.setScores(
                revealDealer ? String.valueOf(dealer.getHand().getValue()) : "??",
                player.getHand().getValue()
        );

        view.updateDealerCards(dealer.getHand().getCards(), revealDealer);
    }
}
