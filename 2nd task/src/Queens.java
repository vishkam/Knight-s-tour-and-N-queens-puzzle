//importing Libries
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 Application Programming in Java (001)
 Professor: Abhijit Tarawade
 ID: U1710276
 Name: MAKSUDOVA SARVINOZ
 */
public class Queens extends JFrame{
    private int SIZE;
    boolean isShown = false;
    private JPanel contents;
    private JPanel inputContainer;
    private JButton start;
    private JLabel inputText;
    private JTextField inputField;
    private JButton startButton;
    private JButton[][] squares;
    private ImageIcon iconQueen;
    private Image imgQueen;
    private ImageIcon imgIconQueen;
    //


    public static void main(String[]args){
        Queens queen = new Queens();
        queen.setVisible(true);
    }

    public Queens(){
        super("MAKSUDOVA SARVINOZ Production: Queen Puzzle");

        contents = new JPanel();
        contents.setBackground(Color.WHITE);

        inputContainer = new JPanel();
        inputContainer.setBackground(Color.WHITE);

        ButtonHandler buttonHandler = new ButtonHandler();
        inputText = new JLabel("Enter the size");
        inputField = new JTextField("8", 10);
        startButton = new JButton("Start");


        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        iconQueen = new ImageIcon(s + "\\src\\QSarvina.png");

        imgQueen = iconQueen.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imgIconQueen = new ImageIcon(imgQueen);

        inputContainer.add(inputText);
        inputContainer.add(inputField);
        inputContainer.add(startButton);

        add(inputContainer, BorderLayout.NORTH);
        add(contents, BorderLayout.SOUTH);

        startButton.addActionListener(buttonHandler);

        setSize(550, 550);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String str = inputField.getText();
            try {
                SIZE = Integer.parseInt(str);
                if(SIZE == 2 || SIZE == 3 || SIZE == 4)
                    JOptionPane.showMessageDialog(null, "No solutions", "Warning Message", JOptionPane.WARNING_MESSAGE);
                else {
                    squares = new JButton[SIZE][SIZE];
                    int[] queen = new int[SIZE];
                    startButton.setVisible(false);
                    inputField.setEditable(false);
                    drawDesk();
                    enumerate(queen, 0);
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Inappropriate format of the number", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public boolean isValid(int[]queen, int n){
        for(int i = 0; i < n; i++){
            if(queen[i] == queen[n])
                return false;
            if((queen[i] - queen[n]) == (n - i))
                return false;
            if((queen[n] - queen[i]) == (n - i))
                return false;
        }
        return true;
    }

    public void drawDesk(){
        contents.setLayout(new GridLayout(SIZE, SIZE));
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                squares[i][j] = new JButton();
                if((i + j) % 2 != 0){
                    squares[i][j].setBackground(Color.blue);
                }
                contents.add(squares[i][j]);
                squares[i][j].setPreferredSize(new Dimension((int)(8 * 60/SIZE), (int)(8 * 60/SIZE)));

            }
        }
    }


    private void showDesk(int[] queen) {
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(queen[i] == j)
                    squares[i][j].setIcon(imgIconQueen);
            }
        }
    }

    public void enumerate(int[]queen, int k){
        int col = queen.length;

        if(isShown)
            return;

        if(k == col) {
            showDesk(queen);
            isShown = true;
        }
        else{
            for(int i = 0; i < col; i++){
                queen[k] = i;
                if(isValid(queen, k))
                    enumerate(queen, k + 1);
            }
        }
    }

}
