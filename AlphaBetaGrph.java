
/*
Javafx and jdk23.0.1 required
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

public class AlphaBetaGrph extends Application {
    //number of levels for alpha and beta
 public int GRIDSIZE = 80;

    // pixel size of visual display total
 public int SCREENSIZE = 960;

    // length of x and y axes of graph
 public int xylength = GRIDSIZE;

    // number of nodes along one side of the square node network
 public int latSize = 20;

    // number of nodes in the network
 public int numnodes = latSize*latSize;

    /*
        Initializes Array of doubles progressing evenly from 'from' at [0] to 'to' at [Arr.length - 1]
        \param Arr is an empty double array 
        \param from is a double which the array starts with
        \param to is a double which the array ends with
        returns an initialized double array
    */

    public double[] initArrs(double[] Arr, double from, double to){
        for(int r = 0; r < xylength; r++){
            Arr[r] =from - (((from - to)/xylength)*r);
        }
        return Arr;
    }
    
    /*
        Starts the program, creates a graph of alphas and betas using the Driver.java class
        \param stage do not worry about this. It is taken from the current environment. Used to display visual.
    */

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {
        System.out.println("System Running");

        //Initialize simulations
        Driver driver = new Driver(latSize);
        double[] Alphas = new double[xylength];
        double[] Betas = new double[xylength];
        //choose upper and lower bounds for visual graph
        initArrs(Alphas, 1, 0);
        initArrs(Betas, 1, 2);
        //choose number of simulations to be averaged(for small networks number should be higher)
        int numberOfRuns = 5;
        int[][] returned = new int[numberOfRuns][3];
        double[] Averages = { 0, 0, 0 };
        double[][] graph = new double[Alphas.length][Betas.length];
        int alph = 0;
        int beta = 0;
        //run a simulation on each combination of alpha nad beta
        for (double a : Alphas) {
            for (double b : Betas) {
                for (int run = 0; run < numberOfRuns; run++) {
                    returned[run] = driver.runGame(a, b);
                    Averages[2] = Averages[2] + returned[run][2];
                }
                Averages[2] = Averages[2] / numberOfRuns;
                Averages[2] = Math.round(Averages[2] * 100) / 100.0;
                graph[alph][beta] = Averages[2] / numnodes;
                Averages[0] = 0;
                Averages[1] = 0;
                Averages[2] = 0;
                beta++;
            }
            alph++;
            beta = 0;
        }
       //create a graph of the average mortality rate of each alpha,beta combination
        GridPane grid = new GridPane();
        for(int c = 0; c < xylength; c++ ){
            for(int r = 0; r < xylength; r++){
                    grid.add(new Rectangle(SCREENSIZE/GRIDSIZE,SCREENSIZE/GRIDSIZE, Color.web(pickcolor(graph[r][c]), 1)), c, r);
                }
            }
        Scene scene_3 = new Scene(grid, SCREENSIZE, SCREENSIZE);
        //display graph
        stage.setScene(scene_3);
        stage.show();
    }

    /*
        Takes a mortality rate and translates it to a color for the graph
        \param mortality is a double describing the mortality rate of nodes in a simulation 
        returns a string of the hex number of the color corresponding to the mortality rate of a simulation
    */

    public static String pickcolor(double mortality){
        mortality = 1 - mortality;
        if(mortality < .1){
            return "DC143C";
        }if(mortality < .2){
            return "FF8C00";
        }if(mortality < .3){
            return "FFD700";
        }if(mortality < .4){
            return "7CFC00";
        }if(mortality < .5){
            return "87CEFA";
        }if(mortality < .6){
            return "00BFFF";
        }if(mortality < .7){
            return "1E90FF";
        }if(mortality < .8){
            return "0000CD";
        }if(mortality < .9){
            return "483D8B";
        }
        return "000000";
    }

    public static void main(String[] args) {
        launch();
    }

}