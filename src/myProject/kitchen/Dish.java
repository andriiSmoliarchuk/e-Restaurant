package myProject.kitchen;

public enum Dish {
    FISH(25), STEAK(30), SOUP(15), JUICE(5), WATER(3);
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }


    public static String allDishesToString(){
        StringBuilder builder=new StringBuilder();
        for (Dish dish:Dish.values()){
            builder.append(dish.name()+", ");
        }
        if (builder.length() >= 2) {
            builder.setLength(builder.length() - 2); // Удаляем последние два символа (", ")
        }
        return builder.toString().trim();
    }

    
}
