import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe implements ActionListener {
    JFrame frame;
    JButton[] buttons = new JButton[9];
    boolean userTurn = true;
    String userSymbol = "X";
    String computerSymbol = "O";
    Random rand = new Random();

    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(3, 3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create 9 buttons and add to the frame
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].addActionListener(this);
            frame.add(buttons[i]);
        }

        selectSymbol(); // Ask user for X or O

        frame.setVisible(true);

        // Let computer start if user is O
        if (!userTurn) {
            computerMove();
        }
    }

    private void selectSymbol() {
        String[] options = {"X", "O"};
        int choice = JOptionPane.showOptionDialog(
            frame,
            "Choose your symbol:",
            "Player Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 1) { // User chose O
            userSymbol = "O";
            computerSymbol = "X";
            userTurn = false;
        } else {
            userSymbol = "X";
            computerSymbol = "O";
            userTurn = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!userTurn) return; // block input if it's computer's turn

        JButton btnClicked = (JButton) e.getSource();
        if (!btnClicked.getText().equals("")) return;

        btnClicked.setText(userSymbol);

        if (checkWin(userSymbol)) {
            JOptionPane.showMessageDialog(frame, userSymbol + " wins!");
            resetGame();
            return;
        }

        if (isDraw()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetGame();
            return;
        }

        userTurn = false;
        computerMove();
    }

    private void computerMove() {
        int index;
        do {
            index = rand.nextInt(9);
        } while (!buttons[index].getText().equals(""));

        buttons[index].setText(computerSymbol);

        if (checkWin(computerSymbol)) {
            JOptionPane.showMessageDialog(frame, computerSymbol + " wins!");
            resetGame();
            return;
        }

        if (isDraw()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetGame();
            return;
        }

        userTurn = true;
    }

    public boolean checkWin(String symbol) {
        int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] pos : winPositions) {
            String a = buttons[pos[0]].getText();
            String b = buttons[pos[1]].getText();
            String c = buttons[pos[2]].getText();
            if (a.equals(symbol) && b.equals(symbol) && c.equals(symbol)) return true;
        }

        return false;
    }

    public boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    public void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
        }

        // Let user select again on reset
        selectSymbol();

        if (!userTurn) {
            computerMove();
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
