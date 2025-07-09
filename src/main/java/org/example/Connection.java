package org.example;

import java.util.Objects;

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
    @Override
    public int hashCode() {
        return Objects.hash(in, out, weight, enabled, innovationNumber);
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (! (o instanceof Connection otherConnection)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        return otherConnection.in == in && otherConnection.out == out && otherConnection.weight == weight && otherConnection.innovationNumber == innovationNumber;
    }
}
