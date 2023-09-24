package myProject;

import myProject.kitchen.Cook;
import myProject.kitchen.Order;
import myProject.kitchen.Waiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private final static LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>(200);
    private final static int ORDER_CREATING_INTERVAL = 100;
    public static void main(String[] args) throws IOException {


        Cook amigo = new Cook("Amigo");
        amigo.setQueue(ORDER_QUEUE);
        Cook fry=new Cook("Fry");
        fry.setQueue(ORDER_QUEUE);
        Waiter waiter = new Waiter();

        amigo.addObserver(waiter);
        fry.addObserver(waiter);
        Thread cook1=new Thread(amigo);
        cook1.start();
        Thread cook2=new Thread(fry);

        List<Tablet>tablets=new ArrayList<>();
        for (int i=0;i<5;i++){
            Tablet tablet=new Tablet(i+1);
            tablet.setQueue(ORDER_QUEUE);


            tablets.add(tablet);
        }
        Thread thread=new Thread(new RandomOrderGeneratorTask(tablets,ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
