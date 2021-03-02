# Print start and end index of consecutive elements that sum to the target value

def solution(l, t):
    # Your code here
    sol = [-1, -1]
    sum = 0
    for n in range(0, len(l)):
        sol[0] = n
        for i in range(n, len(l)):
            sum = 0
            sol[1] = i
            if (sol[0] != sol[1]):
                for index in range(sol[0], sol[1]+1):
                    sum += l[index]
            else:
                sum = l[sol[0]]
            
            if (sum == t):
                return sol
            elif (sum > t):
                break

        sol = [-1, -1]
    
    return [-1, -1]

def main():
    l1 = [1, 2, 3, 4]
    t1 = 15

    l2 = [4, 3, 10, 2, 8]
    t2 = 12

    l3 = [11, 4, 4, 5, 4]
    t3 = 12

    print(solution(l2, t2))

if __name__ == "__main__":
    main()