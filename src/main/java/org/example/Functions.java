package org.example;

public interface Functions {
    default double[] matrixVectorMultiply(double[][] matrix, double[] vector) {
        double[] r = new double[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                r[i] += matrix[i][j] * vector[j];
            }
        }
        return r;
    }
    default void applyTanh(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Math.tanh(vector[i]);
        }
    }
}
