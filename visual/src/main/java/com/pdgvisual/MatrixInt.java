package com.pdgvisual;

public class MatrixInt {

    public MatrixInt() {

    }

    public int[][] createMatrix(int row, int col, int prefill) {
        int[][] rtn = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rtn[i][j] = prefill;
            }
        }
        return rtn;
    }

    public void printMatrix(int[][] matrix) {
        if (matrix == null) {
            System.out.println("Null Matrix");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        String line = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                line += "[" + matrix[i][j] + "]";
            }
            System.out.println(line);
            line = "";
        }
    }

    public int[][] matrixMultiply(int[][] m1, int[][] m2) {
        int M = m1.length;
        int N = m1[0].length;
        int P = m2.length;
        int Q = m2[0].length;
        if (N != P) {
            return null;
        }
        int[][] rtn = new int[M][Q];

        int i, j, k;
        for (i = 0; i < M; i++) {
            for (j = 0; j < Q; j++) {
                for (k = 0; k < N; k++)
                    rtn[i][j] += m1[i][k] * m2[k][j];
            }
        }

        return rtn;

    }

    public int[][] matrixPointMulitply(int[][] m1, int[][] m2) {
        int M = m1.length;
        int N = m1[0].length;
        int P = m2.length;
        int Q = m2[0].length;
        if (M != P || N != Q) {
            return null;
        }
        int[][] rtn = new int[M][N];

        int i, j;
        for (i = 0; i < M; i++) {
            for (j = 0; j < Q; j++) {
                rtn[i][j] = m1[i][j] * m2[i][j];
            }
        }

        return rtn;
    }

    public int[][] matrixScalarMultiply(int s, int[][] m) {
        int M = m.length;
        int N = m[0].length;
        int[][] rtn = new int[M][N];
        int i, j;
        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                rtn[i][j] = s * m[i][j];
            }
        }

        return rtn;
    }
}
