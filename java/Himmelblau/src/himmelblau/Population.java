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
    DecimalFormat popVal;
    Random rand;

    int safetyLimit;

    /* One-time header information */
    String problemName;
    int popSize;
    int lambdaSize;
    int bitstringGenomeLen;
    //double mutationRate;
    double standardDeviation;
    double crossoverRate;

    /* Iteration information */
    int generationNumber;
    double highestFitnessScore;
    double averageFitnessScore;
    double lowestFitnessScore;
    double percentOfIdenticalGenomes;

    String terminationString;
    
    double[][] population;
    List<Integer> matingPool; // List of indexes 
    double[][] childrenPool;


    public Population() {
        gen = new DecimalFormat("000000");
        ftns = new DecimalFormat("0.0000");
        prcnt = new DecimalFormat("00.##");
        popVal = new DecimalFormat("0.00");
        rand = new Random();
        safetyLimit = 200000;

        /* One-time header information */
        problemName = "himmelblau";
        popSize = 15;
        lambdaSize = 100;
        bitstringGenomeLen = 20;
        //mutationRate = 0.01;
        standardDeviation = 0.5;
        crossoverRate = 0.0;
        
        /* Iteration information */
        generationNumber = 0;
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        percentOfIdenticalGenomes = 0.0;

        terminationString = "Default termination string.";

        population = new double[popSize][bitstringGenomeLen];
        matingPool = new ArrayList<Integer>(popSize);
        childrenPool = new double[lambdaSize][bitstringGenomeLen];
    }

    void printOneTimeHeader() {  // One time header information
        System.out.println(problemName + " " + popSize + " " + lambdaSize + " " + standardDeviation + " " + crossoverRate);
      }
    void printGenerationalStatistics() {
        System.out.println(gen.format(generationNumber) + " " + ftns.format(highestFitnessScore) + " " + ftns.format(averageFitnessScore) + " " + prcnt.format(percentOfIdenticalGenomes) + "%");
    }

    double[][] clone2DArray(double[][] array) {
        double[][] returnArray = new double[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            returnArray[i] = array[i].clone();
        }
        return returnArray;
    }

    //ARRAY IMPLEMENTATION
    void initializePopulation() {
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                population[i][n] = rand.nextGaussian();

                if (population[i][n] > 1.0) {
                    population[i][n] = 1.0;
                } else if (population[i][n] < -1.0) {
                    population[i][n] = -1.0;
                }
            }
        }
    }

    void print2DArray(double[][] array) {
        // System.out.println("2D array:");
        for (int i = 0; i < array.length; i++) {
            for (int n = 0; n < array[0].length; n++) {
                System.out.print(popVal.format(array[i][n]));
                if (n != bitstringGenomeLen - 1) {  // straight copied, maybe change from pop implementation
                    System.out.print(",");
                } else {
                    System.out.println();
                }
            }
        }
    }
    void printArray(double[] array) {
        // System.out.println("array:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(popVal.format(array[i]));
            if (i != bitstringGenomeLen - 1) {
                System.out.print(",");
            } else {
                System.out.println();
            }
        }
    }

    void printPopulation() {
        System.out.println("Population:");
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                System.out.print(popVal.format(population[i][n]));
                if (n != bitstringGenomeLen - 1) {
                    System.out.print(",");
                } else {
                    System.out.println();
                }
            }
        }
    }
    void printIndividual(int individual) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            System.out.print(population[individual][i]);
        }
    }

    double fitnessFunc(int individual) {
        double fitnessScore = 0.0;
        double xAllele = 0;
        double yAllele = 0;

        for (int i = 0; i < (bitstringGenomeLen/2); i++) {   //xAllele is first half of genome
            xAllele += population[individual][i];
        }
        for (int i = (bitstringGenomeLen/2); i < (bitstringGenomeLen); i++) { //yAllele is second half of genome
            yAllele += population[individual][i];
        }

        double xSquared = Math.pow(xAllele, 2);
        double ySquared = Math.pow(yAllele, 2);
        double objFunc = Math.pow( (xSquared + yAllele - 11) , 2) + Math.pow( (xAllele + ySquared - 7) , 2);
        if (objFunc == 0) {
            fitnessScore = 2.0 - 0.0;
        } else if (objFunc >= 1) {
            fitnessScore = 1.0/objFunc;
        } else { 
            fitnessScore = (1.0-objFunc) + 1;
        }

        fitnessScore = fitnessScore/2.0;

        // if (individual == 0 && generationNumber == 2000) {
        //     printArray(population[0]);
        //     System.out.println("Index: " + individual);
        //     System.out.println("X: " + xAllele);
        //     System.out.println("Y: " + yAllele);
        //     System.out.println("ObjFunc: " + objFunc);
        //     System.out.println("Fitness: " + fitnessScore);
        // }

        return fitnessScore;
    }

    double childrenFitnessFunc(int individual) {
        double fitnessScore = 0.0;
        double xAllele = 0;
        double yAllele = 0;

        for (int i = 0; i < (bitstringGenomeLen/2); i++) {   //xAllele is first half of genome
            xAllele += childrenPool[individual][i];
        }
        for (int i = (bitstringGenomeLen/2); i < (bitstringGenomeLen); i++) { //yAllele is second half of genome
            yAllele += childrenPool[individual][i];
        }

        double xSquared = Math.pow(xAllele, 2);
        double ySquared = Math.pow(yAllele, 2);
        double objFunc = Math.pow( (xSquared + yAllele - 11) , 2) + Math.pow( (xAllele + ySquared - 7) , 2);
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

    void discreteRecombination() {
        for (int i = 0; i < matingPool.size()/2; i++) {
            int parentOneIndex = matingPool.get(i*2);
            int parentTwoIndex = matingPool.get((i*2)+1);

            double[] parentOne = population[parentOneIndex].clone();
            double[] parentTwo = population[parentTwoIndex].clone();
            
            int numOfChildren = 2;
            double[][] children = new double[numOfChildren][bitstringGenomeLen];

            if (Math.random() < crossoverRate) {
                children = crossoverFunc(parentOne, parentTwo);
            } else {
                children[0] = parentOne.clone();
                children[1] = parentTwo.clone();
            }

            childrenPool[(i*2)+0] = children[0].clone();
            childrenPool[(i*2)+1] = children[1].clone();
        }
    }

    double[][] crossoverFunc(double[] parentOne, double[] parentTwo) {
        double[][] children = new double[2][bitstringGenomeLen];

        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (Math.random() < 0.5) {
                children[0][i] = parentOne[i];
                children[1][i] = parentTwo[i];
            } else {
                children[0][i] = parentTwo[i];
                children[1][i] = parentOne[i];
            }
        }

        return children;
    }

    void gaussianPerturbation() {
        for (int i = 0; i < childrenPool.length; i++) {
            // if (generationNumber == 50) { printArray(childrenPool[i]); }
            childrenPool[i] = defaultMutation(childrenPool[i]);
            // if (generationNumber == 50) { printArray(childrenPool[i]); }
        }
        // if (generationNumber == 50) { print2DArray(childrenPool); }
    }

    double[] defaultMutation(double[] toMutate) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            double mutation = rand.nextGaussian() * standardDeviation;
            // System.out.println(mutation);
            toMutate[i] += mutation;

            if (toMutate[i] > 1.0) {
                toMutate[i] = 1.0;
            } else if (toMutate[i] < -1.0) {
                toMutate[i] = -1.0;
            }
        }

        return toMutate;
    }

    void childSelection() { // Placeholder for previous homework
        population = clone2DArray(childrenPool);
    }
        
    void uniformRandomSelection() {
        matingPool.clear();
        for (int i = 0; i < lambdaSize; i++) {
            matingPool.add(rand.nextInt(popSize));
        }
    }

    void muCommaLambda() {  // Only children can move onto next generation
        Map<Integer, Double> fitnessMap = new HashMap<Integer, Double>(lambdaSize);
        for (int i = 0; i < lambdaSize; i++) {
            fitnessMap.put(i, childrenFitnessFunc(i));   // Needs to be of children
        }

        fitnessMap = MapUtil.sortByValue(fitnessMap); // Sort by fitness value (need map to retain original indexes)

        List<Integer> rankedFitness = new ArrayList<Integer>();
        rankedFitness = MapUtil.mapToList(fitnessMap);  // Get list from map to use to set population

        // for (int i = 0; i < popSize; i++) {
        //     population[i] = childrenPool[rankedFitness.get(i)].clone();
        // }
        for (int i = 0; i < popSize; i++) {
            population[i] = childrenPool[rankedFitness.get(rankedFitness.size()-1-i)].clone();
        }

        return;
    }
    
    void muPlusLambda() {  // Only children can move onto next generation
        Map<Integer, Double> fitnessMap = new HashMap<Integer, Double>(lambdaSize);
        // for (int i = 0; i < lambdaSize; i++) {
        //     fitnessMap.put(i, childrenFitnessFunc(i));   // Needs to be of children
        // }
        double[][] tempChildrenPool = new double[popSize + lambdaSize][bitstringGenomeLen];
        for (int i = 0; i < popSize + lambdaSize; i++) {
            if (i < popSize) {
                tempChildrenPool[i] = population[i].clone();
            } else {
                tempChildrenPool[i] = childrenPool[i - popSize].clone();
            }
        }
        for (int i = 0; i < popSize + lambdaSize; i++) {
            if (i < popSize) {
                fitnessMap.put(i, fitnessFunc(i));   // Needs to be of children
            } else {
                fitnessMap.put(i, childrenFitnessFunc(i - popSize));   // Needs to be of children
            }
        }

        fitnessMap = MapUtil.sortByValue(fitnessMap); // Sort by fitness value (need map to retain original indexes)

        List<Integer> rankedFitness = new ArrayList<Integer>();
        rankedFitness = MapUtil.mapToList(fitnessMap);  // Get list from map to use to set population

        // for (int i = 0; i < popSize; i++) {
        //     population[i] = childrenPool[rankedFitness.get(i)].clone();
        // }
        for (int i = 0; i < popSize; i++) {
            population[i] = tempChildrenPool[rankedFitness.get(rankedFitness.size()-1-i)].clone();
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
        while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .95)) { // Convergence Termination 
            uniformRandomSelection();
            discreteRecombination();
            gaussianPerturbation();
            // muCommaLambda();
            muPlusLambda();
            
            fitnessStatistics();
            percentOfIdenticalGenomes = getPercentOfIdenticalGenomes();

            generationNumber++;
            printGenerationalStatistics();
            if (generationNumber > 2000) {
                printPopulation();
                System.exit(0);
            }
            // if (highestFitnessScore > 0.5) {
            //     standardDeviation *= (1.0 - highestFitnessScore);
            // } else {
            //     standardDeviation *= 1 + highestFitnessScore;
            // }
            standardDeviation = 1.0 - averageFitnessScore;
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
        } else if (highestFitnessScore >= 1.0 && averageFitnessScore >= .95) {// && percentOfIdenticalGenomes >= 50) {
            terminationString = "Population has MOSTLY converged";
        } else {
            //default string
        }
        System.out.print(terminationString);
        printPopulation();
    }
}
