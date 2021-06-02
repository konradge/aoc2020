package Helpers;

import java.util.Arrays;

public class Utils {
    public static String[][] clone(String[][] arr) {
        String[][] clone = new String[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                clone[i][j] = arr[i][j];
            }
        }
        return clone;
    }
    
    public static boolean isEqual(Object[][] arr1, Object[][] arr2){
        if(arr1.length != arr2.length) return false;
        if(arr1.length == 0) return true;
        if(arr1[0].length != arr2[0].length) return false;
        for(int i = 0; i < arr1.length; i++){
            for(int j = 0; j < arr1[i].length; j++){
                if(!arr1[i][j].equals(arr2[i][j])) return false;
            }
        }
        return true;
    }
}
