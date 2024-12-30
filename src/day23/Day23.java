package day23;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 {



    public static void main(String[] args) {
        var filePath = "/day23.txt";

        Map<String,List<String>> map = readLists(filePath);
        Set<List<String>> output = new HashSet<>();
        map.keySet().stream().filter(k -> k.startsWith("t")).forEach(k->{
            List<String> master = map.get(k);
            for(int i=0; i<master.size()-1;i++){
                for(int j=i+1; j<master.size();j++){
                    if(map.get(master.get(i)).contains(master.get(j))){
                        List<String> singleMap = new ArrayList<>(List.of(k, master.get(i), master.get(j)));
                        Collections.sort(singleMap);
                        output.add(singleMap);
                    }
                }
            }
        });


        System.out.println(output.size());
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
