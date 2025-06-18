package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main implements Constants{
    static Random random = new Random();
    public static void main(String[] args) {
        Environment environment = new Environment();
        for (int i = 0; i < NUMBER_OF_ENTITIES; i++) {
            environment.addEntity(new Entity(random.nextInt(SIZE), random.nextInt(SIZE), buildRandomBrain()));
        }
        Visualisation visualisation = new Visualisation(environment);
        int i = 1;
        int generation = 1;
        while (true){
            try {
                Thread.sleep(TIME_STEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            visualisation.update();
//            if (i == KILL_TIME - 1) {
//                visualisation.kill(KILL_X, KILL_Y);
//            } else if (i == 0) {
//                visualisation.reproduce();
//                System.out.println("generation " + generation);
//                generation++;
//            }
//            i = (i + 1) % KILL_TIME;
        }
    }
    private static SimpleBrain buildRandomBrain() {
        int hiddenNodeNumber = 3;
        int inputNodeNumber = 1;
        byte[] nodeGenes = new byte[] {inputNode, hiddenNode, hiddenNode, hiddenNode, moveLeftNode, moveUpNode, moveRightNode, moveDownNode, moveRandomNode};
        int outputNodeNumber = (nodeGenes.length - hiddenNodeNumber) - inputNodeNumber;
        Connection[] connections = new Connection[random.nextInt(hiddenNodeNumber * outputNodeNumber + inputNodeNumber * (hiddenNodeNumber + outputNodeNumber))];
        int inputNodesConnections = Math.min(random.nextInt(1, nodeGenes.length - inputNodeNumber), connections.length);
        for (int i = 0; i < inputNodesConnections; i++) {
            connections[i] = new Connection(random.nextInt(inputNodeNumber), random.nextInt(inputNodeNumber, nodeGenes.length), random.nextDouble(weightMin, weightMax), random.nextDouble() < 0.8, i);
        }
        for (int i = inputNodesConnections; i < connections.length; i++) {
            connections[i] = new Connection(random.nextInt(inputNodeNumber, inputNodeNumber + hiddenNodeNumber), random.nextInt(inputNodeNumber + hiddenNodeNumber, nodeGenes.length), random.nextDouble(weightMin, weightMax), random.nextDouble() < 0.8, i);
        }
        return new SimpleBrain(nodeGenes, connections);
    }
}