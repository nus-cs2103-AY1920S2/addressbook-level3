package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Cart;
import seedu.address.model.Cookbook;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CookingPapa} with sample data.
 */
public class SampleDataUtil {

    public static ReadOnlyCookbook getSampleCookbook() {
        Cookbook sampleCookbook = new Cookbook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleCookbook.addRecipe(sampleRecipe);
        }
        return sampleCookbook;
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleInventory = new Inventory();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleInventory.addIngredient(sampleIngredient);
        }
        return sampleInventory;
    }

    public static ReadOnlyCart getSampleCart() {
        Cart sampleCart = new Cart();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleCart.addIngredient(sampleIngredient);
        }
        return sampleCart;
    }

    public static Ingredient[] getSampleIngredients() {
        return new Ingredient[] {
            new Ingredient(new IngredientName("Eggs"), new IngredientQuantity("10")),
            new Ingredient(new IngredientName("Parmesan"), new IngredientQuantity("1 kg")),
            new Ingredient(new IngredientName("Bacon"), new IngredientQuantity("10 pieces")),
            new Ingredient(new IngredientName("Linguine"), new IngredientQuantity("250 g")),
            new Ingredient(new IngredientName("Olive oil"), new IngredientQuantity("600 ml")),
            new Ingredient(new IngredientName("Black pepper"), new IngredientQuantity("300 g")),
            new Ingredient(new IngredientName("Salt"), new IngredientQuantity("500 g")),
            new Ingredient(new IngredientName("Garlic"), new IngredientQuantity("5 cloves")),
            new Ingredient(new IngredientName("Dried chili flakes"), new IngredientQuantity("50 g")),
            new Ingredient(new IngredientName("Minced beef"), new IngredientQuantity("500 g")),
            new Ingredient(new IngredientName("Tomatoes"), new IngredientQuantity("10")),
            new Ingredient(new IngredientName("Milk"), new IngredientQuantity("500 ml")),
            new Ingredient(new IngredientName("Butter"), new IngredientQuantity("500 g"))
        };
    }

    public static Recipe[] getSampleRecipes() {

        UniqueIngredientList scrambledEggsIngredients = new UniqueIngredientList();
        UniqueStepList scrambledEggsSteps = new UniqueStepList();
        scrambledEggsIngredients.add(new Ingredient(new IngredientName("Eggs"), new IngredientQuantity("4")));
        scrambledEggsIngredients.add(new Ingredient(new IngredientName("Butter"), new IngredientQuantity("20 g")));
        scrambledEggsIngredients.add(new Ingredient(new IngredientName("Milk"), new IngredientQuantity("200 ml")));
        scrambledEggsSteps.add(new Step("Beat eggs, milk, salt and pepper in medium bowl until blended."));
        scrambledEggsSteps.add(new Step("Heat butter in large nonstick skillet over medium heat until hot. "
                + "Pour in egg mixture. As eggs begin to set, gently PULL the eggs across the pan with a spatula, "
                + "forming large soft curds."));
        scrambledEggsSteps.add(new Step("Continue cooking pulling, lifting and folding eggs until thickened "
                + "and no visible liquid egg remains. Do not stir constantly. "
                + "Remove from heat. Serve immediately."));

        UniqueIngredientList carbonaraIngredients = new UniqueIngredientList();
        UniqueStepList carbonaraSteps = new UniqueStepList();
        carbonaraIngredients.add(new Ingredient(new IngredientName("Guanciale"), new IngredientQuantity("200 g")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Spaghetti"), new IngredientQuantity("1 serving")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Cheese"), new IngredientQuantity("200 g")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Eggs"), new IngredientQuantity("6")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Olive oil"), new IngredientQuantity("2 tbsp")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Salt"), new IngredientQuantity("1 tbsp")));
        carbonaraIngredients.add(new Ingredient(new IngredientName("Pepper"), new IngredientQuantity("2 tbsp")));
        carbonaraSteps.add(new Step("Heat 500ml of water in a large pot over high. "
                + "When water starts to steam, add 3 Tbsp. salt and "
                + "cover pot with a lid (this will bring water to a boil faster)."));
        carbonaraSteps.add(new Step("While you are waiting on the water, do a little prep. Remove guanciale from "
                + "packaging and cut into about quarter inch strips. "
                + "Finely grate 2 oz. cheese and set aside one-quarter of cheese for later."));
        carbonaraSteps.add(new Step("Whisk 4 egg yolks and 2 whole eggs in a medium bowl until no streaks remain, "
                + "then stir in remaining grated cheese. Add several cranks of pepper and set aside."));
        carbonaraSteps.add(new Step("Working next to pot, heat 2 Tbsp. oil in a large Dutch oven or "
                + "other heavy pot over medium. Add guanciale and cook, stirring occasionally, "
                + "until crisp around the edges, 7 to 10 minutes."));
        carbonaraSteps.add(new Step("Remove pot from heat. Using a wooden spoon, fish out guanciale and transfer to "
                + "a small bowl. Pour fat into a heatproof measuring cup, then add back about 3 Tbsp of fat to pot. "
                + "Discard any remaining fat."));
        carbonaraSteps.add(new Step("Cook pasta in boiling water, stirring occasionally, 2 minutes shy of package "
                + "instructions. Just before pasta is finished, scoop out 1 and 3 quarter cups pasta cooking "
                + "liquid with same heatproof measuring cup."));
        carbonaraSteps.add(new Step("Add 1 cup reserved pasta cooking liquid to Dutch oven and bring to a boil over "
                + "medium high. Drain pasta in a colander, then transfer to Dutch oven."));
        carbonaraSteps.add(new Step("Cook pasta, stirring constantly and vigorously, until al dente and water is "
                + "reduced by about half, about 2 minutes. Remove pot from heat."));
        carbonaraSteps.add(new Step("Whisk quarter cup pasta cooking liquid into reserved egg mixture, "
                + "then very slowly stream into Dutch oven, stirring constantly, until cheese is melted and egg is "
                + "thickened to form a glossy sauce. Season with salt, if needed. Thin sauce with remaining half cup "
                + "pasta cooking liquid, adding a tablespoonful at a time, "
                + "until it's the consistency of heavy cream"));
        carbonaraSteps.add(new Step("Mix in guanciale and divide pasta among bowls. "
                + "Top with pepper and reserved cheese."));

        UniqueIngredientList aglioOlioIngredients = new UniqueIngredientList();
        UniqueStepList aglioOlioSteps = new UniqueStepList();
        aglioOlioIngredients.add(new Ingredient(new IngredientName("Spaghetti"), new IngredientQuantity("1 serving")));
        aglioOlioIngredients.add(new Ingredient(new IngredientName("Garlic"), new IngredientQuantity("6 cloves")));
        aglioOlioIngredients.add(new Ingredient(new IngredientName("Olive Oil"), new IngredientQuantity("1 tbsp")));
        aglioOlioIngredients.add(new Ingredient(new IngredientName("Parsley"), new IngredientQuantity("1 serving")));
        aglioOlioIngredients.add(new Ingredient(new IngredientName("Cheese"), new IngredientQuantity("200 g")));
        aglioOlioSteps.add(new Step("Bring a large pot of lightly salted water to a boil. Cook spaghetti in the "
                + "boiling water, stirring occasionally until cooked through but firm to the bite, "
                + "about 12 minutes. Drain and transfer to a pasta bowl."));
        aglioOlioSteps.add(new Step("Combine garlic and olive oil in a cold skillet. Cook over medium heat to slowly "
                + "toast garlic, about 10 minutes. Reduce heat to medium low when olive oil begins to bubble. "
                + "Cook and stir until garlic is golden brown, about another 5 minutes. Remove from heat."));
        aglioOlioSteps.add(new Step("Stir red pepper flakes, black pepper, and salt into the pasta. "
                + "Pour in olive oil and garlic, and sprinkle on Italian parsley "
                + "and half of the cheese; stir until combined."));
        aglioOlioSteps.add(new Step("Serve pasta topped with the remaining cheese."));

        UniqueIngredientList bologneseIngredients = new UniqueIngredientList();
        UniqueStepList bologneseSteps = new UniqueStepList();
        bologneseIngredients.add(new Ingredient(new IngredientName("Olive oil"), new IngredientQuantity("2 tbsp")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Beef"), new IngredientQuantity("400 g")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Onion"), new IngredientQuantity("1")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Garlic"), new IngredientQuantity("2 cloves")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Carrot"), new IngredientQuantity("100 g")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Tomatoes"), new IngredientQuantity("800 g")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Beef Stock cube"), new IngredientQuantity("1")));
        bologneseIngredients.add(new Ingredient(new IngredientName("Spaghetti"), new IngredientQuantity("1 Serving")));
        bologneseSteps.add(new Step("Heat a large saucepan over a medium heat. Add a tablespoon of olive oil "
                + "and once hot add the beef mince and a pinch of salt and pepper. Cook the mince until well browned "
                + "over a medium high heat. Be careful not to burn the mince. It just needs to be a dark "
                + "brown colour. Once browned, transfer the mince to a bowl and set aside."));
        bologneseSteps.add(new Step("Add another tablespoon of oil to the saucepan you browned the mince in and "
                + "turn the heat to medium. Add the onions and a pinch of salt and fry gently for 5 to 6 minutes, "
                + "or until softened and translucent. Add the garlic and cook for another 2 minutes. "
                + "Add the grated carrot then pour the mince and any juices in the bowl back into the saucepan."));
        bologneseSteps.add(new Step("Add the tomatoes to the pan and stir well to mix. Pour in the stock, "
                + "bring to a simmer and then reduce the temperature to simmer gently for 45 minutes, "
                + "or until the sauce is thick and rich. Taste and adjust the seasoning as necessary."));
        bologneseSteps.add(new Step("When ready to cook the spaghetti, heat a large saucepan of water and add a "
                + "pinch of salt. Cook according to the packet instructions. "
                + "Once the spaghetti is cooked through, drain and add to the pan with the "
                + "bolognese sauce. Mix well and serve."));

        return new Recipe[]{
            new Recipe(new RecipeName("Scrambled eggs"),
                new RecipeDescription("Gordan Ramsay's famous creamy and fluffy scrambled eggs."),
                    scrambledEggsIngredients, scrambledEggsSteps,
                getTagSet("Simple", "Celebrity")),
            new Recipe(new RecipeName("Carbonara"),
                new RecipeDescription("The best dish sold in NUS."),
                carbonaraIngredients, carbonaraSteps,
                getTagSet("Pasta", "Italian", "Simple", "Creamy")),
            new Recipe(new RecipeName("Aglio olio"),
                new RecipeDescription("A traditional Italian pasta dish made "
                    + "with garlic in olive oil, and dried red chili flakes."),
                aglioOlioIngredients, aglioOlioSteps,
                getTagSet("Pasta", "Spicy", "Italian", "Vegan")),
            new Recipe(new RecipeName("Spaghetti bolognese"),
                new RecipeDescription("Jaime Oliver's great version of the classic Italian Bolognese."),
                bologneseIngredients, bologneseSteps,
                getTagSet("Pasta", "Tomatoes", "Celebrity"))
        };
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }



}
