package Task09_3;

import java.sql.SQLException;

public class QuizController implements QuizView.Listener {

    private final MathQuiz model;
    private final QuizView view;

    public QuizController(MathQuiz model, QuizView view) {
        this.model = model;
        this.view = view;
        view.setListener(this);
    }

    @Override
    public void loginRequested(String username, String password) {
        try {
            if (!model.login(username, password)) {
                view.showMessage(
                        "Wrong login, or username/password is empty or over 12 characters.");
                return;
            }

            showNextQuestion();
        } catch (SQLException e) {
            view.showMessage("Database error: " + e.getMessage());
        }
    }

    @Override
    public void nextRequested(String answerText) {
        try {
            int answer = Integer.parseInt(answerText.trim());
            model.checkAnswer(answer);
            showNextQuestion();
        } catch (NumberFormatException e) {
            view.showMessage("Enter a whole number for your answer.");
        }
    }

    @Override
    public void quitRequested() {
        try {
            model.saveScore();
            view.showFinalScore(model.getScore());
            model.close();
        } catch (SQLException e) {
            view.showMessage("Could not save the score: " + e.getMessage());
        }
    }

    private void showNextQuestion() {
        int[] numbers = model.newQuestion();
        view.showQuestion(numbers[0], numbers[1], model.getScore());
    }
}