package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Brain implements Constants{
    private Node[] nodes;
    private byte[] nodeGenes;
    private Connection[] connectionGenes;
    private Node moveLeftNode;
    private Node moveUpNode;
    private Node moveRightNode;
    private Node moveDownNode;
    private Node moveRandomNode;
    public Brain(byte[] nodeGenes, Connection[] connectionGenes) {
        this.nodeGenes = nodeGenes;
        this.connectionGenes = connectionGenes;
        this.nodes = new Node[nodeGenes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(nodeGenes[i]);
            switch (nodeGenes[i]) {
                case Constants.moveLeftNode:
                    this.moveLeftNode = nodes[i];
                    break;
                case Constants.moveUpNode:
                    this.moveUpNode = nodes[i];
                    break;
                case Constants.moveRightNode:
                    this.moveRightNode = nodes[i];
                    break;
                case Constants.moveDownNode:
                    this.moveDownNode = nodes[i];
                    break;
                case Constants.moveRandomNode:
                    this.moveRandomNode = nodes[i];
                    break;
            }
        }
        for (Connection connection: connectionGenes) {
            if (connection.enabled) {
                nodes[connection.in].addOut(nodes[connection.out], connection.weight);
            }
        }
    }
    public void exciteNodes(double in) {
        for (Node node: nodes) {
            if (node.getType() == inputNode) {
                node.updateIn(in);
                node.excite();
            }
        }
    }
    public ArrayList<Return> getOutput() {
        ArrayList<Return> returns = new ArrayList<>();
        for (Node node: nodes) {
            if (node.getType() > hiddenNode) {
                returns.add(new Return(node.getType(), node.getIn()));
            }
        }
        returns.sort(Comparator.comparingInt((Return r) -> r.type));
        return returns;
    }
    public double getLeftNodeOutput() {
        if (this.moveLeftNode != null) {
            return this.moveLeftNode.getIn();
        }
        else return 0.0;
    }
    public double getUpNodeOutput() {
        if (this.moveUpNode != null) {
            return this.moveUpNode.getIn();
        }
        else return 0.0;
    }
    public double getRightNodeOutput() {
        if (this.moveRightNode != null) {
            return this.moveRightNode.getIn();
        }
        else return 0.0;
    }
    public double getDownNodeOutput() {
        if (this.moveDownNode != null) {
            return this.moveDownNode.getIn();
        }
        else return 0.0;
    }
    public double getRandomNodeOutput() {
        if (this.moveRandomNode != null) {
            return this.moveRandomNode.getIn();
        }
        else return 0.0;
    }
    public void cleanse() {
        for (Node node: nodes) {
            if (node.getType() == inputNode) {
                node.cleanse();
            }
        }
    }
    public void updateWeights() {

    }
}
