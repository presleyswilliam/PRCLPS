/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /eightqueens/eightqueens_WilliamPrelsey.java
run: java -classpath . /eightqueens/eightqueens_WilliamPresley
*/

package eightqueens;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class eightqueens_WilliamPresley {
    public static void main(String args[]) {
        // Population pop = new Population();
        // pop.run_eightqueens();

        int numOfRunsToAvg = 25;

        double minMutation = 0.0025;
        double maxMutation = 0.03;
        double mutationStep = maxMutation / (maxMutation/minMutation);

        double minCrossover = 0.1;
        double maxCrossover = 1.0;
        double crossoverStep = maxCrossover / (maxCrossover/minCrossover);

        int minLSize = 10;
        int maxLSize = 150;
        int LStep = maxLSize / (maxLSize/minLSize);

        for (double i = minMutation-minMutation; i <= maxMutation; i += mutationStep) { //mutationRate
            List<Integer> toAvg = new ArrayList<>(10);
            for (int j = 0; j < numOfRunsToAvg; j++) {  //getting numOfRunsToAvg values to avg

                Population pop = new Population(i, 0.05, 100, "mutationRate");
                List<String> pythonTextString = pop.run_eightqueens();

                toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == numOfRunsToAvg-1) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("eightqueens/varyingMutationRate.txt", true); //overwrites file
            
                        
                        fileWriter.write(avgToWrite + " " + i);

                        if (i != maxMutation) {
                            fileWriter.write(System.lineSeparator());
                        }
                        
            
                        fileWriter.close();
            
                    } catch (IOException ex){
                        System.out.println (ex.toString());
                    }
                }
            }
        }

        // for (double i = minCrossover-minCrossover; i <= maxCrossover; i += crossoverStep) { //crossover rate
        //     List<Integer> toAvg = new ArrayList<>(10);
        //     for (int j = 0; j < numOfRunsToAvg; j++) {  //getting numOfRunsToAvg values to avg

        //         Population pop = new Population(0.01, i, 100, "crossoverRate");
        //         List<String> pythonTextString = pop.run_eightqueens();

        //         toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

        //         if (j == numOfRunsToAvg-1) {
        //             double avgToWrite = 0;
        //             for (int n = 0; n < toAvg.size(); n++) {
        //                 avgToWrite += toAvg.get(n);
        //             }
        //             avgToWrite = avgToWrite / toAvg.size();

        //             try {
        //                 Writer fileWriter = new FileWriter("eightqueens/varyingCrossoverRate.txt", true); //overwrites file
            
                        
        //                 fileWriter.write(avgToWrite + " " + i);

        //                 if (i != maxCrossover-crossoverStep) {
        //                     fileWriter.write(System.lineSeparator());
        //                 }
                        
            
        //                 fileWriter.close();
            
        //             } catch (IOException ex){
        //                 System.out.println (ex.toString());
        //             }
        //         }
        //     }
        // }

        for (int i = minLSize-minLSize; i <= maxLSize; i += LStep) { //lambda size rate
            List<Double> toAvg = new ArrayList<>(10);
            for (int j = 0; j < numOfRunsToAvg; j++) {  //getting numOfRunsToAvg values to avg

                Population pop = new Population(0.01, 0.05, i, "lambdaSize");
                List<String> pythonTextString = pop.run_eightqueens();

                toAvg.add(j, Double.parseDouble(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == numOfRunsToAvg-1) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("eightqueens/varyingLambdaSize.txt", true); //overwrites file
            
                        
                        fileWriter.write(avgToWrite + " " + i);

                        if (i != maxLSize) {
                            fileWriter.write(System.lineSeparator());
                        }
                        
            
                        fileWriter.close();
            
                    } catch (IOException ex){
                        System.out.println (ex.toString());
                    }
                }
            }
        }

        // //Doesn't show output/errors in terminal
        // CMD.runSingleCommand("./eightqueens/runPython.sh");
    }
}