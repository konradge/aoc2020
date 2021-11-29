import java.awt.List;
import java.util.*;

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
        return "" + calcScore();
    }

    public int calcScore() {
        int score = 0;
        Player winner = p1.cards.isEmpty() ? p2 : p1;

        int size = winner.cards.size();
        while (!winner.cards.isEmpty()) {
            score += winner.cards.remove() * size;
            size--;
        }
        return score;
    }

    @Override
    public String partTwo() {
        resetPlayers(input);
        boolean winner = new Game(p1.cards, p2.cards).play();
        return "" + calcScore();
    }

    public static class Game {
        private Queue<Integer> cards1, cards2;
        private final HashMap<String, Boolean> alreadySeenCards = new HashMap<>();
        static int openGames = 0;

        public Game(Queue<Integer> cards1, Queue<Integer> cards2) {
            this.cards1 = cards1;
            this.cards2 = cards2;
            openGames++;
            //System.out.println(++openGames);
        }

        public static boolean playGame(Queue<Integer> c1, int quantity1, Queue<Integer> c2, int quantity2) {
            Game g = new Day22.Game(new LinkedList<>(c1.stream().toList().subList(0, quantity1)), new LinkedList<>(c2.stream().toList().subList(0, quantity2)));
            return g.play();
        }

        public boolean play() {
            int rounds = 0;
            // true -> Player1, false -> Player2
            boolean roundWinner = true;
            while (!cards1.isEmpty() && !cards2.isEmpty()) {
                rounds++;
                if (rounds >= 500000) return true;
                if (alreadySeenCards.getOrDefault(cards1.hashCode() + "|" + cards2.hashCode(), false)) {
                    // Player 1 instantly wins the game!
                    //System.out.println("Instant win!");
                    openGames--;
                    return true;
                } else {
                    alreadySeenCards.put(cards1.hashCode() + "|" + cards2.hashCode(), true);
                    //if (alreadySeenCards.size() > 50 * 50) System.out.println(alreadySeenCards.size());
                    // Take the two cards on top of both decks
                    int card1 = cards1.remove();
                    int card2 = cards2.remove();
                    if (cards1.size() >= card1 && cards2.size() >= card2) {
                        // Determine player by playing a new game
                        roundWinner = playGame(cards1, card1, cards2, card2);
                    } else {
                        // Winner is the one with higher valued card
                        roundWinner = (card1 > card2);
                    }

                    // Winner takes the two cards
                    if (roundWinner) {
                        cards1.add(card1);
                        cards1.add(card2);
                    } else {
                        cards2.add(card2);
                        cards2.add(card1);
                    }
                }
            }
            // The player with no cards looses
            openGames--;
            return !cards1.isEmpty();
        }
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
