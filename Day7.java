import java.util.ArrayList;
import java.util.HashMap;

public class Day7 implements Day{
    HashMap<String, Bag> bags = new HashMap<>();
    public void prepare(String input){
        for(String bagDescr: input.split("\n")){
            String color = bagDescr.split(" bags contain ")[0];
            if(bags.get(color) == null){
                bags.put(color, new Bag(color));
            }
            String[] containedBags = bagDescr.split(" contain ")[1].split(" bags?(, |.)");
            BagCount[] containedBagsList = new BagCount[containedBags.length];
            int i = 0;
            for(String containedBag : containedBags){
                if(containedBag.equals("no other")) continue;
                int count = Integer.parseInt("" + containedBag.charAt(0));
                String containedColor = containedBag.substring(2, containedBag.length());
                if(bags.get(containedColor) == null){
                    bags.put(containedColor, new Bag(containedColor));
                }
                containedBagsList[i++] = new BagCount(bags.get(containedColor), count);
            }
            if(containedBagsList[0] == null){
                bags.get(color).contains = new BagCount[0];
            }else {
                bags.get(color).contains = containedBagsList;
            }
        }
    }
    @Override
    public String partOne() {
        int res = 0;
        for(Bag b: bags.values()){
            res += b.canContainShinyGoldBag()?1:0;
        }
        return "" + (res-1);
    }

    @Override
    public String partTwo() {
        return "" + bags.get("shiny gold").contains();
    }
}

class Bag {
    BagCount[] contains;
    String color;
    public Bag(String color){
        this.color = color;
    }
    boolean canContainShinyGoldBag(){
        if(this.color.equals("shiny gold")){
            return true;
        }
        for(BagCount bag: contains){
            if(bag.bag.canContainShinyGoldBag()){
                return true;
            }
        }
        return false;
    }
    
    int contains(){
        if(contains.length == 0){
            return 0;
        }
        int res = 0;
        for(BagCount bag: contains){ 
            res += bag.count;
            res += bag.count * bag.bag.contains(); 
        }
        return res;
    }
}

class BagCount {
    int count;
    Bag bag;
    public BagCount(Bag bag, int count){
        this.bag = bag;
        this.count = count;
    }
}
