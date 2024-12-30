package day23;

import day1.Day1;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day23Part2 {



    public static void main(String[] args) {
        var filePath = "/day23.txt";

        Map<String,List<String>> map = readLists(filePath);
        Set<List<String>> output = new HashSet<>();

        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, Comparator.comparingInt(k -> map.get(k).size()));

        final List<List<String>> finalOutput = new ArrayList<>();

        map.keySet().stream().forEach(k->{

            List<String> master = map.get(k);
            List<List<String>> commons = new ArrayList<>();
            commons.add(new ArrayList<>());
            commons.get(commons.size()-1).addAll(master);
            commons.get(commons.size()-1).add(k);
            Collections.sort(commons.get(commons.size()-1));



            for(int i=0; i<master.size()-1;i++){
                commons.add(new ArrayList<>());
                commons.get(commons.size()-1).add(k);
                commons.get(commons.size()-1).add(master.get(i));
                map.get(master.get(i)).forEach(s ->{
                    if(master.contains(s)){
                        commons.get(commons.size()-1).add(s);
                    }
                });
                Collections.sort(commons.get(commons.size()-1));

                //System.out.println(commons.get(commons.size()-1));
            }
            System.out.println("------");

            Collections.sort(commons, Comparator.comparingInt(l -> l.size()));
            Collections.reverse(commons);


            List<String> starting = commons.get(0);
            for(int i =1; i< commons.size();i++){
                System.out.println(commons.get(i));

                final int currentI = i;
                starting = starting.stream()
                        .distinct()
                        .filter(v ->commons.get(currentI).contains(v))
                        .collect(Collectors.toList());
                System.out.println(i);
                System.out.println(starting.size());
                if(starting.size()-1 == i+1){
                    System.out.println("output");
                    System.out.println(starting);
                    finalOutput.add(new ArrayList<>(starting));
                }
            }

        });

        Collections.sort(finalOutput, Comparator.comparingInt(s -> s.size()));
        System.out.println("final");
        finalOutput.stream().forEach(v -> System.out.println(v));
        System.out.println(finalOutput.get(finalOutput.size()-1).size());
        System.out.println(finalOutput.get(finalOutput.size()-1));
    }





    private static Map<String,List<String>> readLists(String filePath) {
        Map<String,List<String>> map = new HashMap<>();
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String key = line.trim().split("-")[0];
                String value = line.trim().split("-")[1];
                map.computeIfAbsent(key, k-> new ArrayList<>()).add(value);
                map.computeIfAbsent(value, k-> new ArrayList<>()).add(key);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
