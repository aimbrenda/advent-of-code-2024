package day6;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {

    // find the X
    // search regex back and forward strings
    // translate verticals in new strings and apply step one again
    // translate diagolans to strings how? from last index of the row go down till the index is > then the limit
    // from (j = length -1; j>=0; j--)
    //      for (i = 0; i< lenghthigh; i++)
    //          if( j+i < length)
    //              string += value[i][j+i]

    public static void main(String[] args) {
        var filePath = "/day6.txt";


        List<String> map = new ArrayList<>();
        List<Integer> startPosition = readLists(filePath, map);
        Set<String> visited = new HashSet<>();


        System.out.println(startPosition);

        int i = startPosition.get(0);
        int j = startPosition.get(1);
        System.out.println(map.get(i).toCharArray()[j]);
        int direction = checkInitialPosition(map.get(i).toCharArray()[j]);
        while(i>=0 && i<map.size() && j >= 0 && j<map.get(0).length()) {
            visited.add(i+" " +j);

            System.out.println(i+"-"+j+" " +direction);
            switch (direction) {
                case 0:
                    if(inRange(i-1, j, map.size(), map.get(0).length()) && map.get(i-1).toCharArray()[j] == '#') {
                        direction = rotate(direction);
                    } else {
                        i--;
                    }
                    break;
                case 1:
                    if(inRange(i, j+1, map.size(), map.get(0).length()) && map.get(i).toCharArray()[j+1] == '#'){
                        direction = rotate(direction);
                    } else {
                        j++;
                    }
                    break;
                case 2:
                    if(inRange(i+1, j, map.size(), map.get(0).length()) && map.get(i+1).toCharArray()[j] == '#'){
                        direction = rotate(direction);
                    } else {
                        i++;
                    }
                    break;
                case 3:
                    if(inRange(i, j-1, map.size(), map.get(0).length()) && map.get(i).toCharArray()[j-1] == '#'){
                        direction = rotate(direction);
                    } else {
                        j--;
                    }
                    break;
            }
        }

        System.out.println(visited.size());
    }

    private static boolean inRange(int i, int j, int sizeA, int sizeB){
        return i>=0 && i<sizeA && j >= 0 && j<sizeB;
    }

    private static int checkStartingPosition (String s){
        Pattern pattern = Pattern.compile("\\^|>|<|v");
        Matcher m = pattern.matcher(s);

        if(m.find()) {
            return m.start();
        }
        return -1;
    }

    private static int rotate (int position) {
        switch (position) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 0;
        }

        return position;
    }

    private static int checkInitialPosition (char s){
        System.out.println(s);
        switch (s) {
            case '^':
                return 0;
            case '>':
                return 1;
            case 'v':
                return 2;
            case '<':
                return 3;
        }

        return -1;
    }


    private static List<Integer> readLists(String filePath, List<String> map) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            int start = -1;
            while ((line = reader.readLine()) != null) {
                int match = checkStartingPosition(line);
                if(match != -1) {
                    start = match;
                }


                map.add(line);

                if(start== -1){
                    i++;
                }

            }


            return List.of(i, start);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }
}
