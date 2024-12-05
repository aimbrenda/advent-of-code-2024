package day5;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {

    // find the X
    // search regex back and forward strings
    // translate verticals in new strings and apply step one again
    // translate diagolans to strings how? from last index of the row go down till the index is > then the limit
    // from (j = length -1; j>=0; j--)
    //      for (i = 0; i< lenghthigh; i++)
    //          if( j+i < length)
    //              string += value[i][j+i]

    public static void main(String[] args) {
        var filePath = "/day5.txt";


        Map<Integer, Set<Integer>> order = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        readLists(filePath, order,updates);

        System.out.println(order);
        System.out.println(updates);





        var sum = 0;
        for(List<Integer> update : updates) {
            Map<Integer, Set<Integer>> relevantOrder = new HashMap<>();

            for(Integer singleUpdate : update){
                if(order.containsKey(singleUpdate))
                    relevantOrder.put(singleUpdate, new HashSet<>(order.get(singleUpdate)));
                else {
                    relevantOrder.put(singleUpdate, new HashSet<>());
                }
            }

            var expandedRelevanOrder = addRecursiveDependencies(relevantOrder, update);

            boolean orderCorrect = true;

            for (int i = 0; i < update.size(); i++){
                /*System.out.println("Order empty " +expandedRelevanOrder.get(update.get(i)).isEmpty());
                System.out.println("Order contains all " +expandedRelevanOrder.get(update.get(i)).containsAll(update.subList(i+1,update.size())));
                System.out.println(expandedRelevanOrder.get(update.get(i)));
                System.out.println(update.subList(i+1,update.size()));*/
                orderCorrect = expandedRelevanOrder.get(update.get(i)).containsAll(update.subList(i+1,update.size()));

                if(!orderCorrect)
                    break;
            }


            if(!orderCorrect) {
                var finalUpdate = expandedRelevanOrder.entrySet()
                        .stream()
                        .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                System.out.println(finalUpdate);
                sum += finalUpdate.get((finalUpdate.size() - 1) / 2 );
            }


        }

        System.out.println(sum);
    }

    public static Map<Integer, Set<Integer>> addRecursiveDependencies(Map<Integer, Set<Integer>> dependencyMap, List<Integer> update) {
        // Convert the input map to use sets for easier handling of dependencies
        Map<Integer, Set<Integer>> expandedMap = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> entry : dependencyMap.entrySet()) {
            expandedMap.put(entry.getKey(), new HashSet<>(entry.getValue().stream().filter(update::contains).toList()));
        }

        // Flag to check if changes have been made
        boolean changesMade = true;

        while (changesMade) {
            changesMade = false;
            for (Integer key : expandedMap.keySet()) {
                Set<Integer> currentDeps = expandedMap.get(key);
                Set<Integer> newDeps = new HashSet<>(currentDeps);

                // Expand dependencies
                for (Integer dep : currentDeps) {
                    if (expandedMap.containsKey(dep)) {
                        newDeps.addAll(expandedMap.get(dep));
                    }
                }

                // Update if there are new dependencies found
                if (!newDeps.equals(currentDeps)) {
                    expandedMap.put(key, newDeps);
                    changesMade = true;
                }
            }
        }

        return expandedMap;
    }

    private static void readLists(String filePath, Map<Integer, Set<Integer>> order, List<List<Integer>> updates) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isOrder = true;
            while ((line = reader.readLine()) != null) {
                if("".equals(line.trim())){
                    isOrder = false;
                } else if(isOrder){
                    var numbers = line.trim().split("\\|");
                    if(!order.containsKey(Integer.parseInt(numbers[0]))){
                        order.put(Integer.parseInt(numbers[0]), new TreeSet<>());
                    }

                    order.get(Integer.parseInt(numbers[0])).add(Integer.parseInt(numbers[1]));
                } else {
                    var numbers = line.trim().split(",");
                    updates.add(Arrays.stream(numbers).map(Integer::parseInt).toList());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
