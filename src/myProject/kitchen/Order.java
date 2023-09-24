package myProject.kitchen;


import myProject.ConsoleHelper;
import myProject.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        if (dishes.size() == 0)
            return "";

        return String.format("Your order: %s of %s, cooking time %dmin", dishes, tablet, getTotalCookingTime());

    }

    public int getTotalCookingTime() {
        int result = 0;
        for (Dish val : dishes) {
            result += val.getDuration();
        }
        return result;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();


    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    protected void initDishes() throws IOException {
     this.dishes= ConsoleHelper.getAllDishesForOrder();

    }
}
