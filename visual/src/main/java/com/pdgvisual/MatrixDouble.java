package com.pdgvisual;

public class MatrixDouble {

    public MatrixDouble() {

    }

    public double[][] createMatrix(int row, int col, double prefill) {
        double[][] rtn = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rtn[i][j] = prefill;
            }
        }
        return rtn;
    }

    public void printMatrix(double[][] matrix) {
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

    public double[][] matrixMultiply(double[][] m1, double[][] m2) {
        int M = m1.length;
        int N = m1[0].length;
        int P = m2.length;
        int Q = m2[0].length;
        if (N != P) {
            return null;
        }
        double[][] rtn = new double[M][Q];

        int i, j, k;
        for (i = 0; i < M; i++) {
            for (j = 0; j < Q; j++) {
                for (k = 0; k < N; k++)
                    rtn[i][j] += m1[i][k] * m2[k][j];
            }
        }

        return rtn;

    }

    public double[][] matrixPointMulitply(double[][] m1, int[][] m2) {
        int M = m1.length;
        int N = m1[0].length;
        int P = m2.length;
        int Q = m2[0].length;
        if (M != P || N != Q) {
            return null;
        }
        double[][] rtn = new double[M][N];

        int i, j;
        for (i = 0; i < M; i++) {
            for (j = 0; j < Q; j++) {
                rtn[i][j] = m1[i][j] * m2[i][j];
            }
        }

        return rtn;
    }

    public double[][] matrixScalarMultiply(double s, double[][] m) {
        int M = m.length;
        int N = m[0].length;
        double[][] rtn = new double[M][N];
        int i, j;
        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                rtn[i][j] = s * m[i][j];
            }
        }

        return rtn;
    }
}
