package day10;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {



    public static void main(String[] args) {
        var filePath = "/day10.txt";

        Graph g = new Graph();

        readLists(filePath, g);

        System.out.println(g.graph);

        g.visitAllPaths("02");
    }



    private static void readLists(String filePath, Graph graph) {
        int lines = 0;

        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.readLine() != null) {
                lines++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());

                for(int j=0; j< values.size(); j++){
                    String id = String.valueOf(i) + String.valueOf(j);
                    graph.addNode(id, Integer.valueOf(values.get(j)));

                    if(i>0){
                        String upId = String.valueOf(i-1) + String.valueOf(j);
                        graph.addEdge(id, upId);
                    }
                    if(i<lines-1){
                        String downId = String.valueOf(i+1) + String.valueOf(j);
                        graph.addEdge(id, downId);
                    }

                    if(j<values.size()-1){
                        String rightId = String.valueOf(i) + String.valueOf(j+1);
                        graph.addEdge(id, rightId);
                    }

                    if(j>0){
                        String leftId = String.valueOf(i) + String.valueOf(j-1);
                        graph.addEdge(id, leftId);
                    }

                }

                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Graph {
        private Map<String, List<String>> graph;
        private Map<String, Integer> nodes;

        public Graph() {
            this.graph = new HashMap<>();
            this.nodes = new HashMap<>();
        }


        public void addEdge(String from, String to){
            graph.computeIfAbsent(from, k-> new ArrayList<>()).add(to);
        }

        public void addNode(String id, Integer value){
            nodes.put(id, value);
        }

        public void visitAllPaths(String id){
            List<String> path = new ArrayList<>();
            List<String> visited = new ArrayList<>();
            path.add(id);
            visited.add(id);
            int sequence = 1;
            Integer valid = 0;

            visitAllPathsUtil(id, path, sequence, valid, visited);
            System.out.println(valid);
        }

        private void visitAllPathsUtil(String id, List<String> path, int sequence, Integer valid, List<String> visited) {
            System.out.println(sequence);
            visited.add(id);
            if(graph.get(id).isEmpty() || nodes.get(id) != sequence){
                if(sequence == 10){
                    valid = valid+ 1;
                    return;
                }
            }

            for (String s : graph.get(id)){
                if(!visited.contains(s)) {
                    System.out.println(s);
                    path.add(s);
                    sequence++;
                    visitAllPathsUtil(s, path, sequence, valid, visited);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    private static class Node {
        private Integer value;
        private LinkedList childs;

        public Node(Integer value, LinkedList childs) {
            this.value = value;
            this.childs = childs;
        }
    }
}
