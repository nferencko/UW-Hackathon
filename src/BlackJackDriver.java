/**
 * Driver class to launch the Swing Blackjack game.
 * * @author Nic Ferencko
 */
public class BlackJackDriver {

    public static void main(String[] args) {

        // Use invokeLater to ensure Swing components are created on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Initialize the game models using default player and
                Player player = new Player();
                Dealer dealer = new Dealer();

                // Initialize the GUI view window
                BlackJackView view = new BlackJackView();

                // Initialize the controller to link the models and the view
                // The controller constructor will automatically wire up the button listeners
                new BlackJackController(view, player, dealer);
            }
        });
    }
}
