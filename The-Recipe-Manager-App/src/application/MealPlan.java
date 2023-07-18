package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MealPlan {
    private static Queue<Recipe> lowCarbRecipes = new LinkedList<>();
    private static Queue<Recipe> ketoRecipes = new LinkedList<>();
    private static Queue<Recipe> weightLossRecipes = new LinkedList<>();
    private static Queue<Recipe> customRecipes = new LinkedList<>();

    private static HashMap<Integer, List<Recipe>> customRecipesMap = new HashMap<>();


    public static void addRecipe(String mealPlan, Recipe recipe) {
        switch(mealPlan) {
            case "Low Carb":
                lowCarbRecipes.add(recipe);
                break;
            case "Keto":
                ketoRecipes.add(recipe);
                break;
            case "Weight Loss":
                weightLossRecipes.add(recipe);
                break;
            case "Custom Meal Plan":
            	customRecipes.add(recipe);
                break;

        }
    }

   
    public static void addRecipeCustom(String mealPlan, Recipe recipe, Integer userId) {
        if (customRecipesMap == null) {
            customRecipesMap = new HashMap<>();
        }
        
       
        
        switch(mealPlan) {
            case "Custom Meal Plan":
                if (customRecipesMap.containsKey(userId)) {
                    // add the recipe to the user's existing list of recipes
                    List<Recipe> recipes = customRecipesMap.get(userId);
                    recipes.add(recipe);
                } else {
                    // create a new list and add the recipe to it
                    List<Recipe> recipes = new ArrayList<>();
                    recipes.add(recipe);
                    customRecipesMap.put(userId, recipes);
                }
                break;
            default:
                break;
        }
        
        if (userId == null || customRecipesMap.containsKey(null)) {
            System.out.println("Invalid user ID or null key in customRecipesMap.");
            return;
        }
    }



    public static Queue<Recipe> getRecipes(String mealPlan) {
        switch(mealPlan) {
            case "Low Carb":
                return lowCarbRecipes;
            case "Keto":
                return ketoRecipes;
            case "Weight Loss":
                return weightLossRecipes;
            case "Custom Meal Plan":
            	return customRecipes;
            default:
                return null;
        }
    }

    public static Queue<Recipe> getCustomRecipes(String mealPlan,  Integer userId) {


        switch(mealPlan) {
        case "Custom Meal Plan":
            if (customRecipesMap.containsKey(userId)) {
                // return the user's list of custom recipes
                return new LinkedList<>(customRecipesMap.get(userId));
            } else {
                // return an empty queue if the user has no custom recipes
            	System.out.println("Meal Plan is empty");
                return new LinkedList<>();
            }
        default:
            return null;
    }
        }
    }


