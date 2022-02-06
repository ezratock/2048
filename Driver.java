import java.util.Scanner;

public class Driver {
    private static boolean gameOver = false;
    private static Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        board.print();

        //Game loop
        String input;
        Boolean validMove = false;
        while(!gameOver) {
            input = scnr.nextLine();
            if (input.equals("w")) {
                validMove = board.move(0,-1);
            } else if (input.equals("a")) {
                validMove = board.move(-1,0);
            } else if (input.equals("s")) {
                validMove = board.move(0,1);
            } else if (input.equals("d")) {
                validMove = board.move(1,0);
            }

            if (validMove) {
                board.addTile();
            }
            System.out.println(input + (validMove ? " - valid move!" : " - invalid move."));
            board.print();

            //Ends that game if there are no possible moves
            Boolean movePossible = false;
            Board testBoard;
            for (int[] direction : new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}}) {
                testBoard = new Board(board.getBoard().clone());
                if (testBoard.move(direction[0], direction[1])) {
                    movePossible = true;
                }
            }
            if (!movePossible) {
                gameOver = true;
            }

        }

        System.out.println("Game over");
    }
}