import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPlayer {
    public Chip[][] gameBoard;
    public int[] columns;

    ArrayList<Integer> allBoards = new ArrayList<Integer>();


    public MyPlayer() {
        columns = new int[10];
        //Generate();
    }

    public void Generate() {
        int counter = 0;
        System.out.println("HI");
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < x+1; y++) {
                for(int z = 0; z < y+1; z++) {
                    System.out.println(x + "-" + y + "-" + z);
                   // allBoards.add(x * 100 + y * 10 + z);
                    counter++;

                }
            }
        }
        System.out.println(counter);



    }
    public int[] bestMove(int x, int y, int z) {
        int chooseX = 0;
        int chooseY = 0;
        Integer[] chosen = {0,0,0};
        Integer[] input = {x,y,z};
        Integer[] difference = {0,0,0};
        boolean move = false;

        ArrayList<Integer[]> possible = possibleMoves(x,y,z);
        for(Integer[] q: possible) {
            for(Integer[] p: Losing()) {
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
            //THIS IS WHAT NEEDS WORK
            for(int m = 0; m < input.length; m++) {
                if(difference[m] != 0) {
                    chooseY = m;
                    chooseX = chosen[m];
                    int[] bestMove = {chooseX,chooseY};
                    return(bestMove);
                }
            }

//            for (int m = input.length - 1; m > -1; m--) {
//                if(difference[m] == 0 && input[m] != 0) {
//                    chooseX = m+1;
//                    chooseY = chosen[m+1];
//                } else {
//                    chooseX = 0;
//                    chooseY = chosen[0];
//                }



        } else {
            System.out.println("No Best choice");
            chooseY = 0;
            chooseX = 0;
            int[] bestMove = {chooseX, chooseY};
            return (bestMove);
        }

        return(new int[]{1, 2, 3});




    }

    public ArrayList<Integer[]> Losing() {
        ArrayList<Integer[]> losing = new ArrayList<Integer[]>();
        losing.add(new Integer[] {1,0,0});
        losing.add(new Integer[] {2,1,0});
        losing.add(new Integer[] {2, 1, 1});
        losing.add(new Integer[] {3,1,1});
        losing.add(new Integer[] {3,2,0});
        return losing;
    }

    public ArrayList<Integer[]> possibleMoves(int x, int y, int z) {
        // Array able to be iterated thru w/ the initial values
        int[] setUp = {x, y, z};
        ArrayList<Integer[]> outputs = new ArrayList<Integer[]>();

        //System.out.println("Possible boards from board: " +x+"-"+y+"-"+z);

        // For each column
        for (int q = 1; q < setUp.length + 1; q++) {
            // For each square in that column
            for (int o = 1; o < setUp[q - 1] + 1; o++) {
                // Array with the results that is reset before, and will be updated
                int[] results = {x, y, z};
                // Results for the row being changed = square chosen - 1
                results[q - 1] = o - 1;
                // For the other columns: if same number as taken or greater, subtract 1
                for (int i = q; i < setUp.length; i++) {
                    if (setUp[i] > results[q - 1] - 1) {
                        results[i] = o - 1;
                    }
                }

                if (results[0] == 0 && results[1] == 0 && results[2] == 0) {
                    continue;
                } else {
                    Integer[] out = {results[0], results[1], results[2]};
                    outputs.add(out);
                }
            }
        }
        return outputs;
    }
    public void movesFromPossible() {
        for(int x = 1; x < 4; x++) {
            for(int y = 0; y < x+1; y++) {
                for(int z = 0; z < y+1; z++) {
                    possibleMoves(x,y,z);
                }
            }
        }
    }

    public int[] changeBoardFormat(Chip[][] board) {
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
        int[] work = {newFormat.get(0).intValue(), newFormat.get(1).intValue(), newFormat.get(2).intValue()};

        return(work);
    }




    //add your code to return the row and the column of the chip you want to take.
    //you'll be returning a data type called Point which consists of two integers.
    public Point move(Chip[][] pBoard) {

        // Define in terms of colunms --- 3-3-3, 3-3-2, 4-4-1


        gameBoard = pBoard;
//        int column = 0;
//        int row = 0;
        int[] newBoard = changeBoardFormat(pBoard);
        int[] theMove = bestMove(newBoard[0], newBoard[1], newBoard[2]);
        System.out.println(theMove[0]+"-"+theMove[1]);

        Point myMove = new Point(theMove[0], theMove[1]);
        //Generate();
        //possibleMoves(3,2,1);
        //movesFromPossible();
        //bestMove(3,0,0);
        return myMove;
    }

}

