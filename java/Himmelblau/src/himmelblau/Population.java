package himmelblau;


import java.lang.Math;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Population {
    DecimalFormat gen;
    DecimalFormat ftns;
    DecimalFormat prcnt;
    Random rand;

    int safetyLimit;

    /* One-time header information */
    String problemName;
    int popSize;
    int lambdaSize;
    int bitstringGenomeLen;
    double mutationRate;
    double crossoverRate;

    /* Iteration information */
    int generationNumber;
    double highestFitnessScore;
    double averageFitnessScore;
    double lowestFitnessScore;
    double percentOfIdenticalGenomes;

    String terminationString;
    
    int[][] population;
    List<Integer> matingPool;
    int[][] childrenPool;


    public Population() {
        gen = new DecimalFormat("000000");
        ftns = new DecimalFormat("0.0000");
        prcnt = new DecimalFormat("00.##");
        rand = new Random();
        safetyLimit = 200000;

        /* One-time header information */
        problemName = "himmelblau";
        popSize = 100;
        lambdaSize = 100;
        bitstringGenomeLen = 32;
        mutationRate = 0.01;
        crossoverRate = 0.5;
        
        /* Iteration information */
        generationNumber = 0;
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        percentOfIdenticalGenomes = 0.0;

        terminationString = "Default termination string.";

        population = new int[popSize][bitstringGenomeLen];
        matingPool = new ArrayList<Integer>(popSize);
        childrenPool = new int[lambdaSize][bitstringGenomeLen];
    }

    void printOneTimeHeader() {  // One time header information
        System.out.println(problemName + " " + popSize + " " + bitstringGenomeLen + " " + mutationRate + " " + crossoverRate);
      }
    void printGenerationalStatistics() {
        System.out.println(gen.format(generationNumber) + " " + ftns.format(highestFitnessScore) + " " + ftns.format(averageFitnessScore) + " " + prcnt.format(percentOfIdenticalGenomes) + "%");
    }

    int[][] clone2DArray(int[][] array) {
        int[][] returnArray = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].clone();
        }
        return returnArray;
    }

    //ARRAY IMPLEMENTATION
    void initializePopulation() {
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                // population[i][n] = rand.nextInt(2);
                population[i][n] = (int) Math.round( Math.random() );
            }
        }
    }

    void print2DArray(int[][] array) {
        System.out.println("2D array:");
        for (int i = 0; i < array.length; i++) {
            for (int n = 0; n < array[0].length; n++) {
                System.out.print(array[i][n]);
            }
            System.out.println();
        }
    }
    void printArray(int[] array) {
        System.out.println("array:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
        System.out.println();
    }

    void printPopulation() {
        System.out.println("Population:");
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                System.out.print(population[i][n]);
            }
            System.out.println();
        }
    }
    void printIndividual(int individual) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            System.out.print(population[individual][i]);
        }
    }

    double fitnessFunc(int individual) {
        double fitnessScore = 0;
        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (population[individual][i] == 1) {
                fitnessScore++;
            }
        }

        fitnessScore = fitnessScore / (bitstringGenomeLen);

        return fitnessScore;
    }
    void fitnessStatistics() {
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        lowestFitnessScore = 1.0;
        for (int i = 0; i < popSize; i++) {
            double currentIndividualsFitness = fitnessFunc(i);
            averageFitnessScore += currentIndividualsFitness;
            if (highestFitnessScore < currentIndividualsFitness) {
                highestFitnessScore = currentIndividualsFitness;
            }
            if (lowestFitnessScore > currentIndividualsFitness) {
                lowestFitnessScore = currentIndividualsFitness;
            }
        }

        averageFitnessScore = averageFitnessScore / popSize;

        return;
    }

    double getPercentOfIdenticalGenomes() {
        double percentOfIdenticalGenomes = 0.0;
        List<Integer> matchedIndexes = new ArrayList<Integer>();
        
        for (int i = 0; i < popSize; i++) {
            boolean isExistingMatch = false;
            for (int n = 0; n < matchedIndexes.size(); n++) {
                if (i == matchedIndexes.get(n)) {   // If already matched then continue outer loop
                    isExistingMatch = true;
                    break;
                }
            }
            if (isExistingMatch) {
                continue;
            }


            boolean isiAlreadyMatched = false;
            for (int j = i + 1; j < popSize; j++) {
                if(Arrays.equals(population[i], population[j])) {
                    if (isiAlreadyMatched) {
                        matchedIndexes.add(j);
                    } else {
                        matchedIndexes.add(i);
                        matchedIndexes.add(j);
                        isiAlreadyMatched = true;
                    }
                }
            }

        }

        percentOfIdenticalGenomes = ((double) (matchedIndexes.size()) / (double) popSize) * 100.0;

        return percentOfIdenticalGenomes;
    }

    void uniformMutation() {
        for (int i = 0; i < popSize; i++) {
            population[i] = defaultMutation(population[i]);
        }
    }

    int[] defaultMutation(int[] toMutate) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            double r = Math.random();   //Mutation Chance
            if (r < mutationRate) {
                if (toMutate[i] == 1) {
                    toMutate[i] = 0;
                } else if (toMutate[i] == 0) {
                    toMutate[i] = 1;
                } else {
                    throw new java.lang.RuntimeException("Population bit should never not be 1 or 0.");
                }
            }
        }

        return toMutate;
    }

    void onePointCrossover() {
        for (int i = 0; i < matingPool.size()/2; i++) {
            int parentOneIndex = matingPool.get(i*2);
            int parentTwoIndex = matingPool.get((i*2)+1);

            int[] parentOne = population[parentOneIndex].clone();
            int[] parentTwo = population[parentTwoIndex].clone();
            
            int numOfChildren = 2;
            int[][] children = new int[numOfChildren][bitstringGenomeLen];

            if (Math.random() < crossoverRate) {
                children = crossoverFunc(parentOne, parentTwo);
            } else {
                children[0] = parentOne.clone();
                children[1] = parentTwo.clone();
            }

            population[(i*2)+0] = children[0].clone();
            population[(i*2)+1] = children[1].clone();
        }
    }

    int[][] crossoverFunc(int[] parentOne, int[] parentTwo) {
        int[][] children = new int[2][bitstringGenomeLen];
        int crossoverPoint = rand.nextInt(bitstringGenomeLen-2) + 1; // range from 1-30 otherwise its just two cases of asexual reproduction of parents

        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (i < crossoverPoint) {
                children[0][i] = parentOne[i];
                children[1][i] = parentTwo[i];
            } else {
                children[0][i] = parentTwo[i];
                children[1][i] = parentOne[i];
            }
        }

        return children;
    }
        
    void rouletteWheelSelection() {
    /* Roulette Wheel Selection
        1. Compute fitnessVector
        2. Find min fitness
        3. Zero offset fitnesses - subtract the min from each element and then add 1/(10^100)
        4. Find sum of fitness
        5. Use sum to create roulette wheel array - (zero offsetted fitness / max fitness)
        6. Pick the first number larger than random number to be parent until mating pool is filled
    */
        matingPool.clear();
        double offsetConst = 1 / (Math.pow(10, 100));

        List<Double> fitnessVector = new ArrayList<Double>();
        List<Double> rouletteBoard = new ArrayList<Double>();
        double fitnessTotal = 0;

        fitnessStatistics();

        for (int i = 0; i < popSize; i++) {
            double fitness = fitnessFunc(i);
            fitnessTotal += fitness - lowestFitnessScore + offsetConst;
            fitnessVector.add(fitness - lowestFitnessScore + offsetConst); //gets vector of fitness of each individual
        }
        for (int i = 0; i < popSize; i++) {
            double slotSize = fitnessVector.get(i)/fitnessTotal;
            if (i == 0) {
                rouletteBoard.add(slotSize);
            } else {
                rouletteBoard.add(rouletteBoard.get(rouletteBoard.size()-1) + slotSize);
            }
        }
        
        while (matingPool.size() < popSize) {
            double r = Math.random();

            for (int n = 0; n < popSize; n++) {
                if (r < rouletteBoard.get(n)) {
                    matingPool.add(n);
                    break;
                } else if (n == popSize-1) {
                    matingPool.add(n);
                    break;
                }
            }
        }  
        
        return;
    }

    void algorithmLogic() {
        /* Steps should be:
            1. Initialize population
            2. Parent Selection
            3. Recombination
            4. Mutation
            5. Survivor Selection
            6. Termination check -> Terminate or go back to step 2
        */
        while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .95 || percentOfIdenticalGenomes < 50)) { // Convergence Termination 
            rouletteWheelSelection();
            onePointCrossover();
            uniformMutation();
            //Survivor Selection
            
            fitnessStatistics();
            percentOfIdenticalGenomes = getPercentOfIdenticalGenomes();

            generationNumber++;
            printGenerationalStatistics();
        }
    }

    void run_himmelblau() {
        initializePopulation();
        fitnessStatistics();
        printOneTimeHeader();
        getPercentOfIdenticalGenomes();
        printGenerationalStatistics();

        algorithmLogic();

        checkTerminationString();
        // printPopulation();
    }

    void checkTerminationString() {
        if (averageFitnessScore >= 1.0) {
            terminationString = "Population has converged";
        } else if (generationNumber >= safetyLimit) {
            terminationString = "Safety limit of " + safetyLimit + " generations";
        } else if (highestFitnessScore >= 1.0 && averageFitnessScore >= .95 && percentOfIdenticalGenomes >= 50) {
            terminationString = "Population has MOSTLY converged";
        } else {
            //default string
        }
        System.out.print(terminationString);
    }
}
