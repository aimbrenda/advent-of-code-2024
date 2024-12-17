package day16;

import day1.Day1;
import day12.Day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {



    public static void main(String[] args) {
        var filePath = "/day16.txt";

        String source = null;
        String target = null;

        Graph g = new Graph();

        List<List<String>> input = readLists(filePath, g);


        for(int i=0; i< input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                if(input.get(i).get(j).equals("S")){
                    source = j + " " + i;
                }else if(input.get(i).get(j).equals("E")){
                    target = j + " " + i;
                }
            }
        }

        System.out.println(g.findShortestPathWeight(source, target, Direction.EAST));
    }




    private static List<List<String>> readLists(String filePath, Graph graph) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<List<String>> input = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                input.add(values);
            }

            for(int i=0; i< input.size(); i++){
                for(int j=0; j< input.get(i).size(); j++){
                    String id = j + " " + i;
                    if(!input.get(i).get(j).equals("#")) {
                        if (i > 0 && !input.get(i - 1).get(j).equals("#")) {
                            String upId = j + " " + (i - 1);

                            graph.addEdge(id, upId, Direction.NORTH, 1);
                        }
                        if (i < input.size() - 1 && !input.get(i + 1).get(j).equals("#")) {
                            String downId = j + " " + (i + 1);
                            graph.addEdge(id, downId, Direction.SOUTH, 1);
                        }

                        if (j < input.get(i).size() - 1 && !input.get(i).get(j + 1).equals("#")) {
                            String rightId = (j + 1) + " " + (i);
                            graph.addEdge(id, rightId, Direction.EAST, 1);
                        }

                        if (j > 0 && !input.get(i).get(j - 1).equals("#")) {
                            String leftId = (j - 1) + " " + (i);
                            graph.addEdge(id, leftId, Direction.WEST, 1);
                        }
                    }
                }
            }
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST;

        // Determine the cost of turning from one direction to another
        public static int getTurnCost(Direction from, Direction to) {
            if (from == to) return 0;
            int turns = Math.abs(from.ordinal() - to.ordinal());
            if (turns == 3) turns = 1; // Simplify full circle turn cost
            return 1000 * turns;
        }
    }

    private static class Edge {
        String target;
        Direction requiredDirection; // Direction you need to face after traversing this edge
        int baseWeight;

        Edge(String target, Direction requiredDirection, int baseWeight) {
            this.target = target;
            this.requiredDirection = requiredDirection;
            this.baseWeight = baseWeight;
        }

    }

    private static class Graph {
        private Map<String, List<Edge>> adjList = new HashMap<>();

        public void addEdge(String source, String target, Direction requiredDirection, int baseWeight) {
            adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(target, requiredDirection, baseWeight));
        }

        public List<Edge> getEdges(String vertex) {
            return adjList.getOrDefault(vertex, new ArrayList<>());
        }


        public Integer findShortestPathWeight(String startVertex, String targetVertex, Direction startDirection) {
            Map<String, Integer> distances = new HashMap<>();
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));

            distances.put(startVertex, 0);
            priorityQueue.add(new Node(startVertex, 0, startDirection));

            while (!priorityQueue.isEmpty()) {
                Node current = priorityQueue.poll();

                if (current.vertex.equals(targetVertex)) {
                    return current.cost;
                }

                if (current.cost > distances.getOrDefault(current.vertex, Integer.MAX_VALUE)) {
                    continue;
                }

                for (Edge edge : getEdges(current.vertex)) {
                    int newDirectionCost = Direction.getTurnCost(current.direction, edge.requiredDirection);
                    int newDist = current.cost + edge.baseWeight + newDirectionCost;

                    if (newDist < distances.getOrDefault(edge.target, Integer.MAX_VALUE)) {
                        distances.put(edge.target, newDist);
                        priorityQueue.add(new Node(edge.target, newDist, edge.requiredDirection));
                    }
                }
            }

            return null; // Return null if the target vertex is not reachable
        }

        private static class Node {
            String vertex;
            int cost;
            Direction direction;

            Node(String vertex, int cost, Direction direction) {
                this.vertex = vertex;
                this.cost = cost;
                this.direction = direction;
            }
        }
    }
}
