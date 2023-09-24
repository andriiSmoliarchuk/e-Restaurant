package myProject.kitchen;


import myProject.ConsoleHelper;
import myProject.Tablet;
import myProject.event.CookedOrderEventDataRow;
import myProject.statistic.StatisticManager;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable{
    private final String name;
   private LinkedBlockingQueue queue;

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    private boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    public void startCookingOrder(Order order){
        this.busy = true;

        Tablet tablet = order.getTablet();

        ConsoleHelper.writeMessage(name + " Start cooking - " + order);

        int totalCookingTime = order.getTotalCookingTime();
        CookedOrderEventDataRow row = new CookedOrderEventDataRow(order.getTablet().toString(), name, totalCookingTime * 60, order.getDishes());
        StatisticManager.getInstance().register(row);

        try {
            Thread.sleep(totalCookingTime * 10);
        } catch (InterruptedException ignored) {
        }

        setChanged();
        notifyObservers(order);
        this.busy = false;
    }

    @Override
    public void run() {
        Thread deamonThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(10);
                        if (!queue.isEmpty()) {

                                if (!Cook.this.isBusy()) {
                                    Cook.this.startCookingOrder((Order) queue.take());
                                }
                            }
                        }

                } catch (InterruptedException e) {
                }

            }
        };
        deamonThread.setDaemon(true);
        deamonThread.start();
    }
    }

