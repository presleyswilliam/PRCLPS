/*
William Presley
To run and compile from directory above ('-classpath' can be shortened to '-cp'):
compile: javac -classpath . /rosenbrock/rosenbrock_WilliamPrelsey.java
run: java -classpath . /rosenbrock/rosenbrock_WilliamPresley
*/

package rosenbrock;

public class rosenbrock_WilliamPresley {
    public static void main(String args[]) {
        Population pop = new Population();
        pop.run_rosenbrock();
    }
}
