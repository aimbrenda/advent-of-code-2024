package day9;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day9Part2 {

/*
    From the original string
    for each space if going back if not -1
    if space less then space put in but first concatenate 0 free space
    place -1 add create an order id list
 */

    public static void main(String[] args) {
        var filePath = "/day9.txt";

        long sum = 0;

        String input = readLists(filePath);

        List<String> outputString = new ArrayList<>();

        List<Integer> dots = new ArrayList<>();
        List<Integer> memory = new ArrayList<>();
        List<Integer> occurrencies = new ArrayList<>();
        Map<Integer, Integer> memoryMap = new HashMap<>();
        int id = 0;
        for(int i =0; i< input.length(); i++){
            int value = Integer.parseInt(input, i, i+1, 10);
            for(int j =0; j<value; j++){
                if(i%2 == 0){
                    outputString.add(String.valueOf(id));

                } else {
                    outputString.add(".");
                }
            }

            if(i%2 == 0) {
                memory.add(id);
                memoryMap.put(id, value);
                id++;
            } else{
                dots.add(value);
            }

        }



        List<Integer> values = new LinkedList<>();
        for(char c: input.toCharArray()) {
            values.add(Integer.parseInt(String.valueOf(c)));
        }



        int startV = memory.get(memory.size()-1);

        for(int i = startV; i>0;i--){
            for(int j=0; j< memory.indexOf(i); j++){
                if(dots.get(j) >= memoryMap.get(i)) {
                    int originalSize = dots.get(j);
                    dots.set(j, 0);
                    dots.add(j+1, originalSize-memoryMap.get(i));
                    memory.add(j+1, i);
                    memory.remove(memory.lastIndexOf(i));
                }
            }

        }

        System.out.println(memory);
        System.out.println(dots);

        for(int i =0; i<memory.size()+dots.size()-1;i++){

        }





        /*for(int i = 0; i< values.size(); i++){
            System.out.println(i);
            if(i%2 != 0){
                for(int j = startPoint; j > i; j--) {
                    if(j%2 == 0){
                        int space = values.get(i);
                        int memSpace = values.get(j);

                        if(space >= memSpace){
                            memory.add((i+1)/2, memory.get(j/2));
                            memory.remove(j/2+1);
                            values.set(i,0);

                            if(j+1 <values.size()){
                                values.set(j-1, values.get(j-1)+memSpace+ values.get(j+1));
                                values.remove(j);
                                values.remove(j);
                            }else {
                                values.set(j-1, values.get(j-1)+memSpace);
                                values.remove(j);
                            }

                            values.add(i+1, memSpace);

                            if(space > memSpace){
                                values.add(i+2, (space -memSpace));
                                i= i+2;
                            }

                            System.out.println(memory);
                            System.out.println(values);
                            break;
                        }
                    }
                }

                startPoint--;
            }
        }*/
/*
        List<String> str = new ArrayList<>();
        for(int i =0; i< values.size(); i++){
            for(int j =0; j<values.get(i); j++){
                if(i%2 == 0){
                    str.add(String.valueOf(memory.get(i/2)));
                } else {
                    str.add(".");
                }
            }
        }

        System.out.println(str);*/
    }



    private static String readLists(String filePath) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                return line.trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
