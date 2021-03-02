# Find the value of the extra element and return as solution

def solution(x, y):
    # Your code here
    sol = 0
    for val in x:
        sol += val
    
    for val in y:
        sol -= val

    if (len(y) > len(x)):
        sol *= -1

    return(sol)

def main():
    x = [2, 3, -4, 7]
    y = [2, 3, -4, 7, 6]

    solution(y, x)

if __name__ == "__main__":
    main()