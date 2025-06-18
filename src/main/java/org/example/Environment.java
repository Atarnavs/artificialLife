package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Environment implements Constants{
    ArrayList<Entity> entities;
    Random random;
    public Environment() {
        entities = new ArrayList<>();
        random = new Random();
    }
    public Environment(ArrayList<Entity> entities) {
        this.entities = entities;
        random = new Random();
    }
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void updateEntities() {
        for (Entity entity: entities) {
            entity.act(entity.getX() + entity.getY());
        }
    }
    public void reproduce() {
        ArrayList<Entity> offsprings = new ArrayList<>();
        int numberOfOffsprings = 0;
        while (numberOfOffsprings < NUMBER_OF_ENTITIES) {
            for (Entity entity : entities) {
                offsprings.add(entity.reproduce());
                numberOfOffsprings++;
            }
        }
        entities = offsprings;
    }
    public void kill(int x, int y) {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            Entity entity = entities.get(i);
            if (entity.getX() < x || entity.getY() < y) {
                entities.remove(i);
                i--;
                size--;
            }
        }
    }
}
