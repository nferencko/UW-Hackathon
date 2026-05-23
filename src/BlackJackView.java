import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackJackView extends JFrame {

    // Colors and fonts used in the GUI
    private final Color TABLE_GREEN = new Color(0, 122, 50);
    private final Color DARKER_GREEN = new Color(0, 100, 40);
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private final Font STATUS_FONT = new Font("Arial", Font.BOLD, 18);

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


        dealerCardPanel.setBackground(TABLE_GREEN);
        playerCardPanel.setBackground(TABLE_GREEN);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        doubleButton = new JButton("Double Down");
        dealButton = new JButton("Deal");
    }

    private void layoutComponents() {

        // Set text to white so it's readable on a green background
        dealerScoreLabel.setForeground(Color.WHITE);
        playerScoreLabel.setForeground(Color.WHITE);
        playerStatsLabel.setForeground(Color.WHITE);
        gameStatusLabel.setForeground(Color.WHITE);

        // Set the fonts for all the labels
        dealerScoreLabel.setFont(LABEL_FONT);
        playerScoreLabel.setFont(LABEL_FONT);
        playerStatsLabel.setFont(LABEL_FONT);

        gameStatusLabel.setFont(STATUS_FONT);

        // Top Region: Dealer Area
        JPanel dealerArea = new JPanel(new BorderLayout());
        dealerArea.setBackground(TABLE_GREEN); // Set the container panel green
        dealerArea.add(dealerScoreLabel, BorderLayout.NORTH);
        dealerArea.add(dealerCardPanel, BorderLayout.CENTER);
        add(dealerArea, BorderLayout.NORTH);

        // Center Region: Status and Player Area (Using your original layout)
        JPanel centerArea = new JPanel(new GridLayout(2, 1));
        centerArea.setBackground(DARKER_GREEN); // Set the main grid container green

        gameStatusLabel.setOpaque(false); // Keeps background transparent to show panel green
        centerArea.add(gameStatusLabel);

        JPanel playerArea = new JPanel(new BorderLayout());
        playerArea.setBackground(TABLE_GREEN); // Set the player's inner container green
        playerArea.add(playerStatsLabel, BorderLayout.NORTH);
        playerArea.add(playerCardPanel, BorderLayout.CENTER);
        playerArea.add(playerScoreLabel, BorderLayout.SOUTH);

        centerArea.add(playerArea);
        add(centerArea, BorderLayout.CENTER);

        // Bottom Area: Controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(new Color(45, 45, 45)); // Slate/matte black border style

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
