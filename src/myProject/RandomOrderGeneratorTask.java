package myProject;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private int interval;
    private List<Tablet> tablets;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Tablet tablet = tablets.get((int) (Math.random() * tablets.size()));
                tablet.createTestOrder();
                Thread.sleep(interval);
            } catch (InterruptedException e) {

            }
        }

    }
}
