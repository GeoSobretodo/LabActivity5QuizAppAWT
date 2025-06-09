import java.awt.*;
import java.awt.event.*;

public class QuizApp extends Frame implements ActionListener {

    // --- Data Storage ---
    String[] questions = {
        "What does CPU stand for?",
        "Which of the following is an example of an operating system?",
        "What is 'RAM' primarily used for in a computer?"
    };

    // Choices for each question (4 choices per question)
    String[][] choices = {
        {"A. Central Processing Unit", "B. Computer Personal Unit", "C. Central Program Utility", "D. Control Panel Unit"},
        {"A. Microsoft Word", "B. Google Chrome", "C. Windows 11", "D. Adobe Photoshop"},
        {"A. Long-term data storage", "B. Executing programs and temporary data storage", "C. Permanent system files", "D. Printing documents"}
    };

    // Index of the correct answer for each question (0-3 for choice A-D)
    int[] correctAnswers = {0, 2, 1}; // CPU, Windows 11, Executing programs

    // --- Quiz State ---
    int currentQuestionIndex = 0;
    int score = 0;

    // --- GUI Components ---
    Label questionLabel;
    CheckboxGroup answerGroup;
    Checkbox[] answerChoices; // Array to hold the 4 radio buttons
    Button nextButton;
    Panel questionPanel;
    Panel choicesPanel;
    Panel buttonPanel;
    Label feedbackLabel; // NEW: Label for displaying feedback like "Please select an answer."

    // --- Constructor ---
    public QuizApp() {
        super("Quiz App"); // Set frame title as "Quiz App" based on your image
        setSize(500, 300); // Set frame dimensions
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for overall layout
        setBackground(new Color(240, 240, 240)); // Light grey background

        // 1. Initialize Question Label
        questionLabel = new Label("", Label.CENTER); // Center align text
        Font questionFont = new Font("Arial", Font.BOLD, 16);
        questionLabel.setFont(questionFont);
        Panel questionWrapper = new Panel(new FlowLayout(FlowLayout.CENTER));
        questionWrapper.add(questionLabel);
        add(questionWrapper, BorderLayout.NORTH); // Top part of the frame

        // 2. Initialize Answer Choices (Radio Buttons)
        answerGroup = new CheckboxGroup();
        answerChoices = new Checkbox[4];
        choicesPanel = new Panel();
        choicesPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Adjusted layout for better spacing (2x2 grid)
        choicesPanel.setBackground(new Color(255, 255, 255)); // White background for choices

        for (int i = 0; i < 4; i++) {
            answerChoices[i] = new Checkbox("", answerGroup, false); // Create radio button
            answerChoices[i].setFont(new Font("SansSerif", Font.PLAIN, 14)); // Customization 1: Font for radio buttons
            answerChoices[i].setForeground(new Color(50, 50, 50)); // Customization 2: Text color for radio buttons
            choicesPanel.add(answerChoices[i]);
        }
        add(choicesPanel, BorderLayout.CENTER); // Center part of the frame

        // NEW: Feedback Label
        feedbackLabel = new Label("", Label.CENTER);
        feedbackLabel.setForeground(Color.RED); // Make error message red
        feedbackLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        add(feedbackLabel, BorderLayout.SOUTH); // Initially place it at the bottom, before the button panel

        // 3. Initialize Next Button
        nextButton = new Button("Next"); // Label changed to "Next" as per your screenshot
        nextButton.addActionListener(this); // Register action listener
        nextButton.setBackground(new Color(0, 120, 215)); // Customization 1: Button background color
        nextButton.setForeground(Color.WHITE); // Customization 2: Button text color
        nextButton.setFont(new Font("Arial", Font.BOLD, 14)); // Customization 3: Button font

        buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(nextButton);
        // The feedback label is now in BorderLayout.SOUTH, so the button panel needs its own sub-panel or to be managed differently.
        // For simplicity, let's put the feedback label ABOVE the button panel within the SOUTH region.
        Panel southPanel = new Panel(new GridLayout(2, 1)); // Panel for feedback and button
        southPanel.add(feedbackLabel);
        southPanel.add(buttonPanel);
        add(southPanel, BorderLayout.SOUTH);

        // 4. Set up Window Listener for closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose(); // Release resources
                System.exit(0); // Terminate program
            }
        });

        // Display the first question
        displayQuestion();

        // Make the frame visible
        setVisible(true);
    }

    // --- Method to display the current question ---
    private void displayQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                answerChoices[i].setLabel(choices[currentQuestionIndex][i]);
                answerChoices[i].setState(false); // Reset selection
                answerChoices[i].setEnabled(true); // Ensure choices are enabled
            }
            nextButton.setLabel("Next"); // Ensure button label is "Next"
            nextButton.setEnabled(true); // Ensure button is enabled
            feedbackLabel.setText(""); // Clear any previous feedback message
        } else {
            // Quiz finished, display score
            endQuiz();
        }
    }

    // --- Method to handle quiz end ---
    private void endQuiz() {
        questionLabel.setText("You got " + score + " out of " + questions.length + " correct!");
        feedbackLabel.setText(""); // Clear feedback label at the end
        for (Checkbox cb : answerChoices) {
            cb.setEnabled(false); // Disable choices
        }
        nextButton.setEnabled(false); // Disable next button
        nextButton.setLabel("Quiz Finished!"); // Optional: change button text
    }

    // --- ActionListener implementation for the "Next Question" button ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            Checkbox selectedChoice = answerGroup.getSelectedCheckbox();

            if (selectedChoice == null) { // If no choice is selected
                feedbackLabel.setText("Please select an answer."); // Display feedback
                return; // Stop execution here, don't proceed to next question
            } else {
                feedbackLabel.setText(""); // Clear feedback if a choice was made
            }

            // Determine which choice was selected
            int selectedIndex = -1;
            for (int i = 0; i < answerChoices.length; i++) {
                if (answerChoices[i] == selectedChoice) {
                    selectedIndex = i;
                    break;
                }
            }

            // Check if the selected answer is correct
            if (selectedIndex == correctAnswers[currentQuestionIndex]) {
                score++;
            }

            // Move to the next question
            currentQuestionIndex++;
            displayQuestion(); // Call to display the next question or end the quiz
        }
    }

    // --- Main method to run the application ---
    public static void main(String[] args) {
        new QuizApp();
    }
    }
