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
        // CMD.runSingleCommand("ls"); //call python graphing program
        CMD.runSingleCommand("python3 himmelblau/himmelblauStats.py");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
