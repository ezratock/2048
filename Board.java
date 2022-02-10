import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private int[][] board;
    private static Random rand = new Random();
    private int moves = 0;

    public Board() {
        this.board = board = new int[4][4];
    }

    public Board(int[][] board) {
        this.board = board = board;
    }

    public int[][] getBoard() {
        return new int[][]{board[0].clone(), board[1].clone(), board[2].clone(), board[3].clone()};
    }

    //Initializes board with 2 new tiles in random positions
    public void initialize(){
        int[] previousPosition = new int[]{-1,-1};
        board = new int[4][4];
        for (int i = 0; i < 2; i++) {
            addTile();
        }
    }

    //Adds a new tile to the board: 80% chance of 2, 20% chance of 4
    public void addTile() {
        int[] position = newPosition();
        while (board[position[1]][position[0]] != 0){
            position = newPosition();
        }
        board[position[1]][position[0]] = (int) Math.floor(rand.nextFloat() / 0.8) * 2 + 2;
    }

    //returns a random board position
    private static int[] newPosition() {
        return new int[]{rand.nextInt(4), rand.nextInt(4)};
    }

    //Prints 37x17 ASCII-art representation of the board
    public void print() {
        System.out.println("+--------+--------+--------+--------+");
        int num;
        int numLen;
        for (int col = 0; col < 4; col++) {
            System.out.print("|        |        |        |        |\n|");
            for (int row = 0; row < 4; row++) {
                num = board[col][row];
                numLen = num == 0 ? 1 : (int) Math.floor(Math.log10(num)) + 1;
                System.out.print(" ".repeat((8-numLen)/2) + (num == 0 ? " " : num) + " ".repeat((9-numLen)/2) + "|");
            }
            System.out.println("\n|        |        |        |        |\n+--------+--------+--------+--------+");
        }
    }

    public void printStats(){
        System.out.printf("Highest number: %d\nMoves: %d\n", getMax(), moves);
    }

    public boolean move(int x, int y) {
        boolean returnVar = false;
        //starting pos at the far-side most row/col
        int sign = x == 0 ? y : x;
        int start = (int) (sign * 1.5 + 1.5);
        int[] pos = new int[]{start, start};
        //skips first row/col
        pos = new int[]{pos[0] - x, pos[1] - y};

        //performing movement:
        boolean[][] merged = new boolean[4][4];
        int tile;
        int checkTile;
        ArrayList<Integer> valid = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                tile = board[pos[1]][pos[0]];

                if (tile != 0) {
                    for (int k = 1; valid.contains(pos[1] + y * k) && valid.contains(pos[0] + x * k); k++) {
                        checkTile = board[pos[1] + y * k][pos[0] + x * k];
                        //don't merge with an already merged tile
                        if (merged[pos[1] + y * k][pos[0] + x * k]) {
                            break;
                        }
                        //stop looking if you hit a non-empty
                        else if (checkTile != 0 && checkTile != tile) {
                            break;
                        }

                        //otherwise, move the tile over 1
                        returnVar = true;
                        board[pos[1] + y * k][pos[0] + x * k] = tile;
                        board[pos[1] + y * (k-1)][pos[0] + x * (k-1)] = 0;
                        //if the tiles match, merge
                        if (tile == checkTile) {
                            board[pos[1] + y * k][pos[0] + x * k] = tile * 2;
                            merged[pos[1] + y * k][pos[0] + x * k] = true;
                            break;
                        }
                    }
                }
                //next ROW if horizontal moving, next COLUMN if vertical moving
                pos = new int[]{pos[0] - y, pos[1] - x};
            }
            //next COLUMN if horizontal moving, next ROW if vertical moving
            pos = new int[]{x==0 ? start : pos[0] - x, y == 0 ? start : pos[1] - y};
        }

        return returnVar;
    }

    public void incrementMoves() {
        moves ++;
    }

    public int getMax() {
        int max = 0;
        for (int[] i : board) {
            for (int j : i) {
                max = Math.max(max, j);
            }
        }
        return max;
    }
}
