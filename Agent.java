import java.util.random;

public class Agent{
    private int lifePoints;
    private double coop_chance;
    private boolean cooperator;
    private boolean alive;

    public Agent (int lifePoints, double coop_chance){
        this.lifePoints = lifePoints;
        this.cooperator = set_coop(coop_chance);
        this.alive = lifePoints > 0; // Agent is alive if lifePoints > 0
     }
    
    public int getLifePoints(){
        return this.lifePoints;
    }
    
    public boolean isCooperator(){
        return this.cooperator;
    }
        
    public boolean isAlive(){
        return this.alive;
    }
//Christina 
    private boolean set_live(){
      if (this.lifePoints > 0){//possibly change from zero, depending  
          this.alive = true;
      }
      else {
      this.alive = false; 
    }
    }

/*
set_coop is intended to be used for setting whether the agent is choosing to cooperate or not
    Precondition: double chance : the chance, from 0 to 1, of an agent defecting
    Postcondition: boolean : returns false if the agent is a defector
    (Kevin)
*/
public boolean set_coop(double chance){
    Random rand = new Random();
    double num = (1 + rand.nextInt(100)/ 100);
    if(num < chance){
    return true;
    }else{
    return false;
    }
} 
}



 