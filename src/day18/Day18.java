package day18;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day18 {



    public static void main(String[] args) {
        var filePath = "/day18.txt";

        String source = null;
        String target = null;

        Graph g = new Graph();
        //3450


        List<List<String>> input = readLists(filePath, g, 71, 71,1024);



        List<Node> shortestPath = g.findShortestPathWeight("0 0", "70 70", Direction.EAST,0);

        System.out.println(shortestPath);
        System.out.println(shortestPath.size()-1);

    }




    private static List<List<String>> readLists(String filePath, Graph graph, int xSize, int ySize, int nBytesMax) {

        List<String> dots = new ArrayList<>();
        List<List<String>> input = new ArrayList<>();
        IntStream.range(0, xSize).forEach(i -> dots.add("."));

        for(int i=0; i< ySize; i++){
            input.add(new ArrayList<>(dots));
        }


        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int nBytes = 0;

            while ((line = reader.readLine()) != null && nBytes< nBytesMax) {
                String[] coordinates = line.split(",");
                input.get(Integer.valueOf(coordinates[1])).set(Integer.valueOf(coordinates[0]),"#");
                nBytes++;
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
            /*if (from == to) return 0;
            int turns = Math.abs(from.ordinal() - to.ordinal());
            if (turns == 3) turns = 1; // Simplify full circle turn cost
            return 1000 * turns;*/
            return 0;
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


        public List<Node> findShortestPathWeight(String startVertex, String targetVertex, Direction startDirection, Integer startCost) {
            Map<String, Integer> distances = new HashMap<>();
            Map<String, Node> predecessors = new HashMap<>();
            PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));

            distances.put(startVertex, startCost);
            priorityQueue.add(new Node(startVertex, startCost, startDirection));

            while (!priorityQueue.isEmpty()) {
                Node current = priorityQueue.poll();

                if (current.vertex.equals(targetVertex)) {
                    return reconstructPath(predecessors, current);
                }

                if (current.cost > distances.getOrDefault(current.vertex, Integer.MAX_VALUE)) {
                    continue;
                }

                for (Edge edge : getEdges(current.vertex)) {
                    int newDirectionCost = Direction.getTurnCost(current.direction, edge.requiredDirection);
                    int newDist = current.cost + edge.baseWeight + newDirectionCost;

                    if (newDist < distances.getOrDefault(edge.target, Integer.MAX_VALUE)) {
                        distances.put(edge.target, newDist);
                        predecessors.put(edge.target, new Node(current.vertex, current.cost, current.direction));
                        priorityQueue.add(new Node(edge.target, newDist, edge.requiredDirection));
                    }
                }
            }

            return Collections.EMPTY_LIST; // Return null if the target vertex is not reachable
        }


        private List<Node> reconstructPath(Map<String, Node> predecessors, Node targetVertex) {
            List<Node> path = new LinkedList<>();
            for (Node at = targetVertex; at != null; at = predecessors.get(at.vertex)) {
                path.add(at);
            }
            //Collections.reverse(path);
            return path;
        }



    }
    private static class Path {
        int cost;
        List<Node> path;

        Path(int cost, List<Node> path) {
            this.cost = cost;
            this.path = path;
        }
    }


    private static class Node {
        String vertex;
        int cost;
        Direction direction;

        int  finalCost;

        Node(String vertex, int cost, Direction direction) {
            this.vertex = vertex;
            this.cost = cost;
            this.finalCost = cost;
            this.direction = direction;
        }

        public static Node copyOf(Node n){
            return new Node(n.vertex, n.cost, n.direction);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "vertex='" + vertex + '\'' +
                    ", cost=" + cost +
                    '}';
        }
    }
}
