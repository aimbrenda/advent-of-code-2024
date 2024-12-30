package day12;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day12Part2 {



    public static void main(String[] args) {
        var filePath = "/day12.txt";

        Graph g = new Graph();



        List<List<String>> input = readLists(filePath, g);

        System.out.println(g.graph);

        long finalSum = 0;

        for(int  i=0; i < input.size();i++){
            for(int j=0; j< input.get(0).size();j++){
                System.out.print(g.nodes.get(j+ " " + i).value);
            }
            System.out.println("");
        }

        for(int  i=0; i < input.size();i++){
            for(int j=0; j< input.get(0).size();j++){
                System.out.print("("+g.nodes.get(j+ " " + i).region+")");
            }
            System.out.println("");
        }


        for(String key : g.graph.keySet()){
            long sum = 0;

            for(int  i=0; i < input.size();i++){
                int prevUp=1;
                int prevDown =1;
                final int indexY = i;
                /*List<Node> sorted = g.graph.get(key).stream()
                        .filter(n -> n.y==indexY)
                        .sorted(Comparator.comparingInt(n -> n.x))
                        .collect(Collectors.toList());*/
                for(int j=0; j< input.get(0).size();j++){
                    final int indexX = j;
                        Node n = g.nodes.get(j + " " + i);

                        if (n.region != Integer.valueOf(key)) {
                            prevUp = 1;
                            prevDown = 1;
                        } else {
                            if (prevUp == 0) {
                                if (n.childs[0] == 1) {
                                    prevUp = 1;
                                }
                            } else {
                                if (n.childs[0] == 0) {
                                    prevUp = 0;
                                    sum++;
                                }
                            }


                            if (prevDown == 0) {
                                if (n.childs[2] == 1 ) {
                                    prevDown = 1;
                                }
                            } else {
                                if (n.childs[2] == 0) {
                                    prevDown = 0;
                                    sum++;
                                }
                            }
                    }

                }

            }




            for(int  j=0; j < input.size();j++){
                int prevRight=1;
                int prevLeft =1;
                for(int i=0; i< input.get(0).size();i++){
                    Node n = g.nodes.get(j + " " + i);

                    if (n.region != Integer.valueOf(key)) {
                        prevRight = 1;
                        prevLeft = 1;
                    } else {
                        if (prevRight == 0) {
                            if (n.childs[1] == 1) {
                                prevRight = 1;
                            }
                        } else {
                            if (n.childs[1] == 0) {
                                prevRight = 0;
                                sum++;
                            }
                        }

                        if (prevLeft == 0) {
                            if (n.childs[3] == 1 ) {
                                prevLeft = 1;
                            }
                        } else {
                            if (n.childs[3] == 0) {
                                prevLeft = 0;
                                sum++;
                            }
                        }

                    }

                }

            }

            System.out.println("Key "+ key);
            System.out.println(sum+ " size " + g.graph.get(key).size());

            finalSum += sum * g.graph.get(key).size();

        }


        System.out.println(finalSum);


    }



    private static List<List<String>> readLists(String filePath, Graph graph) {
        int lines = 0;

        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.readLine() != null) {
                lines++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<String>> input = new ArrayList<>();

        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                input.add(values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int  i=0; i < input.size();i++){
            for(int j=0; j< input.get(0).size();j++){
                String id = j + " " + i;
                Node n = new Node(id);
                n.x= j;
                n.y = i;
                n.value = input.get(i).get(j);
                graph.nodes.put(id, n);

                if((i>0 && input.get(i-1).get(j).equals(n.value))){
                    n.childs[0] = 1;
                }
                if((i<input.size()-1 && input.get(i+1).get(j).equals(n.value)) ){
                    n.childs[2]=1;
                }

                if((j<input.get(i).size()-1 && input.get(i).get(j+1).equals(n.value))){
                    n.childs[1]=1;
                }

                if((j>0 && input.get(i).get(j-1).equals(n.value)) ){
                    n.childs[3]=1;
                }
                //graph.addEdge(id, n);
            }
        }

        for(Node n: graph.nodes.values()){
            n.visited = false;
        }

        int region = 0;
        for(Node n: graph.nodes.values()){
            Queue<Node> nodes = new ArrayDeque<>();


            if (!n.visited) {
                nodes.add(n);
                region++;
                n.region = region;
            }



            Node node = null;
            while(!nodes.isEmpty()) {
                node = nodes.poll();
                if (!node.visited) {
                    graph.addEdge(String.valueOf(node.region), node);
                    node.visited = true;
                    if (node.childs[0] == 1) {
                        Node up = graph.nodes.get((node.x) + " " +(node.y-1));
                        if (!up.visited) {
                            up.region = node.region;
                            graph.addEdge(String.valueOf(node.region), up);
                            nodes.add(up);
                        }
                    }

                    if (node.childs[1] == 1) {
                        Node right = graph.nodes.get((node.x+1) + " " +(node.y));
                        if (!right.visited) {
                            right.region = node.region;
                            graph.addEdge(String.valueOf(node.region), right);
                            nodes.add(right);
                        }
                    }

                    if (node.childs[2] == 1) {
                        Node down = graph.nodes.get((node.x) + " " +(node.y+1));
                        if (!down.visited) {
                            down.region = node.region;
                            graph.addEdge(String.valueOf(node.region), down);
                            nodes.add(down);
                        }
                    }

                    if (node.childs[3] == 1) {
                        Node left = graph.nodes.get((node.x-1) + " " +(node.y));
                        if (!left.visited) {
                            left.region = node.region;
                            graph.addEdge(String.valueOf(node.region), left);
                            nodes.add(left);
                        }
                    }
                }
            }
        }

        for(Node n: graph.nodes.values()){
            n.visited = false;
        }


        return input;
    }

    private static class Graph {
        private Map<String, Set<Node>> graph;
        private Map<String, Node> nodes;

        public Graph() {
            this.graph = new HashMap<>();
            this.nodes = new HashMap<>();
        }


        public void addEdge(String from, Node to){
            graph.computeIfAbsent(from, k-> new HashSet<>()).add(to);
        }


    }

    private static  class Node{

        private String value;
        private int region = 0;
        private int[] childs = {0,0,0,0};
        private int x;
        private int y;
        private boolean visited;

        private String id;
        public Node(String value, boolean visited){
            this.value = value;
            this.visited = visited;
            this.id = value;
        }
        public Node(String value) {
            this(value, false);
        }


    }

    private record Region (Integer area, Integer perimeter) {

    }
}
