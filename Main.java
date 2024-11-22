
class Main {

    public static void main(String[] args) {
        System.out.println("System Running");

        Driver driver = new Driver();
        double[] Alphas = { 0.75, 0.5, 0.2 };
        double[] Betas = { 1.01, 1.25, 1.5, 2 };
        int numberOfRuns = 30;
        int[][] returned = new int[numberOfRuns][3];
        double[] Averages = { 0, 0, 0 };
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

            }
        }
    }

}