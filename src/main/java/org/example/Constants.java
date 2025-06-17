package org.example;

public interface Constants {
    byte inputNode = 0;
    byte hiddenNode = 1;
    byte moveLeftNode = 2;
    byte moveUpNode = 3;
    byte moveRightNode = 4;
    byte moveDownNode = 5;
    byte moveRandomNode = 6;
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
