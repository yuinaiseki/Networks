
public class Driver {

    public static final int[] LATICE_DIMENTIONS = { 12, 12 };
    public static final int NUM_AGENTS = LATICE_DIMENTIONS[0] * LATICE_DIMENTIONS[1];
    public static final String[] LIFE_STATES = { "dead", "alive" };

    public double[] LIFE_POINT_REWARDS = { 1.25, 1, 0, 0 }; // 1.01 1.25 1.5 2 beta
    public double LIFE_POINT_THRESHOLD = 2; // 0.75 0.5 0.2 alpha

    public Agent[] Agents;
    public double[][] CooperationScore;
    public double[][] SummingMatrix;
    public MatrixDouble mat;

    public Driver() {

    }

    public void initialize() {
        Agents = new Agent[NUM_AGENTS];
        mat = new MatrixDouble();
        setupAgents();
        setupConnections("square");

        // System.out.println("Cooperation Matrix");
        // mat.printMatrix(CooperationScore);

        SummingMatrix = mat.createMatrix(1, NUM_AGENTS, 1);
    }

    public int[] runGame(double alpha, double beta) {
        LIFE_POINT_THRESHOLD = 4 * alpha;
        LIFE_POINT_REWARDS[0] = beta;

        initialize();

        // System.out.println("ALPHA: " + LIFE_POINT_THRESHOLD + " BETA: " +
        // LIFE_POINT_REWARDS[0]);

        boolean gameRunning = true;

        double[] payoffs;
        double lifeScore;
        int i;
        int NumRuns = 0;
        while (gameRunning && NumRuns < 40) {
            // System.out.println("\nRound " + NumRuns);
            payoffs = mat.matrixMultiply(SummingMatrix, CooperationScore)[0];
            for (i = 0; i < NUM_AGENTS; i++) {

                // System.out.print("Agent " + i + Agents[i].printAgent());

                if (!Agents[i].isAlive()) {
                    // System.err.println("");
                    continue;
                }

                // Deaths
                lifeScore = payoffs[i];
                // System.out.print(" {" + lifeScore + "} ");
                if (lifeScore < LIFE_POINT_THRESHOLD) {
                    // Agent Dies
                    // System.out.println("DIES");
                    handleAgentDeath(i);
                } else {
                    // Updating Stratagies
                    updateStratagies(i, payoffs);
                }

            }
            NumRuns++;
        }
        return getEnv();
    }

    public int[] getEnv() {
        int numCooperator = 0;
        int numDefector = 0;
        int numDead = 0;
        for (int i = 0; i < NUM_AGENTS; i++) {
            if (Agents[i].isAlive()) {
                if (Agents[i].isCooperator()) {
                    numCooperator += 1;
                } else {
                    numDefector += 1;
                }
            } else {
                numDead += 1;
            }
        }
        int[] rtn = { numCooperator, numDefector, numDead };
        // System.out.println(
        // "Num Cooperators: " + numCooperator + "\nNum Defectors: " + numDefector +
        // "\nNum Dead: " + numDead);
        return rtn;

    }

    public void setupAgents() {
        Agents = new Agent[NUM_AGENTS];
        for (int i = 0; i < NUM_AGENTS; i++) {
            Agents[i] = new Agent();
        }
        int startingDefector = (int) NUM_AGENTS / 2;
        Agents[startingDefector].setCooperator(false);
        // System.out.println("Defector " + startingDefector);
    }

    public void setupConnections(String latice) {
        CooperationScore = mat.createMatrix(NUM_AGENTS, NUM_AGENTS, 0);
        if (latice == "square") {
            int M = LATICE_DIMENTIONS[0];
            int N = LATICE_DIMENTIONS[1];
            int Ai, Aj;
            for (Ai = 0; Ai < NUM_AGENTS; Ai++) {
                int num = 0;
                int[] connections = new int[4];
                for (Aj = 0; Aj < NUM_AGENTS; Aj++) {
                    int AiRow = Ai / N;
                    int AiCol = Ai % N;
                    int AjRow = Aj / N;
                    int AjCol = Aj % N;
                    // TOP
                    if (Ai - Aj == N || Ai - Aj == (M - 1) * N) {
                        CooperationScore[Ai][Aj] = calculateLifePoints(Ai, Aj);
                        connections[num] = Aj;
                        num++;
                    }
                    // Bottom
                    if (Ai - Aj == -N || Ai - Aj == -(M - 1) * N) {
                        CooperationScore[Ai][Aj] = calculateLifePoints(Ai, Aj);
                        connections[num] = Aj;
                        num++;
                    }
                    // Left
                    if (AiRow == AjRow && (AiCol - AjCol == 1 || AiCol - AjCol == N - 1)) {
                        CooperationScore[Ai][Aj] = calculateLifePoints(Ai, Aj);
                        connections[num] = Aj;
                        num++;
                    }
                    // Right
                    if (AiRow == AjRow && (AiCol - AjCol == -1 || AiCol - AjCol == 1 - N)) {
                        CooperationScore[Ai][Aj] = calculateLifePoints(Ai, Aj);
                        connections[num] = Aj;
                        num++;
                    }
                }

                Agents[Ai].connections = connections;

                // Printing Connections
                // String str = "";
                // for (int i = 0; i < Agents[Ai].connections.length; i++) {
                // str = str + Agents[Ai].connections[i] + " ";
                // }
                // System.out.println("Agent " + Ai + " Connections " + str);
            }
        }
    }

