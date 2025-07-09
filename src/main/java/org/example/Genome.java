package org.example;

import java.util.*;

public class Genome {
    public byte[] nodeGenes;
    public Connection[] connections;
    public Genome(byte[] nodeGenes, Connection[] connections) {
        this.nodeGenes = nodeGenes;
        this.connections = connections;
    }
    @Override
    public int hashCode() {
        return Objects.hash(simplify());
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Genome)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        HashMap<Integer, ArrayList<Integer>> otherGraph = ((Genome) o).simplify();
        HashMap<Integer, ArrayList<Integer>> thisGraph = simplify();
        if (otherGraph.size() != thisGraph.size()) {
            return false;
        }

        return true;
    }
    private HashMap<Integer, ArrayList<Integer>> simplify() {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>(nodeGenes.length);
        for (Connection c: connections) {
            if (c.enabled && c.weight != 0.0) {
//                graph[c.in][c.out] = c.innovationNumber * (int) Math.copySign(1.0, c.weight); //(int) (c.weight / Math.abs(c.weight)); //(int) Math.copySign(1.0, c.weight);
                ArrayList<Integer> connections = graph.getOrDefault(c.in, new ArrayList<Integer>(nodeGenes.length));
                connections.add(c.out);
                graph.put(c.in, connections);
            }
        }
        HashSet<Integer> visited = new HashSet<>(nodeGenes.length);
        depthFirstWalkthrough(graph, visited, 0);
        depthFirstWalkthrough(graph, visited, 1);
        depthFirstWalkthrough(graph, visited, 2);
        for (int node: graph.keySet()) {
            if (!visited.contains(node)) {
                graph.remove(node);
            }
        }

        return graph;
    }
    private void depthFirstWalkthrough(HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> visited, int start) {
        if (visited.contains(start)) return;
        visited.add(start);
        for (int i = 0; i < graph.get(start).size(); i++) {
            depthFirstWalkthrough(graph, visited, i);
        }
    }
    private boolean hasPath(HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> visited, int start, int end) {
        if (start == end) {
            return true;
        }
        Queue<Integer> queue = new LinkedList<>();

        return false;
    }
}
