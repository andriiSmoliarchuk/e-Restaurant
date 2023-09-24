package myProject;

import myProject.ad.AdvertisementManager;
import myProject.ad.NoVideoAvailableException;
import myProject.kitchen.Order;
import myProject.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    private LinkedBlockingQueue queue;

    public void setQueue(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "myProject.Tablet{" +
                "number=" + number +
                '}';
    }


    public Order createOrder() {
        Order order = null;
        try {
            order = new TestOrder(this);
            extracted(order);
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

        return order;
    }

    private void extracted(Order order) {

        if (!order.isEmpty())
            queue.add(order);
        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        advertisementManager.processVideos();
    }

    public void createTestOrder() {
        TestOrder order = null;
        try {
            order = new TestOrder(this);
            extracted(order);
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }


    }


}
