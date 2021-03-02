def shortest_path(startingX, startingY, maze):
    w = len(maze[0])
    h = len(maze)
    board = [[None for i in range(w)] for i in range(h)]
    board[startingX][startingY] = 1

    arr = [(startingX, startingY)]
    while arr:
        x, y = arr.pop(0)
        for i in [[1,0],[-1,0],[0,-1],[0,1]]:
          newX, newY = x + i[0], y + i[1]
          if 0 <= newX < h and 0 <= newY < w:
            if board[newX][newY] is None:
                board[newX][newY] = board[x][y] + 1
                if maze[newX][newY] == 1 :
                  continue
                arr.append((newX, newY)) 

    return board

def solution(maze):
  w = len(maze[0])
  h = len(maze)
  tb = shortest_path(0, 0, maze)
  bt = shortest_path(h-1, w-1, maze)
  board = []

  sol = 2 ** 32-1
  for i in range(h):
      for j in range(w):
          if tb[i][j] and bt[i][j]:
              sol = min(tb[i][j] + bt[i][j] - 1, sol)
  return sol

#maze = [[0, 0, 0, 0, 0, 0],[1, 1, 1, 1, 1, 0],[1, 1, 1, 1, 1, 1],[0, 0, 0, 0, 0, 0],[0, 1, 1, 1, 1, 1],[0, 0, 0, 0, 0, 0]] #Answer 21
#maze = [[0, 1, 1, 0], [0, 0, 0, 1], [1, 1, 0, 0], [1, 1, 1, 0]] #Answer 7
#maze = [[0,1,0,0,0],[0,0,0,1,0],[1,1,1,1,0]] #Answer 7
#maze = [[0,1,1,1],[0,1,0,0],[1,0,1,0],[1,1,0,0]] #Answer 7
maze = [[0, 0, 0, 0, 0, 0], [1, 1, 1, 1, 1, 0], [0, 0, 0, 0, 0, 0], [0, 1, 1, 1, 1, 1], [0, 1, 1, 1, 1, 1], [0, 0, 0, 0, 0, 0]] #Answer 11
print solution(maze)