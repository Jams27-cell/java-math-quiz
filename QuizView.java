package Task09_3;

import java.awt.FlowLayout;
import javax.swing.*;

public class QuizView extends JFrame {

    public interface Listener {
        void loginRequested(String username, String password);
        void nextRequested(String answer);
        void quitRequested();
    }

    private Listener listener;

    private final JTextField usernameField = new JTextField(10);
    private final JPasswordField passwordField = new JPasswordField(10);
    private final JTextField answerField = new JTextField(10);

    private final JLabel firstNumber = new JLabel();
    private final JLabel secondNumber = new JLabel();
    private final JLabel scoreLabel = new JLabel();

    public QuizView() {
        setTitle("Math Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        showLogin();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void showPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }

    public void showLogin() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Log in");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            if (listener != null) {
                listener.loginRequested(
                        usernameField.getText(),
                        new String(passwordField.getPassword()));
            }
        });

        showPanel(panel);
    }

    public void showQuestion(int first, int second, int score) {
        firstNumber.setText(Integer.toString(first));
        secondNumber.setText(Integer.toString(second));
        scoreLabel.setText("Score: " + score);
        answerField.setText("");

        JPanel panel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("Next");
        JButton quitButton = new JButton("Quit");

        panel.add(firstNumber);
        panel.add(new JLabel("+"));
        panel.add(secondNumber);
        panel.add(new JLabel("="));
        panel.add(answerField);
        panel.add(nextButton);
        panel.add(quitButton);
        panel.add(scoreLabel);

        nextButton.addActionListener(e -> {
            if (listener != null) {
                listener.nextRequested(answerField.getText());
            }
        });

        quitButton.addActionListener(e -> {
            if (listener != null) {
                listener.quitRequested();
            }
        });

        showPanel(panel);
        answerField.requestFocusInWindow();
    }

    public void showFinalScore(int score) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Your score: " + score));
        showPanel(panel);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}