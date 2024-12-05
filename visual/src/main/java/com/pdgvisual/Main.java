package com.pdgvisual;

class Main {

    public static void main(String[] args) {
        System.out.println("System Running");

        Driver driver = new Driver();
        double[] Alphas = {1, 0.95,0.9, 0.85, 0.8, 0.75, 0.7, 0.65, 0.6, 0.55, 0.5, 0.45, 0.4, 0.35, 0.3, 0.25, 0.2, 0.15, 0.1, 0.05 };
        double[] Betas = {2, 1.95,1.9, 1.85, 1.8, 1.75, 1.7, 1.65, 1.6, 1.55, 1.5, 1.45, 1.4, 1.35, 1.3, 1.25, 1.2, 1.15, 1.1, 1.05 };
        int numberOfRuns = 20;
        int[][] returned = new int[numberOfRuns][3];
        double[] Averages = { 0, 0, 0 };
        double[][] graph = new double[Alphas.length*Betas.length][Alphas.length*Betas.length];
        int alph = 0;
        int beta = 0;
        for (double a : Alphas) {
            for (double b : Betas) {

                for (int run = 0; run < numberOfRuns; run++) {
                    returned[run] = driver.runGame(a, b);
                    Averages[0] = Averages[0] + returned[run][0];
                    Averages[1] = Averages[1] + returned[run][1];
                    Averages[2] = Averages[2] + returned[run][2];
                }
                Averages[0] = Averages[0] / numberOfRuns;
                Averages[1] = Averages[1] / numberOfRuns;
                Averages[2] = Averages[2] / numberOfRuns;

                Averages[0] = Math.round(Averages[0] * 100) / 100.0;
                Averages[1] = Math.round(Averages[1] * 100) / 100.0;
                Averages[2] = Math.round(Averages[2] * 100) / 100.0;
                System.out.println("ALPHA: " + a + "      BETA: " + b +
                        "\nMean Num Cooperators:     " + Averages[0] +
                        "\nMean Num Defectors:       " + Averages[1] +
                        "\nMean Num Dead:            " + Averages[2] + "\n\n");
            graph[alph][beta] = Averages[2] / 144;
            Averages[0] = 0;
            Averages[1] = 0;
            Averages[2] = 0;
            
            beta++;
            }
            alph++;
        }

        

    }

}