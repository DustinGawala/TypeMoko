import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TypeMoko {

    static JFrame frame;

    static JTextArea typingArea;
    static JLabel timerLabel;
    static JLabel wpmLabel;
    static JLabel errorLabel;
    static JLabel accuracyLabel;
    static JLabel passageLabel;
    static JLabel fingerLabel;
    static JLabel progressLabel;

    static JProgressBar progressBar;

    static JButton startButton;
    static JButton restartButton;
    static JButton playAgainButton;

    static Timer timer;

    static int timeLeft = 60;

    static int totalTypedCharacters = 0;
    static int totalCorrectCharacters = 0;
    static int totalErrors = 0;

    static int currentPassageIndex = 0;

    static final String[] PASSAGES = {

        "The best way to improve your typing is to practice every day and focus on accuracy before speed.",

        "Learning to type faster can help students finish their schoolwork more easily and save more time.",

        "Computers are useful tools that allow people to communicate learn solve problems and create projects.",

        "Practice makes progress so stay focused keep your hands relaxed and try to type each word correctly.",

        "Technology changes quickly so learning new skills can help us become more confident and prepared.",

        "Every small improvement matters so keep typing stay patient and challenge yourself to do better."
    };

    static Map<Character, JButton> keyboardButtons = new HashMap<>();


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            createWindow();
        });

    }


    public static void createWindow() {

        frame = new JFrame("TypeMoko - Typing Speed and Accuracy Game");

        frame.setSize(1050, 780);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        showWelcomeScreen();

        frame.setVisible(true);

    }


    // =========================
    // WELCOME SCREEN
    // =========================

    public static void showWelcomeScreen() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));

        mainPanel.setBorder(
                new EmptyBorder(40, 50, 40, 50)
        );


        JLabel title = new JLabel(
                "TYPEMOKO",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 48)
        );


        JLabel subtitle = new JLabel(
                "Typing Speed and Accuracy Game",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 22)
        );


        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        titlePanel.add(title);

        titlePanel.add(subtitle);


        JTextArea instructions = new JTextArea();

        instructions.setText(
                "WELCOME TO TYPEMOKO!\n\n" +

                "Test how fast and accurately you can type in 60 seconds.\n\n" +

                "HOW TO PLAY:\n" +
                "1. Click START CHALLENGE.\n" +
                "2. Type the passage shown on the screen.\n" +
                "3. When you finish a passage, a new passage appears automatically.\n" +
                "4. Keep typing as many words as you can before the 60-second timer ends.\n" +
                "5. Your WPM, errors, and accuracy will be calculated.\n\n" +

                "Use the keyboard and finger guide to practice proper typing."
        );

        instructions.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        instructions.setEditable(false);

        instructions.setLineWrap(true);

        instructions.setWrapStyleWord(true);

        instructions.setBackground(
                mainPanel.getBackground()
        );


        startButton = new JButton(
                "START CHALLENGE"
        );

        startButton.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        startButton.setPreferredSize(
                new Dimension(250, 60)
        );


        startButton.addActionListener(
                e -> startGame()
        );


        mainPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                instructions,
                BorderLayout.CENTER
        );

        mainPanel.add(
                startButton,
                BorderLayout.SOUTH
        );


        frame.setContentPane(mainPanel);

        frame.revalidate();

        frame.repaint();

    }


    // =========================
    // GAME SCREEN
    // =========================

    public static void showGameScreen() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                new EmptyBorder(15, 20, 15, 20)
        );


        // =========================
        // TOP STATISTICS
        // =========================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(1, 4, 10, 10)
                );


        timerLabel =
                createStatLabel("TIME: 60");

        wpmLabel =
                createStatLabel("WPM: 0");

        errorLabel =
                createStatLabel("ERRORS: 0");

        accuracyLabel =
                createStatLabel("ACCURACY: 100%");


        statsPanel.add(timerLabel);

        statsPanel.add(wpmLabel);

        statsPanel.add(errorLabel);

        statsPanel.add(accuracyLabel);


        mainPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );


        // =========================
        // CENTER
        // =========================

        JPanel centerPanel =
                new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel instructionLabel =
                new JLabel(
                        "TYPE THE PASSAGE BELOW",
                        SwingConstants.CENTER
                );

        instructionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        instructionLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        centerPanel.add(
                instructionLabel
        );

        centerPanel.add(
                Box.createVerticalStrut(10)
        );


        passageLabel =
                new JLabel(
                        "",
                        SwingConstants.CENTER
                );

        passageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        passageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        centerPanel.add(
                passageLabel
        );


        centerPanel.add(
                Box.createVerticalStrut(10)
        );


        // =========================
        // PROGRESS BAR
        // =========================

        progressBar =
                new JProgressBar(
                        0,
                        100
                );

        progressBar.setValue(0);

        progressBar.setStringPainted(true);

        progressBar.setString(
                "Passage Progress"
        );


        centerPanel.add(
                progressBar
        );


        progressLabel =
                new JLabel(
                        "Passage 1",
                        SwingConstants.CENTER
                );

        progressLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        progressLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        centerPanel.add(
                progressLabel
        );


        centerPanel.add(
                Box.createVerticalStrut(10)
        );


        // =========================
        // TYPING AREA
        // =========================

        typingArea =
                new JTextArea();

        typingArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20
                )
        );

        typingArea.setLineWrap(true);

        typingArea.setWrapStyleWord(true);

        typingArea.setRows(4);

        typingArea.setEnabled(false);

        typingArea.setBorder(
                BorderFactory.createLineBorder(
                        Color.GRAY,
                        2
                )
        );


        typingArea.addKeyListener(
                new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {

                        updateGameInfo();

                        updateKeyboardGuide();

                    }

                }
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        typingArea
                );


        centerPanel.add(
                scrollPane
        );


        centerPanel.add(
                Box.createVerticalStrut(10)
        );


        // =========================
        // FINGER GUIDE
        // =========================

        fingerLabel =
                new JLabel(
                        "Finger Guide: Start typing!",
                        SwingConstants.CENTER
                );

        fingerLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        fingerLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        centerPanel.add(
                fingerLabel
        );


        centerPanel.add(
                Box.createVerticalStrut(10)
        );


        restartButton =
                new JButton(
                        "RESTART"
                );

        restartButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        restartButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        restartButton.addActionListener(
                e -> startGame()
        );


        centerPanel.add(
                restartButton
        );


        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        // =========================
        // KEYBOARD
        // =========================

        JPanel keyboardPanel =
                createKeyboard();


        mainPanel.add(
                keyboardPanel,
                BorderLayout.SOUTH
        );


        frame.setContentPane(
                mainPanel
        );

        frame.revalidate();

        frame.repaint();

    }


    public static JLabel createStatLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        label.setBorder(
                BorderFactory.createLineBorder(
                        Color.GRAY,
                        1
                )
        );

        return label;

    }


    // =========================
    // KEYBOARD
    // =========================

    public static JPanel createKeyboard() {

        JPanel keyboardPanel =
                new JPanel();

        keyboardPanel.setLayout(
                new GridLayout(
                        3,
                        1,
                        3,
                        3
                )
        );


        String[] rows = {

                "QWERTYUIOP",

                "ASDFGHJKL",

                "ZXCVBNM"

        };


        keyboardButtons.clear();


        for (String row : rows) {

            JPanel rowPanel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER,
                                    3,
                                    3
                            )
                    );


            for (char c : row.toCharArray()) {

                JButton button =
                        new JButton(
                                String.valueOf(c)
                        );


                button.setPreferredSize(
                        new Dimension(
                                55,
                                35
                        )
                );


                final char key = c;


                button.addActionListener(
                        e -> {

                            if (!typingArea.isEnabled()) {

                                return;

                            }


                            typingArea.requestFocus();


                            typingArea.insert(
                                    String.valueOf(
                                            Character.toLowerCase(
                                                    key
                                            )
                                    ),
                                    typingArea.getCaretPosition()
                            );


                            updateGameInfo();

                            updateKeyboardGuide();

                        }
                );


                keyboardButtons.put(
                        Character.toLowerCase(c),
                        button
                );


                rowPanel.add(button);

            }


            keyboardPanel.add(
                    rowPanel
            );

        }


        return keyboardPanel;

    }


    // =========================
    // START GAME
    // =========================

    public static void startGame() {

        if (timer != null) {

            timer.stop();

        }


        timeLeft = 60;

        totalTypedCharacters = 0;

        totalCorrectCharacters = 0;

        totalErrors = 0;

        currentPassageIndex = 0;


        showGameScreen();


        passageLabel.setText(
                PASSAGES[currentPassageIndex]
        );


        progressLabel.setText(
                "Passage 1 of " +
                PASSAGES.length
        );


        typingArea.setText("");

        typingArea.setEnabled(true);

        typingArea.requestFocus();


        timerLabel.setText(
                "TIME: 60"
        );

        wpmLabel.setText(
                "WPM: 0"
        );

        errorLabel.setText(
                "ERRORS: 0"
        );

        accuracyLabel.setText(
                "ACCURACY: 100%"
        );


        progressBar.setValue(0);


        updateKeyboardGuide();


        // =========================
        // 60 SECOND TIMER
        // =========================

        timer =
                new Timer(
                        1000,
                        e -> {

                            timeLeft--;


                            timerLabel.setText(
                                    "TIME: " +
                                    timeLeft
                            );


                            updateGameInfo();


                            if (timeLeft <= 0) {

                                timer.stop();

                                endGame();

                            }

                        }
                );


        timer.start();

    }


    // =========================
    // UPDATE GAME INFORMATION
    // =========================

    public static void updateGameInfo() {

        String typedText =
                typingArea.getText();


        String passage =
                PASSAGES[currentPassageIndex];


        int currentCorrect = 0;

        int currentErrors = 0;


        for (
                int i = 0;
                i < typedText.length();
                i++
        ) {

            if (
                    i < passage.length()
                    &&
                    typedText.charAt(i)
                    ==
                    passage.charAt(i)
            ) {

                currentCorrect++;

            }
            else {

                currentErrors++;

            }

        }


        int currentTyped =
                typedText.length();


        int totalTyped =
                totalTypedCharacters
                +
                currentTyped;


        int totalCorrect =
                totalCorrectCharacters
                +
                currentCorrect;


        int totalErrorCount =
                totalErrors
                +
                currentErrors;


        // =========================
        // ERRORS
        // =========================

        errorLabel.setText(
                "ERRORS: " +
                totalErrorCount
        );


        // =========================
        // ACCURACY
        // =========================

        double accuracy = 100.0;


        if (totalTyped > 0) {

            accuracy =
                    (
                            totalCorrect
                            *
                            100.0
                    )
                    /
                    totalTyped;

        }


        accuracyLabel.setText(
                String.format(
                        "ACCURACY: %.1f%%",
                        accuracy
                )
        );


        // =========================
        // WPM
        // =========================

        int elapsedSeconds =
                60 - timeLeft;


        if (elapsedSeconds > 0) {

            double minutes =
                    elapsedSeconds / 60.0;


            int wpm =
                    (int)
                    (
                            (
                                    totalCorrect
                                    /
                                    5.0
                            )
                            /
                            minutes
                    );


            wpmLabel.setText(
                    "WPM: " +
                    wpm
            );

        }


        // =========================
        // PASSAGE PROGRESS
        // =========================

        int progress = 0;


        if (passage.length() > 0) {

            progress =
                    (
                            currentTyped
                            *
                            100
                    )
                    /
                    passage.length();

        }


        progress =
                Math.min(
                        progress,
                        100
                );


        progressBar.setValue(
                progress
        );


        progressBar.setString(
                progress +
                "% Complete"
        );


        // =========================
        // AUTOMATICALLY CHANGE
        // PASSAGE
        // =========================

        if (
                currentTyped
                >=
                passage.length()
        ) {

            finishCurrentPassage();

        }

    }


    // =========================
    // FINISH CURRENT PASSAGE
    // =========================

    public static void finishCurrentPassage() {

        String typedText =
                typingArea.getText();


        String passage =
                PASSAGES[currentPassageIndex];


        for (
                int i = 0;
                i < typedText.length();
                i++
        ) {

            if (
                    i < passage.length()
                    &&
                    typedText.charAt(i)
                    ==
                    passage.charAt(i)
            ) {

                totalCorrectCharacters++;

            }
            else {

                totalErrors++;

            }

        }


        totalTypedCharacters +=
                typedText.length();


        // Move to next passage

        currentPassageIndex++;


        // If all passages are used,
        // start again from the first.

        if (
                currentPassageIndex
                >=
                PASSAGES.length
        ) {

            currentPassageIndex = 0;

        }


        typingArea.setText("");


        passageLabel.setText(
                PASSAGES[currentPassageIndex]
        );


        progressLabel.setText(
                "Passage " +
                (currentPassageIndex + 1)
                +
                " of "
                +
                PASSAGES.length
        );


        progressBar.setValue(0);


        updateKeyboardGuide();

    }


    // =========================
    // KEYBOARD GUIDE
    // =========================

    public static void updateKeyboardGuide() {

        String typedText =
                typingArea.getText();


        String passage =
                PASSAGES[currentPassageIndex];


        // Remove previous highlight

        for (
                JButton button :
                keyboardButtons.values()
        ) {

            button.setBackground(null);

        }


        if (
                typedText.length()
                >=
                passage.length()
        ) {

            fingerLabel.setText(
                    "Passage Complete!"
            );

            return;

        }


        char nextCharacter =
                Character.toLowerCase(
                        passage.charAt(
                                typedText.length()
                        )
                );


        JButton nextButton =
                keyboardButtons.get(
                        nextCharacter
                );


        if (nextButton != null) {

            nextButton.setBackground(
                    Color.YELLOW
            );

        }


        String finger =
                getFinger(
                        nextCharacter
                );


        fingerLabel.setText(
                "Next Key: "
                +
                Character.toUpperCase(
                        nextCharacter
                )
                +
                " | "
                +
                finger
        );

    }


    // =========================
    // FINGER GUIDE
    // =========================

    public static String getFinger(
            char key
    ) {

        if (
                "qaz".indexOf(key)
                >=
                0
        ) {

            return "Left Pinky";

        }


        if (
                "wsx".indexOf(key)
                >=
                0
        ) {

            return "Left Ring Finger";

        }


        if (
                "edc".indexOf(key)
                >=
                0
        ) {

            return "Left Middle Finger";

        }


        if (
                "rfvtgb".indexOf(key)
                >=
                0
        ) {

            return "Left Index Finger";

        }


        if (
                "yhnujm".indexOf(key)
                >=
                0
        ) {

            return "Right Index Finger";

        }


        if (
                "ik".indexOf(key)
                >=
                0
        ) {

            return "Right Middle Finger";

        }


        if (
                "ol".indexOf(key)
                >=
                0
        ) {

            return "Right Ring Finger";

        }


        if (
                key == 'p'
        ) {

            return "Right Pinky";

        }


        return "Use your thumb";

    }


    // =========================
    // END GAME
    // =========================

    public static void endGame() {

        if (timer != null) {

            timer.stop();

        }


        // Count whatever is currently typed

        String typedText =
                typingArea.getText();


        String passage =
                PASSAGES[currentPassageIndex];


        for (
                int i = 0;
                i < typedText.length();
                i++
        ) {

            if (
                    i < passage.length()
                    &&
                    typedText.charAt(i)
                    ==
                    passage.charAt(i)
            ) {

                totalCorrectCharacters++;

            }
            else {

                totalErrors++;

            }

        }


        totalTypedCharacters +=
                typedText.length();


        typingArea.setEnabled(false);


        // =========================
        // FINAL WPM
        // =========================

        int finalWPM =
                (int)
                (
                        totalCorrectCharacters
                        /
                        5.0
                );


        // =========================
        // FINAL ACCURACY
        // =========================

        double finalAccuracy =
                100.0;


        if (
                totalTypedCharacters > 0
        ) {

            finalAccuracy =
                    (
                            totalCorrectCharacters
                            *
                            100.0
                    )
                    /
                    totalTypedCharacters;

        }


        showResults(
                finalWPM,
                totalErrors,
                finalAccuracy
        );

    }


    // =========================
    // RESULTS SCREEN
    // =========================

    public static void showResults(
            int wpm,
            int errors,
            double accuracy
    ) {

        JPanel panel =
                new JPanel();


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        panel.setBorder(
                new EmptyBorder(
                        50,
                        60,
                        50,
                        60
                )
        );


        JLabel title =
                new JLabel(
                        "TIME'S UP!",
                        SwingConstants.CENTER
                );


        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        36
                )
        );


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel subtitle =
                new JLabel(
                        "Here is your TypeMoko performance",
                        SwingConstants.CENTER
                );


        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );


        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel wpmResult =
                new JLabel(
                        "WPM: " +
                        wpm,
                        SwingConstants.CENTER
                );


        wpmResult.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );


        wpmResult.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel errorResult =
                new JLabel(
                        "Errors: " +
                        errors,
                        SwingConstants.CENTER
                );


        errorResult.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );


        errorResult.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel accuracyResult =
                new JLabel(
                        String.format(
                                "Accuracy: %.1f%%",
                                accuracy
                        ),
                        SwingConstants.CENTER
                );


        accuracyResult.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );


        accuracyResult.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel charactersResult =
                new JLabel(
                        "Correct Characters: "
                        +
                        totalCorrectCharacters,
                        SwingConstants.CENTER
                );


        charactersResult.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );


        charactersResult.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        playAgainButton =
                new JButton(
                        "PLAY AGAIN"
                );


        playAgainButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );


        playAgainButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        playAgainButton.addActionListener(
                e -> startGame()
        );


        panel.add(title);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(subtitle);

        panel.add(
                Box.createVerticalStrut(30)
        );

        panel.add(wpmResult);

        panel.add(
                Box.createVerticalStrut(15)
        );

        panel.add(errorResult);

        panel.add(
                Box.createVerticalStrut(15)
        );

        panel.add(accuracyResult);

        panel.add(
                Box.createVerticalStrut(15)
        );

        panel.add(charactersResult);

        panel.add(
                Box.createVerticalStrut(30)
        );

        panel.add(playAgainButton);


        frame.setContentPane(
                panel
        );

        frame.revalidate();

        frame.repaint();

    }

}