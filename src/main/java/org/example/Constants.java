package org.example;

public interface Constants {
    byte inputNode = -1;
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
    int SIZE = 1_000;
    long TIME_STEP = 33;
    int KILL_TIME = 100;
    int KILL_X = 500;
    int KILL_Y = 0;
    int NUMBER_OF_ENTITIES = 300;
}
