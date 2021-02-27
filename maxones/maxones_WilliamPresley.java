package maxones;

public class maxones_WilliamPresley {
    public static void main(String args[]) {
        Population pop = new Population();
        pop.initializePopulation();
        pop.fitnessStatistics();
        pop.getPercentOfIdenticalGenomes();
        pop.printGenerationalStatistics();
        pop.algorithmLogic();
        pop.checkTerminationString();
        pop.printPopulation();
    }
}
