package day11;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Day11 {



    public static void main(String[] args) {
        var filePath = "/day11.txt";
        var blinks = 75;

        List<Long> nums = readLists(filePath);

/*        for(int i = 0; i<blinks; i++){
            System.out.println("Blink " + i);
            List<Long> finalList = new ArrayList<>();
            for(int j=0; j< nums.size(); j++) {
                if (nums.get(j) == 0) {
                    finalList.add(Long.valueOf(1));
                } else if (String.valueOf(nums.get(j)).length() %2 == 0) {
                    String numString =String.valueOf(nums.get(j));
                    finalList.add(Long.valueOf(numString.substring(0, numString.length()/2)));
                    finalList.add(Long.valueOf(numString.substring(numString.length()/2)));
                } else {
                    finalList.add(nums.get(j)*2024);
                }

            }

            nums = new ArrayList<>(finalList);
            System.out.println(nums.size());
            //System.out.println(nums);
        }*/

        long size = 0;
        /*for(int j=0; j< nums.size(); j++) {
            System.out.println("Num " + j);
            List<Long> sts = new ArrayList<>();
            sts.add(nums.get(j));
            for(int i = 0; i<blinks; i++){
                System.out.println("Blink " + i);
                List<Long> stones = new ArrayList<>();
                for(int n = 0; n< sts.size();n++) {
                    if (sts.get(n) == 0) {
                        stones.add(Long.valueOf(1));
                    } else if (String.valueOf(sts.get(n)).length() % 2 == 0) {
                        String numString = String.valueOf(sts.get(n));
                        stones.add(Long.valueOf(numString.substring(0, numString.length() / 2)));
                        stones.add(Long.valueOf(numString.substring(numString.length() / 2)));
                    } else {
                        stones.add(sts.get(n) * 2024);
                    }

                }

                sts = new ArrayList<>(stones);

            }

            size += sts.size();
        }*/

        //System.out.println(nums);

        for(int j=0; j< nums.size(); j++) {
            System.out.println(j);
            size += recursiveList(blinks, nums.get(j), 0, new ConcurrentHashMap<>());
        }

        System.out.println(size);

    }

    public static long recursiveList(int blinks, long num, int step, ConcurrentHashMap<String, Long> memory){
        long size = 0;
        if(step < blinks){
            if (num == 0) {
                long tmpSize = memory.containsKey((step + 1) + "-" + 1 ) ? memory.get((step + 1) + "-" + 1) :  recursiveList(blinks, 1, step + 1, memory);
                memory.put((step + 1) + "-" + 1 , tmpSize);
                size += tmpSize;
            } else if (String.valueOf(num).length() % 2 == 0) {
                String numString = String.valueOf(num);
                long tmpSize1 = memory.containsKey((step + 1) + "-" + numString.substring(0, numString.length() / 2)) ?
                        memory.get((step + 1) + "-" + numString.substring(0, numString.length() / 2)) :
                        recursiveList(blinks, Long.valueOf(numString.substring(0, numString.length() / 2)), step + 1, memory);
                long tmpSize2 = memory.containsKey((step + 1) + "-" + numString.substring(numString.length() / 2)) ?
                        memory.get((step + 1) + "-" + numString.substring(numString.length() / 2)) :
                        recursiveList(blinks, Long.valueOf(numString.substring(numString.length() / 2)), step + 1, memory);

                memory.put((step + 1) + "-" + numString.substring(0, numString.length() / 2) , tmpSize1);
                memory.put((step + 1) + "-" + numString.substring(numString.length() / 2), tmpSize2);

                size = size + tmpSize1 + tmpSize2;

            } else {
                long tmpSize = memory.containsKey((step + 1) + "-" + (num * 2024)) ? memory.get((step + 1) + "-" + (num * 2024)) : recursiveList(blinks, num * 2024, step + 1, memory);
                memory.put((step + 1) + "-" + (num * 2024) , tmpSize);

                size += tmpSize;
            }
        } else {
            return 1;
        }

        return size;
    }



    private static List<Long> readLists(String filePath) {

        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Long> values = Arrays.stream(line.trim().split(" ")).map(c -> Long.valueOf(String.valueOf(c))).collect(Collectors.toList());


                return values;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
