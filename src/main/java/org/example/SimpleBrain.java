package org.example;

import java.util.ArrayList;
import java.util.Stack;

public class SimpleBrain implements Constants {
    private final Node[] nodes;
    private Node moveLeftNode;
    private Node moveUpNode;
    private Node moveRightNode;
    private Node moveDownNode;
    private Node moveRandomNode;
    private Node xInputNode;
    private Node yInputNode;
    private Node biasNode;
    public SimpleBrain(byte[] nodeGenes, Connection[] connections) {
        nodes = new Node[nodeGenes.length];
        for (int i = 0; i < nodeGenes.length; i++) {
            nodes[i] = new Node(nodeGenes[i]);
            switch (nodeGenes[i]) {
                case (Constants.biasNode):
                    biasNode = nodes[i];
                    biasNode.setIn(1.0);
                    break;
                case (Constants.xInputNode):
                    xInputNode = nodes[i];
                    break;
                case (Constants.yInputNode):
                    yInputNode = nodes[i];
                    break;
                case (Constants.moveLeftNode):
                    moveLeftNode = nodes[i];
                    break;
                case (Constants.moveUpNode):
                    moveUpNode = nodes[i];
                    break;
                case (Constants.moveRightNode):
                    moveRightNode = nodes[i];
                    break;
                case (Constants.moveDownNode):
                    moveDownNode = nodes[i];
                    break;
                case (Constants.moveRandomNode):
                    moveRandomNode = nodes[i];
                    break;
            }
        }
        for (Connection connection: connections) {
            if (connection.enabled) {
                nodes[connection.in].addOut(nodes[connection.out], connection.weight);
                nodes[connection.out].incrementInConnections();
            }
        }
        setUpBackwardsConnections();
    }
    public void propagate(double[] in) {
        if (xInputNode != null) {
            xInputNode.setIn(in[0]);
            yInputNode.setIn(in[1]);
            xInputNode.excite();
            yInputNode.excite();
            biasNode.excite();
        }
    }
    public double getMoveLeftOutput() {
        if (moveLeftNode != null) {
            return moveLeftNode.getIn();
        }
        else return 0.0;
    }
    public double getMoveUpOutput() {
        if (moveUpNode != null) {
            return moveUpNode.getIn();
        }
        else return 0.0;
    }
    public double getMoveRightOutput() {
        if (moveRightNode != null) {
            return moveRightNode.getIn();
        }
        else return 0.0;
    }
    public double getMoveDownOutput() {
        if (moveDownNode != null) {
            return moveDownNode.getIn();
        }
        else return 0.0;
    }
    public double getMoveRandomOutput() {
        if (moveRandomNode != null) {
            return moveRandomNode.getIn();
        }
        else return 0.0;
    }
    private void setUpBackwardsConnections() {
        ArrayList<Node> visited = new ArrayList<>(nodes.length);
        Stack<Node> stack = new Stack<>();
        helper(stack, visited, xInputNode);
        helper(stack, visited, yInputNode);
        helper(stack, visited, biasNode);
    }
    private void helper(Stack<Node> stack, ArrayList<Node> visited, Node start) {
        if (stack.contains(start)) {
            start.backwardsConnection();
            return;
        }
        if (visited.contains(start)) {
            return;
        }
        visited.add(start);
        stack.push(start);
        for (Node next: start.getOut()) {
            helper(stack, visited, next);
        }
        stack.pop();
    }
}
