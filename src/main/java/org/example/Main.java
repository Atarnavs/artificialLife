package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main implements Constants{
    public static Random random = new Random();
    private static int innovationNumber = 0;
    private static File output = new File("C:\\Users\\tarna\\Fun Projects\\ArtificialLifeSimulator\\survival_log3.csv");
    private static PrintWriter out;
    private static int differentGenomesCounter = 0;
    static {
        try {
            out = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        runWithWindow();
//        runWIthLog();
//        tryMutations();
    }
    private static void runWithWindow() {
        Environment environment = new Environment();
        for (int i = 0; i < NUMBER_OF_ENTITIES; i++) {
            environment.addEntity(buildRandomEntity());
        }
        Visualisation visualisation = new Visualisation(environment);
    }
    private static void runWIthLog() {
        Environment environment = new Environment();
        int i = 1;
        int generation = 1;
        for (int j = 0; j < KILL_TIME * 300; j++) {
            environment.updateEntities();
            if (i == KILL_TIME - 1) {
                environment.kill(KILL_X_LEFT, KILL_Y_DOWN, KILL_X_RIGHT, KILL_Y_UP);
            } else if (i == 0) {
                environment.reproduce();
                System.out.println("generation " + generation);
                out.print(generation + ",");
                generation++;
            }
            i = (i + 1) % KILL_TIME;
        }
        out.flush();
        out.close();
    }
    private static void tryMutations() {
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < NUMBER_OF_ENTITIES; i++) {
                Creature creature = buildRandomEntity();
                Creature child = creature.reproduce();
                compareGenomes(creature.getGenome(), child.getGenome());
                System.out.println("_____________________________________________");
            }
        }
        System.out.println((double) differentGenomesCounter / 200);
    }
    private static Creature buildRandomEntity() {
        int hiddenNodeNumber = 0;
        int inputNodeNumber = 3;
        byte[] nodeGenes = new byte[] {biasNode, yInputNode, xInputNode, moveLeftNode, moveUpNode, moveRightNode, moveDownNode, moveRandomNode};
        int outputNodeNumber = (nodeGenes.length - hiddenNodeNumber) - inputNodeNumber;
        Connection[] connections = new Connection[0];
        return new Creature(new Genome(nodeGenes, connections));
    }
    public static int useInnovationNumber() {
        return innovationNumber++;
    }
    public static void writeToFile(double s) {
        out.println(s);
    }
    public static void printGenome(Genome genome) {
        int i = 0;
        for (byte type: genome.nodeGenes) {
            System.out.print("Node number: " + i + " type: ");
            i++;
            printGene(type);
            System.out.print("|");
        }
        System.out.println();
        for (Connection connection: genome.connections) {
            printConnection(connection);
        }
        System.out.println();
    }
    public static void compareGenomes(Genome genome1, Genome genome2) {
        int i = 0;
        boolean same = true;
        while (i < genome1.nodeGenes.length && i < genome2.nodeGenes.length) {
            if (genome1.nodeGenes[i] == genome2.nodeGenes[i]) {
//                System.out.print("Genes number " + i + "match");
            }
            else {
                System.out.println("Number one is: ");
                printGene(genome1.nodeGenes[i]);
                System.out.println(" but number 2 is: ");
                printGene(genome2.nodeGenes[i]);
                if (same) {
                    differentGenomesCounter++;
                    same = false;
                }
            }
            i++;
        }
        int j = i;
        while (i < genome1.nodeGenes.length) {
            System.out.print("Extra gene in the first one: ");
            printGene(genome1.nodeGenes[i]);
            i++;
            if (same) {
                differentGenomesCounter++;
                same = false;
            }
        }
        while (j < genome2.nodeGenes.length) {
            System.out.print("Extra gene in the second one: ");
            printGene(genome2.nodeGenes[j]);
            j++;
            if (same) {
                differentGenomesCounter++;
                same = false;
            }
        }
        System.out.println();
        i = 0;
        while (i < genome1.connections.length && i < genome2.connections.length) {
            Connection connection1 = genome1.connections[i];
            Connection connection2 = genome2.connections[i];
            System.out.print(i + " | ");
            if (connection1.in == connection2.in) {
//                System.out.print("ins match ");
            }
            else {
                System.out.print("in 1 is: " + connection1.in + "but in 2 is: " + connection2.in);
                if (same) {
                    differentGenomesCounter++;
                    same = false;
                }
            }
            if (connection1.out == connection2.out) {
//                System.out.print("outs match ");
            }
            else {
                System.out.print("out 1 is: " + connection1.out + "but out 2 is: " + connection2.out);
                if (same) {
                    differentGenomesCounter++;
                    same = false;
                }
            }
            if (connection1.weight == connection2.weight) {
//                System.out.print("weights match ");
            }
            else {
                System.out.print("weight 1 is: " + connection1.weight + "but weight 2 is: " + connection2.weight);
                if (same) {
                    differentGenomesCounter++;
                    same = false;
                }
            }
            if (connection1.enabled == connection2.enabled) {
//                System.out.print("states match ");
            }
            else {
                System.out.print("state 1 is: " + connection1.enabled + "but state 2 is: " + connection2.enabled);
                if (same) {
                    differentGenomesCounter++;
                    same = false;
                }
            }
            i++;
        }
        j = i;
        while (i < genome1.connections.length) {
            System.out.print("Extra connection in the first one: ");
            printConnection(genome1.connections[i]);
            i++;
            if (same) {
                differentGenomesCounter++;
                same = false;
            }
        }
        while (j < genome2.connections.length) {
            System.out.print("Extra connection in the second one: ");
            printConnection(genome2.connections[j]);
            j++;
            if (same) {
                differentGenomesCounter++;
                same = false;
            }
        }
        System.out.println();
    }
    public static void printConnection(Connection connection) {
        System.out.println("in: " + connection.in + " out: " + connection.out + " weight: " + connection.weight + " enabled: " + connection.enabled + " inn.numb.: " + connection.innovationNumber);
    }
    public static void printGene(byte type) {
        switch (type) {
            case yInputNode:
                System.out.print("yInputNode");
                break;
            case xInputNode:
                System.out.print("xInputNode");
                break;
            case hiddenNode:
                System.out.print("hiddenNode");
                break;
            case moveLeftNode:
                System.out.print("moveLeftNode");
                break;
            case moveUpNode:
                System.out.print("moveUpNode");
                break;
            case moveRightNode:
                System.out.print("moveRightNode");
                break;
            case moveDownNode:
                System.out.print("moveDownNode");
                break;
            case moveRandomNode:
                System.out.print("moveRandomNode");
                break;
        }
    }
}