import java.util.*;

public class Day15BM {
    static long start;
    public static void main(String[] args) {
        HashMap<Integer, int[]> hm = new HashMap<>();
        hm.put(11, new int[]{-1, 0});
        hm.put(18, new int[]{-1, 1});
        hm.put(0, new int[]{-1, 2});
        hm.put(20, new int[]{-1, 3});
        hm.put(1, new int[]{-1, 4});
        hm.put(7, new int[]{-1, 5});
        hm.put(16, new int[]{-1, 6});

        Hashtable<Integer, int[]> ht = new Hashtable<>(hm);
        TreeMap<Integer, int[]> tm = new TreeMap<>(hm);
        LinkedHashMap<Integer, int[]> lhm = new LinkedHashMap<>(hm);
        System.out.println(Arrays.toString(ht.keySet().toArray()));
        System.out.println(Arrays.toString(hm.keySet().toArray()));
        System.out.println(Arrays.toString(tm.keySet().toArray()));
        System.out.println(Arrays.toString(lhm.keySet().toArray()));
        
        calc(ht);
        calc(hm);
        calc(tm);
        calc(lhm);
    }

    public static long calc(Map<Integer, int[]> m) {
        int lastNum = 16;
        int turn = 7;
        start = System.nanoTime();
        while (turn < 30000000) {
            if (m.get(lastNum)[0] == -1) {
                int[] lastSayings = m.get(0);
                m.put(0, new int[]{lastSayings == null ? -1 : lastSayings[1], turn++});
                lastNum = 0;
            } else {
                int diff = m.get(lastNum)[1] - m.get(lastNum)[0];
                int[] lastSayings = m.get(diff);
                m.put(diff, new int[]{lastSayings == null ? -1 : lastSayings[1], turn++});
                lastNum = diff;
            }
            //System.out.println("Speaking " + lastNum + " in Turn " + turn);
        }
        System.out.println(System.nanoTime() - start);
        return lastNum;
    }
}
