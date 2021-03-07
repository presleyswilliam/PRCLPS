import matplotlib.pyplot as plt
import numpy as np
import sys

#import warnings
# warnings.simplefilter("ignore", UserWarning)  # Warning is for if xticks aren't set before xticklabels are set

class Graphing:
    def __init__(self, filePath):
        self.generation = []
        self.averageFitness = []
        self.bestFitness = []
        self.diversity = []

        with open(filePath) as f:
            for line in f:  # for i, line in enumerate(f):
                line = line.split(' ')
                self.generation.append(line[0])
                self.averageFitness.append(line[2])
                self.bestFitness.append(line[1])
                self.diversity.append(line[3])

        # Not needed anymore
        # # Delete first and last line of file i.e. initial message and termination string
        # for key in vars(self).keys():
        #     del vars(self)[key][0]
        #     del vars(self)[key][-1]

        # Convert from string to int after getting rid of non-data lines
        for i in range(0, len(self.generation)):
            self.generation[i] = float(self.generation[i])
            self.averageFitness[i] = float(self.averageFitness[i])
            self.bestFitness[i] = float(self.bestFitness[i])
            self.diversity[i] = float(self.diversity[i])

    def printGeneration(self):
        print("Generation: ")
        print(self.generation)
        return self
    def printAverageFitness(self):
        print("Average Fitness: ")
        print(self.averageFitness)
        return self
    def printBestFitness(self):
        print("Best Fitness: ")
        print(self.bestFitness)
        return self
    def printDiversity(self):
        print("Diversity: ")
        print(self.diversity)
        return self
        
    def plotStats(self):
        fig, ax = plt.subplots(nrows=2, ncols=1)
        ax[0].plot(self.generation, self.averageFitness, label="Average Fitness")
        ax[0].plot(self.generation, self.bestFitness, label="Best Fitness")
        # ax[0].set_yticks(np.linspace(0, 1, 11))
        ax[0].set_ylabel("Fitness")
        # ax[0].set_title("Performance Graphs")

        ax[1].plot(self.generation, self.diversity, label="Diversity")
        # ax[1].set_yticks(np.linspace(self.diversity[0], self.diversity[len(self.diversity)-1], 11))#int(len(self.diversity)/3)))
        ax[1].set_xlabel("Generations")
        ax[1].set_ylabel("Diversity Metric")
        # ax[1].set_title("Performance Graphs")

        for i, axVal in enumerate(ax):
            plt.draw()
            ax[i].set_xticks(self.generation) # This prevents UserWarning from .set_xticklabels()
            ax[i].set_xticklabels(ax[i].get_xticks(), rotation=80)
            ax[i].legend(loc="upper left")

        plt.suptitle("Performance Graphs")
        plt.tight_layout()
        plt.show()



def main():
    himmelblau = Graphing("output.txt")
    # himmelblau.printGeneration().printAverageFitness().printBestFitness().printDiversity()
    himmelblau.plotStats()

if __name__ == "__main__":
    main()