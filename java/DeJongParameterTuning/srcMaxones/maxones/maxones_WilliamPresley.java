/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /maxones/maxones_WilliamPrelsey.java
run: java -classpath . /maxones/maxones_WilliamPresley
*/

package maxones;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class maxones_WilliamPresley {
    public static void main(String args[]) {
        // Population pop = new Population(100, 32);
        // pop.run_maxones();

        for (int i = 15; i < 101; i++) { //muSize
            List<Integer> toAvg = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {  //getting 10 values to avg

                Population pop = new Population(i, 10);
                List<String> pythonTextString = pop.run_maxones();

                toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == 9) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("maxones/varyingMuSize.txt", true); //overwrites file
            
                        
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

        for (int i = 3; i < 33; i++) { //bitstring size
            List<Integer> toAvg = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {  //getting 10 values to avg

                Population pop = new Population(100, i);
                List<String> pythonTextString = pop.run_maxones();

                toAvg.add(j, Integer.parseInt(pythonTextString.get(pythonTextString.size()-1)) );

                if (j == 9) {
                    double avgToWrite = 0;
                    for (int n = 0; n < toAvg.size(); n++) {
                        avgToWrite += toAvg.get(n);
                    }
                    avgToWrite = avgToWrite / toAvg.size();

                    try {
                        Writer fileWriter = new FileWriter("maxones/varyingBitStringLen.txt", true); //overwrites file
            
                        
                        fileWriter.write(avgToWrite + " " + i);

                        if (i != 32) {
                            fileWriter.write(System.lineSeparator());
                        }
                        
            
                        fileWriter.close();
            
                    } catch (IOException ex){
                        System.out.println (ex.toString());
                    }
                }
            }
        }


    }
}
