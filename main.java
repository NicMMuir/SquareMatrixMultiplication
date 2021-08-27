import java.util.*;


public class main {


  public static int[][] partition_Matrix(int[][] M,int row , int col){
    int matrixSize = (M.length)/2;
    int [][] newMatrix = new int[matrixSize][matrixSize];
    for(int k = 0;k<matrixSize;k++){
      for(int j = 0;j<matrixSize;j++){
        newMatrix[k][j] = M[k+row][j+col];
      }
    }
    return newMatrix;
  }

  public static int[][] Recombine_Matrix(int[][] m1, int[][] m2, int[][] m3, int[][] m4){

    int[][] newMatrix = new int[m1.length*2][m1.length*2];
    int n = newMatrix.length;
    for(int k = 0;k<newMatrix.length;k++){
      for(int j = 0;j<newMatrix.length;j++){
        if(k<m1.length && j<m1.length)
          newMatrix[k][j] = m1[k][j];

        if(k>=m1.length && j<m1.length)
          newMatrix[k][j] = m3[k-n/2][j];


        if(k<m1.length && j>=m1.length)
          newMatrix[k][j] = m2[k][j-n/2];

        if(k>=m1.length && j>=m1.length)
          newMatrix[k][j] = m4[k-n/2][j-n/2];

      }
    }
    return newMatrix;
  }

  public static int[][] Pad_Matrix(int[][] matrix){
    int matrixSize = matrix.length;
    int [][] newMatrix = new int[matrixSize+1][matrixSize+1];
    for(int k =0;k<matrixSize;k++){
      for(int j =0;j<matrixSize;j++){
        newMatrix[k][j] = matrix[k][j];
      }
    }
    return newMatrix;
  }


  public static int[][] DePad_Matrix(int[][] matrix , boolean padded){
    if(padded == true){
    int matrixSize = matrix.length;
    int [][] newMatrix = new int[matrixSize-1][matrixSize-1];
    for(int k =0;k<matrixSize-1;k++){
      for(int j =0;j<matrixSize-1;j++){
        newMatrix[k][j] = matrix[k][j];
      }
    }
    return newMatrix;
  }else{
    return matrix;
  }
  }

  public static int[][] Square_Matrix_Multiply(int[][] M1,int[][] M2){
    int n = M1.length;
    int [][] C = new int[n][n];
    for(int k =0;k<n;k++){
      for(int j =0;j<n;j++){
        C[k][j] = 0;
        for(int l=0;l<n;l++){
          C[k][j] = C[k][j]+M1[k][l]*M2[l][j];
        }
      }
    }

    return C;
  }

  public static int[][] Matrix_Add(int[][] M1,int[][] M2){
    int n = M1.length;
    int [][] C = new int[n][n];
    for(int k =0;k<n;k++){
      for(int j =0;j<n;j++){
        C[k][j] = M1[k][j]+M2[k][j];
      }
    }
    return C;
  }

  public static int[][] Matrix_Sub(int[][] M1,int[][] M2){
    int n = M1.length;
    int [][] C = new int[n][n];
    for(int k =0;k<n;k++){
      for(int j =0;j<n;j++){
        C[k][j] = M1[k][j]-M2[k][j];
      }
    }
    return C;
  }


  public static int[][] Square_Matrix_Multiply_Recursive(int[][] M1,int[][] M2){
    boolean padded = false;
    if(M1.length>1 && M1.length % 2 != 0){
      M1 = Pad_Matrix(M1);
      M2 = Pad_Matrix(M2);

      padded = true;
    }

    int n = M1.length;
    int [][] C = new int[n][n];
    if(n==1){
      C[0][0] = M1[0][0]*M2[0][0];
    }else{
      int [][] A11 = partition_Matrix(M1, 0 , 0);
      int [][] A12 = partition_Matrix(M1, 0 , n/2);
      int [][] A21 = partition_Matrix(M1, n/2 , 0);
      int [][] A22 = partition_Matrix(M1, n/2 , n/2);


      int [][] B11 = partition_Matrix(M2, 0 , 0);
      int [][] B12 = partition_Matrix(M2, 0 , n/2);
      int [][] B21 = partition_Matrix(M2, n/2 , 0);
      int [][] B22 = partition_Matrix(M2, n/2 , n/2);

      int [][] C11 = Matrix_Add(Square_Matrix_Multiply_Recursive(A11,B11), Square_Matrix_Multiply_Recursive(A12,B21));
      int [][] C12 = Matrix_Add(Square_Matrix_Multiply_Recursive(A11,B12), Square_Matrix_Multiply_Recursive(A12,B22));
      int [][] C21 = Matrix_Add(Square_Matrix_Multiply_Recursive(A21,B11), Square_Matrix_Multiply_Recursive(A22,B21));
      int [][] C22 = Matrix_Add(Square_Matrix_Multiply_Recursive(A21,B12), Square_Matrix_Multiply_Recursive(A22,B22));

      C = DePad_Matrix(Recombine_Matrix(C11,C12,C21,C22),padded);

    }

    return C;


  }





















  public static void print(int[][] M){
    System.out.print(" /////////////////////////////\n");
    for (int k = 0; k < M.length; k++){
            // Loop through all elements of current row
            for (int j = 0; j < M.length; j++){
                System.out.print(M[k][j] + " ");

              }
              System.out.print(" \n");
          }
    System.out.print(" /////////////////////////////\n");
  }




  public static void main(String[] args) {
    int matrixSize = 5;

        int [][] matrix1 = new int[matrixSize][matrixSize];
        int [][] matrix2 = new int[matrixSize][matrixSize];

        for (int k=0; k<matrix1.length; k++) {
            for (int j=0; j<matrix1[k].length; j++) {
                matrix1[k][j] = (int) (Math.random()*10);
                matrix2[k][j] = (int) (Math.random()*10);
            }
          }
        int [][] output = new int[matrixSize][matrixSize];


        output = Square_Matrix_Multiply(matrix1,matrix2);
        print(matrix1);
        print(matrix2);
        print(output);




        output = Square_Matrix_Multiply_Recursive(matrix1,matrix2);
        print(matrix1);
        print(matrix2);
        print(output);
  }





}
