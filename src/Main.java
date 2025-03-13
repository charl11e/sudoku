import javax.swing.*;

public class Main extends JFrame {

    private JLabel jsTEST;
    private JTextField textField1;

    public Main() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
