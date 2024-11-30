import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuizApp {

    static class Question {
        String question;
        String[] options;
        String correctAnswer;

        public Question(String question, String[] options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public boolean isCorrect(String answer) {
            return correctAnswer.equalsIgnoreCase(answer);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create some sample questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, "Paris"));
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, "4"));
        questions.add(new Question("What is the color of the sky?", new String[]{"Red", "Blue", "Green", "Yellow"}, "Blue"));

        int score = 0;
        int timeLimitInSeconds = 30; // 30 seconds for each question

        // Loop through each question
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);

            System.out.println("Question " + (i + 1) + ": " + currentQuestion.question);
            for (int j = 0; j < currentQuestion.options.length; j++) {
                System.out.println((j + 1) + ". " + currentQuestion.options[j]);
            }

            // Start a timer for each question
            System.out.println("You have " + timeLimitInSeconds + " seconds to answer...");
            String userAnswer = getAnswerWithTimer(scanner, timeLimitInSeconds);

            if (userAnswer != null && currentQuestion.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer is: " + currentQuestion.correctAnswer);
            }
            System.out.println();
        }

        System.out.println("Quiz finished! Your final score is: " + score + "/" + questions.size());
        scanner.close();
    }

    // Method to get the user's answer with a timer
    public static String getAnswerWithTimer(Scanner scanner, int timeLimitInSeconds) {
        final String[] userAnswer = new String[1];
        Thread timerThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(timeLimitInSeconds);
                if (userAnswer[0] == null) {
                    System.out.println("\nTime's up! No answer given.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        timerThread.start();

        // Wait for the user input
        System.out.print("Your answer: ");
        userAnswer[0] = scanner.nextLine();

        // If the user answers within time limit, return the answer
        if (userAnswer[0] != null) {
            timerThread.interrupt(); // Stop the timer if the user answered
            return userAnswer[0];
        }

        return null; // Return null if no answer given
    }
}
