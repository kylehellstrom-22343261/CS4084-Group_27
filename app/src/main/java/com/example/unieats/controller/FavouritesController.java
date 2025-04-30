package com.example.unieats.controller;

import android.content.Context;

import com.example.unieats.model.Restaurant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FavouritesController {
    private static String favouritesFileName = "favourites.dsv";

    public interface FavouritesCallback {
        void onFavouritesLoaded(List<Restaurant> favourites);
    }

    public static boolean isFavourite(Context context, String restaurantName) {
        ArrayList<String> currentFavourites = readFavourites(context);
        return currentFavourites.contains(restaurantName);
    };

    public static void writeFavourite(Context context, String restaurantName) {
        ArrayList<String> currentFavourites = readFavourites(context);
        String outputString = "";

        for (String restaurant : currentFavourites) {
            outputString += restaurant + "\n";
        }

        try (FileOutputStream fos = context.openFileOutput(favouritesFileName, Context.MODE_PRIVATE)) {
            if(!currentFavourites.contains(restaurantName)){
                outputString += restaurantName + "\n";
            }

            fos.write(outputString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeFavourite(Context context, String restaurantName) {
        ArrayList<String> currentFavourites = readFavourites(context);
        currentFavourites.remove(restaurantName);

        String outputString = "";

        for (String restaurant : currentFavourites) {
            outputString += restaurant + "\n";
        }

        try (FileOutputStream fos = context.openFileOutput(favouritesFileName, Context.MODE_PRIVATE)) {
            fos.write(outputString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getFavourites(Context context, FavouritesCallback callback) {
        ArrayList<String> favouritesStrings = readFavourites(context);
        ArrayList<Restaurant> result = new ArrayList<>();

        writeFavourite(context, "Stables Club");
        writeFavourite(context, "Scholars Club");

        RestaurantController.getRestaurants(restaurants -> {
            for (Restaurant r : restaurants) {
                if (favouritesStrings.contains(r.getBusinessName())) {
                    result.add(r);
                }
            }

            callback.onFavouritesLoaded(result);
        });

    }

    public static ArrayList<String> readFavourites(Context context) {
        ArrayList<String> result = new ArrayList<>();

        try (FileInputStream fis = context.openFileInput(favouritesFileName)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();

                while (line != null) {
                    result.add(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error opening raw file for reading
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
