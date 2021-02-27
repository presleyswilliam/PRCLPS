package maxones;

import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Population {
    DecimalFormat df2;
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
    double percentOfIdenticalGenomes;

    String terminationString;
    
    int[][] population;


    public Population() {
        df2 = new DecimalFormat("0.0000");
        rand = new Random();
        safetyLimit = 20000;

        /* One-time header information */
        problemName = "rosenbrock";
        popSize = 100;
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
    }

    public void printOneTimeHeader() {  // One time header information
        System.out.println(problemName + " " + popSize + " " + bitstringGenomeLen + " " + mutationRate + " " + crossoverRate);
      }
    public void printGenerationalStatistics() {
        System.out.println(generationNumber + " " + df2.format(highestFitnessScore) + " " + df2.format(averageFitnessScore) + " " + percentOfIdenticalGenomes + "%");
    }

    //ARRAY IMPLEMENTATION
    public void initializePopulation() {
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                population[i][n] = rand.nextInt(2);
            }
        }
    }

    public void printPopulation() {
        System.out.println("Population:");
        for (int i = 0; i < popSize; i++) {
            for (int n = 0; n < bitstringGenomeLen; n++) {
                System.out.print(population[i][n]);
            }
            System.out.println();
        }
    }
    public void printIndividual(int individual) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            System.out.print(population[individual][i]);
        }
    }

    public double fitnessFunc(int individual) {
        double fitnessScore = 0;
        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (population[individual][i] == 1) {
                fitnessScore++;
            }
        }

        fitnessScore = fitnessScore / (bitstringGenomeLen);

        return fitnessScore;
    }
    public void fitnessStatistics() {
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        for (int i = 0; i < popSize; i++) {
            double currentIndividualsFitness = fitnessFunc(i);
            averageFitnessScore += currentIndividualsFitness;
            if (highestFitnessScore < currentIndividualsFitness) {
                highestFitnessScore = currentIndividualsFitness;
            }
        }

        averageFitnessScore = averageFitnessScore / popSize;

        return;
    }

    public double getPercentOfIdenticalGenomes() {
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

    public int[][] crossoverFunc(int parent1Index, int parent2Index, int crossoverPoint, int numOfChildren) {
        int[][] children = new int[numOfChildren][bitstringGenomeLen];
        boolean doCrossover = false;
        if (Math.random() > (1 - crossoverRate)) {
            doCrossover = true;
        }

        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (doCrossover) {  //  CROSSOVER IMPLEMENTATION
                if (i < crossoverPoint) {
                    children[0][i] = population[parent1Index][i];
                    children[1][i] = population[parent2Index][i];
                } else {
                    children[0][i] = population[parent2Index][i];
                    children[1][i] = population[parent1Index][i];
                }
            } else {
                children[0][i] = population[parent1Index][i];
                children[1][i] = population[parent2Index][i];
            }

            //Run the chance of mutation past each chromosome
            double[] childMutationChance = new double[numOfChildren];
            for (int c = 0; c < numOfChildren; c++) {  // Populate random chance of mutation for each bit
                childMutationChance[c] = Math.random();
            }
            for (int c = 0; c < numOfChildren; c++) {  // MUTATION IMPLEMENTATION - CHANCE OF MUTATION APPLIED TO EACH CHROMOSOME
                if (childMutationChance[c] > (1 - mutationRate)) {
                    if (children[c][i] == 1) {
                        children[c][i] = 0;
                    } else {
                        children[c][i] = 1;
                    }
                }
            }

        }

        return children;
    }

    public void rouletteWheelSelection() {
        int[][] newPop = population;
        List<Integer> matingPool = new ArrayList<Integer>(popSize/2);   // Vector of individuals to be selected for mating pool

        List<Double> fitnessVector = new ArrayList<Double>();
        List<Double> rouletteBoard = new ArrayList<Double>();
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        double fitnessTotal = 0;
        for (int i = 0; i < popSize; i++) {
            double fitness = fitnessFunc(i);
            fitnessTotal += fitness;
            fitnessVector.add(fitness); //gets vector of fitness of each individual
        }
        for (int i = 0; i < popSize; i++) {
            double slotSize = fitnessVector.get(i)/fitnessTotal;
            if (i == 0) {
                rouletteBoard.add(slotSize);
            } else {
                rouletteBoard.add(rouletteBoard.get(rouletteBoard.size()-1) + slotSize);
            }
        }
        for (int i = 0; i < popSize; i++) {
            map.put(i, rouletteBoard.get(i));
        }
        MapUtil.sortByValue(map);
        System.out.println(rouletteBoard);
        System.out.println(map);
        // Collections.sort(rouletteBoard);
        // System.out.println();
        // System.out.println(rouletteBoard);
        System.exit(0);

        for (int currentMember = 0; currentMember < 50; currentMember++) { //chosing 50 to have parents with 2 kids
            double r = Math.random();
            int i = 0;

            while (fitnessVector.get(i) < r) {
                i++;
                if (i >= popSize-1) {
                    i = 0;
                    r = Math.random();
                }
            }
            matingPool.add(i);
            i = 0;
        }
        for (int i = 0; i < matingPool.size(); i++) {
            int parent1 = matingPool.get(rand.nextInt(popSize/2));
            int parent2 = matingPool.get(rand.nextInt(popSize/2));
            int crossoverPoint = rand.nextInt(30) + 1; // range from 1-30 otherwise its just two cases of asexual reproduction of parents
            int numOfChildren = 2;
            int[][] children = new int[numOfChildren][bitstringGenomeLen];
            children = crossoverFunc(parent1, parent2, crossoverPoint, numOfChildren);
            newPop[(i*2)+0] = children[0];
            newPop[(i*2)+1] = children[1];
        }

        population = newPop;

        return;
    }

    public void algorithmLogic() {
        /* Begin algorithm logic */
        while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .90 || percentOfIdenticalGenomes < 90)) { // Convergence Termination 
            rouletteWheelSelection();
            
            fitnessStatistics();
            percentOfIdenticalGenomes = getPercentOfIdenticalGenomes();

            generationNumber++;
            printGenerationalStatistics();
        }
    }

    public void checkTerminationString() {
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
