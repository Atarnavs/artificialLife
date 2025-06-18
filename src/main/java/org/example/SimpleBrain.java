package org.example;

public class SimpleBrain implements Constants {
    private byte[] nodeGenes;
    private Connection[] connections;
    private Node[] nodes;
    private Node moveLeftNode;
    private Node moveUpNode;
    private Node moveRightNode;
    private Node moveDownNode;
    private Node moveRandomNode;
    private Node inputNode;
    public SimpleBrain(byte[] nodeGenes, Connection[] connections) {
        this.nodeGenes = nodeGenes;
        this.connections = connections;
        nodes = new Node[nodeGenes.length];
        for (int i = 0; i < nodeGenes.length; i++) {
            nodes[i] = new Node(nodeGenes[i]);
            switch (nodeGenes[i]) {
                case (Constants.inputNode):
                    inputNode = nodes[i];
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
    }
    public void excite(double in) {
        if (inputNode != null) {
            inputNode.setIn(in);
            inputNode.excite();
        }
    }
    public void cleanse() {
        if (inputNode != null) {
            inputNode.cleanse();
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
}
