import math
import time
import random
import csv



def partition_Matrix(M):
    n = len(M)
    #Below is too slow
    #
    m1 = [[0 for k in range(n//2)] for j in range(n//2)]
    m2 = [[0 for k in range(n//2)] for j in range(n//2)]
    m3 = [[0 for k in range(n//2)] for j in range(n//2)]
    m4 = [[0 for k in range(n//2)] for j in range(n//2)]


    if n <3:
        for k in range(n//2):
            for j in range(n//2):
                m1[k][j] = M[k][j]

        for k in range(n//2):
            for j in range((n+1)//2,n):
                m2[k][j-1] = M[k][j]

        for k in range((n+1)//2,n):
            for j in range(n//2):
                m3[k-1][j] = M[k][j]

        for k in range((n+1)//2 ,n):
            for j in range((n+1)//2 ,n):
                m4[k-1][j-1] = M[k][j]
    else:

        for k in range(n//2):
            for j in range(n//2):
                m1[k][j] = M[k][j]

        for k in range(n//2):
            for j in range(n//2,n):
                m2[k][j-n] = M[k][j]

        for k in range(n//2,n-1):
            for j in range(n//2):
                m3[k-n][j] = M[k][j]

        for k in range(n//2 ,n-1):
            for j in range(n//2 ,n):
                m4[k-n][j-n] = M[k][j]


    div = int(n/2)



    return m1,m2,m3,m4




def Recombine_Matrix(m1,m2,m3,m4):

    m12 = [[0 for k in range(len(m1[0]))] for j in range(len(m1))]
    m34 = [[0 for k in range(len(m1[0]))] for j in range(len(m1))]
    for k in range(len(m1)):
        m12[k] = m1[k] + m2[k]
        m34[k] = m3[k] + m4[k]
    M = m12 + m34


    return(M)

def Matrix_Add(M1 , M2):
    Res= [[0 for k in range(len(M1))] for j in range(len(M1))]
    for k in range(len(M1)):
        for j in range(len(M1[0])):
            Res[k][j] = M1[k][j]+M2[k][j]
    return Res

def Matrix_Sub(M1 , M2):

    Res= [[0 for k in range(len(M1))] for j in range(len(M1))]
    for k in range(len(M1)):
        for j in range(len(M1[0])):
            Res[k][j] = M1[k][j]-M2[k][j]
    return Res

def DePad(M,pd):
    if pd == True:
        Res= [[0 for k in range(len(M)-1)] for j in range(len(M)-1)]
        for k in range(len(M)-1):
            for j in range(len(M[0])-1):
                Res[k][j] = M[k][j]
        return Res
    else:
        return M



def Pad_Matrix(M):
    n = len(M)

    New = [[0 for i in range(n+1)] for j in range(n+1)]
    for k in range(n):
        for j in range(n):
            New[k][j] = M[k][j]

    return New


def Square_Matrix_Multiply(M1,M2):
    n = len(M1)
    C = [[0 for i in range(n)] for j in range(n)]
    for k in range(n):
        for j in range(n):
            C[k][j] = 0
            for l in range(n):
                C[k][j] = C[k][j]+M1[k][l]*M2[l][j]
    return C

def Square_Matrix_Multiply_Recursive(A,B):
    padded = False

    if len(A)>1 and not len(A)%2 == 0 :
        padded = True
        A =  Pad_Matrix(A)
        B =  Pad_Matrix(B)

    n = len(A)


    C= [[0 for i in range(n)] for j in range(n)]
    if n == 1:
        C[0][0]= A[0][0]*B[0][0]
    else:
        A11 , A12 , A21 , A22 = partition_Matrix(A)
        B11 , B12 , B21 , B22 = partition_Matrix(B)
        C11 , C12 , C21 , C22 = partition_Matrix(C)

        C11 = Matrix_Add(Square_Matrix_Multiply_Recursive(A11,B11), Square_Matrix_Multiply_Recursive(A12,B21))
        C12 = Matrix_Add(Square_Matrix_Multiply_Recursive(A11,B12), Square_Matrix_Multiply_Recursive(A12,B22))
        C21 = Matrix_Add(Square_Matrix_Multiply_Recursive(A21,B11), Square_Matrix_Multiply_Recursive(A22,B21))
        C22 = Matrix_Add(Square_Matrix_Multiply_Recursive(A21,B12), Square_Matrix_Multiply_Recursive(A22,B22))

        C = DePad(Recombine_Matrix(C11,C12,C21,C22),padded)

    return C

def Strassens_Method(A,B):
    padded = False

    if len(A)>1 and not len(A)%2 == 0 :
        padded = True
        A =  Pad_Matrix(A)
        B = Pad_Matrix(B)

    n = len(A)
    C= [[0 for i in range(n)] for j in range(n)]

    if n == 1:
        C[0][0]= A[0][0]*B[0][0]
    else:
        A11 , A12 , A21 , A22 = partition_Matrix(A)
        B11 , B12 , B21 , B22 = partition_Matrix(B)
        C11 , C12 , C21 , C22 = partition_Matrix(C)

        C1 = Strassens_Method(A11,Matrix_Sub(B12,B22)) #A11*S1
        C2 = Strassens_Method(Matrix_Add(A11,A12),B22) #S2*B22
        C3 = Strassens_Method(Matrix_Add(A21,A22),B11) #S3*B11
        C4 = Strassens_Method(A22,Matrix_Sub(B21,B11)) #A22*S4
        C5 = Strassens_Method(Matrix_Add(A11,A22),Matrix_Add(B11,B22)) #S5*S6
        C6 = Strassens_Method(Matrix_Sub(A12,A22),Matrix_Add(B21,B22)) #S7*S8
        C7 = Strassens_Method(Matrix_Sub(A11,A21),Matrix_Add(B11,B12)) #S9*S10

        C11 = Matrix_Add(Matrix_Sub(Matrix_Add(C5,C4),C2),C6) #P5+P4-P2+P6
        C12 = Matrix_Add(C1,C2) #P1+P2
        C21 = Matrix_Add(C3,C4) #P3+P4
        C22 = Matrix_Sub(Matrix_Sub(Matrix_Add(C1,C5),C3),C7) #P5+P1-P3-P7

        C = DePad(Recombine_Matrix(C11,C12,C21,C22),padded)

    return C

M = [[random.randint(1,5) for k in range(3)] for j in range(3)]
N = [[random.randint(1,5) for k in range(3)] for j in range(3)]

print('Initial test')
print('Matrix 1:' , M)
print('Matrix 2:' , N)
print()
print(Square_Matrix_Multiply(M,N))
print('//////////')
print(Square_Matrix_Multiply_Recursive(M,N))
print('//////////')
print(Strassens_Method(M,N))
print('###############################################')

# with open('Matrix.csv', mode='w') as MatrixSave:
#     MatrixSave = csv.writer(MatrixSave, delimiter=',')
#     MatrixSave.writerow(['Size of Matrix', 'Square_Matrix_Multiply', 'Square_Matrix_Multiply_Recursive','Strassens_Method'])
#     for k in range(2,100):
#         sumoftime1 = 0
#         sumoftime2 = 0
#         sumoftime3 = 0
#         print('Size of Matrix:',k)
#         for j in range(3):
#             C=  [[random.randint(1,20) for k in range(k)] for j in range(k)]
#             M = [[random.randint(1,20) for k in range(k)] for j in range(k)]
#             N = [[random.randint(1,20) for k in range(k)] for j in range(k)]
#
#
#             start = time.time()
#             Square_Matrix_Multiply(M,N)
#             end = time.time()
#             sumoftime1 = sumoftime1+(end-start)
#
#             start = time.time()
#             Square_Matrix_Multiply_Recursive(M,N)
#             end = time.time()
#             sumoftime2 = sumoftime2+(end-start)
#
#             start =time.time()
#             Strassens_Method(M,N)
#             end = time.time()
#             sumoftime3 = sumoftime3+(end-start)
#
#
#
#         MatrixSave.writerow([k, sumoftime1/3, sumoftime2/3,sumoftime3/3])
#
#
#         print('Square_Matrix_Multiply (s):',sumoftime1/3)
#         print('Square_Matrix_Multiply_Recursive (s):',sumoftime2/3)
#         print('Strassens_Method (s):',sumoftime3/3)
