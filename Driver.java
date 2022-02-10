import java.util.Scanner;

public class Driver {
    private static boolean gameOver = false;
    private static Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) {
        Board board = new Board();
        board.initialize();
        board.print();

        //Game loop
        String input = "";
        Boolean validMove = false;
        Boolean validInput;
        while(!gameOver) {
            do {
                validInput = true;
                input = scnr.nextLine();
                if (input.equalsIgnoreCase("w")) {
                    validMove = board.move(0, -1);
                } else if (input.equalsIgnoreCase("a")) {
                    validMove = board.move(-1, 0);
                } else if (input.equalsIgnoreCase("s")) {
                    validMove = board.move(0, 1);
                } else if (input.equalsIgnoreCase("d")) {
                    validMove = board.move(1, 0);
                } else if (input.equalsIgnoreCase("q")) {
                    System.out.println("Are you sure you would like to quit (y/n)?");
                    if (scnr.nextLine().equalsIgnoreCase("y")){
                        System.out.println("Game quit.");
                        board.print();
                        board.printStats();
                        System.exit(0);
                    } else {
                        System.out.println("Continuing game. Enter you're next move.");
                    }
                } else if (input.equalsIgnoreCase("r")) {
                    System.out.println("Are you sure you would like to restart (y/n)?");
                    if (scnr.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Game reset.");
                        board = new Board();
                        board.initialize();
                        board.print();
                    } else {
                        System.out.println("Continuing game. Enter you're next move.");
                    }
                } else {
                    validInput = false;
                }
            } while (!validInput);

            if (validMove) {
                board.addTile();
                board.incrementMoves();
            }
            System.out.println(input + (validMove ? " - valid move!" : " - invalid move."));
            board.print();
            board.printStats();

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