import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SudokuGUI {
    private static final JTextField[][] inputFields = new JTextField[9][9];

    public static void main(String[] args) {
        JFrame jframe = new JFrame("Sudoku Solver");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(500,500);

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(9, 9));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Arial", Font.BOLD, 18));
                field.setBorder(makeBold(row, col));
                inputFields[row][col] = field;
                inputs.add(field);
            }
        }

        jframe.add(inputs, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 2));

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> getInput());
        buttons.add(solveButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearInput());
        buttons.add(clearButton);

        jframe.add(buttons, BorderLayout.SOUTH);

        jframe.setVisible(true);
    }

    private static void getInput() {
        int[][] values = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = inputFields[row][col].getText();
                if (!text.matches("[1-9]") && !text.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if (text.isEmpty()) {
                    values[row][col] = 0;
                }
                else {
                    values[row][col] = Integer.parseInt(text);
                }
            }
        }
        int[][] result = Convertor.solve(values);

        // TODO what to do when unsat
        if (false) {
            JOptionPane.showMessageDialog(null, "No solution", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            fillResult(result);
        }
    }

    private static void fillResult(int[][] result) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                inputFields[row][col].setText(String.valueOf(result[row][col]));
            }
        }
    }

    private static Border makeBold(int row, int col) {
        int top = 0;
        int left = 0;
        int right = 0;

        int bottom = 0;
        if ( row == 2 || row == 5 || row == 8) {
            bottom = 2;
        }

        if (row == 0) {
            top = 2;
        }

        if (col == 0 || col == 3 || col == 6) {
            left = 2;
        }

        if (col == 8) {
            right = 2;
        }

        return BorderFactory.createMatteBorder(1 + top, 1 + left , 1 + bottom, 1 + right, Color.BLACK);
    }

    private static void clearInput() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                inputFields[row][col].setText("");
            }
        }
    }
}
