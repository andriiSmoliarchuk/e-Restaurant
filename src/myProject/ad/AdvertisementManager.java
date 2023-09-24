package myProject.ad;



import myProject.ConsoleHelper;
import myProject.event.EventDataRow;
import myProject.event.VideoSelectedEventDataRow;
import myProject.statistic.StatisticManager;
import myProject.statistic.event.NoAvailableVideoEventDataRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {

        if (storage.list().isEmpty()) {
            StatisticManager.getInstance().register((EventDataRow) new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }
 List<Advertisement>list=getVideos();//получить из списка доступных рекламных роликов оптимальный
//register new myProject.statistic myProject.event
        StatisticManager.
                getInstance().
                register(new VideoSelectedEventDataRow(list,list.stream()
                        .mapToLong(a->a.getAmountPerOneDisplaying())
                        .sum(),list.stream().mapToInt(ad->ad.getDuration())
                        .sum()));//register new myProject.event to myProject.statistic manager

        //displaying an myProject.ad videos
 for(Advertisement advertisement:list){
     ConsoleHelper.writeMessage((advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() +
             ", " + (1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration())));
     advertisement.revalidate();
 }
     
    }
       private List<Advertisement> getVideos(){
           List<Advertisement> list=storage.list().stream().filter(a->a.getHits()>0).collect(Collectors.toList());
   Integer[] weights = list.stream().map(a->a.getDuration()).toArray(Integer[]::new);
           Integer[] prices = list.stream().map(a->(int)a.getAmountPerOneDisplaying()).toArray(Integer[]::new);

        int count = weights.length;
        int maxWeight = timeSeconds;

        int[][] A;
        A = new int[count + 1][];
        for (int i = 0; i < count + 1; i++) {
            A[i] = new int[maxWeight + 1];
        }

        for (int k = 0; k <= count; k++) {
            for (int s = 0; s <= maxWeight; s++) {
                if (k == 0 || s == 0) {
                    A[k][s] = 0;
                } else {
                    if (s >= weights[k - 1]) {
                        A[k][s] = Math.max(A[k - 1][s], A[k - 1][s - weights[k - 1]] + prices[k - 1]);
                    } else {
                        A[k][s] = A[k - 1][s];
                    }
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        traceResult(A, weights, count, maxWeight, result);
return result.stream().map(a->list.get(a-1)).collect(Collectors.toList());
      
    }
       private static void traceResult(int[][] A, Integer[] weight, int k, int s, ArrayList<Integer> result) {
        if (A[k][s] == 0) {
            return;
        }
        if (A[k -1][s] == A[k][s]) {
            traceResult(A, weight, k - 1, s, result);
        } else {
            traceResult(A, weight, k - 1, s - weight[k - 1], result);
            result.add(0, k);
        }
    }
}



