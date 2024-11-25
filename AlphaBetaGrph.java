/*
Javafx + jdk 23.0.1 required
*/
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

public class AlphaBetaGrph extends Application {
 public int GRIDSIZE = 20;
 public int SCREENSIZE = 960;
    @Override
    public void start(Stage stage) {
        System.out.println("System Running");

        Driver driver = new Driver();
        double[] Alphas = {1, 0.95,0.9, 0.85, 0.8, 0.75, 0.7, 0.65, 0.6, 0.55, 0.5, 0.45, 0.4, 0.35, 0.3, 0.25, 0.2, 0.15, 0.1, 0.05 };
        double[] Betas = {2, 1.95,1.9, 1.85, 1.8, 1.75, 1.7, 1.65, 1.6, 1.55, 1.5, 1.45, 1.4, 1.35, 1.3, 1.25, 1.2, 1.15, 1.1, 1.05 };
        int numberOfRuns = 20;
        int[][] returned = new int[numberOfRuns][3];
        double[] Averages = { 0, 0, 0 };
        String[][] graph = new String[Alphas.length][Betas.length];
        System.out.println(Alphas.length)
        ;System.out.println(Betas.length);
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
                graph[alph][beta] = pickcolor(Averages[2] / 144);
                Averages[0] = 0;
                Averages[1] = 0;
                Averages[2] = 0;
                beta++;
            }
            alph++;
            beta = 0;
        }
       
        GridPane grid = new GridPane();
        for(int c = 0; c < 20; c++ ){
            for(int r = 0; r < 20; r++){
                    grid.add(new Rectangle(SCREENSIZE/GRIDSIZE,SCREENSIZE/GRIDSIZE, Color.web(graph[r][c], .8)), c, r);
                }
            }
        Scene scene_3 = new Scene(grid, SCREENSIZE, SCREENSIZE);
        stage.setScene(scene_3);
        stage.show();
    }

    public static String pickcolor(double mortality){
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
            return "48D1CC";
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