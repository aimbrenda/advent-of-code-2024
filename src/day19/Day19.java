package day19;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day19 {



    public static void main(String[] args) {
        var filePath = "/day19.txt";

        Set<String> words =  new HashSet<>();
        List<String> patterns = new ArrayList<>();


        AtomicInteger output = new AtomicInteger();

        readLists(filePath, words, patterns);

        System.out.println(words);
        System.out.println(patterns);
        Set<String> set = new HashSet<>();
        Set<String> setFalse = new HashSet<>();
        patterns.stream().parallel().forEach( p -> {
            if(checkWord(p, words, set,setFalse)){
                output.getAndIncrement();
            }else{
                System.out.println(p);
            }
        });


        System.out.println(output.get());
    }

    private static boolean checkWord(String pattern, Set<String> words, Set<String> memory, Set<String> memoryFalse){
        if(memory.contains(pattern)){
            return true;
        }else if(words.contains(pattern)){
            return true;
        }else if(memoryFalse.contains(pattern)){
            return false;
        }else{
           for (String w : words){
               if(w.length() <= pattern.length() && pattern.startsWith(w)) {
                   boolean check = checkWord(pattern.substring(w.length()), words, memory, memoryFalse);

                   if(check){
                       memory.add(pattern);
                       return true;
                   }
               }
           }
        }

        memoryFalse.add(pattern);
        return false;
    }


    private static void readLists(String filePath, Set<String> words, List<String> patterns) {



        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int nLine = 0;

            while ((line = reader.readLine()) != null) {

                if(nLine == 0){
                    words.addAll(Arrays.stream(line.trim().split(",")).map(w -> w.trim()).collect(Collectors.toList()));
                } else if(nLine>1) {
                    patterns.add(line.trim());
                }



                nLine++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
