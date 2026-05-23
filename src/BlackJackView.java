import javax.swing.*;
import java.awt.*;

public class BlackJackView extends JFrame {
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

        dealerCardPanel = new JPanel();
        playerCardPanel = new JPanel();

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        doubleButton = new JButton("Double Down");
        dealButton = new JButton("Deal");
    }

    private void layoutComponents() {
        // Top: Dealer Area
        JPanel dealerArea = new JPanel(new BorderLayout());
        dealerArea.add(dealerScoreLabel, BorderLayout.NORTH);
        dealerArea.add(dealerCardPanel, BorderLayout.CENTER);
        add(dealerArea, BorderLayout.NORTH);

        // Center: Status and Player Area
        JPanel centerArea = new JPanel(new GridLayout(2, 1));
        centerArea.add(gameStatusLabel);

        JPanel playerArea = new JPanel(new BorderLayout());
        playerArea.add(playerStatsLabel, BorderLayout.NORTH);
        playerArea.add(playerCardPanel, BorderLayout.CENTER);
        playerArea.add(playerScoreLabel, BorderLayout.SOUTH);
        centerArea.add(playerArea);

        add(centerArea, BorderLayout.CENTER);

        // Bottom: Controls
        JPanel controlsPanel = new JPanel();
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

    // Methods for the Controller to update the View
    public void updatePlayerCards(String cardsStr) {
        playerCardPanel.removeAll();
        // For simple text, add labels. If you upgrade to image icons later, add images here.
        playerCardPanel.add(new JLabel(cardsStr));
        playerCardPanel.revalidate();
        playerCardPanel.repaint();
    }

    public void updateDealerCards(String cardsStr) {
        dealerCardPanel.removeAll();
        dealerCardPanel.add(new JLabel(cardsStr));
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();
    }

    public void setGameStatus(String message) { gameStatusLabel.setText(message); }
    public void setPlayerStats(double balance, double bet) {
        playerStatsLabel.setText("Balance: $" + balance + " | Bet: $" + bet);
    }
    public void setScores(String dealerScore, int playerScore) {
        dealerScoreLabel.setText("Dealer: " + dealerScore);
        playerScoreLabel.setText("Player: " + playerScore);
    }

    public void setTurnState(boolean inGame) {
        hitButton.setEnabled(inGame);
        standButton.setEnabled(inGame);
        doubleButton.setEnabled(inGame);
        dealButton.setEnabled(!inGame);
    }
}
