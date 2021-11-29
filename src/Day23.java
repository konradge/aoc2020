import java.util.*;

public class Day23 implements Day {
    Ring cups = new Ring();
    EfficientList efficientCups = new EfficientList();
    String input;
    final static int MILLION = 1000000;

    @Override
    public void prepare(String input) {
        this.input = input.replace("\n", "");
        for (String cup : input.replace("\n", "").split("")) {
            cups.add(Integer.parseInt(cup));
            efficientCups.add(Integer.parseInt(cup));
        }

        for (int i = efficientCups.size(); i < MILLION; i++) {
            efficientCups.add(i + 1);
        }
    }

    @Override
    public String partOne() {
        return correctGame(cups, 100);
    }

    @Override
    public String partTwo() {
        return "" + playGame(efficientCups, 10 * MILLION);
    }

    public String correctGame(Ring cups, int rounds) {
        int currentCup = cups.get(0);
        int size = cups.size();
        for (int i = 0; i < rounds; i++) {
            // Remove three cups right to the current cup
            Stack<Integer> removedCups = cups.remove(3, cups.indexOf(currentCup) + 1);
            // Select the destination cup
            int destinationCup = removedCups.peek();
            for (int j = 2; removedCups.contains(destinationCup); j++) {
                destinationCup = ((size + currentCup - j) % size) + 1;
            }
            // Insert back the removed cups
            while (!removedCups.isEmpty()) {
                cups.insert(cups.indexOf(destinationCup), removedCups.pop());
            }

            // Select the next current cup right to the current cup
            currentCup = cups.get(cups.indexOf(currentCup) + 1);
        }
        return cups.print(-1).replace("1", "");
    }

    public long playGame(EfficientList cups, int rounds) {
        int currentCup = cups.getFirst();

        for (int i = 0; i < rounds; i++) {
            //System.out.println("----- Round " + i + " ------");
            //System.out.println(cups.print(currentCup));
            // Remove three cups right to the current cup
            Stack<Integer> removedCups = cups.remove(currentCup, 3);
            //System.out.println("Taken: " + Arrays.toString(removedCups.toArray()));
            // Select the destination cup
            int destinationCup = removedCups.peek();
            for (int j = 1; removedCups.contains(destinationCup); j++) {
                destinationCup = (cups.maxSize() + currentCup - j - 1) % cups.maxSize() + 1;
            }
            //System.out.println("Destination: " + destinationCup);
            // Insert back the removed cups
            while (!removedCups.isEmpty()) {
                cups.add(destinationCup, removedCups.pop());
            }

            // Select the next current cup right to the current cup
            currentCup = cups.getNext(currentCup);
        }
        long[] res = new long[2];
        res[0] = cups.getNext(1);
        res[1] = cups.getNext((int) res[0]);
        return res[0] * res[1];
    }
}

class EfficientList {
    private final HashMap<Integer, Node> map = new HashMap<>();
    private Node head;
    int size = 0;

    public int maxSize() {
        return map.size();
    }

    public int getFirst() {
        return head.getValue();
    }

    public int getNext(int element) {
        return map.get(element).getNext().getValue();
    }

    public void add(int element) {
        if (head == null) this.add(-1, element);
        else this.add(head.getPrevious().getValue(), element);
    }

    /**
     * Add an element at the position after some element
     *
     * @param elementBefore Element will be inserted after this element
     * @param element       Element to insert (no duplicates allowed)
     */
    public void add(int elementBefore, int element) {
        if (head == null) {
            head = new Node(element, null);
            head.setNext(head);
            map.put(element, head);
        } else {
            if (map.get(element) != null && (map.get(element).getNext() != null || map.get(element).getPrevious() != null))
                throw new RuntimeException("Element already in list");
            Node nodeBefore = map.get(elementBefore);
            if (nodeBefore == null) throw new RuntimeException("Element before not in list!");
            Node newNode = new Node(element, nodeBefore);
            map.put(element, newNode);
        }
        size++;
    }

    /**
     * Remove the element from the list
     *
     * @param element element to remove
     * @return Node, where the element was stored
     */
    public Node remove(int element) {
        Node toRemove = map.get(element);
        toRemove.getPrevious().setNext(toRemove.getNext());
        toRemove.getNext().setPrevious(toRemove.getPrevious());
        toRemove.setNext(null);
        toRemove.setPrevious(null);
        size--;
        return toRemove;
    }

    /**
     * Removes count elements from the list beginning at the element after elementToStart
     *
     * @param elementToStart The starting point, from where to remove the elements (not itself!)
     * @param count          Number of elements to remove
     * @return Elements, that have been removed (last element is on top of the stack)
     */
    public Stack<Integer> remove(int elementToStart, int count) {
        Stack<Integer> res = new Stack<>();
        Node nodeToStart = map.get(elementToStart);
        for (int i = 0; i < count; i++) {
            Node toRemove = nodeToStart.getNext();
            res.push(toRemove.getValue());
            toRemove.getNext().setPrevious(toRemove.getPrevious());
            toRemove.getPrevious().setNext(toRemove.getNext());
            if (toRemove == head) {
                head = toRemove.getNext();
            }
            toRemove.setNext(null);
            toRemove.setPrevious(null);
        }
        size -= count;
        return res;
    }

    public int size() {
        return this.size;
    }

    public String print() {
        return this.print(-1);
    }

    public String print(int current) {
        StringBuilder res = new StringBuilder(head.getValue());
        Node start = map.get(1);
        Node runner = start;
        do {
            if (runner.getValue() == current) res.append("(");
            res.append(runner.getValue());
            if (runner.getValue() == current) res.append(")");
            runner = runner.getNext();
        } while (runner != start);
        return res.toString();
    }

    static class Node {
        private final int value;
        private Node next;
        private Node previous;

        public Node(int value, Node nodeBefore) {
            this.value = value;
            if (nodeBefore == null) {
                this.setNext(this);
                this.setPrevious(this);
            } else {
                this.setPrevious(nodeBefore);
                this.setNext(nodeBefore.getNext());

                nodeBefore.getNext().setPrevious(this);
                nodeBefore.setNext(this);
            }
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public int getValue() {
            return value;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
}


class Ring {
    private ArrayList<Integer> ring = new ArrayList<>();

    /**
     * Adds the element to the end of the list
     *
     * @param element
     */
    public void add(int element) {
        ring.add(element);
    }

    public int size() {
        return ring.size();
    }

    public Object[] toArray() {
        return ring.toArray();
    }

    public Stack<Integer> remove(int count, int startIndex) {
        Stack<Integer> res = new Stack<>();
        for (int i = 0; i < count; i++) {
            res.add(this.remove(startIndex));
            if (startIndex >= (ring.size() + 1)) {
                startIndex--;
            }
        }
        return res;
    }

    public int remove(int index) {
        return ring.remove(index % ring.size());
    }

    /**
     * Inserts the element after the given position
     *
     * @param position
     * @param element
     */
    public void insert(int position, int element) {
        ring.add((position + 1) % ring.size(), element);
    }

    public int indexOf(int element) {
        return ring.indexOf(element);
    }

    public int get(int index) {
        return ring.get(index % ring.size());
    }

    public String print(int currentCup) {
        StringBuilder res = new StringBuilder();
        int startIndex = ring.indexOf(1);
        for (int i = 0; i < ring.size(); i++) {
            if (this.get(startIndex + i) == currentCup) res.append("(");
            res.append(this.get(startIndex + i));
            if (this.get(startIndex + i) == currentCup) res.append(")");

        }
        return res.toString();
    }
}
