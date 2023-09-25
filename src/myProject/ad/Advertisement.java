package myProject.ad;

public class Advertisement {

    private Object content;//some advertisement video

    private String name;

    private long initialAmount;//initial cost, cost of advertising in kopecks. use long to avoid rounding problems


    private int hits; //amount of paid orders


    private int duration;//duration in seconds

    private long amountPerOneDisplaying;//amount of money per impression in kopecks

    public int getHits() {
        return hits;
    }

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

        if (hits > 0) {
            amountPerOneDisplaying = initialAmount / hits;
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() {
        if (hits == 0) {
            throw new UnsupportedOperationException();
        }
        hits--;
    }


}
