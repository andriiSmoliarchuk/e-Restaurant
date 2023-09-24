package myProject;

import myProject.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish>list=new ArrayList<>();
        writeMessage("Введите блюдо или \"exit\n"+Dish.allDishesToString());
 while (true) {
     String dishName = readString();
     if (dishName.toUpperCase().equals("EXIT"))
         break;
     try {
         Dish dish = Dish.valueOf(dishName.toUpperCase());
         list.add(dish);
         writeMessage(dish + " has been successfully added to your order");
     } catch (IllegalArgumentException e) {
         writeMessage(dishName + " hasn't been detected");

     }
 }

        return list;
    }
}
