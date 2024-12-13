package day12;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {



    public static void main(String[] args) {
        var filePath = "/day12.txt";

        Graph g = new Graph();

        readLists(filePath, g);

        System.out.println(g.graph);

        long sum = 0;
        for(String s : g.nodes.keySet()){
            if(!g.nodes.get(s).visited) {
                System.out.println(g.nodes.get(s).value);
                var r = g.visitAllPaths(s);
                sum += r.area*r.perimeter;
            }
        }

        System.out.println(sum);


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
                    String id = i + " " + j;
                    graph.addNode(id, values.get(j));

                    if(i>0){
                        String upId = i-1 + " " + String.valueOf(j);
                        graph.addEdge(id, upId);
                    }
                    if(i<lines-1){
                        String downId = String.valueOf(i+1) + " " + String.valueOf(j);
                        graph.addEdge(id, downId);
                    }

                    if(j<values.size()-1){
                        String rightId = String.valueOf(i) + " " + String.valueOf(j+1);
                        graph.addEdge(id, rightId);
                    }

                    if(j>0){
                        String leftId = String.valueOf(i) + " " + String.valueOf(j-1);
                        graph.addEdge(id, leftId);
                    }

                }

                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(graph.graph);
        for(String s : graph.graph.keySet()) {
            List<String> links = new ArrayList<>();

            for (String d : graph.graph.get(s)){

                if(graph.nodes.get(d).value.equals(graph.nodes.get(s).value)){
                    links.add(d);
                }
            }

            graph.graph.put(s, links);
        }
    }

    private static class Graph {
        private Map<String, List<String>> graph;
        private Map<String, Node> nodes;

        public Graph() {
            this.graph = new HashMap<>();
            this.nodes = new HashMap<>();
        }


        public void addEdge(String from, String to){
            graph.computeIfAbsent(from, k-> new ArrayList<>()).add(to);
        }

        public void addNode(String id, String value){
            nodes.put(id, new Node(value));
        }

        public Region visitAllPaths(String id){
            Region region = new Region(0,0);

            return visitAllPathsUtil(id, region, nodes.get(id).value);

        }

        private Region visitAllPathsUtil(String id, Region region, String value) {
            System.out.println(id);
            region = new Region(region.area+1, region.perimeter+ (4-graph.get(id).size()));
            nodes.get(id).visited = true;

            for (String s : graph.get(id)){
                if(!nodes.get(s).visited && nodes.get(s).value.equals(value)) {
                    region = visitAllPathsUtil(s, region, value);

                    //region = new Region(region.area+r.area, region.perimeter + r.perimeter);
                }
            }


            return region;
        }
    }

    private static  class Node{

        private String value;
        private boolean visited;
        public Node(String value, boolean visited){
            this.value = value;
            this.visited = visited;
        }
        public Node(String value) {
            this(value, false);
        }


    }

    private record Region (Integer area, Integer perimeter) {

    }
}
