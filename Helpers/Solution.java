package Helpers;
public class Solution {
    public long d, lam, my, a, b;

    public Solution(long d, long lam, long my, long a, long b) {
        this.d = d;
        this.lam = lam;
        this.my = my;
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return d + "=" + this.my + "*" + this.b + "+" + this.lam + "*" + this.a;
    }
}