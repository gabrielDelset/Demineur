//annalyse moi ce code
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.* ;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class GUI extends JPanel implements ActionListener {
    MineLabel minesLabels[][];
    Champ champ;
    JPanel grid;

    Main main;

    public JMenuBar mb = new JMenuBar();
    JMenuItem itemQuit, itemNetwork, e3, e4, e5, e6;

    GUI(Champ champ) {
        this.champ = champ;
        int width = this.champ.getWidth();
        champ.emptyMines();
        champ.placeMines();
        int height = this.champ.getHeight();
        minesLabels = new MineLabel[height][width];

        //barre de menu

        JMenu m = new JMenu("file");
        JMenu c = new JMenu("connect");
        itemQuit = new JMenuItem("quit");
        itemNetwork = new JMenuItem("connect");
        mb.add(m);
        m.add(itemQuit);
        m.add(itemNetwork);


        this.grid = new JPanel();

        setLayout(new BorderLayout());

        this.grid.setLayout(new GridLayout(height, width));

        for (int row = 0; row < champ.getHeight(); row++) {
            for (int col = 0; col < champ.getWidth(); col++) {
                MineLabel minelabel = new MineLabel(row, col);
                minesLabels[row][col] = minelabel;     //
                grid.add(minelabel);
            }
        }
        add(this.grid, BorderLayout.CENTER);
        initGrid();
        JButton restartButton = new JButton();
        restartButton.addActionListener(this);
        //  add(restartButton, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof RestartButton) {
            champ.emptyMines();
            champ.placeMines();
            initGrid();
            checkWin();
        }
        if (e.getSource()==itemQuit){
            System.exit( 0);
        }else if (e.getSource()==itemNetwork){
            connectNetwork();
            checkWin();
        }
    }

    private void initGrid() {
        for (int row = 0; row < champ.getHeight(); row++) {
            for (int col = 0; col < champ.getWidth(); col++) {                  //2 boucles for pour parcourir tout le tableau
                MineLabel minelabel = minesLabels[row][col];
                minelabel.setBorder(new LineBorder(Color.BLACK, 1));    //créer les bordures des cases
                minelabel.setHorizontalAlignment(JLabel.CENTER);                //Met les labels au centres
                minelabel.setVisible(true);
                if (champ.isMine(row, col)) {                                   //vérifie si le champ est une mine, et si oui déclare un événement qui permet de mettre din a la partie
                    minelabel.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Fin de partie", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    });
                } else {
                    minelabel.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            minelabel.setText(champ.getNbMinesProximite(minelabel.getRow(), minelabel.getCol()) + "");      //permet d'afficher le nombre de mine a proximité
                            minelabel.setVisible(true);
                            checkWin();
                        }
                    });
                }
            }
        }
    }

    private void expandEmptyArea(int row, int col) {             //fonction qui sert a l'expension mais elle ne fonctionne pas bien
        if(!champ.isInBounds(row, col)){
            return;
        }
        if(champ.isMine(row, col)){
            return;
        }
        if(!champ.isEmpty(row, col)){
            return;
        }
        MineLabel minelabel = minesLabels[row][col];
        minelabel.setText(champ.getNbMinesProximite(row, col) + "");
        minelabel.setVisible(true);
        if(champ.getNbMinesProximite(row, col) == 0){
            expandEmptyArea(row-1, col-1);
            expandEmptyArea(row-1, col);
            expandEmptyArea(row-1, col+1);
            expandEmptyArea(row, col-1);
            expandEmptyArea(row, col+1);
            expandEmptyArea(row+1, col-1);
            expandEmptyArea(row+1, col);
            expandEmptyArea(row+1, col+1);
        }
    }

    private void checkWin() {
        boolean win = true;
        for (int row = 0; row < champ.getHeight(); row++) {
            for (int col = 0; col < champ.getWidth(); col++) {
                MineLabel minelabel = minesLabels[row][col];
                if (!champ.isMine(row, col) && minelabel.isVisible()) {
                    win = false;
                    break;
                }
            }
        }
        if (win) {
            JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class MineLabel extends JLabel {
        private int row, col;
        public MineLabel(int mineRow, int mineCol) {                           // constructeur de la classe MineLabel
            super("");
            setPreferredSize(new Dimension(50, 50));
            row = mineRow;
            col = mineCol;
            this.addMouseListener(new MouseAdapter() {                          // ajout d'un MouseListener pour détecter un clic sur une case
                public void mousePressed(MouseEvent e) {
                    if (champ.isMine(row, col)) {                               // si cliqué sur mine : fin de la partie
                        JOptionPane.showMessageDialog(null, "Game over", "You lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
        public int getRow() { return row; }                                  //retournes les coordonnées de la case
        public int getCol() { return col; }
    }



    private class RestartButton extends JButton {
        public RestartButton() {
            super("Restart");
        }
    }

    //création de la barre des menus



    void connectNetwork() {
        System.out.println("Try to connect");
        try {
            Socket sock = new Socket("localhost", 10000);
            System.out.println("connected to server");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }




}