package day1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {


    public static void main(String[] args) {
        var filePath = "/day1.txt";

        var leftList = new ArrayList<Integer>();
        var rightList = new ArrayList<Integer>();

        readLists(filePath, leftList, rightList);

        Collections.sort(leftList);
        Collections.sort(rightList);

        long sum = 0;
        for (int i = 0; i < leftList.size(); i++) {
            sum += (Math.abs(leftList.get(i) - rightList.get(i)));
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
