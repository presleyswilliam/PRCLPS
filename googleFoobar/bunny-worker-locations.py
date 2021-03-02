# Find the value of element at x,y of Floyd's Triangle

def solution(x, y):
    # Your code here
    term1 = (x * (x + 1) / 2 )  # series for moving left->right on row 1

    # These are for adding up each incremenet to previous number without for loop to maintain O(1)
    term2 = (x+y-2)*(x+y-1)/2   # n(n+1) / 2
    term3 = x*(x-1)/2   # n(n+1) / 2
    sol = term1 + (term2 - term3)
    return str(sol)

    ##Alex's sol
    # sol = x + (y-1)
    # sol = (sol * (sol+1)) / 2
    # sol -= (y-1)
    # print(sol)
    # return str(sol)

    ##For loop sol
    # xInc = 2
    # yInc = 1
    # for i in range(0, x-1):
    #     sol += xInc
    #     xInc += 1
    #     yInc += 1

    # for i in range(0, y-1):
    #     sol += yInc
    #     yInc += 1

    print(sol)
    return(sol)

def main():
    x = 5
    y = 10

    solution(x, y)

if __name__ == "__main__":
    main()