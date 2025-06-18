package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Brain implements Constants, Functions {
    private ArrayList<double[][]> layers;
    private double[] inputs;
    private byte[] nodeGenes;
    private Connection[] connections;
    public Brain(byte[] nodeGenes, Connection[] connections) {
        this.nodeGenes = nodeGenes;
        this.connections = connections;
        layers = new ArrayList<>();
        int inputs = 0;
        int hiddens = 0;
        int outputs = 0;
        for (byte type: nodeGenes) {
            if (type <= inputNode) inputs++;
            else if (type == hiddenNode) hiddens++;
            else outputs++;
        }
        this.inputs = new double[inputs];
        for (Connection connection: connections) {

        }
    }
    private void propagate() {
        for (double[][] layer: layers) {
            inputs = matrixVectorMultiply(layer, inputs);
            applyTanh(inputs);
        }
    }
    private void setInputs(double[] inputs) {
        this.inputs = inputs;
    }
    public double[] getResult() {
        return inputs;
    }
}
