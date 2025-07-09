package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment implements Constants{
    Entity[] world;
    private static int numberOfEntities = 0;
    public Environment() {
        world = new Entity[WORLD_SIZE * WORLD_SIZE];
    }
    public Environment(ArrayList<Creature> entities) {
        world = new Entity[WORLD_SIZE * WORLD_SIZE];
        for (Creature creature : entities) {
            int nextPosition = Main.random.nextInt(WORLD_SIZE * WORLD_SIZE);
            boolean assigned = false;
            while (!assigned) {
                if (world[nextPosition] == null) {
                    world[nextPosition] = creature;
                    assigned = true;
                }
                nextPosition = Main.random.nextInt();
            }
            incrementNumberOfEntities();
        }
    }
    public void addEntity(Creature creature) {
        int nextPosition = Main.random.nextInt(world.length);
        boolean assigned = false;
        while (!assigned) {
            if (world[nextPosition] == null) {
                world[nextPosition] = creature;
                assigned = true;
            }
            nextPosition = Main.random.nextInt(world.length);
        }
        incrementNumberOfEntities();
    }
    public Entity[] getWorld() {
        return world;
    }
    public void updateEntities() {
        for (int i = 0; i < world.length; i++) {
            Entity entity = world[i];
            if (entity instanceof Creature) {
                int[] result = (((Creature) entity).act(new double[] {(double) i % WORLD_SIZE, (double) (i / WORLD_SIZE)}));
                int newI = WORLD_SIZE * result[1] + result[0];
                if (newI < world.length && newI >= 0 && result[0] >= 0 && result[0] < WORLD_SIZE && world[newI] == null) {
                    world[newI] = entity;
                    world[i] = null;
                }
            }
        }
    }
    public void reproduce() {
        ArrayList<Creature> offsprings = new ArrayList<>();
        int numberOfOffsprings = 0;
        int j = 0;
        while (numberOfOffsprings < NUMBER_OF_ENTITIES) {
            Entity entity = world[j];
            if (entity instanceof Creature) {
                offsprings.add(((Creature) entity).reproduce());
                numberOfOffsprings++;
            }
            j = (j + 1) % world.length;
        }
        for (int i = 0; i < world.length; i++) {
            if (world[i] instanceof Creature) {
                world[i] = null;
            }
        }
        numberOfEntities = 0;
        for (Creature creature : offsprings) {
            addEntity(creature);
        }
    }
    public void kill(int leftX, int downY, int rightX, int upY) {
        int initialSize = numberOfEntities;
        for (int i = 0; i < world.length; i++) {
            int y = i / WORLD_SIZE;
            int x = i % WORLD_SIZE;
            if (x < leftX || y < downY || (WORLD_SIZE - x) < rightX || (WORLD_SIZE - y) < upY) {
                if (world[i] instanceof Creature) {
                    decrementNumberOfEntities();
                    world[i] = null;
                }
            }
        }
        System.out.println("Survival rate: " + (double) numberOfEntities / initialSize);
//        Main.writeToFile((double) size / initialSize);
    }
    private static void incrementNumberOfEntities() {
        numberOfEntities++;
    }
    private static void decrementNumberOfEntities() {
        numberOfEntities--;
    }
    public void setObstacle() {
        for (int i = 0; i < 600; i++) {
            int x = i % 10;
            int y = i / 10;
            world[(y + 34) * WORLD_SIZE + (x + 59)] = new Entity();
        }
    }
    public void removeObstacle() {
        for (int i = 0; i < 600; i++) {
            int x = i % 10;
            int y = i / 10;
            world[(y + 34) * WORLD_SIZE + (x + 59)] = null;
        }
    }
    public HashMap<Genome, Integer> getFrequencyTable() {
        HashMap<Genome, Integer> table = new HashMap<>();
        for (Entity entity: world) {
            if (entity instanceof Creature) {
                Genome g = ((Creature) entity).getGenome();
                table.put(g, table.getOrDefault(g, 0) + 1);
            }
        }
        return table;
    }
}
