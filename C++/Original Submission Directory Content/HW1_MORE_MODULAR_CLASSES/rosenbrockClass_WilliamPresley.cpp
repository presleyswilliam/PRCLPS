/* William Presley CS 5146 */

/* Compile and output to text file
make rosenbrockClass_WilliamPresley
./rosenbrockClass_WilliamPresley > rosenbrockClass_WilliamPresley_sampleOutput.txt
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include <vector>

using namespace std;

class Population {
  public:

    int safetyLimit;

    /* One-time header information */
    string problemName;
    int popSize;
    int bitstringGenomeLen;
    double mutationRate;
    double crossoverRate;

    /* Iteration information */
    int generationNumber;
    double highestFitnessScore;
    double averageFitnessScore;
    double percentOfIdenticalGenomes;

    string terminationString;

    vector< vector<bool> > population;

    Population() {
        safetyLimit = 20000;
        /* One-time header information */
        problemName = "rosenbrock";
        popSize = 200;
        bitstringGenomeLen = 16;
        mutationRate = 0.005;
        crossoverRate = 0.9;
        
        /* Iteration information */
        generationNumber = 0;
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        percentOfIdenticalGenomes = 0.0;

        terminationString = "Default termination string.";

        vector< vector<bool> > tempPopulation( popSize , vector<bool> (bitstringGenomeLen, 0)); // Population declaration
        population = tempPopulation;
    }

    void printOneTimeHeader() {  // One time header information
      cout << problemName << " " << popSize << " " << bitstringGenomeLen << " " << mutationRate << " " << crossoverRate <<endl<<endl;
    }
    void printGenerationalStatistics() {
        cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes << "%" <<endl;
    }

    void initializePopulation() {
        int numOfRows = population.size();
        int numOfCols = population[0].size();


        //srand(time(NULL));
        for (int i = 0; i < numOfRows; i++) {
            for (int n = 0; n < numOfCols; n++) {
                population[i][n] = rand()%2;
            }
        }
    }

    void printPopulation() {
        cout << "Population:"<<endl;
        for (int i = 0; i < population.size(); i++) {
            for (int n = 0; n < population[0].size(); n++) {
                cout << population[i][n];
            }
            cout <<endl;
        }
    }
    void printIndividual(int individual) {
        for (int i = 0; i < population[0].size(); i++) {
            cout << population[individual][i];
        }
    }

    double fitnessFunc(int individual) {
        double fitnessScore = 0.0;
        int xAllele = 0;
        int yAllele = 0;

        for (int i = 0; i < (population[0].size()/2); i++) {   //xAllele is first half of genome
            xAllele = xAllele + (pow(2,(((population[0].size()/2)-1) - i)) * population[individual][i]);
        }
        for (int i = (population[0].size()/2); i < (population[0].size()); i++) { //yAllele is second half of genome
            yAllele = yAllele + (pow(2,((population[0].size()-1) - i)) * population[individual][i]);
        }

        double objFunc = pow(1.0 - xAllele, 2.0) + (100.0*pow((yAllele - pow(xAllele,2.0)), 2.0));
        if (objFunc == 0) {
            fitnessScore = 2.0 - 0.0;
        } else if (objFunc >= 1) {
            fitnessScore = 1.0/objFunc;
        } else { 
            fitnessScore = (1.0-objFunc) + 1;
        }

        fitnessScore = fitnessScore/2.0;

        return fitnessScore;
    }
    void fitnessStatistics() {
        highestFitnessScore = 0.0;
        averageFitnessScore = 0.0;
        int fittestIndividualIndex = 0;
        for (int i = 0; i < population.size(); i++) {
            double currentIndividualsFitness = fitnessFunc(i);
            averageFitnessScore += currentIndividualsFitness;
            if (highestFitnessScore < currentIndividualsFitness) {
                highestFitnessScore = currentIndividualsFitness;
                fittestIndividualIndex = i;
            }
        }

        averageFitnessScore = averageFitnessScore / population.size();

        return;
    }

    double getPercentOfIdenticalGenomes() {
        double percentOfIdenticalGenomes = 0.0;
        vector<int> matchedIndexes;
        
        for (int i = 0; i < population.size(); i++) {
            bool isExistingMatch = 0;
            for (int n = 0; n < matchedIndexes.size(); n++) {
                if (i == matchedIndexes[n]) {   // If already matched then continue outer loop
                    isExistingMatch = 1;
                    break;
                }
            }
            if (isExistingMatch) {
                continue;
            }


            bool isiAlreadyMatched = false;
            for (int j = i + 1; j < population.size(); j++) {
                if(population[i] == population[j]) {
                    if (isiAlreadyMatched) {
                        matchedIndexes.insert(matchedIndexes.end(), j);
                    } else {
                        matchedIndexes.insert(matchedIndexes.end(), i);
                        matchedIndexes.insert(matchedIndexes.end(), j);
                        isiAlreadyMatched = true;
                    }
                }
            }

        }

        percentOfIdenticalGenomes = (static_cast<double>(matchedIndexes.size()) / population.size()) * 100;

        return percentOfIdenticalGenomes;
    }

    vector< vector<bool> > crossoverFunc(int parent1Index, int parent2Index, int crossoverPoint, int numOfChildren) {
        vector< vector<bool> > children( numOfChildren , vector<bool> (population[0].size(), 0));
        bool doCrossover = 0;
        if ((static_cast<double>(rand()) / RAND_MAX) > (1 - crossoverRate)) {
            doCrossover = 1;
        }

        for (int i = 0; i < population[0].size(); i++) {
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
            vector<double> childMutationChance(numOfChildren, 0);
            for (int c = 0; c < childMutationChance.size(); c++) {  // Populate random chance of mutation for each bit
                childMutationChance[c] = static_cast<double>(rand()) / RAND_MAX;
            }
            for (int c = 0; c < childMutationChance.size(); c++) {  // MUTATION IMPLEMENTATION - CHANCE OF MUTATION APPLIED TO EACH CHROMOSOME
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

    void rouletteWheelSelection() {   // Best interpretation of book's explanation that terminates...
        vector< vector<bool> > newPop = population;

        vector<double> fitnessVector;
        for (int n = 0; n < population.size(); n++) {
            fitnessVector.insert(fitnessVector.end(), fitnessFunc(n)); //gets vector of fitness of each individual
        }

        for (int currentMember = 0; currentMember < population.size()/2; currentMember++) { //chosing 50 to have parents with 2 kids
            double r = static_cast<double>(rand()) / RAND_MAX;
            int i = 0;

            while (fitnessVector[i] < r) {
                i++;
                if (i >= population.size()-1) {
                    break;
                }
            }

            int crossoverPoint = (rand() % 30) + 1; // range from 1-30 otherwise its just two cases of asexual reproduction of parents
            int numOfChildren = 2;
            vector< vector<bool> > children = crossoverFunc(currentMember, i, crossoverPoint, numOfChildren);
            for (int j = 0; j < children.size(); j++) {
                newPop[(currentMember * 2) + j] = children[j];
            }

        }

        population = newPop;

        return;
    }

    void algorithmLogic() {
        /* Begin algorithm logic */
        while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .90 || percentOfIdenticalGenomes < 90)) { // Convergence Termination 
            rouletteWheelSelection();
            
            fitnessStatistics();
            percentOfIdenticalGenomes = getPercentOfIdenticalGenomes();

            generationNumber++;
            printGenerationalStatistics();
        }
    }

    void checkTerminationString() {
        if (averageFitnessScore >= 1.0) {
            terminationString = "Population has converged";
        } else if (generationNumber >= safetyLimit) {
            terminationString = "Safety limit of " + to_string(safetyLimit) + " generations";
        } else if (highestFitnessScore >= 1.0 && averageFitnessScore > .90 && percentOfIdenticalGenomes >= 90) {
            terminationString = "Population has MOSTLY converged";
        } else {
            //default string
        }
        cout <<endl<< terminationString <<endl;
    }

};

int main() {
    srand(time(NULL));
    Population population;
    population.initializePopulation();
    population.fitnessStatistics();
    population.getPercentOfIdenticalGenomes();
    population.printGenerationalStatistics();
    population.algorithmLogic();
    population.checkTerminationString();


    return 0;
}