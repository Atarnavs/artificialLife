package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Entity implements Constants{
    private int x;
    private int y;
    private Random random;
//    private double fieldOfVisionCoefficient1;
//    private double energy;
//    private String genome;
    private Brain brain;
    public Entity(int x, int y, Brain brain) {
        this.x = x;
        this.y = y;
        this.brain = brain;
//        energy = StartingEnergy;
        this.random = new Random();
    }
    public void act(double in) {
        brain.exciteNodes(in);
        double leftOut = brain.getLeftNodeOutput();
        double upOut = brain.getUpNodeOutput();
        double rightOut = brain.getRightNodeOutput();
        double downOut = brain.getDownNodeOutput();
        double randomOut = brain.getRandomNodeOutput();
        if (leftOut > 0.0) {
            this.x -= 1;
        }
        if (upOut > 0.0) {
            this.y += 1;
        }
        if (rightOut > 0.0) {
            this.x += 1;
        }
        if (downOut > 0.0) {
            this.y -= 1;
        }
        if (randomOut > 0.0) {
            this.y += random.nextInt(-1,2);
            this.x += random.nextInt(-1, 2);
        }
        brain.cleanse();
    }
//    private void updateEnergy(double startX, double startY) {
//        energy -= Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2)) * StepCost;
//    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Entity reproduce() {
        Random random = new Random();
        Entity offspring = new Entity(random.nextInt(SIZE), random.nextInt(SIZE), brain);
        offspring.brain.updateWeights();
        return offspring;
    }
}
