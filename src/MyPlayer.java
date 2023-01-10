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

//    public void Generate() {
//        int counter = 0;
//        System.out.println("HI");
//        for(int x = 1; x < 4; x++) {
//            for(int y = 0; y < x+1; y++) {
//                for(int z = 0; z < y+1; z++) {
//                    System.out.println(x + "-" + y + "-" + z);
//                   // allBoards.add(x * 100 + y * 10 + z);
//                    counter++;
//
//                }
//            }
//        }
//        System.out.println(counter);



 //   }
    public int[] bestMove(Board Board) {
        // finds the best move for a given board
        int chooseX;
        int chooseY;
        int[] chosen = new int[Board.state.length];
        int[] input = Board.state;
        int[] difference = new int[Board.state.length];
        boolean move = false;

        ArrayList<int[]> possible = possibleMoves(Board);
        for(int[] q: possible) {
            //System.out.println(q[0] +"-"+q[1]+"-"+q[2]+"-"+q[3]+"-"+q[4]+"-"+q[5]+"-"+q[6]+"-"+q[7]+"-"+q[8]+"-"+q[9]);
            for(int[] p: losing) {
                if(Arrays.equals(p, q)) {
                    chosen = q;
                    move = true;
                }
            }
        }
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


        } else {
//            System.out.println("No Best choice");
            chooseY = 0;
            chooseX = Board.state[0] - 1;
            int[] bestMove = {chooseX, chooseY};
            return (bestMove);
        }

//        System.out.println("Should never be here!");
        return(new int[]{1, 2, 3});




    }

    public int Losing() {
        // find all losing boards
        ArrayList<int[]> losing = new ArrayList<int[]>();
        for(int x = 1; x < 11; x++) {
//            System.out.println("Part one");
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

//        for(int[] b : losing) {
//            System.out.println(b[0]+"-"+b[1]+"-"+b[2] + "-" + b[3] + "-" + b[4] + "-" + b[5] + "-" + b[6] + "-" + b[8] + "-" + b[9]);
//        }
        return 1;
    }
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

                if (skip == true) {
                    skip = false;
                } else {
                    for(int[] loss: losing) {
                        if (Arrays.equals(loss, results)) {
                            return;

                        }
                    }
                }
            }
        }
        losing.add(Board.state);
        System.out.println(Board.state[0] + "-" + Board.state[1] + "-" + Board.state[2] + "-" + Board.state[3] + "-" + Board.state[4] + "-" + Board.state[5] + "-" + Board.state[6] + "-" + Board.state[7] + "-" + Board.state[8] + "-" + Board.state[9]);
    }

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

    public Board changeBoardFormat(Chip[][] board) {
        // Changes board format from a 2d arraylist of chips either alive or dead to an array of integers
        // for each number of chips in each column
        ArrayList<Integer> newFormat = new ArrayList<Integer>();
        for (int col = 0; col < board[0].length; col++) {
            Integer count = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col].isAlive) {
                    count = count + 1;
                }
            }
            newFormat.add(count);
        }

        int[] work = new int[board.length];
        for(int q = 0; q < board.length; q++) {
            work[q] = newFormat.get(q);
        }
        return(new Board(work));
    }



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

