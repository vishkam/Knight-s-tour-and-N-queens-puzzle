
//importing libraries
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
public class Knight extends JFrame{
    // Declaring переменные
    private JPanel contents;
    private JPanel inputContainer;
    private JLabel inputText;
    private JTextField inputField;
    private JButton startButton;
    private JButton moveButton;

    private JButton[][] squares;
    protected int[] row;
    protected int[] col;
    boolean[][] covered;
    private int SIZE;

    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();

    private ImageIcon knightIcon = new ImageIcon(s + "\\src\\KTsarvina.png");
    private Image imgKnight = knightIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
    private ImageIcon knight = new ImageIcon(imgKnight);
    private int count = 1;

    public static void main(String[] args){
        Knight knightTour = new Knight();
        knightTour.setVisible(true);
    }

    public Knight(){
        super("MAKSUDOVA SARVINOZ Production: Knight's Tour");
        contents = new JPanel();
        contents.setBackground(Color.WHITE);

        inputContainer = new JPanel(new FlowLayout());
        inputContainer.setBackground(Color.WHITE);

        // creating the objects
        ButtonHandler buttonHandler = new ButtonHandler();
        inputText = new JLabel("Enter the size");
        inputField = new JTextField("8", 10);
        startButton = new JButton("Start");
        moveButton = new JButton("Move");


        inputContainer.add(inputText);
        inputContainer.add(inputField);
        inputContainer.add(startButton);
        inputContainer.add(moveButton).setVisible(false);

        add(inputContainer, BorderLayout.NORTH);
        add(contents, BorderLayout.SOUTH);

        startButton.addActionListener(buttonHandler);
        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                processClick();
            }
        });

        setSize(500, 555);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializing(){
        contents.setLayout(new GridLayout(SIZE ,SIZE));
        squares = new JButton[SIZE][SIZE];
        covered = new boolean[SIZE][SIZE];
        row = new int[SIZE * SIZE];
        col = new int[SIZE * SIZE];

        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++) {
                covered[i][j] = false;
            }

        for(int i = 0; i < SIZE * SIZE; i++){
            row[i] = 0;
            col[i] = 0;
        }
    }


    public void drawEmptyDesk(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                squares[i][j] = new JButton();
                if((i + j) % 2 != 0){
                    squares[i][j].setBackground(Color.BLUE);
                }
                contents.add(squares[i][j]);
                squares[i][j].setPreferredSize(new Dimension((int)(8 * 60/SIZE), (int)(8 * 60/SIZE)));
            }
        }
        squares[row[0]][col[0]].setIcon(knight);
        squares[row[0]][col[0]].setBackground(Color.DARK_GRAY);
    }

    public boolean move(int i, int j, int countSteps, int size){
        if(covered[i][j])
            return false;

        covered[i][j] = true;

        row[countSteps] = i;
        col[countSteps] = j;

        if(countSteps == size * size - 1)
            return true;

        if(isValidMove( i + 2, j + 1, size) && move(i + 2, j + 1, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i + 1, j + 2, size) && move(i + 1, j + 2, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i - 1, j + 2, size) && move(i - 1, j + 2, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i - 2, j + 1, size) && move(i - 2, j + 1, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i - 2, j - 1, size) && move(i - 2, j - 1, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i - 1, j - 2, size) && move(i - 1, j - 2, countSteps + 1, size)){
            return true;
        }

        if(isValidMove( i + 1, j - 2, size) && move(i + 1, j - 2, countSteps + 1, size)){
            return true;
        }

        if(isValidMove(i + 2, j - 1, size) && move(i + 2, j - 1, countSteps + 1, size)){
            return true;
        }
        countSteps--;
        covered[i][j] = false;
        return false;
    }

    public boolean isValidMove(int i, int j, int size){
        if(i >= 0 && j >= 0 && i < size && j < size)
            return true;
        return false;
    }

    private class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) throws NumberFormatException{
            String str = inputField.getText();
            try {
                SIZE = Integer.parseInt(str);
                if(SIZE == 2 || SIZE == 3 || SIZE == 4)
                    JOptionPane.showMessageDialog(null, "No solutions", "Warning Message", JOptionPane.WARNING_MESSAGE);
                else {
                    initializing();
                    drawEmptyDesk();
                    startButton.setEnabled(false);
                    inputField.setEditable(false);
                    moveButton.setVisible(true);
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Inappropriate format of the number", "Warning Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void moveKnight() {
        squares[row[count - 1]][col[count - 1]].setIcon(null);
        squares[row[count]][col[count]].setIcon(knight);
        squares[row[count]][col[count]].setBackground(Color.DARK_GRAY);
       count++;
    }

    public void processClick() {
        if(count == 1) {
            startButton.setVisible(false);
            move(0, 0, 0, SIZE);
        }
        if(count == SIZE * SIZE){
            moveButton.setEnabled(false);
        }else
            moveKnight();
    }

}
