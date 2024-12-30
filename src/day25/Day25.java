package day25;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day25 {



    public static void main(String[] args) {
        var filePath = "/day25.txt";

        List<List<Integer>> slotsNumbers = new ArrayList<>();
        List<List<Integer>> keysNumbers = new ArrayList<>();

        readLists(filePath, slotsNumbers, keysNumbers);

        System.out.println(slotsNumbers);
        System.out.println(keysNumbers);

        int max = 5;

        long sum = 0;
        for (List<Integer> slot : slotsNumbers){
            for(List<Integer> key : keysNumbers){
                if(slot.size() == key.size()) {
                    boolean eq = true;

                    int i =0;
                    while (eq && i<slot.size()){
                        if(key.get(i) + slot.get(i) > max){
                            eq =false;
                        }
                        i++;
                    }

                    if(eq){
                        sum++;
                    }
                }
            }

        }

        System.out.println(sum);

    }





    private static void readLists(String filePath, List<List<Integer>> slotsNumbers,List<List<Integer>> keysNumbers) {
        List<List<List<String>>> slots = new ArrayList<>();
        List<List<List<String>>> keys = new ArrayList<>();
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            boolean newGrid = true;
            boolean isKey = false;
            while ((line = reader.readLine()) != null) {
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                if(newGrid){
                    HashSet<String> distinct = new HashSet<>(values);
                    if(distinct.contains("#")){
                        slots.add(new ArrayList<>());
                        isKey = false;
                    }else {
                        keys.add(new ArrayList<>());
                        isKey = true;
                    }
                    newGrid = false;
                 } else{
                    if(line.isBlank()){
                        newGrid = true;
                    } else {
                        if(isKey){
                            keys.get(keys.size()-1).add(values);
                        }else{
                            slots.get(slots.size()-1).add(values);
                        }

                    }
                }



            }


            for(List<List<String>> slot : slots){
                slotsNumbers.add(new ArrayList<>());
                for(int i=0;i<slot.get(0).size();i++){
                    int n =0;
                    for (int j=0; j< slot.size();j++){
                        if(slot.get(j).get(i).equals("#")){
                            n++;
                        }
                    }

                    slotsNumbers.get(slotsNumbers.size()-1).add(n);
                }
            }

            for(List<List<String>> key : keys){
                keysNumbers.add(new ArrayList<>());
                for(int i=0;i<key.get(0).size();i++){
                    int n =0;
                    for (int j=0; j< key.size();j++){
                        if(key.get(j).get(i).equals("#")){
                            n++;
                        }
                    }

                    keysNumbers.get(keysNumbers.size()-1).add(n-1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
