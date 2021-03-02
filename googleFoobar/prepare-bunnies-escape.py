# Maze where you find best path to solution but are allowed to break a single wall element

def solution(map):
    # Your code here

    wallBreakMap = [row[:] for row in map]
    tempLenSol = len(map) * len(map[0])
    for wallLoopRowIndex in range(0, len(wallBreakMap)):
        for wallLoopColIndex in range(0, len(wallBreakMap[0])):
            if(wallBreakMap[wallLoopRowIndex][wallLoopColIndex] == 1):
                wallBreakTempMap = [row[:] for row in wallBreakMap]
                wallBreakTempMap[wallLoopRowIndex][wallLoopColIndex] = 0
                # print(wallBreakTempMap)

                sol = [[0,0]]
                hasWallBroken = False
                isDone = False
                while (not isDone):
                    # print(sol)
                    currentCoord = sol[len(sol)-1]
                    if (currentCoord[0] == len(wallBreakTempMap)-1 and currentCoord[1] == len(wallBreakTempMap[0])-1):
                        isDone = True
                        break

                    currentCoordToRight = [currentCoord[0], currentCoord[1] + 1]
                    currentCoordToDown = [currentCoord[0] + 1, currentCoord[1]]
                    currentCoordToLeft = [currentCoord[0], currentCoord[1] - 1]
                    currentCoordToUp = [currentCoord[0] - 1, currentCoord[1]]

                    if (currentCoordToRight[1] < len(wallBreakTempMap[0]) and wallBreakTempMap[currentCoordToRight[0]][currentCoordToRight[1]] == 0):  # Handles checking and moving to right
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToRight)
                    elif (currentCoordToDown[0] < len(wallBreakTempMap) and wallBreakTempMap[currentCoordToDown[0]][currentCoordToDown[1]] == 0):  # Handles checking and moving down
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToDown)
                    elif (currentCoordToLeft[1] >= 0 and wallBreakTempMap[currentCoordToLeft[0]][currentCoordToLeft[1]] == 0):  # Handles checking and moving left
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToLeft)
                    elif (currentCoordToUp[0] >= 0 and wallBreakTempMap[currentCoordToUp[0]][currentCoordToUp[1]] == 0):  # Handles checking and moving up
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToUp)
                    else:
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.pop(len(sol) - 1)   # Remove last choice
                if (len(sol) < tempLenSol):
                    tempLenSol = len(sol)
            else:
                wallBreakTempMap = [row[:] for row in wallBreakMap]
                # print(wallBreakTempMap)

                sol = [[0,0]]
                hasWallBroken = False
                isDone = False
                while (not isDone):
                    # print(sol)
                    currentCoord = sol[len(sol)-1]
                    if (currentCoord[0] == len(wallBreakTempMap)-1 and currentCoord[1] == len(wallBreakTempMap[0])-1):
                        isDone = True
                        break

                    currentCoordToRight = [currentCoord[0], currentCoord[1] + 1]
                    currentCoordToDown = [currentCoord[0] + 1, currentCoord[1]]
                    currentCoordToLeft = [currentCoord[0], currentCoord[1] - 1]
                    currentCoordToUp = [currentCoord[0] - 1, currentCoord[1]]

                    if (currentCoordToRight[1] < len(wallBreakTempMap[0]) and wallBreakTempMap[currentCoordToRight[0]][currentCoordToRight[1]] == 0):  # Handles checking and moving to right
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToRight)
                    elif (currentCoordToDown[0] < len(wallBreakTempMap) and wallBreakTempMap[currentCoordToDown[0]][currentCoordToDown[1]] == 0):  # Handles checking and moving down
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToDown)
                    elif (currentCoordToLeft[1] >= 0 and wallBreakTempMap[currentCoordToLeft[0]][currentCoordToLeft[1]] == 0):  # Handles checking and moving left
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToLeft)
                    elif (currentCoordToUp[0] >= 0 and wallBreakTempMap[currentCoordToUp[0]][currentCoordToUp[1]] == 0):  # Handles checking and moving up
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToUp)
                    else:
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.pop(len(sol) - 1)   # Remove last choice
                if (len(sol) < tempLenSol):
                    tempLenSol = len(sol)

    
    for wallLoopRowIndex in range(0, len(wallBreakMap)):
        for wallLoopColIndex in range(0, len(wallBreakMap[0])):
            if(wallBreakMap[wallLoopRowIndex][wallLoopColIndex] == 1):
                wallBreakTempMap = [row[:] for row in wallBreakMap]
                wallBreakTempMap[wallLoopRowIndex][wallLoopColIndex] = 0
                # print(wallBreakTempMap)

                sol = [[0,0]]
                hasWallBroken = False
                isDone = False
                while (not isDone):
                    # print(sol)
                    currentCoord = sol[len(sol)-1]
                    if (currentCoord[0] == len(wallBreakTempMap)-1 and currentCoord[1] == len(wallBreakTempMap[0])-1):
                        isDone = True
                        break

                    currentCoordToRight = [currentCoord[0], currentCoord[1] + 1]
                    currentCoordToDown = [currentCoord[0] + 1, currentCoord[1]]
                    currentCoordToLeft = [currentCoord[0], currentCoord[1] - 1]
                    currentCoordToUp = [currentCoord[0] - 1, currentCoord[1]]

                    if (currentCoordToDown[0] < len(wallBreakTempMap) and wallBreakTempMap[currentCoordToDown[0]][currentCoordToDown[1]] == 0):  # Handles checking and moving down
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToDown)
                    elif (currentCoordToRight[1] < len(wallBreakTempMap[0]) and wallBreakTempMap[currentCoordToRight[0]][currentCoordToRight[1]] == 0):  # Handles checking and moving to right
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToRight)
                    elif (currentCoordToLeft[1] >= 0 and wallBreakTempMap[currentCoordToLeft[0]][currentCoordToLeft[1]] == 0):  # Handles checking and moving left
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToLeft)
                    elif (currentCoordToUp[0] >= 0 and wallBreakTempMap[currentCoordToUp[0]][currentCoordToUp[1]] == 0):  # Handles checking and moving up
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToUp)
                    else:
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.pop(len(sol) - 1)   # Remove last choice
                if (len(sol) < tempLenSol):
                    tempLenSol = len(sol)
            else:
                wallBreakTempMap = [row[:] for row in wallBreakMap]
                # print(wallBreakTempMap)

                sol = [[0,0]]
                hasWallBroken = False
                isDone = False
                while (not isDone):
                    # print(sol)
                    currentCoord = sol[len(sol)-1]
                    if (currentCoord[0] == len(wallBreakTempMap)-1 and currentCoord[1] == len(wallBreakTempMap[0])-1):
                        isDone = True
                        break

                    currentCoordToRight = [currentCoord[0], currentCoord[1] + 1]
                    currentCoordToDown = [currentCoord[0] + 1, currentCoord[1]]
                    currentCoordToLeft = [currentCoord[0], currentCoord[1] - 1]
                    currentCoordToUp = [currentCoord[0] - 1, currentCoord[1]]

                    if (currentCoordToDown[0] < len(wallBreakTempMap) and wallBreakTempMap[currentCoordToDown[0]][currentCoordToDown[1]] == 0):  # Handles checking and moving down
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToDown)
                    elif (currentCoordToRight[1] < len(wallBreakTempMap[0]) and wallBreakTempMap[currentCoordToRight[0]][currentCoordToRight[1]] == 0):  # Handles checking and moving to right
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToRight)
                    elif (currentCoordToLeft[1] >= 0 and wallBreakTempMap[currentCoordToLeft[0]][currentCoordToLeft[1]] == 0):  # Handles checking and moving left
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToLeft)
                    elif (currentCoordToUp[0] >= 0 and wallBreakTempMap[currentCoordToUp[0]][currentCoordToUp[1]] == 0):  # Handles checking and moving up
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.append(currentCoordToUp)
                    else:
                        wallBreakTempMap[sol[len(sol)-1][0]][sol[len(sol)-1][1]] = 1
                        sol.pop(len(sol) - 1)   # Remove last choice
                if (len(sol) < tempLenSol):
                    tempLenSol = len(sol)


        # print(sol)
    # #DOESN'T FIND WALL TO BREAK OFF THE SOLUTION PATH
    # for i, coord in enumerate(sol):
    #     for n, reverseCoord in enumerate(reversed(sol)):
    #         if (coord[1] + 1 < len(wallBreakMap[0]) and abs(coord[1] - reverseCoord[1]) == 2 and coord[0] == reverseCoord[0] and wallBreakMap[coord[0]][coord[1] + 1] == 1 and wallBreakMap[coord[0]][coord[1] - 1] == 1 and not hasWallBroken):  # Horizontal single-width wall
    #             lenOfCutOut = ((len(sol)-1)  -  n-i-1)
    #             lenOfSol = len(sol)
    #             solLen = lenOfSol - lenOfCutOut + 1 # '+ 1' is for the turn taken to remove wall
    #             hasWallBroken = True
    #             print(coord)
    #             print(reverseCoord)
    #             print(sol)
    #             return solLen
    #             break
    #         elif (coord[0] + 1 < len(wallBreakMap) and abs(coord[0] - reverseCoord[0]) == 2 and coord[1] == reverseCoord[1] and wallBreakMap[coord[0] + 1][coord[1]] == 1 and wallBreakMap[coord[0] + 1][coord[1]] == 1 and not hasWallBroken):    # Vertical single-width wall
    #             lenOfCutOut = ((len(sol)-1)  -  n-i-1)
    #             lenOfSol = len(sol)
    #             solLen = lenOfSol - lenOfCutOut + 1 # '+ 1' is for the turn taken to remove wall
    #             hasWallBroken = True
    #             print(coord)
    #             print(reverseCoord)
    #             print(sol)
    #             return solLen
    #             break

    return(tempLenSol)

def main():
    map0 = [[0,1,1,0],
           [0,0,0,1],
           [1,1,0,0],
           [1,1,1,0]]

    # map1 = [[0,0,0,0,0],
    #        [1,1,1,1,0],
    #        [0,0,0,0,0],
    #        [0,1,1,1,1],
    #        [0,1,1,1,1],
    #        [0,0,0,0,0]]
    map1 = [[0,0,0,0,0,0],
            [1,1,1,1,1,0],
            [0,0,0,0,0,0],
            [0,1,1,1,1,1],
            [0,1,1,1,1,1],
            [0,0,0,0,0,0]]

    map2 = [[0,0,0,0,0,0,0,0,0],
            [1,1,1,1,1,1,1,1,0],
            [1,0,0,0,0,0,1,1,0],
            [0,0,1,1,1,0,1,1,0],
            [0,1,1,1,1,0,0,0,0],
            [0,1,1,1,1,1,1,1,0],
            [0,1,1,1,1,1,1,1,1],
            [0,0,0,0,0,0,0,0,0]]


    print(solution(map2))

if __name__ == "__main__":
    main()