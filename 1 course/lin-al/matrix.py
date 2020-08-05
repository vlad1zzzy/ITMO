def matrixSum(a, b):
        if len(a) != len(b) or len(a[0]) != len(b[0]):
                raise Exepiton("False")

        for i in range(len(a)):
                for j in range(len(a[0])):
                        a[i][j] += b[i][j]
        return a

def matrixDif(a, b):
        if len(a) != len(b) or len(a[0]) != len(b[0]):
                raise Exepiton("False")

        for i in range(len(a)):
                for j in range(len(a[0])):
                        a[i][j] -= b[i][j]
        return a

def matrixMultiX(a, x):
        for i in range(len(a)):
                for j in range(len(a[0])):
                        a[i][j] *= x
        return a

def matrixMultiMatrix(a, b):
        if len(a[0]) != len(b):
                raise Exception("False")

        newMatrix = [[0 for i in range(len(b[0]))] for j in range(len(a))]
        for i in range(len(a)):
                for j in range(len(b[0])):
                        summ = 0
                        for k in range(len(b)):
                                summ += a[i][k] * b[k][j]
                        newMatrix[i][j] = summ
        return newMatrix

def matrixTranspose(a):
        newMatrix = [[a[i][j] for i in range(len(a))] for j in range(len(a[0]))]
        return newMatrix

def func(a, b, c, d, f, x, y):
        global complete
        try:
                X1 = matrixMultiX(a, x)
                X2 = matrixTranspose(b)
                X3 = matrixMultiX(X2, y)
                X4 = matrixSum(X1, X3)
                X5 = matrixTranspose(X4)
                X6 = matrixMultiMatrix(c, X5)
                X7 = matrixMultiMatrix(X6, d)
                X8 = matrixDif(X7, f)
                
        except Exception as e:
                complete = False
                return

        return X8

def matrixInput():
        N,M = map(float,input().split())
        Old = list(map(float,input().split()))
        New = []
        for i in range(0, len(Old), M):
                New.extend([Old[i: i+M]])
        return New

if __name__ == '__main__':
        complete = True
        x, y = map(float, input().split())
        A = matrixInput()
        B = matrixInput()
        C = matrixInput()
        D = matrixInput()
        F = matrixInput()

        result = func(A, B, C, D, F, x, y)

        if complete:
                print(1)
                print(len(result), len(result[0]))
                for i in result:
                        print(*i, end=' ')
        else:
                print(0)
