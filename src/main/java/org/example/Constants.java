package org.example;

public interface Constants {
    byte biasNode = -4;
    byte oscillatorNode = -3;
    byte yInputNode = -2;
    byte xInputNode = -1;
    byte hiddenNode = 0;
    byte moveLeftNode = 1;
    byte moveUpNode = 2;
    byte moveRightNode = 3;
    byte moveDownNode = 4;
    byte moveRandomNode = 5;
    double weightMax = 5.0;
    double weightMin = -5.0;
    double StartingEnergy = 10_000.0;
    double StepCost = 10.0;
    int inputNodes = 3;
    int WORLD_SIZE = 128;
    int ENLARGEMENT_FACTOR = 7;
    int SCREEN_SIZE = WORLD_SIZE * ENLARGEMENT_FACTOR;
    long TIME_STEP = 1;
    int KILL_TIME = 100;
    int KILL_X_LEFT = (int) (WORLD_SIZE * 0.1);
    int KILL_Y_DOWN = (int) (WORLD_SIZE * 0.0);
    int KILL_X_RIGHT = (int) (WORLD_SIZE * 0.0);
    int KILL_Y_UP = (int) (WORLD_SIZE * 0.0);
    int NUMBER_OF_ENTITIES = 1_000;
    double weightUpdateChance = 0.0004;
    double weightUpdateValue = 0.1;
    double disableChance = 0.00009;
    double nodeInsertionChance = 0.000025;
    double newConnectionChance = 0.0001;
    double chanceOfWorkingConnection = 0.8;
    double bias = 5.0;
}
