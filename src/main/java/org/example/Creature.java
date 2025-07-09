package org.example;

import java.util.ArrayList;

public class Creature extends Entity implements Constants{
    private final Genome genome;
    private final Brain brain;
    public Creature(Genome genome) {
        this.genome = genome;
        this.brain = new Brain(genome.nodeGenes, genome.connections);
    }
    public int[] act(double[] in) {
        brain.propagate(in);
        int x = (int) in[0];
        int y = (int) in[1];
        x = (x - (Main.random.nextDouble() < brain.getMoveLeftOutput() ? 1 : 0));
        y = (y + (Main.random.nextDouble() < brain.getMoveUpOutput() ? 1 : 0));
        x = (x + (Main.random.nextDouble() < brain.getMoveRightOutput() ? 1 : 0));
        y = (y - (Main.random.nextDouble() < brain.getMoveDownOutput() ? 1 : 0));
        if (Main.random.nextDouble() < brain.getMoveRandomOutput()) {
            x = (x + Main.random.nextInt(-1, 2));
            y = (y + Main.random.nextInt(-1, 2));
        }
        return new int[] {x, y};
    }
    public Creature reproduce() {
        return new Creature(mutateGenome());
//        return new Creature(genome);
    }
    private Genome mutateGenome() {
        ArrayList<Byte> newNodeGenesIntermediary = new ArrayList<>();
        for (byte type: genome.nodeGenes) {
            newNodeGenesIntermediary.add(type);
        }
        ArrayList<Connection> newConnectionsIntermediary = new ArrayList<>();
        for (Connection connection: genome.connections) {
            newConnectionsIntermediary.add(new Connection(connection.in, connection.out, connection.weight, connection.enabled, connection.innovationNumber));
        }
        int size = newConnectionsIntermediary.size();
        // going through each connection and altering it with set probabilities, including a chance of inserting a node in the middle
        for (int i = 0; i < size; i++) {
            Connection connection = newConnectionsIntermediary.get(i);
            connection.weight += ((Main.random.nextDouble() < weightUpdateChance) ? weightUpdateValue * (Main.random.nextBoolean() ? 1.0 : -1.0) : 0.0);
            connection.enabled = ((Main.random.nextDouble() < disableChance) != connection.enabled);
            if (Main.random.nextDouble() < nodeInsertionChance) {
                connection.enabled = false;
                newConnectionsIntermediary.add(
                        new Connection(
                                connection.in,
                                newNodeGenesIntermediary.size(),
                                connection.weight += ((Main.random.nextDouble() < weightUpdateChance) ? weightUpdateValue * (Main.random.nextBoolean() ? 1.0 : -1.0) : 0.0),
                                connection.enabled,
                                Main.useInnovationNumber()
                        )
                );
                newConnectionsIntermediary.add(
                        new Connection(
                                newNodeGenesIntermediary.size(),
                                connection.out,
                                connection.weight += ((Main.random.nextDouble() < weightUpdateChance) ? weightUpdateValue * (Main.random.nextBoolean() ? 1.0 : -1.0) : 0.0),
                                connection.enabled,
                                Main.useInnovationNumber()
                        )
                );
                newNodeGenesIntermediary.add(Constants.hiddenNode);
            }
        }
        // going through each node and adding a connection with set probability
        for (int i = 0; i < newNodeGenesIntermediary.size(); i++) {
            if (Main.random.nextDouble() < newConnectionChance) {
                newConnectionsIntermediary.add(new Connection(
                        Main.random.nextInt(0, newNodeGenesIntermediary.size()),
                        Main.random.nextInt(inputNodes, newNodeGenesIntermediary.size()),
                        Main.random.nextDouble(weightMin, weightMax),
                        true,
                        Main.useInnovationNumber()
                ));
            }
        }
        byte[] newNodeGenes = new byte[newNodeGenesIntermediary.size()];
        Connection[] newConnections = new Connection[newConnectionsIntermediary.size()];
        for (int i = 0; i < newNodeGenesIntermediary.size(); i++) {
            newNodeGenes[i] = newNodeGenesIntermediary.get(i);
        }
        for (int i = 0; i < newConnectionsIntermediary.size(); i++) {
            newConnections[i] = newConnectionsIntermediary.get(i);
        }
        return new Genome(newNodeGenes, newConnections);
    }

    public Genome getGenome() {
        return genome;
    }
}
