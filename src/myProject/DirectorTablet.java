package myProject;

import myProject.ad.Advertisement;
import myProject.ad.StatisticAdvertisementManager;
import myProject.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DirectorTablet {
    public void printAdvertisementProfit(){
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        //*rename. get stats with money per days and sort desc of key;
        Map<Date,Long> adMap= StatisticManager.
                getInstance().
                getAdvertisementStats().
                entrySet().
                stream().
                sorted(Map.Entry.<Date,Long>comparingByKey().reversed()).
                collect(Collectors.toMap(a->a.getKey(),b->b.getValue(), (e1, e2) -> e1,
                        LinkedHashMap::new));


        for(Map.Entry<Date,Long>entry: adMap.entrySet()){
            double amount = 1.0 * entry.getValue()/100;
            System.out.println(formatter.format(entry.getKey()) + " - " + String.format(Locale.ENGLISH, "%.2f", amount));
        }
        double sum=1.0 *adMap.values().stream().mapToDouble(Double::valueOf).sum()/100;

        System.out.println("Total - " + String.format(Locale.ENGLISH, "%.2f", sum));

    }
    public void printCookWorkloading(){
        SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
      //получем рабочую статистику, затем сортируем по дате, потом по именам поваров и затем по рабочим часам в убывющем порядке
      //c chat GPT
        Map<Date, Map<String, Integer>> sortedMap = StatisticManager.getInstance()
                .getCookWorkload().entrySet().
                stream().
                sorted(Map.Entry.<Date,Map<String,Integer>>comparingByKey().reversed()).
                collect(Collectors.toMap(a->a.getKey(),b->b.getValue(), (e1, e2) -> e1,
                        LinkedHashMap::new));
//просто выводим по дням информацию
         for (Map.Entry<Date,Map<String,Integer>>entry:sortedMap.entrySet()){//перебираем даты
             System.out.println(format.format(entry.getKey()));
            for (Map.Entry<String,Integer>namesAndHourses:entry.getValue().entrySet()){// достаем все данные Имя и рабочое время
                System.out.printf("%s - %d min\n",namesAndHourses.getKey(),namesAndHourses.getValue()/60);
            }
             System.out.println();
        }
    }
    public void printActiveVideoSet(){
        List<Advertisement>listActiveVideoSet= StatisticAdvertisementManager.getInstance().getActiveVideoSet();
        Collections.sort(listActiveVideoSet,(o1,o2)->String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName()));
        for (Advertisement ad:listActiveVideoSet){
            ConsoleHelper.writeMessage(ad.getName()+" - "+ad.getHits()+"\n");
        }
    }
    public void printArchivedVideoSet(){
List<Advertisement>listArchivedVideoSet=StatisticAdvertisementManager.getInstance().getArchivedVideoSet();
        Collections.sort(listArchivedVideoSet,(o1,o2)->String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName()));

      for (Advertisement ad:listArchivedVideoSet) {
          ConsoleHelper.writeMessage(ad.getName() + "\n");
      }
    }
}
