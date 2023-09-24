package myProject.ad;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private StatisticAdvertisementManager(){

    }

    public static StatisticAdvertisementManager getInstance(){
        if(instance==null)
            instance=new StatisticAdvertisementManager();
        return instance;
    }

    public List<Advertisement> getActiveVideoSet(){
        return AdvertisementStorage.getInstance().list().stream().filter(v->v.getHits()>0).collect(Collectors.toList());
    }
    public List<Advertisement>getArchivedVideoSet(){
        return AdvertisementStorage.getInstance().list().stream().filter(v->v.getHits()==0).collect(Collectors.toList());
    }
}
