package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Node {
    private byte type;
    private ArrayList<Node> out;
    private ArrayList<Double> weights;
    private double in;

    public Node() {
        in = 0;
        this.out = new ArrayList<>();
        this.weights = new ArrayList<>();
    }
    public Node(byte type) {
        this.type = type;
        in = 0;
        this.out = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    public void addOut(Node out, double weight) {
        this.out.add(out);
        this.weights.add(weight);
    }
    private void addWeight(double weight) {
        this.weights.add(weight);
    }
    public void setType(byte type) {
        this.type = type;
    }
    public void excite() {
        for (int i = 0; i < out.size(); i++) {
            out.get(i).updateIn(Math.tanh(in * weights.get(i)));
            out.get(i).excite();
        }
    }
    public void cleanse() {
        setIn(0);
        for (Node out: this.out) {
            out.cleanse();
        }
    }
    public void updateIn(double in) {
        this.in += in;
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
}
