package Task09_3;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class QuizMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MathQuiz model = new MathQuiz();
                QuizView view = new QuizView();
                new QuizController(model, view);
                view.setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null, "Could not open PlayerDB: " + e.getMessage());
            }
        });
    }
}