    public void updateStratagies(int AgentNumber, double[] payoffs) {
        double agentLifePoints = payoffs[AgentNumber];
        boolean agentStrat = Agents[AgentNumber].isCooperator();
        int numConnections = Agents[AgentNumber].connections.length;
        if (numConnections == 0) {
            // System.out.println("Agent has no connections");
            return;
        }

        double doubleRandomNumber = Math.random() * numConnections;
        int randomNumber = (int) doubleRandomNumber;

        int otherAgent = Agents[AgentNumber].connections[randomNumber];
        double otherAgentLifePoints = payoffs[otherAgent];
        boolean otherAgentStrat = Agents[otherAgent].isCooperator();

        if (agentStrat == otherAgentStrat) {
            // System.out.println("Same Strat");
            // System.out.println("");
            return;
        }

        double CONSTANT = 0.1;
        double prob = 1 / (1 + Math.exp(-(otherAgentLifePoints - agentLifePoints) / CONSTANT));
        double randNum = Math.random();
        if (prob > randNum) {
            // System.out.println("Changing Sides");
            Agents[AgentNumber].setCooperator(otherAgentStrat);
            updateLifePoints(AgentNumber);
        } else {
            // System.out.println("No Change");
            // System.out.println("");

        }
    }

    public void handleAgentDeath(int AgentNumber) {
        // kill agent
        Agents[AgentNumber].setAlive(false);
        // remove cooperation scores
        for (int i = 0; i < NUM_AGENTS; i++) {
            CooperationScore[AgentNumber][i] = 0;
            CooperationScore[i][AgentNumber] = 0;
        }
        // Remove connections
        int[] connections = Agents[AgentNumber].connections;
        for (int i = 0; i < connections.length; i++) {
            int[] cons = Agents[connections[i]].connections;
            int[] newCons = new int[cons.length - 1];
            int newConsNum = 0;
            for (int j = 0; j < cons.length; j++) {
                if (cons[j] != AgentNumber) {
                    newCons[newConsNum] = cons[j];
                    newConsNum++;
                }
            }
            Agents[connections[i]].connections = newCons;
        }
        Agents[AgentNumber].connections = new int[0];

    }

    public void updateLifePoints(int AgentNumber) {
        for (int i = 0; i < NUM_AGENTS; i++) {

            boolean isAdjacent = CooperationScore[AgentNumber][i] != 0;
            if (isAdjacent) {
                CooperationScore[AgentNumber][i] = calculateLifePoints(AgentNumber, i);
                CooperationScore[i][AgentNumber] = calculateLifePoints(i, AgentNumber);
            }
        }

    }

    public double calculateLifePoints(int Aj, int Ai) {
        // System.out.println("Calculating Life Points");
        if (Aj == Ai) {
            return 0;
        }
        if (!Agents[Ai].isAlive() || !Agents[Aj].isAlive()) {
            return 0;
        }
        boolean AiCoop = Agents[Ai].isCooperator();
        boolean AjCoop = Agents[Aj].isCooperator();
        // System.out.println("Coop " + AiCoop + ":" + AjCoop);
        if (AiCoop) {
            if (AjCoop) {
                // Both are cooperators
                return LIFE_POINT_REWARDS[1];
            } else {
                // Aj is a defector
                return LIFE_POINT_REWARDS[3];
            }
        } else {
            if (AjCoop) {
                // Ai is a defector
                return LIFE_POINT_REWARDS[0];
            } else {
                // both are defectors
                return LIFE_POINT_REWARDS[2];
            }
        }
    }
}
