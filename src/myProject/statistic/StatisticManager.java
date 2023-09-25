package myProject.statistic;



import myProject.statistic.event.CookedOrderEventDataRow;
import myProject.statistic.event.EventDataRow;
import myProject.statistic.event.EventType;
import myProject.statistic.event.VideoSelectedEventDataRow;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticManager {

    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {

    }


public Map<Date,Long> getAdvertisementStats(){
         return statisticStorage.
                getStorage().
                get(EventType.SELECTED_VIDEOS).
                 stream().
                 collect(Collectors.groupingBy(v->{Date date=v.getDate();
                     Date currentDate = new Date(date.getYear(), date.getMonth(), date.getDate());
                     return currentDate;}
                         ,Collectors.summingLong(adMoney->((VideoSelectedEventDataRow)adMoney).getAmount())));
}

    public static StatisticManager getInstance() {
        if (instance == null)
            instance = new StatisticManager();
        return instance;
    }

    public void register(EventDataRow data) {
statisticStorage.put(data);
    }


    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage= new HashMap<>();

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        public StatisticStorage() {
            for (EventType event:EventType.values()){
                storage.put(event,new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data){
storage.get(data.getType()).add(data);
        }
    }
   public Map<Date, Map<String, Integer>> getCookWorkload(){

        return  statisticStorage.getStorage().
                get(EventType.COOKED_ORDER).
                stream().
                collect(Collectors.groupingBy(
                                a->{Date date=a.getDate();
                                   Date currentDate = new Date(date.getYear(), date.getMonth(), date.getDate());
                        return currentDate;},
                        Collectors.groupingBy(a->((CookedOrderEventDataRow)a).getCookName(),
                                Collectors.summingInt(a->((CookedOrderEventDataRow)a).getTime()))));
    }
}
