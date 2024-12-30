package day19;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day19Part2 {



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


        //253746934216647
        long sum = patterns.stream().parallel().map( p -> {
            Map<String, Long> memory = new HashMap<>();
            return checkWord(p, words, memory);
        }).reduce(0L, Long::sum);


        System.out.println(sum);
    }

    private static Long checkWord(String pattern, Set<String> words, Map<String, Long> memory){
        //System.out.println(memory);
        //System.out.println(pattern);
        long ways = 0;


        //System.out.println("patter " + pattern + " - drill " + ways );
        if(memory.containsKey(pattern)){
            ways += memory.get(pattern);
        }else {
            for (String w : words) {
                //System.out.println(pattern + " - word " + w);

                if (w.length() <= pattern.length()) {
                    if (pattern.startsWith(w) && memory.containsKey(pattern)) {
                        ways += memory.get(pattern);
                    } else if (pattern.equals(w)) {
                        ways += 1L;
                    } else if (pattern.startsWith(w)) {
                        long check = checkWord(pattern.substring(w.length()), words, memory);

                        ways += check;
                    }
                }
            }
            memory.put(pattern,ways);
        }


        //System.out.println("patter " + pattern + " - final " + ways );

        return ways;
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
