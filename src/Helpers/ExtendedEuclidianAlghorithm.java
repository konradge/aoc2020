package Helpers;

public class ExtendedEuclidianAlghorithm {
    public static Solution getGCD(long a, long b) {
        if(a < b){
            long temp = a;
            a = b;
            b = temp;
        }
        long q = (long) Math.floor(a / b);
        long r = a % b;
        if (r == 0) {
            return new Solution(b, 0, Math.abs(b) / b, a, b);
        } else {
            Solution sol = getGCD(b, r);
            return new Solution(sol.d, sol.my, sol.lam - q * sol.my, a, b);
        }
    }

    public static void main(String[] args) {
        System.out.println(getGCD(78, 99));
    }
}

