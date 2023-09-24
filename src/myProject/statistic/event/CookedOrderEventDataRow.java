package myProject.statistic.event;



import myProject.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow{
    private String tabletName;
    private String cookName;
    private int cookingTimeSeconds;

    public String getCookName() {
        return cookName;
    }

    private List<Dish> cookingDishes;
    private Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;// - время приготовления заказа в секундах
        this.cookingDishes = cookingDishes;//список блюд для приготовления
        this.currentDate=new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }
}
