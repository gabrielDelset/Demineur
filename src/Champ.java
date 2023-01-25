
import java.util.Random;

public class Champ {
    final static int N_MINES = 10;
    final static int HEIGHT = 10;
    final static int WIDTH = 10;
    private boolean [][] mines = new boolean[HEIGHT][WIDTH];

    private Random rng = new Random();

    public void emptyMines() {
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                mines[row][col] = false;
            }
        }
    }

    public void placeMines() {
        for (int i = 0; i < N_MINES; i++) {
            int x, y;
            while (mines[y = rng.nextInt(HEIGHT)][x = rng.nextInt(WIDTH)]);
            mines[y][x] = true;
        }
    }

    public void displayMines() {
        boolean darkCol = false;

        for (boolean [] row: mines) {
            boolean darkCell = darkCol = !darkCol;
            for (boolean mine : row) {
                darkCell = !darkCell;
                System.out.print(darkCell ? mine ? "\033[48;2;210;0;0m\033[38;2;0;0;0m  \033[0m"
                        : "\033[48;2;40;40;40m\033[38;2;0;0;0m  \033[0m"
                        : mine ? "\033[48;2;255;0;0m\033[38;2;0;0;0m  \033[0m"
                        : "\033[48;2;50;50;50m\033[38;2;0;0;0m  \033[0m");
            }
            System.out.print("\n");
        }
    }

    public int getNbMinesProximite(int row, int col) {
        int nbMinesProximite = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int currentRow = row + i;
                int currentCol = col + j;
                if (currentRow >= 0 && currentRow < getHeight() && currentCol >= 0 && currentCol < getWidth() && isMine(currentRow, currentCol)) {
                    nbMinesProximite++;
                }
            }
        }
        return nbMinesProximite;
    }

    public boolean isEmpty(int row, int col) {
        if(!isInBounds(row, col)){
            return false;
        }
        if(isMine(row, col)){
            return false;
        }
        return true;
    }

    public boolean isInBounds(int row, int col) {
// Vérifie si les coordonnées de la case sont valides (dans les limites de la grille)
        if (row < 0 || row >= getHeight()) {
            return false;
        }
        if (col < 0 || col >= getWidth()) {
            return false;
        }
        return true;
    }

    public int getWidth() {
        return mines[0].length;
    }

    public int getHeight() {
        return mines.length;
    }

    public boolean isMine(int row, int col) {
        return mines[row][col];
    }

    public void didier() {
        // do nothing
    }



}
