package day6;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6Part2 {

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



        var sum = 0 ;
        for(int m=0; m< map.size(); m++){
            for(int n=0; n< map.get(0).length(); n++){
                if((m!=startPosition.get(0) || n!=startPosition.get(1)) && map.get(m).toCharArray()[n] != '#'){
                    System.out.println("Analyzing " + m + " " + n);
                    Map<String, Integer> visited = new HashMap<>();

                    int i = startPosition.get(0);
                    int j = startPosition.get(1);
                    int direction = checkInitialPosition(map.get(i).toCharArray()[j]);
                    int steps = 0;
                    boolean rotation = false;
                    while(i>=0 && i<map.size() && j >= 0 && j<map.get(0).length() && visited.keySet().stream().allMatch(a -> visited.get(a) < 3 )) {
                        steps++;

                        if(!visited.containsKey(i+" " +j + " " + direction)) {
                            visited.put(i+" " +j + " " + direction, 1);
                        } else {
                            visited.put(i+" " +j + " " + direction, visited.get(i+" " +j + " " + direction) +1);
                        }

                        if(visited.get(i+" " +j + " " + direction) == 3){
                            break;
                        }

                        if(rotation) {
                            if(!visited.containsKey(i+" " +j + " " + rotateInverse(direction))) {
                                visited.put(i+" " +j + " " + rotateInverse(direction), 1);
                            } else {
                                visited.put(i+" " +j + " " + rotateInverse(direction), visited.get(i+" " +j + " " + rotateInverse(direction)) +1);
                            }

                            if(visited.get(i+" " +j + " " + rotateInverse(direction))==3)
                                break;
                        }


                        switch (direction) {
                            case 0:
                                if(inRange(i-1, j, map.size(), map.get(0).length()) && (map.get(i-1).toCharArray()[j] == '#' || (i-1==m&&j==n))) {
                                    direction = rotate(direction);
                                    rotation = true;
                                } else {
                                    rotation = false;
                                    i--;
                                }
                                break;
                            case 1:
                                if(inRange(i, j+1, map.size(), map.get(0).length()) && (map.get(i).toCharArray()[j+1] == '#' || (i==m&&j+1==n))){
                                    direction = rotate(direction);
                                    rotation = true;
                                } else {
                                    rotation = false;
                                    j++;
                                }
                                break;
                            case 2:
                                if(inRange(i+1, j, map.size(), map.get(0).length()) && (map.get(i+1).toCharArray()[j] == '#' || (i+1==m&&j==n))){
                                    direction = rotate(direction);
                                    rotation = true;
                                } else {
                                    rotation = false;
                                    i++;
                                }
                                break;
                            case 3:
                                if(inRange(i, j-1, map.size(), map.get(0).length()) && (map.get(i).toCharArray()[j-1] == '#' || (i==m&&j-1==n))){
                                    direction = rotate(direction);
                                    rotation = true;
                                } else {
                                    rotation = false;
                                    j--;
                                }
                                break;
                        }
                    }


                    if(inRange(i,j,map.size(), map.get(0).length())){
                        var posA = i;
                        var posB = j;
                        var dir = direction;
                        if(visited.keySet().stream().filter(v -> !v.equals(posA+" " +posB + " " + dir)).allMatch(a -> visited.get(a) < 3))
                            sum++;
                    }
                }
            }
        }


        System.out.println(sum);
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

    private static int rotateInverse (int position) {
        switch (position) {
            case 0:
                return 3;
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
        }

        return position;
    }

    private static int checkInitialPosition (char s){
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
