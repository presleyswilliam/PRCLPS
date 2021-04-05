/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /himmelblau/himmelblau_WilliamPrelsey.java
run: java -classpath . /himmelblau/himmelblau_WilliamPresley
*/

package himmelblau;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class himmelblau_WilliamPresley {
    public static void main(String args[]) {
        // Population pop = new Population(100);
        // pop.run_himmelblau();

        for (int i = 15; i < 101; i++) { //lambdaSize
            List<Integer> toAvg = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {  //getting 10 values to avg

                Population pop = new Population(i, 10);
                List<String> pythonTextString = pop.run_himmelblau();

                toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == 9) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("himmelblau/varyingLambdaSize.txt", true); //overwrites file
            
                        
                        fileWriter.write(avgToWrite + " " + i);

                        if (i != 100) {
                            fileWriter.write(System.lineSeparator());
                        }
                        
            
                        fileWriter.close();
            
                    } catch (IOException ex){
                        System.out.println (ex.toString());
                    }
                }
            }
        }

        for (int i = 1; i < 21; i++) { //bitstring size
            List<Integer> toAvg = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {  //getting 10 values to avg

                Population pop = new Population(100, i);
                List<String> pythonTextString = pop.run_himmelblau();

                toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == 9) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("himmelblau/varyingBitStringLen.txt", true); //overwrites file
            
                        
                        fileWriter.write(avgToWrite + " " + i);

                        if (i != 20) {
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
        // CMD.runSingleCommand("./himmelblau/runPython.sh");
    }
}
