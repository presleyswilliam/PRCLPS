/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /himmelblau/himmelblau_WilliamPrelsey.java
run: java -classpath . /himmelblau/himmelblau_WilliamPresley
*/

package himmelblau;

public class himmelblau_WilliamPresley {
    public static void main(String args[]) {
        Population pop = new Population();
        pop.run_himmelblau();

        // //Doesn't show output/errors in terminal
        // CMD.runSingleCommand("./himmelblau/runPython.sh");
    }
}
