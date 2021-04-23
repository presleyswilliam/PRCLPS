import matplotlib.pyplot as plt
import numpy as np
import sys

#import warnings
# warnings.simplefilter("ignore", UserWarning)  # Warning is for if xticks aren't set before xticklabels are set

class Graphing:
    def __init__(self, filePath, paramName):
        self.candidateEvaluations = []
        self.parameterVal = []
        self.paramName = paramName

        with open(filePath) as f:
            for line in f:  # for i, line in enumerate(f):
                line = line.split(' ')
                self.candidateEvaluations.append(line[0])
                self.parameterVal.append(line[1])

        # Not needed anymore
        # # Delete first and last line of file i.e. initial message and termination string
        # for key in vars(self).keys():
        #     del vars(self)[key][0]
        #     del vars(self)[key][-1]

        # Convert from string to int after getting rid of non-data lines
        for i in range(0, len(self.candidateEvaluations)):
            self.candidateEvaluations[i] = float(self.candidateEvaluations[i])
            self.parameterVal[i] = float(self.parameterVal[i])

    def printCandidateEvaluations(self):
        print("Candidate Evaluations: ")
        print(self.candidateEvaluations)
        return self
    def printParameterVal(self):
        print("Parameter Values: ")
        print(self.parameterVal)
        return self
        
    def plotStats(self):
        fig, ax = plt.subplots(nrows=1, ncols=1)
        ax.plot(self.parameterVal, self.candidateEvaluations, label="Candidate Evaluations")
        ax.set_yticks(np.linspace(self.candidateEvaluations[0], self.candidateEvaluations[len(self.candidateEvaluations)-1], 11))
        ax.set_ylabel("Candidate Evaluations")
        ax.set_xlabel("Parameter Value")
        # ax[0].set_title("Performance Graphs")

        # ax[1].plot(self.parameterVal, self.candidateEvaluations, label="Candidate Evaluations")
        # ax[1].set_yticks(np.linspace(self.candidateEvaluations[0], self.candidateEvaluations[len(self.candidateEvaluations)-1], 11))#int(len(self.diversity)/3)))
        # ax[1].set_xlabel("Parameter Value")
        # ax[1].set_ylabel("Candidate Evaluations")
        # ax[1].set_title("Performance Graphs")

        
        plt.draw()
        if (len(self.parameterVal) > 50):
            ax.set_xticks(self.parameterVal[::10]) # This prevents UserWarning from .set_xticklabels()
        else:
            ax.set_xticks(self.parameterVal) # This prevents UserWarning from .set_xticklabels()
        ax.set_xticklabels(ax.get_xticks(), rotation=80)
        ax.legend(loc="upper left")

        plt.suptitle(self.paramName)
        plt.tight_layout()
        plt.show()



def main():
    eightQueensMR = Graphing("eightqueens/varyingMutationRate.txt", "Eight Queens - Mutation Rate")
    # himmelblau.printGeneration().printAverageFitness().printBestFitness().printDiversity()
    eightQueensMR.plotStats()

    # eightQueensCR = Graphing("eightqueens/varyingCrossoverRate.txt", "Eight Queens - Crossover Rate")
    # # himmelblau.printGeneration().printAverageFitness().printBestFitness().printDiversity()
    # eightQueensCR.plotStats()

    eightQueensLS = Graphing("eightqueens/varyingLambdaSize.txt", "Eight Queens - Lambda Size")
    # himmelblau.printGeneration().printAverageFitness().printBestFitness().printDiversity()
    eightQueensLS.plotStats()

if __name__ == "__main__":
    main()