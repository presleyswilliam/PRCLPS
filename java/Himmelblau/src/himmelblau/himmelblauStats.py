import matplotlib.pyplot as plt
import sys

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
                self.diversity.append(line[3].split('%')[0])

        # Delete first and last line of file i.e. initial message and termination string
        for key in vars(self).keys():
            del vars(self)[key][0]
            del vars(self)[key][-1]
        

def main():
    himmelblau = Graphing("output.txt")
    # print(himmelblau.generation)
    # print(himmelblau.averageFitness)
    # print(himmelblau.bestFitness)
    # print(himmelblau.diversity)

    fig, ax = plt.subplots(nrows=2, ncols=1)
    ax[0].plot(himmelblau.generation, himmelblau.averageFitness)
    ax[0].plot(himmelblau.generation, himmelblau.bestFitness)
    ax[1].plot(himmelblau.generation, himmelblau.diversity)
    plt.xlabel("Generations")
    ax[0].set_xticks(range(0, len(himmelblau.generation), 5))
    ax[1].set_xticks(range(0, len(himmelblau.generation), 5))
    ax[0].set_yticks(range(0, len(himmelblau.generation), 5))
    ax[1].set_yticks(range(0, len(himmelblau.generation), 5))
    plt.ylabel("Best fitness")
    plt.title("Performance Graphs")
    plt.show()

if __name__ == "__main__":
    main()