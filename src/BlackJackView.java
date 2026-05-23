import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackJackView extends JFrame {

    // Colors and fonts used in the GUI
    private final Color TABLE_GREEN = new Color(0, 122, 50);
    private final Color DARKER_GREEN = new Color(0, 100, 40);
    private final Color SLATE_GRAY = new Color(45, 45, 45); // button color
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private final Font STATUS_FONT = new Font("Arial", Font.BOLD, 18);
    private final Color LABEL_COLOR = Color.WHITE;

    // Alternate Table Colors in Honor of Knicks
    private final Color KNICKS_BLUE = new Color(0,107,182);
    private final Color KNICKS_ORANGE = new Color(245,132,38);
    private final Color KNICKS_SILVER = new Color(35, 31, 32);

    // Labels for game state
    private JLabel playerStatsLabel;
    private JLabel gameStatusLabel;
    private JLabel dealerScoreLabel;
    private JLabel playerScoreLabel;

    // Panels for displaying cards
    private JPanel dealerCardPanel;
    private JPanel playerCardPanel;

    // Action Buttons
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleButton;
    private JButton dealButton;

    /** Create the BlackJack View */
    public BlackJackView() {
        setTitle("Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        initializeComponents();
        layoutComponents();

        setVisible(true);
    }

    private void initializeComponents() {
        playerStatsLabel = new JLabel("Balance: $0.00 | Bet: $0.00");
        gameStatusLabel = new JLabel("Welcome! Press Deal to start.", SwingConstants.CENTER);
        dealerScoreLabel = new JLabel("Dealer: ");
        playerScoreLabel = new JLabel("Player: ");

        // Using FlowLayout ensures images align in a horizontal row
        dealerCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        playerCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));


        dealerCardPanel.setBackground(KNICKS_BLUE);
        playerCardPanel.setBackground(KNICKS_BLUE);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        doubleButton = new JButton("Double Down");
        dealButton = new JButton("Deal");
    }

    private void layoutComponents() {

        // Set text color of all labels
        dealerScoreLabel.setForeground(LABEL_COLOR);
        playerScoreLabel.setForeground(LABEL_COLOR);
        playerStatsLabel.setForeground(LABEL_COLOR);
        gameStatusLabel.setForeground(LABEL_COLOR);

        // Set the fonts for all the labels
        dealerScoreLabel.setFont(LABEL_FONT);
        playerScoreLabel.setFont(LABEL_FONT);
        playerStatsLabel.setFont(LABEL_FONT);

        gameStatusLabel.setFont(STATUS_FONT);

        // Top Region: Dealer Area
        JPanel dealerArea = new JPanel(new BorderLayout());
        dealerArea.setBackground(KNICKS_BLUE); // Set the container panel green
        dealerArea.add(dealerScoreLabel, BorderLayout.NORTH);
        dealerArea.add(dealerCardPanel, BorderLayout.CENTER);
        add(dealerArea, BorderLayout.NORTH);

        // Center Region: Status and Player Area (Using your original layout)
        JPanel centerArea = new JPanel(new GridLayout(2, 1));
        centerArea.setBackground(KNICKS_ORANGE); // Set the main grid container green

        gameStatusLabel.setOpaque(false); // Keeps background transparent to show panel green
        centerArea.add(gameStatusLabel);

        JPanel playerArea = new JPanel(new BorderLayout());
        playerArea.setBackground(KNICKS_BLUE); // Set the player's inner container green
        playerArea.add(playerStatsLabel, BorderLayout.NORTH);
        playerArea.add(playerCardPanel, BorderLayout.CENTER);
        playerArea.add(playerScoreLabel, BorderLayout.SOUTH);

        centerArea.add(playerArea);
        add(centerArea, BorderLayout.CENTER);

        // Bottom Area: Controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground( KNICKS_SILVER );

        controlsPanel.add(dealButton);
        controlsPanel.add(hitButton);
        controlsPanel.add(standButton);
        controlsPanel.add(doubleButton);
        add(controlsPanel, BorderLayout.SOUTH);
    }

    // Getters for the controller to attach action listeners
    public JButton getHitButton() { return hitButton; }
    public JButton getStandButton() { return standButton; }
    public JButton getDoubleButton() { return doubleButton; }
    public JButton getDealButton() { return dealButton; }

    // Updates the player's card images
    public void updatePlayerCards(ArrayList<Card> cards) {
        playerCardPanel.removeAll();
        for (Card card : cards) {
            JLabel cardLabel = new JLabel(card.getImageIcon());
            playerCardPanel.add(cardLabel);
        }
        playerCardPanel.revalidate();
        playerCardPanel.repaint();
    }

    // Updates the dealer's card images, with a toggle to hide the first card
    public void updateDealerCards(ArrayList<Card> cards, boolean revealDealer) {
        dealerCardPanel.removeAll();

        for (int i = 0; i < cards.size(); i++) {
            JLabel cardLabel;
            if (i == 0 && !revealDealer) {
                // Display the card back image for the dealer's hidden card
                cardLabel = new JLabel(Card.getBackImageIcon());
            } else {
                cardLabel = new JLabel(cards.get(i).getImageIcon());
            }
            dealerCardPanel.add(cardLabel);
        }
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();
    }

    /** Set the game status */
    public void setGameStatus(String message) {
        gameStatusLabel.setText(message);
    }

    /** Set the Player Stats */
    public void setPlayerStats(double balance, double bet) {
        playerStatsLabel.setText("Balance: $" + balance + " | Bet: $" + bet);
    }

    /** Set the scores */
    public void setScores(String dealerScore, int playerScore) {
        dealerScoreLabel.setText("Dealer: " + dealerScore);
        playerScoreLabel.setText("Player: " + playerScore);
    }

    /**
     * Enable/Disable buttons based on inGame.
     * @param inGame true if the game is still active, false if game is over.
     */
    public void setTurnState(boolean inGame) {
        hitButton.setEnabled(inGame);
        standButton.setEnabled(inGame);
        doubleButton.setEnabled(inGame);
        dealButton.setEnabled(!inGame);
    }
}
