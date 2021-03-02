/* William Presley CS 5146 */

/* Compile and output to text file
make rosenbrock_WilliamPresley
./rosenbrock_WilliamPresley > rosenbrock_WilliamPresley_sampleOutput.txt
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include <vector>

using namespace std;

void initializePopulation(vector< vector<bool> > &pop) {
    int numOfRows = pop.size();
    int numOfCols = pop[0].size();


    //srand(time(NULL));
    for (int i = 0; i < numOfRows; i++) {
        for (int n = 0; n < numOfCols; n++) {
            pop[i][n] = rand()%2;
        }
    }
}

void printPopulation(vector< vector<bool> > &pop) {
    cout << "Population:"<<endl;
    for (int i = 0; i < pop.size(); i++) {
        for (int n = 0; n < pop[0].size(); n++) {
            cout << pop[i][n];
        }
        cout <<endl;
    }
}
void printIndividual(vector< vector<bool> > &pop, int individual) {
    for (int i = 0; i < pop[0].size(); i++) {
        cout << pop[individual][i];
    }
}

double fitnessFunc(vector< vector<bool> > &pop, int individual) {
    double fitnessScore = 0.0;
    int xAllele = 0;
    int yAllele = 0;

    for (int i = 0; i < (pop[0].size()/2); i++) {   //xAllele is first half of genome
        xAllele = xAllele + (pow(2,(((pop[0].size()/2)-1) - i)) * pop[individual][i]);
    }
    for (int i = (pop[0].size()/2); i < (pop[0].size()); i++) { //yAllele is second half of genome
        yAllele = yAllele + (pow(2,((pop[0].size()-1) - i)) * pop[individual][i]);
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
void fitnessStatistics(vector< vector<bool> > &pop, double &highestFitnessScore, double &averageFitnessScore) {
    highestFitnessScore = 0.0;
    averageFitnessScore = 0.0;
    int fittestIndividualIndex = 0;
    for (int i = 0; i < pop.size(); i++) {
        double currentIndividualsFitness = fitnessFunc(pop, i);
        averageFitnessScore += currentIndividualsFitness;
        if (highestFitnessScore < currentIndividualsFitness) {
            highestFitnessScore = currentIndividualsFitness;
            fittestIndividualIndex = i;
        }
    }

    averageFitnessScore = averageFitnessScore / pop.size();

    return;
}

double getPercentOfIdenticalGenomes(vector< vector<bool> > &pop) {
    double percentOfIdenticalGenomes = 0.0;
    vector<int> matchedIndexes;
    
    for (int i = 0; i < pop.size(); i++) {
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
        for (int j = i + 1; j < pop.size(); j++) {
            if(pop[i] == pop[j]) {
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

    percentOfIdenticalGenomes = (static_cast<double>(matchedIndexes.size()) / pop.size()) * 100;

    return percentOfIdenticalGenomes;
}

vector< vector<bool> > crossoverFunc(vector< vector<bool> > &pop, int parent1Index, int parent2Index, int crossoverPoint, int numOfChildren, double mutationRate, double crossoverRate) {
    vector< vector<bool> > children( numOfChildren , vector<bool> (pop[0].size(), 0));
    bool doCrossover = 0;
    if ((static_cast<double>(rand()) / RAND_MAX) > (1 - crossoverRate)) {
        doCrossover = 1;
    }

    for (int i = 0; i < pop[0].size(); i++) {
        if (doCrossover) {  //  CROSSOVER IMPLEMENTATION
            if (i < crossoverPoint) {
                children[0][i] = pop[parent1Index][i];
                children[1][i] = pop[parent2Index][i];
            } else {
                children[0][i] = pop[parent2Index][i];
                children[1][i] = pop[parent1Index][i];
            }
        } else {
            children[0][i] = pop[parent1Index][i];
            children[1][i] = pop[parent2Index][i];
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

void rouletteWheelSelection(vector< vector<bool> > &pop, double mutationRate, double crossoverRate) {   // Best interpretation of book's explanation that terminates...
    vector< vector<bool> > newPop = pop;

    vector<double> fitnessVector;
    for (int n = 0; n < pop.size(); n++) {
        fitnessVector.insert(fitnessVector.end(), fitnessFunc(pop, n)); //gets vector of fitness of each individual
    }

    for (int currentMember = 0; currentMember < pop.size()/2; currentMember++) { //chosing 50 to have parents with 2 kids
        double r = static_cast<double>(rand()) / RAND_MAX;
        int i = 0;

        while (fitnessVector[i] < r) {
            i++;
            if (i >= pop.size()-1) {
                break;
            }
        }

        int crossoverPoint = (rand() % 30) + 1; // range from 1-30 otherwise its just two cases of asexual reproduction of parents
        int numOfChildren = 2;
        vector< vector<bool> > children = crossoverFunc(pop, currentMember, i, crossoverPoint, numOfChildren, mutationRate, crossoverRate);
        for (int j = 0; j < children.size(); j++) {
            newPop[(currentMember * 2) + j] = children[j];
        }

    }

    pop = newPop;

    return;
}

int main() {
    srand(time(NULL));  // Initializing random for crossover and population initialization
    int safetyLimit = 20000;
/* One-time header information */
    string problemName = "rosenbrock";
    int popSize = 200;
    int bitstringGenomeLen = 16;
    double mutationRate = 0.005;
    double crossoverRate = 0.9;
    cout << problemName << " " << popSize << " " << bitstringGenomeLen << " " << mutationRate << " " << crossoverRate <<endl<<endl;

/* Iteration information */
    int generationNumber = 0;
    double highestFitnessScore = 0.0;
    double averageFitnessScore = 0.0;
    double percentOfIdenticalGenomes = 0.0;

/* More initializing */
    string terminationString = "Default termination string.";

    vector< vector<bool> > population( popSize , vector<bool> (bitstringGenomeLen, 0)); // Population declaration
    initializePopulation(population);   // Randomize the population's individuals
    
    fitnessStatistics(population, highestFitnessScore, averageFitnessScore);
    percentOfIdenticalGenomes = getPercentOfIdenticalGenomes(population);
    cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes <<endl;



/* Begin algorithm logic */
    while (averageFitnessScore < 1.0 && generationNumber < safetyLimit && (highestFitnessScore < 1.0 || averageFitnessScore < .90 || percentOfIdenticalGenomes < 90)) { // Convergence Termination 
        rouletteWheelSelection(population, mutationRate, crossoverRate);
        
        fitnessStatistics(population, highestFitnessScore, averageFitnessScore);
        percentOfIdenticalGenomes = getPercentOfIdenticalGenomes(population);

        generationNumber++;
        cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes << "%" <<endl;
    }

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

    return 0;
}