public class Day25 implements Day {
    long cardPublic, doorPublic;
    final static long MOD = 20201227;

    @Override
    public void prepare(String input) {
        cardPublic = Long.parseLong(input.split("\n")[0]);
        doorPublic = Long.parseLong(input.split("\n")[1]);
    }

    @Override
    public String partOne() {

        long cardsSecret = calcPrivateKey(cardPublic);
        System.out.println("Door public: " + doorPublic);
        System.out.println("Card secret: " + cardsSecret);
        //int doorsSecret = calcPrivateKey(doorPublic);
        //System.out.println("Door secret: " + doorsSecret);
        return "" + transformKey(doorPublic, cardsSecret);
    }

    public long calcPrivateKey(long publicKey) {
        long privateKey = 0;
        long calculatedKey = 1;
        while (calculatedKey % MOD != publicKey) {
            privateKey++;
            calculatedKey = (long) 7 * calculatedKey;
            calculatedKey %= MOD;
        }
        return privateKey;
    }

    public long transformKey(long subjectNumber, long loopSize) {
        long key = 1;
        for (long i = 0; i < loopSize; i++) {
            key = subjectNumber * key;
            if (key < 0) System.out.println("MINUS!");
            key = key % MOD;
        }
        return key;
    }

    @Override
    public String partTwo() {
        return "";
    }
}
