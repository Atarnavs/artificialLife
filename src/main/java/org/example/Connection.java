package org.example;

public class Connection {
    public int in;
    public int out;
    public double weight;
    public boolean enabled;
    public int innovationNumber;
    public Connection(int in, int out, double weight, boolean enabled, int innovationNumber) {
        this.in = in;
        this.out = out;
        this.weight = weight;
        this.enabled = enabled;
        this.innovationNumber = innovationNumber;
    }
}
