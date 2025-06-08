import java.awt.*; // imports the AWT package.
import java.awt.event.*; // imports AWT event package for handling user interactions.

import javax.swing.JOptionPane; // imports JOptionPane 

public class LabActivity5QuizAppAWT extends Frame implements ActionListener { // Declaring the class, extending frame and implementing ActionListener for button clicks. 
    private Label questionLabel; // Declares a Label component to display the current question.
    private CheckboxGroup answersGroup; // Declares a CheckboxGroup to ensure that only one answer can be selected at atime.
    private Checkbox[] answerOptions; // Declares an array of Checkbox components for the answer options.
    private Button nextButton; // Declares a Button component for moving to the next question.
    private String[] questions; // Declares an array of Strings to hold the quiz questionnaires.
    private String[][] options; // Declares a 2D array of Strings to hold the answer options for each question.
    private int[] correctAnswers; // Declares an array to int(integer) to store the index of the correct answer for each questions. 
    private int currentQuestion; // Declares an integer to keep in track to the current question being displayed. 
    private int score; // Declares an int to keep in track of the users score.

    public LabActivity5QuizAppAWT() { // Constructor for the LabActivity5QuizAppAWT class.
        // Quiz questions and options
        questions = new String[]{ // initializes the array of the quiz questions.
            "What does CPU stand for in Computer Science?",
            "Which of the following is an example of an input device?",
            "What is the primary purpose of an operating system?"
        };

        options = new String[][]{ // Initializes the 2D array of answer options for each questions.
            {"A. Central Processing Unit", "B. Computer Processing Unit", "C. Central Programming Unit", "D. Computer Programming Unit"},
            {"A. Monitor", "B. Keyboard", "C. Printer", "D. Speaker"},
            {"A. To manage hardware and software resources", "B. To create websites", "C. To design computer hardware", "D. To write computer programs"}
        };
        
        correctAnswers = new int[]{0, 1, 0}; // Index of the correct answers

        currentQuestion = 0; // Initializes the current question index to the first question.
        score = 0;

        // Set up the Frame
        setTitle("Quiz App"); // Setting the title of the AWT Windows.
        setSize(400, 300); // size of the windows. (width, height)
        setLayout(new FlowLayout()); // Sets the Layout manager for the frame to FlowLayout.

        // Create question label
        questionLabel = new Label(questions[currentQuestion]); // Creating new label.
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        // Create radio buttons
        answersGroup = new CheckboxGroup();
        answerOptions = new Checkbox[4];
        for (int i = 0; i < options[currentQuestion].length; i++) { // Loops through the answer option for the current question.
            answerOptions[i] = new Checkbox(options[currentQuestion][i], answersGroup, false);
            answerOptions[i].setFont(new Font("Arial", Font.PLAIN, 14));
            add(answerOptions[i]);
        }

        // Next button
        nextButton = new Button("Next");
        nextButton.addActionListener(this);
        add(nextButton);

        // Close button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
@Override // indicate override method.
    public void actionPerformed(ActionEvent e) {
        // Check the answer
        Checkbox selectedAnswer = answersGroup.getSelectedCheckbox();
        if (selectedAnswer != null) {
            // Find the index of the selected answer
            char letter = selectedAnswer.getLabel().charAt(0);
            int answerIndex = letter - 'A'; // Convert 'A', 'B', 'C', 'D' to 0, 1, 2, 3
            
            // int answerIndex = Integer.parseInt(selectedAnswer.getLabel().substring(0, 1)) - 1; // Get the answer index
            if (answerIndex == correctAnswers[currentQuestion]) {
                score++;
            }
        }

        // Move to the next question
        currentQuestion++;
        if (currentQuestion < questions.length) {
            updateQuestion();
        } else {
            displayScore();
        }
    }

    private void updateQuestion() {
        // Update question label and options
        questionLabel.setText(questions[currentQuestion]);
        for (int i = 0; i < answerOptions.length; i++) {
            if (i < options[currentQuestion].length) {
                answerOptions[i].setLabel(options[currentQuestion][i]);
            } else {
                answerOptions[i].setVisible(false);
            }
            answerOptions[i].setState(false); // Reset selection
        }
    }

    private void displayScore() {
        // Show final score
        questionLabel.setText("Quiz Completed! Your Score: " + score + " out of " + questions.length);
        for (Checkbox cb : answerOptions) {
            cb.setVisible(false); // Hide answer options
        }
        nextButton.setVisible(false); // Hide Next button
    }

    public static void main(String[] args) {
        new LabActivity5QuizAppAWT();
    }
}
