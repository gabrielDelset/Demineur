//voici le code que nous avons pour l'instant générer
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
        }
        if (e.getSource()==itemQuit){
            System.exit( 0);
        }else if (e.getSource()==itemNetwork){
            connectNetwork();
        }
    }

    private void initGrid() {
        for (int row = 0; row < champ.getHeight(); row++) {
            for (int col = 0; col < champ.getWidth(); col++) {
                MineLabel minelabel = minesLabels[row][col];
                minelabel.setBorder(new LineBorder(Color.BLACK, 1));
                minelabel.setHorizontalAlignment(JLabel.CENTER);
                minelabel.setVisible(true);
                if (champ.isMine(row, col)) {

                    minelabel.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Fin de partie", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    });
                } else {
                    minelabel.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            minelabel.setText(champ.getNbMinesProximite(minelabel.getRow(), minelabel.getCol()) + "");
                            minelabel.setVisible(true);
                        }
                    });
                }
            }
        }
    }



    private class MineLabel extends JLabel {
        private int row, col;
        public MineLabel(int mineRow, int mineCol) {
            super("");
            setPreferredSize(new Dimension(50, 50));
            row = mineRow;
            col = mineCol;
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (champ.isMine(row, col)) {
                        JOptionPane.showMessageDialog(null, "Game over", "You lost", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
        public int getRow() { return row; }
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