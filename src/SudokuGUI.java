import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private static JTextField[][] inputFields = new JTextField[9][9];

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
    }
}
