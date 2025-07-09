package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Brain implements Constants, Functions {
    private final double[][] weights;
    private byte[] nodes;
    private final double[] values;
    private final int[] inConnections;
    private final int[] incomingSignals;
    private int xInputNode;
    private int yInputNode;
    private int biasNode;
    private int moveLeftNode;
    private int moveUpNode;
    private int moveRightNode;
    private int moveDownNode;
    private int moveRandomNode;
    public Brain(byte[] nodeGenes, Connection[] connections) {
        weights = new double[nodeGenes.length][nodeGenes.length];
        nodes = new byte[nodeGenes.length];
        values = new double[nodeGenes.length];
        inConnections = new int[nodeGenes.length];
        incomingSignals = new int[nodeGenes.length];
        for (Connection connection: connections) {
            weights[connection.in][connection.out] = connection.weight;
            inConnections[connection.out]++;
        }
        nodes = nodeGenes;
        for (int i = 0; i < nodes.length; i++) {
            switch (nodes[i]) {
                case (Constants.xInputNode):
                    xInputNode = i;
                    break;
                case (Constants.yInputNode):
                    yInputNode = i;
                    break;
                case (Constants.biasNode):
                    biasNode = i;
                    break;
                case (Constants.moveLeftNode):
                    moveLeftNode = i;
                    break;
                case (Constants.moveUpNode):
                    moveUpNode = i;
                    break;
                case (Constants.moveRightNode):
                    moveRightNode = i;
                    break;
                case (Constants.moveDownNode):
                    moveDownNode = i;
                    break;
                case (Constants.moveRandomNode):
                    moveRandomNode = i;
                    break;
            }
        }
        setUpBackwardsConnections();
    }
    private void setUpBackwardsConnections() {
        HashSet<Integer> visited = new HashSet<>(nodes.length);
        CustomStack stack = new CustomStack(nodes.length);
        helper(stack, visited, xInputNode);
        helper(stack, visited, yInputNode);
        helper(stack, visited, biasNode);
    }
    public void propagate(double[] in) {
        values[xInputNode] = in[0];
        values[yInputNode] = in[1];
        values[biasNode] = bias;
        excite(xInputNode);
        excite(yInputNode);
        excite(biasNode);
    }
    private void excite(int node) {
        if (inConnections[node] == incomingSignals[node]) {
            double out = Math.tanh(values[node]);
            values[node] = 0.0;
            incomingSignals[node] = 0;
            for (int i = 0; i < weights[node].length; i++) {
                updateIn(i, out);
                excite(i);
            }
        }
    }
    private void helper(CustomStack stack, HashSet<Integer> visited, int start) {
        if (stack.contains(start)) {
            incomingSignals[start]++;
        }
        if (visited.contains(start)) return;
        visited.add(start);
        stack.push(start);
        for (int i = 0; i < weights[start].length; i++) {
            if(weights[start][i] != 0.0) {
                helper(stack, visited, i);
            }
        }
        stack.pop();
    }
    private void updateIn(int node, double in) {
        values[node] += in;
        incomingSignals[node]++;
    }
    public double getMoveLeftOutput() {
        return values[moveLeftNode];
    }
    public double getMoveUpOutput() {
        return values[moveUpNode];
    }
    public double getMoveRightOutput() {
        return values[moveRightNode];
    }
    public double getMoveDownOutput() {
        return values[moveDownNode];
    }
    public double getMoveRandomOutput() {
        return values[moveRandomNode];
    }
}
