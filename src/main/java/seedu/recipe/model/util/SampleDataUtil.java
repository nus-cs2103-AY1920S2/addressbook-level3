package seedu.recipe.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.recipe.model.Date;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.achievement.Content;
import seedu.recipe.model.achievement.Quote;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Vegan Thai Green Curry Soup"), new Time("35"),
                    getGrainSet("200g, rice vermicelli noodles"),
                    getVegetableSet("100g,button mushrooms sliced", "1 cup, broccoli florets",
                            "10g, Thai red eye bird's chili"),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet(" 1 tbsp,thai Green Curry Paste",
                            "0.25 Cup, coconut milk", "2 tsp, oil",
                            "1 tsp, Salt" , "1 tsp, soy sauce",
                            "0.5 tsp, brown sugar"),
                    getStepsList("Heat oil in a saucepan. Add the vegetables (broccoli, mushroom) and saute till"
                                    + " they turn soft. Transfer to a plate and set aside.",
                            "In the same saucepan, add the green curry paste. Saute the curry paste for a minute.",
                            "Add the coconut milk, brown sugar and soy sauce. "
                                    + "Stir until the curry paste is nicely dissolved.",
                            "Now add the vegetable stock, salt, red chillies and stir to combine.",
                            "Bring the soup to a boil over high heat. Reduce the heat and allow the soup to "
                                    + "simmer for 5 minutes.",
                            "Add the vermicelli noodles and the vegetables. Simmer till the noodles are cooked.",
                            "Turn off the heat and give soup a nice stir. Taste and adjust the seasoning and"
                                    + " consistency accordingly. If too thick add more stock or coconut milk.",
                            "Just before serving garnish soup with fresh herbs, crushed peanuts and juice of lemon.",
                            "Serve Thai Green Curry Soup warm."),
                    getGoalSet("Herbivore", "Wholesome Wholemeal"), false),
            new Recipe(new Name("Barley Pilaf"), new Time("50"),
                    getGrainSet("0.8 cup, small pieces of broken spaghetti"),
                    getVegetableSet("50g, small onion (chopped)", "2 tbsp, chives (chopped)"),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("1 tbsp, unsalted butter", "1 cup, hulled barley", "10g, Salt & Pepper",
                            "2.5 cup, low-sodium broth"),
                    getStepsList("Melt butter in saucepan over medium heat. "
                                    + "Add onion and cook until soft and light golden brown.",
                            "Add barley and stir until grains are coated in butter.",
                            "Add broth and 0.5 tsp salt, bring to a boil.",
                            "Reduce heat to medium low, cover, and simmer for 20mins.",
                            "Uncover and quickly stir in spaghetti. Cover and continue to simmer, "
                                    + "stirring occasionally.",
                            "Once most liquid is absorbed and barley and spaghetti are tender, "
                                    + "remove from hit and let sit.",
                            "Season to taste with salt and pepper and sprinkle with chopped chives."),
                    getGoalSet("Wholesome Wholemeal"), false),
            new Recipe(new Name("Asian BBQ Chicken"), new Time("30"),
                    getGrainSet(),
                    getVegetableSet("1 tbsp, garlic & ginger (finely chopped)"),
                    getProteinSet("100g, Chicken breast"),
                    getFruitSet(),
                    getOtherSet("2 tbsp, char siew sauce", "1 tbsp, rice vinegar",
                            "1 tsp, honey",
                            "0.5 tsp, sesame oil",
                            "1 tsp, Salt & pepper"),
                    getStepsList("In a bowl, mix all the ingredients to create marinade. "
                                    + "Mix the chicken with the marinade.",
                            "Pan sear the chicken from both sides.",
                            "Serve with rice."),
                    getGoalSet("Bulk like the Hulk"), false),
            new Recipe(new Name("Sweet and Sour Chicken"), new Time("20"),
                    getGrainSet(),
                    getVegetableSet("10g, Garlic cloves",
                            "10g, knob ginger", "1 cup, carrots (diced)",
                            "1 cup, chinese cabbage (diced)",
                            "1 cup, bell peppers", "1 cup, onions (diced)"),
                    getProteinSet("300g, chicken", "50g, egg"),
                    getFruitSet("1 cup, pineapple (diced)", "1 tbsp, tomato juice"),
                    getOtherSet("1.5 tsp, corn flour",
                            "1 tsp, light soya sauce",
                            "1 tbsp, vegetable oil", "10g, Coriander (optional)"),
                    getStepsList("Pound garlic and ginger into a paste. Marinade chicken in the paste, "
                                    + "along with corn flour, soy sauce, and salt.",
                            "In a wok, take some oil, saute the carrots, cabbage, onions, and bell peppers. "
                                    + "Add tomato juice and bring to a slight boil.",
                            "Fry the chicken and add to the wok along with some stock and set to simmer.",
                            "Garnish with coriander and serve with rice."),
                    getGoalSet("Bulk like the Hulk", "Herbivore", "Fruity Fiesta"),
                    false),
            new Recipe(new Name("Quick Carrot Daikon Stir-fry"), new Time("25"),
                    getGrainSet(),
                    getVegetableSet("30g, ginger (julienned)",
                            "30g, garlic (smashed and chopped)",
                            "120g, carrot (julienned)",
                            "50g, Daikon radish (peeled and julienned)", "30g, scallions"),
                    getProteinSet("50g, Egg"),
                    getFruitSet(),
                    getOtherSet("3 tbsp, vegetable oil",
                            "1 tbsp, shaoxing wine",
                            "1.5 tbsp, oyster sauce",
                            "0.25 tsp, white pepper",
                            "0.5 tsp, sesame oil",
                            "0.5 tsp, salt",
                            "0.25 cup, water"),
                    getStepsList("Heat oil in wok over medium heat. Add ginger and garlic and cook for 20 seconds.",
                            "Add carrot and stir-fry for 30 seconds - oil should turn orange colour. Add in daikon,"
                                    + " turn up heat to high and stir-fry for another 30 seconds.",
                            "Add shaoxing wine, oyster sauce, white pepper, sesame oil, salt, water, "
                                    + "and white parts of the scallions. Stir to combine, cover, "
                                    + "turn down heat to medium. Simmer for 5-8 minutes until tender.",
                            "Uncover, add in rest of the scallions and mix everything well. "
                                    + "When daikon is translucent and tender, the dish is done."),
                    getGoalSet("Herbivore"), false),
            new Recipe(new Name("Chinese Tomato Egg Stir-fry"), new Time("10"),
                    getGrainSet(),
                    getVegetableSet("30 g, tomatoes", "10 g, scallion"),
                    getProteinSet("50 g, Egg"),
                    getFruitSet(),
                    getOtherSet("0.75 tsp, salt", "0.25 tsp, white pepper",
                            "0.5 tsp, sesame oil", "1 tsp, shaoxing wine", "3 tbsp, vegetable oil",
                            "2 tsp, sugar", "0.25 cup, water"),
                    getStepsList("Cut tomatoes into small wedges and finely chop scallion",
                            "Crack eggs into bowl and season with ¼ tsp salt, white pepper",
                            "sesame oil, and shaoxing wine. Beat the eggs.",
                            "Preheat wok over medium heat until it starts to smoke."
                                    + " Add 2 tbsp of oil and immediately add eggs. Scramble the eggs and remove from "
                                    + "wok immediately. Set aside.",
                            "Add the remaining tbsp of oil to the wok, turn up heat to high, add tomatoes"
                                    + " and scallions. Stir-fry for 1min, then add sugar, 0.5 tsp salt, and water. "
                                    + "If water cooks off too quickly, add a little more water. Add the cooked eggs.",
                            "Mix everything together, cover the wok, cook for 1-2mins until the tomatoes are completely"
                                    + " softened.",
                            "Uncover, continue to stir-fry over high heat until sauce thickens to your liking. Serve!"),
                    getGoalSet(), false),
            new Recipe(new Name("5 Ingredient Spelt Spaghetti"), new Time("25"),
                    getGrainSet("80g, Spelt Spaghetti"),
                    getVegetableSet("2 cup, Arugula (Salad Rockets)",
                            "300g, Tomatoes",
                            "300g, Zucchini",
                            "50g, Leek"),
                    getProteinSet("50g, Egg"),
                    getFruitSet(),
                    getOtherSet("2 tbsp, Xucker Ketchup",
                            "2 tbsp, Italian Herbs",
                            "1 tbsp, Fresh Basil",
                            "2 tbsp, Salt",
                            "1 tbsp, Pepper",
                            "0.5 tbsp, Olive Oil"),
                    getStepsList("Thinly slice Tomatoes and Leek and shred your Zucchini.",
                            "Heat up Oil in a large Pan on a medium-high Level and roast Leek until golden.",
                            "Now add Zucchini.",
                            "When the Zucchini´s roasted, stir in Tomatoes and turn down Heat to a lower Level.",
                            "Let cook for 5-7 Minutes until it turn into a Sauce.",
                            "Meanwhile, prepare Spaghetti according to the Directions stated on the Package.",
                            "Then, add Ketchup, Basil, Italian Herbs and Salt, Pepper as desired.",
                            "Finally, stir in Arugula and cooked Noodles and serve on a Plate with Poached Egg,"
                                    + " Parmesan and more fresh Basil."),
                    getGoalSet("Wholesome Wholemeal", "Herbivore"), false),
            new Recipe(new Name("Bacon, Spinach Caramelized Onion Pasta"), new Time("25"),
                    getGrainSet("170g, whole wheat penne pasta"),
                    getVegetableSet("10g, large yellow onion diced", "1 cup, spinach"),
                    getProteinSet("120g, chopped bacon"),
                    getFruitSet(),
                    getOtherSet("2 tbsp, butter",
                            "1 tbsp, brown sugar",
                            "1 tbsp, salt",
                            "1 tbsp, pepper",
                            "1 tbsp, garlic powder",
                            "0.5 cup, Parmesan cheese sprinkles"),
                    getStepsList("In a large skillet, add your bacon and cook for 1-2 mins until bacon is halfway done",
                            "In that same pan, and some butter, sugar, spices, and onion; "
                                    + "sautee until the onion becomes tender, and bacon starts to crisp up.",
                            "Boil pasta in salted water until al dente",
                            "Once the pasta is done, drain it loosely, but leave a tiny amount of water and add it to "
                                    + "the skillet with the bacon, and onion mixture.",
                            "Mix in the spinach and stir until tender",
                            "Sprinkle with Parmesan before serving."),
                    getGoalSet("Wholesome Wholemeal", "Bulk like the Hulk", "Herbivore"), false),
            new Recipe(new Name("Healthy Chicken Salad Sandwich"), new Time("10"),
                    getGrainSet("80 g, whole wheat pita (toasted)"),
                    getVegetableSet("120 g, celery (chopped small)", "55 g, butter lettuce (chopped)"),
                    getProteinSet("200 g, chicken breasts (large, cooked)", "350 g, greek yogurt (non-fat)",
                            "30 g, mixed nuts (chopped)"),
                    getFruitSet(),
                    getOtherSet("3 tbsp, mayonnaise",
                            "1 tbsp, garlic salt", "1 tbsp, Dijon mustard (whole grain dijon)"),
                    getStepsList("Toast one pita.",
                            "Prep ingredients by cooking, and then shredding or chopping 1/2 large chicken breast, "
                                    + "chop 1-2 stalks celery to get 1/4 c chopped celery, "
                                    + "quarter a handful of grapes to get 1/4 cup quartered grapes, "
                                    + "prepare lettuce by washing/chopping it if needed."
                                    + " Also prepare nuts by chopping them if needed, making 1 tbsp chopped nuts.",
                            "Make chicken salad by adding shredded/chopped chicken to a small mixing bowl, "
                                    + "then add 1/4 c Greek yogurt, 1/4 c mayo, 1/4 tsp garlic salt, "
                                    + "1 tsp Dijon mustard.",
                            "Mix together well. Once all incorporated, add in 1/4 c chopped celery, "
                                    + "1/4 c quartered red grapes, and 1 T chopped mixed nuts. Stir together.",
                            "Fill pita with chicken salad mixture and lettuce. Enjoy. "
                                    + "Store chicken salad in airtight container for up to 5 days"),
                    getGoalSet("Wholesome Wholemeal", "Bulk like the Hulk"), false),
            new Recipe(new Name("All Veggie Sandwich"), new Time("10"),
                    getGrainSet("300 g, whole-wheat bread"),
                    getVegetableSet("1 cup, baby spinach leaves", "40 g, thinly sliced cucumber",
                            "20 g, red bell pepper(seeded and sliced))", "1 cup, fresh pea shoots or "
                                    + "your favorite fresh sprouts", "50 g, ripe avocados"),
                    getProteinSet(),
                    getFruitSet(),
                    getOtherSet("4 tbsp, plain hummus", "1 tbsp, cilantro leaves",
                            "2 tbsp, kosher salt and fresh black pepper"),
                    getStepsList("Spread 1 tablespoon of hummus on each slice of bread.",
                            "Then add the avocado pulp of 1/2 avocado per slice and lightly mash with a fork.",
                            "Depending on the size of your bread slices you may need to use little less avocado. "
                                    + "I used about 1 avocado per sandwich.",
                            "Layer on spinach leaves, cucumber slices, red pepper, pea shoots, and cilantro leaves. "
                                    + "Generously season with salt and pepper. "
                                    + "Top with other slice of bread and enjoy."),
                    getGoalSet("Herbivore", "Wholesome Wholemeal"), false),
            new Recipe(new Name("Summer Asian Slaw"), new Time("25"),
                    getGrainSet(),
                    getVegetableSet("200g, shredded red and/or green cabbage",
                            "30g, red bell",
                            "20g, poblano",
                            "10g, scallions",
                            "15g, fresh basil (Thai basil, and/or mint)",
                            "15g, Thai chiles (dice)",
                            "10g, pepitas (with or without sesame seeds)"),
                    getProteinSet("20g, toasted peanuts" , "3 tbsp, cashew or peanut butter"),
                    getFruitSet("50g, ripe peach (thinly sliced)", "0.5 tbsp, lime juice"),
                    getOtherSet("15g, chopped cilantro (including stems)", "1 tbsp, Sea salt",
                            "0.5 tbsp, white miso paste", "0.25 tbsp, sesame oil", "1 tbsp, water"),
                    getStepsList("Make the dressing: In a small bowl, whisk together the cashew butter,"
                                    + " miso paste, lime juice, sesame oil, and ginger. "
                                    + "Whisk in enough water to create a drizzable consistency. Set aside.",
                            "In a dry cast-iron skillet over medium heat, char the peppers whole, "
                                    + "rotating until the edges have a little char, about 2 minutes per side. Remove."
                                    + " When cool to the touch, slice in half lengthwise, remove the stem, ribbing, "
                                    + "and seeds and slice horizontally into thin strips.",
                            "In a large bowl, combine the cabbage, peppers, scallions, cilantro, basil, chiles, "
                                    + "and ¾ of the dressing. Toss until combined. Add the remaining dressing, "
                                    + "if desired, and season to taste with a few pinches of sea salt. "
                                    + "Serve topped with the toasted nuts, seeds, and sliced peaches."),
                    getGoalSet("Herbivore"), false),
            new Recipe(new Name("Avocado Couscous Grapefruit salad with honey lime dressing"), new Time("35"),
                    getGrainSet("0.75 cup, uncooked whole wheat couscous", "100g, white beans"),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet("300g, large avocados",
                            "200g, large ruby red grapefruit",
                            "3 tbsp, freshly squeezed lime juice (for dressing)"),
                    getOtherSet("0.5 cup, crumbled feta cheese",
                            "3 tbsp, chopped fresh mint",
                            "3 tbsp, finely chopped fresh cilantro",
                            "2 tbsp, honey",
                            "2 tbsp, extra virgin olive oil",
                            "0.25 tbsp, kosher salt",
                            "0.75 cup, low sodium stock"),
                    getStepsList("In a small saucepan, bring the stock to a boil. Remove from the heat, "
                                    + "stir in the couscous, then cover at let sit 5 minutes. "
                                    + "Fluff with fork and set aside.",
                            "In a small bowl or large measuring cup,whisk together the dressing ingredients:"
                                    + " lime juice, honey, olive oil, and salt, or shake all of the ingredients "
                                    + "together in a mason jar with a tight-fitting lid.",
                            "In a large bowl, combine beans and couscous. Pour dressing over top and stir to combine. "
                                    + "Add the avocados, grapefruit, feta, mint, and cilantro. Toss gently."
                                    + " Serve chilled or at room temperature."),
                    getGoalSet("Fruity Fiesta", "Wholesome Wholemeal"), false),
            new Recipe(new Name("Teriyaki chicken noodle bowl"), new Time("35"),
                    getGrainSet(),
                    getVegetableSet("50 g, cloves garlic(minced)", "0.25 tbsp, freshly grated ginger",
                            "20g, green onion (thinly sliced)"),
                    getProteinSet("100 g, boneless and skinless chicken thighs(cut into 1-inch chunks)"),
                    getFruitSet(),
                    getOtherSet("150 g, refrigerated Yaki-Soba, seasoning sauce packets discarded",
                            "4 tbsp, cornstarch", "4 tsp, reduced sodium soy sauce", "4 tsp, reduced sodium soy sauce",
                            "3 tsp, brown sugar(packed)", "0.5 tbsp, honey", "0.25 tbsp, olive oil",
                            "0.25 tbsp, sesame seeds"),
                    getStepsList("In a large pot of boiling water, add Yaki-Soba until loosened, "
                                    + "about 1-2 minutes; drain well.",
                            "In a small bowl, whisk together cornstarch and 1/4 cup water; set aside.",
                            "In a small saucepan over medium heat, add soy sauce, brown sugar, garlic, ginger, "
                                    + "honey and 1 cup water; bring to a simmer. "
                                    + "Stir in cornstarch mixture until thickened enough to coat the back of a spoon, "
                                    + "about 2 minutes; let cool to room temperature. Reserve 1/4 cup and set aside.",
                            "In a gallon size Ziploc bag or large bowl, combine soy sauce mixture and chicken; "
                                    + "marinate for at least 30 minutes to overnight, turning the bag occasionally. "
                                    + "Drain the chicken from the marinade.",
                            "Heat olive oil in a large skillet over medium heat. "
                                    + "Add chicken and cook until golden brown and cooked through, about 3-4 minutes;"
                                    + " set aside.",
                            "Stir in Yaki-Soba and 1/4 cup reserved soy sauce"
                                    + " mixture until well combined, about 2 minutes.",
                            "Serve immediately, garnished with sesame seeds and green onion, if desired."),
                    getGoalSet("Bulk like the Hulk"), false),
            new Recipe(new Name("Overnight Oatmeal"), new Time("10"),
                    getGrainSet("1 cup, steel cut oats"),
                    getVegetableSet(),
                    getProteinSet(),
                    getFruitSet("1 cup, dried cranberries", "1 cup, dried figs"),
                    getOtherSet(" 4 cup, water", "0.5 cup, milk"),
                    getStepsList("In a slow cooker, combine all ingredients and set to low heat. "
                                    + "Cover and let cook for 8 to 9 hours.",
                            "Stir and remove to serving bowls. "
                                    + "This method works best if started before you go to bed."),
                    getGoalSet("Fruity Fiesta", "Wholesome Wholemeal"), false),
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.addRecipe(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a goal set containing the list of strings given.
     */
    public static Set<Goal> getGoalSet(String... goals) {
        return Arrays.stream(goals)
                .map(Goal::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an grains set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Grain> getGrainSet(String... grains) {
        Set<Grain> grainSet = new TreeSet<>();
        for (String grain: grains) {
            if (!grain.isBlank()) {
                String[] splitDetails = grain.split(",", 2);
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                grainSet.add(new Grain(name, quantity));
            }
        }
        return grainSet;
    }

    /**
     * Returns an vegetables set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Vegetable> getVegetableSet(String... vegetables) {
        Set<Vegetable> vegetableSet = new TreeSet<>();
        for (String vegetable: vegetables) {
            if (!vegetable.isBlank()) {
                String[] splitDetails = vegetable.split(",", 2);
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                vegetableSet.add(new Vegetable(name, quantity));
            }
        }
        return vegetableSet;
    }


    /**
     * Returns an proteins set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Protein> getProteinSet(String... proteins) {
        Set<Protein> proteinSet = new TreeSet<>();
        for (String protein: proteins) {
            if (!protein.isBlank()) {
                String[] splitDetails = protein.split(",", 2);
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                proteinSet.add(new Protein(name, quantity));
            }
        }
        return proteinSet;
    }

    /**
     * Returns an fruits set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Fruit> getFruitSet(String... fruits) {
        Set<Fruit> fruitSet = new TreeSet<>();
        for (String fruit: fruits) {
            if (!fruit.isBlank()) {
                String[] splitDetails = fruit.split(",", 2);
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                fruitSet.add(new Fruit(name, quantity));
            }
        }
        return fruitSet;
    }


    /**
     * Returns an others set containing the list of strings given in the format (name, quantity).
     */
    public static Set<Other> getOtherSet(String... others) {
        Set<Other> otherSet = new TreeSet<>();
        for (String other: others) {
            if (!other.isBlank()) {
                String[] splitDetails = other.split(",", 2);
                String name = splitDetails[1].trim();
                Quantity quantity = QuantityUtil.parseQuantity(splitDetails[0].trim());

                otherSet.add(new Other(name, quantity));
            }
        }
        return otherSet;
    }


    /**
     * Returns a steps list containing the list of steps given.
     */
    public static List<Step> getStepsList(String ... steps) {
        return Arrays.stream(steps)
                .map(Step::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns an empty grain set, to be used to replace the default grain ingredient.
     */
    public static Set<Grain> emptyGrainSet() {
        return new TreeSet<Grain>();
    }

    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Caesar Salad"), new Date(), getGoalSet("Herbivores")),
            new Record(new Name("Grilled Sandwich"), new Date(), getGoalSet("Wholesome Wholemeal")),
            new Record(new Name("Boiled Chicken"), new Date(), getGoalSet("Bulk like the Hulk")),
            new Record(new Name("Chocolate Cake"), new Date(), getGoalSet("Bulk like the Hulk", "Wholesome Wholemeal")),
            new Record(new Name("Omelet"), new Date(), getGoalSet()),
            new Record(new Name("Tuna Bread"), new Date(), getGoalSet("Wholesome Wholemeal"))
        };
    }

    public static ReadOnlyCookedRecordBook getSampleRecordBook() {
        CookedRecordBook sampleAb = new CookedRecordBook();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
        }
        return sampleAb;
    }

    public static Quote[] getSampleQuotes() {
        return new Quote[] {
            new Quote(new Content("Skip the diet, just eat healthy!")),
            new Quote(new Content("I don't eat junk food because I love my body!")),
            new Quote(new Content("Nothing tastes as good as healthy")),
            new Quote(new Content("I'm doing this for me")),
            new Quote(new Content("Work for it!")),
            new Quote(new Content("Mindset is what separates the best from the rest"))
        };
    }

    public static ReadOnlyQuoteBook getSampleQuoteBook() {
        QuoteBook sampleAb = new QuoteBook();
        for (Quote sampleQuote : getSampleQuotes()) {
            sampleAb.addQuote(sampleQuote);
        }
        return sampleAb;
    }

}
