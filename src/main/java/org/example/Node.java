package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Node {
    private final byte type;
    private ArrayList<Node> out;
    private ArrayList<Double> weights;
    private double in;
    private int inConnections;
    private int signalsCounter;

    public Node(byte type) {
        this.type = type;
        in = 0;
        this.out = new ArrayList<>();
        this.weights = new ArrayList<>();
        inConnections = 0;
        signalsCounter = 0;
    }

    public void addOut(Node out, double weight) {
        this.out.add(out);
        this.weights.add(weight);
    }
    public void excite() {
        if (inConnections == signalsCounter) {
            setIn(Math.tanh(getIn()));
            for (int i = 0; i < out.size(); i++) {
                out.get(i).updateIn(weights.get(i) * getIn());
                out.get(i).excite();
            }
            resetSignalsCounter();
        }
    }
    public void cleanse() {
        setIn(0);
        resetSignalsCounter();
        for (Node out: this.out) {
            out.cleanse();
        }
    }
    public void updateIn(double in) {
        this.in += in;
        signalsCounter++;
    }
    public void setIn(double in) {
        this.in = in;
    }
    public double getIn() {
        return in;
    }
    public void updateWeights() {
        Random random = new Random();
        for (double weight: weights) {
            weight += random.nextDouble(-0.3, 0.3);
        }
    }
    public byte getType() {
        return type;
    }
    public void incrementInConnections() {
        inConnections++;
    }
    public void resetSignalsCounter() {
        signalsCounter = 0;
    }
}
