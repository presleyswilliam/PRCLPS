/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /maxones/maxones_WilliamPrelsey.java
run: java -classpath . /maxones/maxones_WilliamPresley
*/

package maxones;

public class maxones_WilliamPresley {
    public static void main(String args[]) {
        Population pop = new Population();
        pop.run_maxones();
    }
}
