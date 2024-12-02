package day1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day1Part2 {


    public static void main(String[] args) {
        var filePath = "/day1.txt";

        var leftList = new ArrayList<Integer>();
        var rightList = new ArrayList<Integer>();

        readLists(filePath, leftList, rightList);


        long sum = 0;
        for (int value : leftList) {
            sum += value * (rightList.stream().filter(a -> a == value).count());
        }


        System.out.println(sum);
    }

    private static void readLists(String filePath, List<Integer> leftList, List<Integer> rightList) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                var numbers = line.trim().split("\\s+");
                leftList.add(Integer.parseInt(numbers[0]));
                rightList.add(Integer.parseInt(numbers[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
