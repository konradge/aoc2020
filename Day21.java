import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day21 implements Day {
    HashMap<String, ArrayList<String>> allergenMap = new HashMap<>();
    HashMap<String, Integer> allIngredients = new HashMap<>();
    String[] lines;

    @Override
    public void prepare(String input) {
        lines = input.split("\n");
    }

    @Override
    public String partOne() {
        for (String line : lines) {
            String[] allergenList = line.split("\\(contains ")[1].replaceAll("\\)", "").split(", ");
            List<String> ingredients = Arrays.asList(line.split("\\(")[0].split(" "));
            for (String ingredient : ingredients) {
                allIngredients.putIfAbsent(ingredient, 0);
                allIngredients.put(ingredient, allIngredients.get(ingredient) + 1);
            }
            for (String allergen : allergenList) {
                if (allergenMap.get(allergen) == null) {
                    allergenMap.put(allergen, new ArrayList<>(ingredients));
                } else {
                    ArrayList<String> oldIngredients = allergenMap.get(allergen);
                    oldIngredients.removeIf(oldIngr -> !ingredients.contains(oldIngr));
                }
            }
        }

        ArrayList<String> ingredientsMayBeAllergens = new ArrayList<>();
        for (String s : allergenMap.keySet()) {
            ArrayList<String> ingredients = allergenMap.get(s);
            for (String i : ingredients) {
                if (!ingredientsMayBeAllergens.contains(i)) {
                    ingredientsMayBeAllergens.add(i);
                }
            }
        }

        int counter = 0;
        for (String i : allIngredients.keySet()) {
            if (!ingredientsMayBeAllergens.contains(i)) {
                counter += allIngredients.get(i);
            }
        }


        return counter + "";
    }

    @Override
    public String partTwo() {
        HashMap<String, String> solution = new HashMap<>();
        String[] allergens = allergenMap.keySet().toArray(new String[0]);
        for (int i = 0; i < allergens.length; i++) {
            ArrayList<String> current = allergenMap.get(allergens[i]);
            if (solution.keySet().contains(allergens[i])) continue;
            if (current.size() == 1) {
                solution.put(allergens[i], current.get(0));
                removeIngredient(allergenMap, current.get(0));
                i = -1;
            }
        }
        StringBuilder res = new StringBuilder();

        Arrays.sort(allergens);
        for (String a : allergens) {
            res.append(solution.get(a)).append(",");
        }
        return res.substring(0, res.toString().length() - 1);
    }

    public void removeIngredient(HashMap<String, ArrayList<String>> aMap, String ingredient) {
        for (String allergen : aMap.keySet()) {
            aMap.get(allergen).remove(ingredient);
        }
    }

    public String printAllergensIngredients(HashMap<String, String> solution) {
        StringBuilder res = new StringBuilder();
        for (String allergen : solution.keySet()) {
            res.append(allergen).append(": ").append(solution.get(allergen)).append(System.lineSeparator());
        }
        return res.toString();
    }
}
