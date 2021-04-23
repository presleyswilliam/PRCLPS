package eightqueens;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.lang.Math;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Population {
    DecimalFormat gen;
    DecimalFormat ftns;
    DecimalFormat dvsty;
    DecimalFormat popVal;
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
    int candidateEvaluations;
    double highestFitnessScore;
    double averageFitnessScore;
    double lowestFitnessScore;
    double diversity;

    boolean terminationCondition;
    String terminationString;

    String testName;
    List<String> pythonTextString;
    
    int[][] population;
    List<Integer> matingPool; // List of indexes 
    int[][] childrenPool;


    public Population(double mRate, double cRate, int lSize, String tName) {
        gen = new DecimalFormat("000000");
        ftns = new DecimalFormat("0.0000");
        dvsty = new DecimalFormat("00.00");
        popVal = new DecimalFormat("0.00");
        rand = new Random();
        safetyLimit = 10000;

        /* One-time header information */
        problemName = "eightqueens";
        popSize = 50;
        lambdaSize = lSize;
        bitstringGenomeLen = 16;
        mutationRate = mRate;//0.01;
        crossoverRate = cRate;//0.5;
        
        /* Iteration information */
        generationNumber = 0;
        candidateEvaluations = 0;
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        diversity = 0.0;

        terminationCondition = (averageFitnessScore < 0.99999);  // Rounding...
        terminationString = "Default termination string. "; // Extra space on the end is for python file line 21...

        testName = tName;
        pythonTextString = new ArrayList<String>();

        population = new int[popSize][bitstringGenomeLen];
        matingPool = new ArrayList<Integer>(popSize);
        childrenPool = new int[lambdaSize][bitstringGenomeLen];
    }

    void printOneTimeHeader() {  // One time header information
        System.out.println(problemName + " " + popSize + " " + lambdaSize + " " + mutationRate + " " + crossoverRate);
      }
    void printGenerationalStatistics() {
        String outputString = gen.format(generationNumber) + " " + gen.format(candidateEvaluations) + " " + ftns.format(highestFitnessScore) + " " + ftns.format(averageFitnessScore) + " " + dvsty.format(diversity);
        System.out.println(outputString);

        if (testName != "lambdaSize") {
            pythonTextString.add(gen.format(candidateEvaluations));
        } else {
            double percentOfMaxCandidateEvaluations = (candidateEvaluations / ((popSize + lambdaSize) * safetyLimit) ) * 100;
            pythonTextString.add(dvsty.format(percentOfMaxCandidateEvaluations));
        }
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
                population[i][n] = rand.nextInt(8);
            }
        }
    }

    void print2DArray(int[][] array) {
        // System.out.println("2D array:");
        for (int i = 0; i < array.length; i++) {
            for (int n = 0; n < array[0].length; n++) {
                System.out.print(array[i][n]);
                if (n != array[0].length - 1) {
                    System.out.print(",");
                } else {
                    System.out.println();
                }
            }
        }
    }
    void printArray(int[] array) {
        // System.out.println("array:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != bitstringGenomeLen - 1) {
                System.out.print(",");
            } else {
                System.out.println();
            }
        }
    }

    void printPopulation() {
        System.out.println("Population:");
        for (int n = 0; n < popSize; n++) {
            int[][] board = new int[8][8];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = 0;
                }
            }
            for (int i = 0; i < bitstringGenomeLen/2; i++) {
                board[population[n][i*2]][population[n][(i*2)+1]] = 8;

                markInvalidBoardSpots(board, population[n][i*2] +1, population[n][(i*2)+1] +1, "upRight");
                markInvalidBoardSpots(board, population[n][i*2] +1, population[n][(i*2)+1] -1, "downRight");
                markInvalidBoardSpots(board, population[n][i*2] -1, population[n][(i*2)+1] -1, "downLeft");
                markInvalidBoardSpots(board, population[n][i*2] -1, population[n][(i*2)+1] +1, "upLeft");
                markInvalidBoardSpots(board, population[n][i*2] +1, population[n][(i*2)+1], "right");
                markInvalidBoardSpots(board, population[n][i*2], population[n][(i*2)+1] -1, "down");
                markInvalidBoardSpots(board, population[n][i*2] -1, population[n][(i*2)+1], "left");
                markInvalidBoardSpots(board, population[n][i*2], population[n][(i*2)+1] +1, "up");
            }
            print2DArray(board);
            System.out.println();
        }
    }
    void printIndividual(int individual) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            System.out.print(population[individual][i]);
        }
    }

    int[][] markInvalidBoardSpots(int[][] board, int x, int y, String direction) {
        if (x > 7 || y > 7 || x < 0 || y < 0) {
            return board;
        }

        if (direction == "upRight") {
            if (x == 7 || y == 7) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x+1, y+1, "upRight");
            }

        } else if (direction == "right") {
            if (x == 7) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x+1, y, "right");
            }

        } else if (direction == "downRight") {
            if (x == 7 || y == 0) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x+1, y-1, "downRight");
            }

        } else if (direction == "down") {
            if (y == 0) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x, y-1, "down");
            }

        }  else if (direction == "downLeft") {
            if (x == 0 || y == 0) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x-1, y-1, "downLeft");
            }

        } else if (direction == "left") {
            if (x == 0) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x-1, y, "left");
            }

        } else if (direction == "upLeft") {
            if (x == 0 || y == 7) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x-1, y+1, "upLeft");
            }

        } else if (direction == "up") {
            if (y == 7) {
                board[x][y] = 1;
                return board;
            } else {
                board[x][y] = 1;
                markInvalidBoardSpots(board, x, y+1, "up");
            }

        } else {
            throw new RuntimeException("markInvalidBoardSpots() invalid direction");
        }

        return board;
    }
    double fitnessFunc(int individual, String popOrChildrenString) {
        double fitnessScore = 0.0;
        int fitCount = 0;

        /*

        1. get position of each queen
        2. get positions of board that cannot be occuppied
        3. count how many queens are in positions that cant be occupied
        
        */
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
        if (popOrChildrenString == "pop") {
            for (int i = 0; i < bitstringGenomeLen/2; i++) {
                board[population[individual][i*2]][population[individual][(i*2)+1]] = 8;

                markInvalidBoardSpots(board, population[individual][i*2] +1, population[individual][(i*2)+1] +1, "upRight");
                markInvalidBoardSpots(board, population[individual][i*2] +1, population[individual][(i*2)+1] -1, "downRight");
                markInvalidBoardSpots(board, population[individual][i*2] -1, population[individual][(i*2)+1] -1, "downLeft");
                markInvalidBoardSpots(board, population[individual][i*2] -1, population[individual][(i*2)+1] +1, "upLeft");
                markInvalidBoardSpots(board, population[individual][i*2] +1, population[individual][(i*2)+1], "right");
                markInvalidBoardSpots(board, population[individual][i*2], population[individual][(i*2)+1] -1, "down");
                markInvalidBoardSpots(board, population[individual][i*2] -1, population[individual][(i*2)+1], "left");
                markInvalidBoardSpots(board, population[individual][i*2], population[individual][(i*2)+1] +1, "up");
            }
        } else if (popOrChildrenString == "children") {
            for (int i = 0; i < bitstringGenomeLen/2; i++) {
                board[childrenPool[individual][i*2]][childrenPool[individual][(i*2)+1]] = 8;

                markInvalidBoardSpots(board, childrenPool[individual][i*2] +1, childrenPool[individual][(i*2)+1] +1, "upRight");
                markInvalidBoardSpots(board, childrenPool[individual][i*2] +1, childrenPool[individual][(i*2)+1] -1, "downRight");
                markInvalidBoardSpots(board, childrenPool[individual][i*2] -1, childrenPool[individual][(i*2)+1] -1, "downLeft");
                markInvalidBoardSpots(board, childrenPool[individual][i*2] -1, childrenPool[individual][(i*2)+1] +1, "upLeft");
                markInvalidBoardSpots(board, childrenPool[individual][i*2] +1, childrenPool[individual][(i*2)+1], "right");
                markInvalidBoardSpots(board, childrenPool[individual][i*2], childrenPool[individual][(i*2)+1] -1, "down");
                markInvalidBoardSpots(board, childrenPool[individual][i*2] -1, childrenPool[individual][(i*2)+1], "left");
                markInvalidBoardSpots(board, childrenPool[individual][i*2], childrenPool[individual][(i*2)+1] +1, "up");
            }
        } else {
            throw new RuntimeException("Target array not pop or children.");
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 8) {
                    fitCount = fitCount + 1;
                }
            }
        }

        fitnessScore = (double) fitCount / 8;
        // System.out.println(fitnessScore);
        // if (fitnessScore >= 0.8750) {
        //     print2DArray(board);
        // }

        return fitnessScore;
    }
    void fitnessStatistics() {
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        lowestFitnessScore = 1.0;
        for (int i = 0; i < popSize; i++) {
            double currentIndividualsFitness = fitnessFunc(i, "pop");
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

    /* setDiversity
        Computes the euclidean distance between each point in the population
        and sets diversity to the largest distance
    */
    void setDiversity() {
        double maxEuclideanDistance = 0.0;

        for (int individual = 0; individual < popSize; individual++) {
            double xAllele = 0;
            double yAllele = 0;
    
            for (int i = 0; i < (bitstringGenomeLen/2); i++) {   //xAllele is first half of genome
                xAllele += population[individual][i];
            }
            for (int i = (bitstringGenomeLen/2); i < (bitstringGenomeLen); i++) { //yAllele is second half of genome
                yAllele += population[individual][i];
            }

            for (int copmaredIndividual = 0; copmaredIndividual < popSize; copmaredIndividual++) {
                double comparedXAllele = 0;
                double comparedYAllele = 0;
        
                for (int j = 0; j < (bitstringGenomeLen/2); j++) {   //xAllele is first half of genome
                    comparedXAllele += population[copmaredIndividual][j];
                }
                for (int j = (bitstringGenomeLen/2); j < (bitstringGenomeLen); j++) { //yAllele is second half of genome
                    comparedYAllele += population[copmaredIndividual][j];
                }

                double euclideanDistance = Math.sqrt( Math.pow(xAllele - comparedXAllele, 2) + Math.pow(yAllele - comparedYAllele, 2) * 1.0);
                if (euclideanDistance > maxEuclideanDistance) {
                    maxEuclideanDistance = euclideanDistance;
                }
            }
        }
        diversity = maxEuclideanDistance;

        return;
    }

    /* Discrete Recombination
        Chose discrete recombination so that diversity of
        genomes is not artificially lowered by producing
        children that only fall within the range of the parents
    */
    void discreteRecombination() {
        for (int i = 0; i < matingPool.size()/2; i++) {
            int parentOneIndex = matingPool.get(i*2);
            int parentTwoIndex = matingPool.get((i*2)+1);

            int[] parentOne = population[parentOneIndex].clone();
            int[] parentTwo = population[parentTwoIndex].clone();
            
            int numOfChildren = 2;
            int[][] children = new int[numOfChildren][bitstringGenomeLen];

            if (Math.random() < crossoverRate) {
                // children = crossoverFunc(parentOne, parentTwo);
                for (int n = 0; n < bitstringGenomeLen; n++) {
                    if (Math.random() < 0.5) {
                        children[0][n] = parentOne[n];
                        children[1][n] = parentTwo[n];
                    } else {
                        children[0][n] = parentTwo[n];
                        children[1][n] = parentOne[n];
                    }
                }
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

    void mutation() {
        for (int i = 0; i < childrenPool.length; i++) {
            childrenPool[i] = randomResetting(childrenPool[i]);
        }
    }
    int[] randomResetting(int[] toMutate) {
        for (int i = 0; i < bitstringGenomeLen; i++) {
            if (Math.random() < mutationRate) {
                toMutate[i] = rand.nextInt(8);
            }
        }

        return toMutate;
    }

    void uniformRandomSelection() {
        matingPool.clear();
        for (int i = 0; i < lambdaSize; i++) {
            matingPool.add(rand.nextInt(popSize));
        }
    }

    /* Mu, Lambda
        Originally tried to use this since the problem is multi-modal
        however I kept having issues with the elites not staying in
        the population... possibly a coding error but I ended up
        choosing mu + lambda instead
    */
    void muCommaLambda() {  // Only children can move onto next generation
        Map<Integer, Double> fitnessMap = new HashMap<Integer, Double>(lambdaSize);
        for (int i = 0; i < lambdaSize; i++) {
            fitnessMap.put(i, fitnessFunc(i, "children"));   // Needs to be of children
        }

        fitnessMap = MapUtil.sortByValue(fitnessMap); // Sort by fitness value (need map to retain original indexes)

        List<Integer> rankedFitness = new ArrayList<Integer>();
        rankedFitness = MapUtil.mapToList(fitnessMap);  // Get list from map to use to set population

        for (int i = 0; i < popSize; i++) {
            population[i] = childrenPool[rankedFitness.get(rankedFitness.size()-1-i)].clone();  // Weird indexing bc of rankedFitness ascending order
        }

        return;
    }
    
    /* Mu + Lambda
        Needed to keep elites in the population so that the population would
        converge on a solution rather than jumping around too much
    */
    void muPlusLambda() {  // Only children can move onto next generation
        Map<Integer, Double> fitnessMap = new HashMap<Integer, Double>(lambdaSize);
        int[][] tempChildrenPool = new int[popSize + lambdaSize][bitstringGenomeLen];
        for (int i = 0; i < popSize + lambdaSize; i++) {
            if (i < popSize) {
                tempChildrenPool[i] = population[i].clone();
            } else {
                tempChildrenPool[i] = childrenPool[i - popSize].clone();
            }
        }
        for (int i = 0; i < popSize + lambdaSize; i++) {
            if (i < popSize) {
                fitnessMap.put(i, fitnessFunc(i, "pop"));   // Needs to be of population
            } else {
                fitnessMap.put(i, fitnessFunc(i - popSize, "children"));   // Needs to be of children
            }
            candidateEvaluations++; // This code goes through each candidate once so its a good place to increment candidateEvaluations
        }

        fitnessMap = MapUtil.sortByValue(fitnessMap); // Sort by fitness value (need map to retain original indexes)

        List<Integer> rankedFitness = new ArrayList<Integer>();
        rankedFitness = MapUtil.mapToList(fitnessMap);  // Get list from map to use to set population

        for (int i = 0; i < popSize; i++) {
            population[i] = tempChildrenPool[rankedFitness.get(rankedFitness.size()-1-i)].clone();  // Weird indexing bc of rankedFitness ascending order
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
        while (terminationCondition) { // Convergence Termination 
            uniformRandomSelection();
            discreteRecombination();
            mutation();
            // muCommaLambda();
            muPlusLambda();
            
            fitnessStatistics();
            setDiversity();

            generationNumber++;
            printGenerationalStatistics();
            
            terminationCondition = (averageFitnessScore < 0.99999) && generationNumber < safetyLimit;
        }
    }

    List<String>  run_eightqueens() {
        initializePopulation();
        fitnessStatistics();
        printOneTimeHeader();
        setDiversity();
        printGenerationalStatistics();

        algorithmLogic();

        // printPopulation();
        checkTerminationString();

        printToOutputFile();

        return pythonTextString;
    }

    void checkTerminationString() {
        if (averageFitnessScore >= 0.99999) {
            terminationString = "Population has converged";
        } else if (generationNumber >= safetyLimit) {
            terminationString = "Safety limit of " + safetyLimit + " generations";
        } else {
            //default string
        }
        System.out.println(terminationString);
        // printPopulation();
    }

    void printToOutputFile() {
        try {
            Writer fileWriter = new FileWriter("eightqueens/output.txt", false); //overwrites file

            for (int i = 0; i < pythonTextString.size(); i++) {
                fileWriter.write(pythonTextString.get(i));
                if (i != pythonTextString.size() - 1) {
                    fileWriter.write(System.lineSeparator());
                }
            }

            fileWriter.close();

        } catch (IOException ex){
            System.out.println (ex.toString());
        }

    }
}
