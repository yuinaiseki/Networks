
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

public class SingleRun extends Application {
 public int GRIDSIZE = 120;
 public int SCREENSIZE = 960;
 public int xylength = GRIDSIZE;
 public int latSize = GRIDSIZE;
 public int numnodes = latSize*latSize;
 public int stages = 60;
 public int stagesize = 4;
 public double alpha = .49;
 public double beta = 1.75;
    
    public double[] initArrs(double[] Arr, double from, double to){
        for(int r = 0; r < xylength; r++){
            Arr[r] =from - (((from - to)/xylength)*r);
        }
        return Arr;
    }
    
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {
        System.out.println("System Running");

        SingleRunDriver driver = new SingleRunDriver(latSize);
        driver.initialize();
        int[][] graph = new int[latSize][latSize];
        graph = driver.runGame(alpha, beta, 1);
        for(int i = 0; i < stages; i++){
        displayGraph(stage, SCREENSIZE, graph);
        graph = driver.resumeGame(stagesize);
        }
       
    }

    public void displayGraph(Stage stage, int SCREENSIZE, int[][] Graph){
        GridPane grid = new GridPane();
        for(int c = 0; c < latSize; c++ ){
            for(int r = 0; r < latSize; r++){
                    grid.add(new Rectangle(SCREENSIZE/GRIDSIZE,SCREENSIZE/GRIDSIZE, Color.web(pickcolor(Graph[r][c]), 1)), c, r);
                }
            }
        Scene scene_3 = new Scene(grid, SCREENSIZE, SCREENSIZE);
        Stage newstage = new Stage();
        newstage.setScene(scene_3);
        newstage.showAndWait();

    }
    public static String pickcolor(double state){
        if(state == 0){
            return "FFFFFF";
        }if(state == 1){
            return "0000FF";
        }if(state == 2){
            return "FF0000";
        }
        return "000000";
    }

    public static void main(String[] args) {
        launch();
    }

}