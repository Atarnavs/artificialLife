package org.example;

import java.util.Random;

public class Entity implements Constants{
    private int x;
    private int y;
    private final Random random;
//    private double fieldOfVisionCoefficient1;
//    private double energy;
//    private String genome;
//    private Brain brain;
    private final SimpleBrain brain;
    public Entity(int x, int y, SimpleBrain brain) {
        this.x = x;
        this.y = y;
        this.brain = brain;
//        energy = StartingEnergy;
        this.random = new Random();
    }
    public void act(double in) {
        brain.excite(in);
        x = (x - (random.nextDouble() < brain.getMoveLeftOutput() ? 1 : 0)) % SIZE;
        y = (y + (random.nextDouble() < brain.getMoveUpOutput() ? 1 : 0)) % SIZE;
        x = (x + (random.nextDouble() < brain.getMoveRightOutput() ? 1 : 0)) % SIZE;
        y = (y - (random.nextDouble() < brain.getMoveDownOutput() ? 1 : 0)) % SIZE;
        if (random.nextDouble() < brain.getMoveRandomOutput()) {
            x = (x + random.nextInt(-1, 2)) % SIZE;
            y = (y + random.nextInt(-1, 2)) % SIZE;
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
        return offspring;
    }
}
