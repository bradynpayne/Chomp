import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPlayer {
    public Chip[][] gameBoard;
    public int[] columns;

    public ArrayList<int[]> losing = Losing();

    ArrayList<Board> allBoards = new ArrayList<>();


    public MyPlayer() {
        columns = new int[10];
        //Generate();
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
        int chooseX;
        int chooseY;
        int[] chosen = new int[Board.state.length - 1];
        int[] input = Board.state;
        int[] difference = new int[Board.state.length - 1];
        boolean move = false;

        ArrayList<int[]> possible = possibleMoves(Board);
        for(int[] q: possible) {
            System.out.println(q[0] +"-"+q[1]+"-"+q[2]+"-"+q[3]+"-"+q[4]+"-"+q[5]+"-"+q[6]+"-"+q[7]+"-"+q[8]+"-"+q[9]);
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
            System.out.println("No Best choice");
            chooseY = 0;
            chooseX = 0;
            int[] bestMove = {chooseX, chooseY};
            return (bestMove);
        }

        System.out.println("Should never be here!");
        return(new int[]{1, 2, 3});




    }

    public ArrayList<int[]> Losing() {
        ArrayList<int[]> losing = new ArrayList<int[]>();
        for(int x = 1; x < 11; x++) {
            System.out.println("Part one");
            for (int y = 0; y < x + 1; y++) {
                for (int z = 0; z < y + 1; z++) {
                    for(int q = 0; q < z + 1; q++) {
                        for (int t = 0; t < q + 1; t++) {
                            for(int p = 0; p < t + 1; p++) {
                                for (int s = 0; s < p + 1; s++) {
                                    for (int e = 0; e < s + 1; e++) {
                                        for (int r = 0; r < e + 1; r++) {
                                            for (int w = 0; w < r + 1; w++) {
                                                boolean loser = true;
                                                Board b = new Board(new int[]{x, y, z, q, t, p, s, e, r, w});
                                                ArrayList<int[]> bOutputs = possibleMoves(b);

                                                for (int[] possibility : bOutputs) {
                                                    for (int[] loss : losing) {
                                                        if (Arrays.equals(possibility, loss)) {
                                                            loser = false;
                                                        }
                                                    }

                                                }
                                                if (loser == true) {
                                                    losing.add(b.state);
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

        }


//        losing.add(new int[] {2,1,0,0,0,0,0,0,0,0});
//        losing.add(new int[] {2,1,1,0,0,0,0,0,0,0});
//        losing.add(new int[] {3,1,1,0,0,0,0,0,0,0});
//        losing.add(new int[] {3,2,0,0,0,0,0,0,0,0});
        for(int[] b : losing) {
            System.out.println(b[0]+"-"+b[1]+"-"+b[2] + "-" + b[3] + "-" + b[4] + "-" + b[5] + "-" + b[6] + "-" + b[8] + "-" + b[9]);
        }
        return losing;
    }

    public ArrayList<int[]> possibleMoves(Board Board) {
        // Array able to be iterated thru w/ the initial values
        int[] setUp = Board.state;

        boolean skip = true;
        ArrayList<int[]> outputs = new ArrayList<int[]>();

        int[] bad = {0,0,0,0,0,0,0,0,0,0};

        //System.out.println("Possible boards from board: " +x+"-"+y+"-"+z);

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
 //                   System.out.println("Board:" + Board.state[0] + "-" + Board.state[1] + "-" + Board.state[2]);
 //                   System.out.println("I'm not adding this one");
                    skip = false;
                } else {
                    outputs.add(results);
  //                  System.out.println(results[0] + "-" + results[1] + "-" + results[2] + "-" + results[3] + "-" + results[4]);
                }
            }
        }
       // System.out.println(outputs.get(0) + "  " + outputs.get(1));
        return outputs;
    }

    public Board changeBoardFormat(Chip[][] board) {
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




    //add your code to return the row and the column of the chip you want to take.
    //you'll be returning a data type called Point which consists of two integers.
    public Point move(Chip[][] pBoard) {

        // Define in terms of colunms --- 3-3-3, 3-3-2, 4-4-1


        gameBoard = pBoard;
//        int column = 0;
//        int row = 0;

        Board newBoard = changeBoardFormat(pBoard);
        int[] theMove = bestMove(newBoard);
        System.out.println(theMove[0]+"-"+theMove[1]);

//        Point myMove = new Point(1,1);
        Point myMove = new Point(theMove[0], theMove[1]);

 //       Board newBoard = new Board(new int[]{3,3,3,0,0,0,0,0,0,0});

//        ArrayList<int[]> outputs = possibleMoves(newBoard);
        ArrayList<int[]> x = Losing();

        //Point myMove = new Point(1,1);
        //Board newBoard = new Board(new int[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        //ArrayList<int[]> possible = possibleMoves(newBoard);
        //for(int[] x : possible) {
        //    System.out.println(x[0] + "-" + x[1] + "-" + x[2]+"-"+x[3]+"-"+x[4]);
        //}
        //possibleMoves(3,2,1);
        //movesFromPossible();
        //bestMove(3,0,0);
        return myMove;
    }

}

