package rosenbrock;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.text.DecimalFormat;

public class Population {
    DecimalFormat gen;
    DecimalFormat ftns;
    DecimalFormat prcnt;
    Random rand;

    int safetyLimit;

    /* One-time header information */
    String problemName;
    int popSize;
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


    public Population() {
        gen = new DecimalFormat("000000");
        ftns = new DecimalFormat("0.0000");
        prcnt = new DecimalFormat("00.##");
        rand = new Random();
        safetyLimit = 20000;

        /* One-time header information */
        problemName = "rosenbrock";
        popSize = 100;
        bitstringGenomeLen = 16;
        mutationRate = 0.01;
        crossoverRate = 0.5;
        
        /* Iteration information */
        generationNumber = 0;
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        percentOfIdenticalGenomes = 0.0;

        terminationString = "Default termination string.";

        population = new int[popSize][bitstringGenomeLen];
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
                population[i][n] = rand.nextInt(2);
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
        double fitnessScore = 0.0;
        int xAllele = 0;
        int yAllele = 0;

        for (int i = 0; i < (bitstringGenomeLen/2); i++) {   //xAllele is first half of genome
            xAllele = (int) (xAllele + (Math.pow(2,(((bitstringGenomeLen/2)-1) - i)) * population[individual][i]));
        }
        for (int i = (bitstringGenomeLen/2); i < (bitstringGenomeLen); i++) { //yAllele is second half of genome
            yAllele = (int) (yAllele + (Math.pow(2,((bitstringGenomeLen-1) - i)) * population[individual][i]));
        }

        double objFunc = Math.pow(1.0 - xAllele, 2.0) + (100.0*Math.pow((yAllele - Math.pow(xAllele,2.0)), 2.0));
        if (objFunc == 0) {
            fitnessScore = 2.0 - 0.0;
        } else if (objFunc >= 1) {
            fitnessScore = 1.0/objFunc;
        } else { 
            fitnessScore = (1.0-objFunc) + 1;
        }

        fitnessScore = fitnessScore/2.0;

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
        int[][] newPop = clone2DArray(population);
        List<Integer> matingPool = new ArrayList<Integer>(popSize);   // Vector of individuals to be selected for mating pool

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

        for (int i = 0; i < matingPool.size()/2; i++) {
            int parent1 = matingPool.get(i*2);
            int parent2 = matingPool.get((i*2)+1);

            int[] parentOne = population[parent1].clone();
            int[] parentTwo = population[parent2].clone();
            parentOne = defaultMutation(parentOne);
            parentTwo = defaultMutation(parentTwo);
            
            int numOfChildren = 2;
            int[][] children = new int[numOfChildren][bitstringGenomeLen];

            if (Math.random() < crossoverRate) {
                children = crossoverFunc(parentOne, parentTwo);
            } else {
                children[0] = parentOne.clone();
                children[1] = parentTwo.clone();
            }

            newPop[(i*2)+0] = children[0].clone();
            newPop[(i*2)+1] = children[1].clone();
        }

        population = clone2DArray(newPop);
        
        return;
    }

    void algorithmLogic() {
        /* Begin algorithm logic */
        while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .90 || percentOfIdenticalGenomes < 90)) { // Convergence Termination 
            rouletteWheelSelection();
            
            fitnessStatistics();
            percentOfIdenticalGenomes = getPercentOfIdenticalGenomes();

            generationNumber++;
            printGenerationalStatistics();
        }
    }

    void run_rosenbrock() {
        initializePopulation();
        fitnessStatistics();
        printOneTimeHeader();
        getPercentOfIdenticalGenomes();
        printGenerationalStatistics();
        algorithmLogic();
        checkTerminationString();
        printPopulation();
    }

    void checkTerminationString() {
        if (averageFitnessScore >= 1.0) {
            terminationString = "Population has converged";
        } else if (generationNumber >= safetyLimit) {
            terminationString = "Safety limit of " + safetyLimit + " generations";
        } else if (highestFitnessScore >= 1.0 && averageFitnessScore > .90 && percentOfIdenticalGenomes >= 90) {
            terminationString = "Population has MOSTLY converged";
        } else {
            //default string
        }
        System.out.println(terminationString);
    }
}
