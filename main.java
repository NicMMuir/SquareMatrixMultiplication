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






  public static int[][] Strassens_Method(int[][] M1,int[][] M2){
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

      int [][] C1 = Strassens_Method(A11,Matrix_Sub(B12,B22)); //A11*S1
      int [][] C2 = Strassens_Method(Matrix_Add(A11,A12),B22); //S2*B22
      int [][] C3 = Strassens_Method(Matrix_Add(A21,A22),B11); //S3*B11
      int [][] C4 = Strassens_Method(A22,Matrix_Sub(B21,B11)); //A22*S4
      int [][] C5 = Strassens_Method(Matrix_Add(A11,A22),Matrix_Add(B11,B22)); //S5*S6
      int [][] C6 = Strassens_Method(Matrix_Sub(A12,A22),Matrix_Add(B21,B22)); //S7*S8
      int [][] C7 = Strassens_Method(Matrix_Sub(A11,A21),Matrix_Add(B11,B12)); //S9*S10


      int [][] C11 = Matrix_Add(Matrix_Sub(Matrix_Add(C5,C4),C2),C6); //P5+P4-P2+P6
      int [][] C12 = Matrix_Add(C1,C2); //P1+P2
      int [][] C21 = Matrix_Add(C3,C4); //P3+P4
      int [][] C22 = Matrix_Sub(Matrix_Sub(Matrix_Add(C1,C5),C3),C7); //P5+P1-P3-P7

      C = DePad_Matrix(Recombine_Matrix(C11,C12,C21,C22),padded);

    }

    return C;


  }














  public static void print(int[][] M){

    for (int k = 0; k < M.length; k++){
            // Loop through all elements of current row
            for (int j = 0; j < M.length; j++){
                System.out.print(M[k][j] + " ");

              }
              System.out.print(" \n");
          }

  }




  public static void main(String[] args) {

    int matrixSize = 0;
    for(int loop = 2;loop<=1024;loop*=2){

    System.out.println("###################################################");
    System.out.println("Matrix Size :" + loop + "\n");
    matrixSize = loop;

        int [][] matrix1 = new int[matrixSize][matrixSize];
        int [][] matrix2 = new int[matrixSize][matrixSize];

        for (int k=0; k<matrix1.length; k++) {
            for (int j=0; j<matrix1[k].length; j++) {
                matrix1[k][j] = (int) (Math.random()*10);
                matrix2[k][j] = (int) (Math.random()*10);
            }
          }
        int [][] output = new int[matrixSize][matrixSize];



        //print(matrix1);
        //print(matrix2);

System.out.println("Multiply:");
        long startTime = System.nanoTime();
        output = Square_Matrix_Multiply(matrix1,matrix2);
        long endTime = System.nanoTime();
        //print(output);
        double TimeS = (double)(endTime - startTime)/1_000_000_000;
        System.out.println("Time: "+ TimeS + "s");




System.out.println("Recursive:");
         startTime = System.nanoTime();
        output = Square_Matrix_Multiply_Recursive(matrix1,matrix2);
         endTime = System.nanoTime();
        //print(output);
        double TimeR = (double)(endTime - startTime)/1_000_000_000;
        System.out.println("Time: "+TimeR +"s");



System.out.println("Strassens:");
        startTime = System.nanoTime();
       output = Strassens_Method(matrix1,matrix2);
        endTime = System.nanoTime();
       //print(output);
       double TimeM = (double)(endTime - startTime)/1_000_000_000;
       System.out.println("Time: "+TimeM +"s");

       System.out.println("###################################################");
     }


  }





}
