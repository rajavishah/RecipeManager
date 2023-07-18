package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RecipeStorage {

	DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void addRecipesToDb(List<Recipe> addRecipesFromList) throws SQLException {
        String checkRecipeExists = "SELECT * from RecipesInfo where recipeName = ?";
        PreparedStatement checkInfo = connectDB.prepareStatement(checkRecipeExists);
        PreparedStatement insertInfo = null;
        int batchSize = 0;

        for (Recipe recipe : addRecipesFromList) {
            checkInfo.setString(1, recipe.getName());
            ResultSet queryResult = checkInfo.executeQuery();

            if (queryResult.isBeforeFirst()) {
                System.out.println(recipe.getName() + " already exists in the database");
            } else {
                if (insertInfo == null) {
                    String query = "Insert into RecipesInfo (recipesImg, recipeName, recipeDescription, recipeChef, recipeRating, recipeContents) values (?,?,?,?,?,?)";
                    insertInfo = connectDB.prepareStatement(query);
                }

                try {
                    String img = recipe.getImage();
                    File file = new File(img);
                    if (!file.exists()) {
                        throw new FileNotFoundException("File not found: " + img);
                    }
                    System.out.println("File size: " + file.length() + " bytes");
                    FileInputStream fis = new FileInputStream(file);
                    System.out.println("Input stream created: " + fis);
                    insertInfo.setBinaryStream(1, fis, file.length());
                } catch (FileNotFoundException e) {
                    System.err.println("Could not find image file: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Could not set the image file as a binary stream: " + e.getMessage());
                }

                insertInfo.setString(2, recipe.getName());
                insertInfo.setString(3, recipe.getDescription());
                insertInfo.setString(4, recipe.getchefName());
                insertInfo.setDouble(5, recipe.getRating());
                insertInfo.setString(6, recipe.getContents());
                insertInfo.addBatch();
                batchSize++;

                if (batchSize % 50 == 0) {
                    insertInfo.executeBatch();
                    batchSize = 0;
                }
            }
        }

        if (insertInfo != null) {
            insertInfo.executeBatch();
        }

        // Close the database connection
        connectDB.close();
    }
}
