
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
 public int GRIDSIZE = 80;
 public int SCREENSIZE = 960;
 public int xylength = GRIDSIZE;
 public int latSize = 20;
 public int numnodes = latSize*latSize;
    
    public double[] initArrs(double[] Arr, double from, double to){
        for(int r = 0; r < xylength; r++){
            Arr[r] =from - (((from - to)/xylength)*r);
        }
        return Arr;
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {
        System.out.println("System Running");

        Driver driver = new Driver(latSize);
        double[] Alphas = new double[xylength];
        double[] Betas = new double[xylength];
        initArrs(Alphas, 1, 0);
        initArrs(Betas, 1, 2);
        int numberOfRuns = 5;
        int[][] returned = new int[numberOfRuns][3];
        double[] Averages = { 0, 0, 0 };
        double[][] graph = new double[Alphas.length][Betas.length];
        int alph = 0;
        int beta = 0;
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
       
        GridPane grid = new GridPane();
        for(int c = 0; c < xylength; c++ ){
            for(int r = 0; r < xylength; r++){
                    grid.add(new Rectangle(SCREENSIZE/GRIDSIZE,SCREENSIZE/GRIDSIZE, Color.web(pickcolor(graph[r][c]), 1)), c, r);
                }
            }
        Scene scene_3 = new Scene(grid, SCREENSIZE, SCREENSIZE);
        stage.setScene(scene_3);
        stage.show();
    }

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