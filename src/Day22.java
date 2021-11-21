import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//Currently not working
public class Day22 implements Day {
    Player p1, p2;
    String input;
    ArrayList<String> alreadyGot = new ArrayList<>();

    @Override
    public void prepare(String input) {
        resetPlayers(input);
        this.input = input;
    }

    public void resetPlayers(String input) {
        String[] in = input.split("\\n\\n");
        System.out.println(Arrays.toString(in));
        for (int i = 0; i < in.length; i++) {
            Player p = new Player(in[i].split(":\\n")[1].split("\\n"));
            if (i == 0) {
                p1 = p;
            } else {
                p2 = p;
            }
        }
    }

    public void nextRound(Player p1, Player p2) {
        if (p1.cards.peek() > p2.cards.peek()) {
            p1.cards.add(p1.cards.remove());
            p1.cards.add(p2.cards.remove());
        } else {
            p2.cards.add(p2.cards.remove());
            p2.cards.add(p1.cards.remove());
        }
    }

    @Override
    public String partOne() {
        while (!p1.cards.isEmpty() && !p2.cards.isEmpty()) {
            nextRound(p1, p2);
        }
        int score = 0;
        Player winner = p1.cards.isEmpty() ? p2 : p1;

        int size = winner.cards.size();
        while (!winner.cards.isEmpty()) {
            score += winner.cards.remove() * size;
            size--;
        }
        return score + "";
    }

    @Override
    public String partTwo() {
        resetPlayers(input);

        return "";
    }

    public int play(Queue<Integer> deck1, Queue<Integer> deck2, ArrayList<String> alreadyGot) {
        String id = "p1:" + deck1.peek() + "p2:" + deck2.peek();
        if (alreadyGot.contains(id)) {
            //Runde zuende, Player1 gewinnt
            return 1;
        }
        int cardP1 = deck1.remove();
        int cardP2 = deck2.remove();
        if (cardP1 <= deck1.size() && cardP2 <= deck2.size()) {
            //Spiele neues Spiel, um Gewinner herauszufinden
            Queue<Integer> deck1Copy = new LinkedList<>(deck1);
            Queue<Integer> deck2Copy = new LinkedList<>(deck2);
            int winner = play(deck1Copy, deck2Copy, new ArrayList<>());
            if (winner == 1) {
                deck1.add(Math.max(cardP1, cardP2));
                deck2.add(Math.min(cardP1, cardP2));
            } else {
                deck2.add(Math.max(cardP1, cardP2));
                deck2.add(Math.min(cardP1, cardP2));
            }
        } else {
            if (cardP1 > cardP2) {
                //Player1 Sieger
                return 1;
            } else {
                //Player2 Sieger
                return 2;
            }
        }
        return 0;
    }


    class Player {
        Queue<Integer> cards = new LinkedList<>();

        public Player(String[] cards) {
            for (String card : cards) {
                this.cards.add(Integer.parseInt(card));
            }
        }

        public String toString() {
            StringBuilder res = new StringBuilder("<");
            for (Integer card : cards) {
                res.append(card).append(", ");
            }
            return res.append("|").toString();
        }
    }
}
