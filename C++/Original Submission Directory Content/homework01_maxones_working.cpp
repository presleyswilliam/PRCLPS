/* William Presley CS 5146 */

/* Compile and output to text file
make homework01_maxones
./homework01_maxones > homework01_maxones.txt
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>

#include <vector>

using namespace std;

void initializePopulation(vector< vector<bool> > &pop) {
    int numOfRows = pop.size();
    int numOfCols = pop[0].size();
    //cout << "Rows: " << numOfRows <<endl;
    //cout << "Cols: " << numOfCols <<endl;


    //srand(time(NULL));
    for (int i = 0; i < numOfRows; i++) {
        for (int n = 0; n < numOfCols; n++) {
            pop[i][n] = rand()%2;
            //cout << pop[i][n];
        }
        //cout <<endl;
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
    double fitnessScore = 0;
    for (int i = 0; i < pop[0].size(); i++) {
        if (pop[individual][i]) {
            fitnessScore++;
        }
    }

    fitnessScore = fitnessScore / (pop[0].size());

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

    // cout << "Fittest Individual: ";
    // for (int n = 0; n < pop[0].size(); n++) {
    //     cout << pop[fittestIndividualIndex][n];
    // }
    // cout <<endl;

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
                //cout << "already counted: " << i <<endl;
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

    // //For debugging purposes...
    // printPopulation(pop); cout << endl;
    // for(int w = 0; w < matchedIndexes.size(); w++){
    //     cout << matchedIndexes[w] << " ";
    // }
    // cout <<endl;

    percentOfIdenticalGenomes = (static_cast<double>(matchedIndexes.size()) / pop.size()) * 100;
    // cout << "MatchedIndexes.size() =  " << matchedIndexes.size() <<endl;
    // cout << "pop.size() =  " << pop.size() <<endl;
    // cout << "percentageOfIdenticalGenomes =  " << percentOfIdenticalGenomes <<endl;


    return percentOfIdenticalGenomes;
}

vector< vector<bool> > crossoverFunc(vector< vector<bool> > &pop, int parent1Index, int parent2Index, int crossoverPoint, int numOfChildren, double mutationRate, double crossoverRate) {  //PAGE 83 OF BOOK (97 of pdf) FOR ALGORITHM
    // cout << "crossoverFunc" <<endl;
    vector< vector<bool> > children( numOfChildren , vector<bool> (pop[0].size(), 0));
    bool doCrossover = 0;
    if ((static_cast<double>(rand()) / RAND_MAX) > (1 - crossoverRate)) {
        doCrossover = 1;
    }

//  //Printing parents and resulting children
//     //Prints parent 1 and 2 for debugging
//     for (int p = 0; p < 2; p++) {
//         cout << "Parent " << p << ": ";
//         for (int i = 0; i < pop[0].size(); i++) {
//             if (p == 0) {
//                 //pop[parent1Index][i] = 0; //Used for testing to see clear 0s and 1s
//                 cout << pop[parent1Index][i];
//             } else {
//                 //pop[parent2Index][i] = 1; //Used for testing to see clear 0s and 1s
//                 cout << pop[parent2Index][i];
//             }
//         }
//         cout <<endl;
//     }

    for (int i = 0; i < pop[0].size(); i++) {
        vector<double> childMutationChance(numOfChildren, 0);
        for (int c = 0; c < childMutationChance.size(); c++) {  // Populate random chance of mutation for each bit
            childMutationChance[c] = static_cast<double>(rand()) / RAND_MAX;
        }

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

        for (int c = 0; c < childMutationChance.size(); c++) {  // MUTATION IMPLEMENTATION
            if (childMutationChance[c] > (1 - mutationRate)) {
                if (children[c][i] == 1) {
                    children[c][i] = 0;
                } else {
                    children[c][i] = 1;
                }
            }
        }

    }

    // cout <<endl<< "Crossover Point: " << crossoverPoint <<endl<<endl;

    // //Prints the child vectors
    // for (int c = 0; c < 2; c++) {
    //     cout << "Child " << c << ": ";
    //     for (int i = 0; i < children[0].size(); i++) {
    //         if (c == 0) {
    //             cout << children[0][i];
    //         } else {
    //             cout << children[1][i];
    //         }
    //     }
    //     cout <<endl;
    // }
    // cout <<endl;


    return children;
}

void rouletteWheelSelection(vector< vector<bool> > &pop, double mutationRate, double crossoverRate) {  //PAGE 83 OF BOOK (97 of pdf) FOR ALGORITHM
    // cout << "rouletteWheelSelection" <<endl;
    vector< vector<bool> > newPop = pop;

    vector<double> fitnessVector;
    for (int n = 0; n < pop.size(); n++) {
        fitnessVector.insert(fitnessVector.end(), fitnessFunc(pop, n)); //gets vector of fitness of each individual
    }

    for (int currentMember = 0; currentMember < 50; currentMember++) { //chosing 50 to have parents with 2 kids
        double r = static_cast<double>(rand()) / RAND_MAX;
        int i = 0;

        while (fitnessVector[i] < r) {
            i++;
            if (i >= pop.size()-1) {
                //do something for asexual repro
                break;
            }
        }

        int crossoverPoint = (rand() % 30) + 1; // range from 1-30 otherwise its just two cases of asexual reproduction
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
    int safetyLimit = 100000;
/* One-time header information */
    string problemName = "maxones";
    int popSize = 100;
    int bitstringGenomeLen = 32;
    double mutationRate = 0.01;
    double crossoverRate = 0.5;
    cout << problemName << " " << popSize << " " << bitstringGenomeLen << " " << mutationRate << " " << crossoverRate <<endl<<endl;

/* Iteration information */
    int generationNumber = 0;
    double highestFitnessScore = 0.0;
    double averageFitnessScore = 0.0;
    double percentOfIdenticalGenomes = 0.0;
    // cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes <<endl<<endl;

/* More initializing */
    string terminationString = "Default termination string.";

    vector< vector<bool> > population( popSize , vector<bool> (bitstringGenomeLen, 0)); // Population declaration
    initializePopulation(population);   // Randomize the population's individuals
    
    fitnessStatistics(population, highestFitnessScore, averageFitnessScore);
    percentOfIdenticalGenomes = getPercentOfIdenticalGenomes(population);
    cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes <<endl;



/* Begin algorithm logic */
    while (highestFitnessScore < 1.0 && generationNumber < safetyLimit) {
        rouletteWheelSelection(population, mutationRate, crossoverRate);
        
        fitnessStatistics(population, highestFitnessScore, averageFitnessScore);
        percentOfIdenticalGenomes = getPercentOfIdenticalGenomes(population);

        generationNumber++;
        // cout << "Generation: " << generationNumber << " | " 
        // << "Highest: " << highestFitnessScore << " | " 
        // << "Average: " << averageFitnessScore << " | " 
        // << "Percent of Identical: " << percentOfIdenticalGenomes << "%" <<endl<<endl;
        cout << generationNumber << " " << highestFitnessScore << " " << averageFitnessScore << " " << percentOfIdenticalGenomes << "%" <<endl;
    }

    if (highestFitnessScore >= 1.0) {
        terminationString = "Population has converged";
    } else if (generationNumber >= safetyLimit) {
        terminationString = "Safety limit of " + to_string(safetyLimit) + " generations";
    } else {
        //default string
    }
    cout <<endl<< terminationString <<endl;

    return 0;
}