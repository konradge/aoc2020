import Helpers.IO;
import Helpers.Utils;

public class Day11 implements Day {
    String[][] seats;
    String[][] inputSeats;

    @Override
    public void prepare(String input) {
        inputSeats = IO.get2DStringArraySplitByNewline(input);
        seats = Utils.clone(inputSeats);
    }

    @Override
    public String partOne() {
        String[][] newSeats = new String[0][0];
        while (!Utils.isEqual(seats, newSeats)) {
            if (newSeats.length != 0) {
                seats = Utils.clone(newSeats);
            }
            newSeats = Utils.clone(seats);
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    int neighbours = 0;
                    for (int k = i-1; k <= i+1; k++) {
                        for (int l = j-1; l <= j+1; l++) {
                            if (!((k == i && l == j)  || k < 0 || l < 0 || k >= seats.length || l >= seats[i].length)) {
                                neighbours += seats[k][l].equals("#") ? 1 : 0;
                            }
                        }
                    }
                    if (seats[i][j].equals("L") && neighbours == 0) {
                        newSeats[i][j] = "#";
                    } else if (seats[i][j].equals("#") && neighbours >= 4) {
                        newSeats[i][j] = "L";
                    }
                }
            }
        }
        int occupiedSum = 0;
        for (String[] row : newSeats) {
            for (String seat : row) {
                occupiedSum += seat.equals("#") ? 1 : 0;
            }
        }
        return "" + occupiedSum;
    }
    
    public int occupiedNeighbours(String[][] seats, int row, int col){
        int neighbours = 0;
        int i = row;
        //Right
        while(i++ < seats.length-1){
            String seat = seats[i][col];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        //Left;
        i = row;
        while(i-- > 0){
            String seat = seats[i][col];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        //Up
        i = col;
        while(i++ < seats[row].length-1){
            String seat = seats[row][i];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        //Down
        i = col;
        while(i-- > 0){
            String seat = seats[row][i];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        int j = col;
        i = row;
        //Right Up
        while(i++ < seats.length-1 && j++ < seats[i].length-1){
            String seat = seats[i][j];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        i = row;
        j = col;
        //Right Down
        while(i++ < seats.length-1 && j-- > 0){
            String seat = seats[i][j];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        i = row;
        j = col;
        while(i-- > 0 && j++ < seats[i].length-1){
            String seat = seats[i][j];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        i = row;
        j = col;
        while(i-- > 0 && j-- > 0){
            String seat = seats[i][j];
            if(seat.equals("#")){
                neighbours++;
                break;
            }else if(seat.equals("L")){
                break;
            }
        }
        return neighbours;
    }

    @Override
    public String partTwo() {
        seats = Utils.clone(inputSeats);
        String[][] newSeats = new String[0][0];
        while (!Utils.isEqual(seats, newSeats)) {
            if (newSeats.length != 0) {
                seats = Utils.clone(newSeats);
            }
            newSeats = Utils.clone(seats);
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    int neighbours = occupiedNeighbours(seats, i, j);
                    if (seats[i][j].equals("L") && neighbours == 0) {
                        newSeats[i][j] = "#";
                    } else if (seats[i][j].equals("#") && neighbours >= 5) {
                        newSeats[i][j] = "L";
                    }
                }
            }
        }
        int occupiedSum = 0;
        for (String[] row : newSeats) {
            for (String seat : row) {
                occupiedSum += seat.equals("#") ? 1 : 0;
            }
        }
        return "" + occupiedSum;
    }
}
