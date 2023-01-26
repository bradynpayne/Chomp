import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPlayer {
    public Chip[][] gameBoard;
    public int[] columns;

    // creates an arraylist of all the losing boards
    public ArrayList<int[]> losing = new ArrayList<>();
    //Calls losing
    public int L = Losing();

    public MyPlayer() {
        columns = new int[10];
    }


// The first two functions, Losing and pMoves are a part of finding the losing boards
    public int Losing() {
        // Find every single possible board
        ArrayList<int[]> losing = new ArrayList<int[]>();
        for(int x = 1; x < 11; x++) {
            for (int y = 0; y < x + 1; y++) {
                for (int z = 0; z < y + 1; z++) {
                    for(int q = 0; q < z + 1; q++) {
                        for (int t = 0; t < q + 1; t++) {
                            for(int p = 0; p < t + 1; p++) {
                                for (int s = 0; s < p + 1; s++) {
                                    for (int e = 0; e < s + 1; e++) {
                                        for (int r = 0; r < e + 1; r++) {
                                            for (int w = 0; w < r + 1; w++) {
                                                Board b = new Board(new int[]{x, y, z, q, t, p, s, e, r, w});
                                                // Each board goes into pMoves, which is a copy of the possible
                                                // moves function that checks each possible move, and if no move
                                                // leads to a losing board, that board is also added to the losing
                                                // board list. I was not able to fully integrate possible moves
                                                // and pMoves into one function, so both are still neccesary
                                                // for the program to run.
                                                pMoves(b);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        return 1;
    }
    // pMoves finds out whether each possible board, input from the Losing function, is winning or losing
    public void pMoves(Board Board) {
        // Array able to be iterated thru w/ the initial values
        int[] setUp = Board.state;

        boolean skip = true;

        // For each column

        for (int q = 0; q < setUp.length; q++) {
            // For each square in that column
            for (int o = 1; o < setUp[q] + 1; o++) {

                // Array with the results that is reset before, and will be updated
                int[] results = setUp.clone();
                // Results for the row being changed = square chosen - 1
                results[q] = o - 1;
                // For the other columns: if same number as taken or greater, subtract 1
                for (int i = q + 1; i < setUp.length; i++) {
                    if (setUp[i] > results[q] - 1) {
                        results[i] = o - 1;
                    }
                }
// Skips the first board that is generated as a possible, which is taking the poison chip
                if (skip == true) {
                    skip = false;
                } else {
                    for(int[] loss: losing) {
                        // For each board in losing boards, if it is equal to any of the possible boards,
                        // then this is not a losing board and should skip the rest of the function.
                        if (Arrays.equals(loss, results)) {
                            return;

                        }
                    }
                }
            }
        }
        // If it has checked every losing board and none of them are achievable from the given board,
        // add this board to the list of losers
        losing.add(Board.state);
//        System.out.println(Board.state[0] + "-" + Board.state[1] + "-" + Board.state[2] + "-" + Board.state[3] + "-" + Board.state[4] + "-" + Board.state[5] + "-" + Board.state[6] + "-" + Board.state[7] + "-" + Board.state[8] + "-" + Board.state[9]);
    }
    // The next four functions, given a board input, find and make the best move
    public Board changeBoardFormat(Chip[][] board) {
        // Changes board format from a 2d arraylist of chips either alive or dead to an array of integers
        // I want it to be represented as the number of chips "alive" in each column
        ArrayList<Integer> newFormat = new ArrayList<Integer>();
        for (int col = 0; col < board[0].length; col++) {
            Integer count = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col].isAlive) {
                    count = count + 1;
                }
            }
            // Iterates through the columns and rows to find out the number alive in each row.
            newFormat.add(count);
        }

        int[] work = new int[board.length];
        for(int q = 0; q < board.length; q++) {
            work[q] = newFormat.get(q);
        }
        // returns a board with the changed form
        return(new Board(work));
    }

    // Best move finds the best move from a given input board and outputs that move

    public int[] bestMove(Board Board) {
        // finds the best move for a given board
        int chooseX;
        int chooseY;
        int[] chosen = new int[Board.state.length];
        int[] input = Board.state;
        int[] difference = new int[Board.state.length];
        boolean move = false;

        // Does possible moves to find every possible move
        ArrayList<int[]> possible = possibleMoves(Board);

        // If the possible board matches a losing board, pick that one
        for(int[] q: possible) {
            for(int[] p: losing) {
                if(Arrays.equals(p, q)) {
                    chosen = q;
                    move = true;
                }
            }
        }
        // If there is a move, based on the difference between the boards, calculate the
        // coordinates of the best move
        if(move == true) {
            for (int i = 0; i < input.length; i++) {
                difference[i] = input[i] - chosen[i];
            }
            for(int m = 0; m < input.length; m++) {
                if(difference[m] != 0) {
                    chooseY = m;
                    chooseX = chosen[m];
                    int[] bestMove = {chooseX,chooseY};
                    return(bestMove);
                }
            }


        }
        // If there is no best move, and it is a losing board, pick the top chip on the first column.
        // This move will never pick the poison chip unless it is the only move left, because if there
        // is nothing above the poision chip, it is either a winning board or the only chip left.
        else {
            chooseY = 0;
            chooseX = Board.state[0] - 1;
            int[] bestMove = {chooseX, chooseY};
            return (bestMove);
        }

//        System.out.println("Should never be here!");

        // If the code gets here, from debugging, something is very wrong.
        return(new int[]{1, 2, 3});




    }

    // possibleMoves is used to find the possible moves from a given board.
    // used as a part of the bestMoves function
    public ArrayList<int[]> possibleMoves(Board Board) {
        // Array able to be iterated thru w/ the initial values
        int[] setUp = Board.state;

        boolean skip = true;
        ArrayList<int[]> outputs = new ArrayList<int[]>();

        // For each column

        for (int q = 0; q < setUp.length; q++) {
            // For each square in that column
            for (int o = 1; o < setUp[q] + 1; o++) {

                // Array with the results that is reset before, and will be updated
                int[] results = setUp.clone();
                // Results for the row being changed = square chosen - 1
                results[q] = o - 1;
                // For the other columns: if same number as taken or greater, subtract 1
                for (int i = q + 1; i < setUp.length; i++) {
                    if (setUp[i] > results[q] - 1) {
                        results[i] = o - 1;
                    }
                }

                if (skip == true) {
                    skip = false;
                } else {
                    outputs.add(results);
                }
            }
        }
        return outputs;
    }

// the move function makes the moves itself.
    public Point move(Chip[][] pBoard) {

        // gameBoard is the arraylist of chips on and off
        gameBoard = pBoard;

        // newBoard is now an array of numbers that shows the state of the board
        Board newBoard = changeBoardFormat(pBoard);

        // theMove is now an int array of 2 integers that give the x and y coordinates of the best move
        int[] theMove = bestMove(newBoard);

        // point myMove has coordinates of this point
        Point myMove = new Point(theMove[0], theMove[1]);

        // return myMove as the coordinates to play
        return myMove;
    }

}

