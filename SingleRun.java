/*
Javafx and jdk23.0.1 required
*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;

public class SingleRun extends Application {
    //side length of the square lattice of nodes
 public int GRIDSIZE = 120;

    // pixel size of visual display total
 public int SCREENSIZE = 960;

 public int xylength = GRIDSIZE;
 public int latSize = GRIDSIZE;

    //number of total nodes in the lattice
 public int numnodes = latSize*latSize;

    //number rounds that will be displayed
 public int stages = 60;

    //number of simulation steps in each round
 public int stagesize = 4;

    //alpha of the simulation
 public double alpha = .49;

    //beta of the simulation
 public double beta = 1.75;
    

    /*
    Displayes a graph of the state of each node in the lattice after each round. Blue nodes are cooperators. 
    Red nodes are defectors. White nodes are dead. To proceed to the next round, exit the visual.
        \param Stage do not worry about this. It is taken from the current environment. Used to display visual.
    */

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {
        System.out.println("System Running");

        SingleRunDriver driver = new SingleRunDriver(latSize);
        //set up driver and int[][] representation of agent lattice
        driver.initialize();
        int[][] graph = new int[latSize][latSize];
        //start the simulation and stop after the first iteration
        graph = driver.runGame(alpha, beta, 1);
        //graph and then resume the simulation for stagesize iterations, in a loop
        for(int i = 0; i < stages; i++){
        displayGraph(stage, SCREENSIZE, graph);
        graph = driver.resumeGame(stagesize);
        }
       
    }
    
    
 /*
   CReates and displays the graph of a given int array
        \param Stage do not worry about this. It is taken from the current environment. Used to display visual.
        \param SCREENSIZE pixel size of screen
        \param Graph the matrix that is graphed
*/

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

      /*
        Takes an agent state and translates it to a color for the graph
        \param mortality is a double describing the state of nodes in a simulation 
        returns a string of the hex number of the color corresponding to the state of an agent
    */
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