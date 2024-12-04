/** 
 *FIFA Simulation.
 *ICS-118 2024. 
 *
 *The Main method.
 * 
 *@author Nathan Salan
 */

import java.util.Scanner;
        
public class FIFASimulation {

    /**
     * @param args the command line arguments
     */
    
    public static final int MAX_TEAM_SIZE = 11, MAX_TIME = 90;
    
    public static Player[] team1, team2; //An array that holds Players.
    public static String team1Name = "FC Boogle Ban", team2Name = "Team Hupple";
    
    public static int matchCount = 0; //A count that keeps track the number of games played.
    public static int[][] matches = new int[0][]; //An array to keep track the match history.
    
    public static String[] playerNamesTeam1 = {
        "Lavern Graham",
        "Benton Clarke",
        "Mac Murray",
        "Louis Perkins",
        "Marion Hudson",
        "Rocky Haney",
        "Humberto Lloyd",
        "Mervin Wells",
        "Jarvis Brady",
        "Warner Huynh",
        "Edmond Cline"
    };
    
    public static String[] playerNamesTeam2 = {
        "Steve Dunn",
        "Andre Donaldson",
        "Gayle Gentry",
        "Erick Tucker",
        "Arlen Arias",
        "Jerrell Gill",
        "Tim Juarez",
        "Mauricio Anthony",
        "Malcom Reeves",
        "Ismael Cowan",
        "Marco Carlson"
    };
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        boolean valid = false;
        Scanner input = new Scanner(System.in);
        
        System.out.println("FIFA Simulation Begins.\n");
        startGame();
        
        do{
            System.out.println("\nGame Menu");
            System.out.println("1) Simulate Match");
            System.out.println("2) Display Team");
            System.out.println("3) Display All Match Results");
            System.out.println("4) Reset");
            System.out.println("0) Exit.");
            
            int choice = input.nextInt();
            
            switch(choice){
                
                case 0: //Exit game
                    valid = true;
                    break;
                    
                case 1: //Game Simulation
                        simulateGame();
                    break;
                    
                case 2: //Display Team
                    boolean chosen = false;
                    while(!chosen){
                        System.out.println("\nChoose a team to display:");
                        System.out.println("1) "+team1Name);
                        System.out.println("2) "+team2Name);
                        System.out.println("0) Exit");
                        
                        int pick = input.nextInt();
                        
                        if(pick == 1){    
                            System.out.println(displayTeam(team1));
                            chosen = true;
                        } else if(pick == 2){
                            System.out.println(displayTeam(team2));
                            chosen = true;
                        } else if(pick == 0){
                            chosen = true;
                        }
                    }
                    break;
                
                case 3: //Display All Match Results
                    System.out.println(displayMatches());
                    break;
                    
                case 4: //Reset the game
                    startGame();
                    break;
            }
            
        }while(!valid);
        
        System.out.println("FIFA Simulation has ended.");
    }
    
    public static void startGame(){
        /**
         *This method is used to start a new game.
         *
         *It recreates the team which will reset all of its stats to 0.
         *It will also erase the match history.
         */
        team1 = createTeam(team1Name);
        team2 = createTeam(team2Name);
        matches = new int[0][];
    }
    
    public static void simulateGame(){
        /** 
         *This Method is used to Simulate the game.
         *This runs through the game time. Triggering events after every minute
         *in a "chance by chance" basis.
         *
         *Once the time passes, it will display the winner team and the end score.
         */
        clear();
        
        for(int i = 0; i < MAX_TIME;i++){
            
            if(i == 45){
                System.out.println("------ Half-Time! ------ \n");
            }
            
            int eventChance = randomSpan(0,35);
            
            if(eventChance <= 4){
                System.out.print("Game Time: "+displayTimer(i)+" - ");
                eventHandler(eventChance);
                System.out.println("");
            }
            
        }
        
        System.out.println("\n"+displayScore());
        System.out.println(winner());
        
    }
    
    public static void clear(){
        /**
         *This method is used to clear the player's goals of the match.
         */
        for(Player player : team1){
            player.setGoal(0);
        }
        for(Player player : team2){
            player.setGoal(0);
        }
    }
    
    public static void eventHandler(int num){
        /**
         *This method is used to handle all the event throughout the game.
         *
         *@param int - the number index that triggers the event.
         */
        
        int playerNum;
        
        switch(num){
            
            case 0:
            case 1:
                // Team 1 wins a goal
                playerNum = randomSpan(6,MAX_TEAM_SIZE-1);
                System.out.println(team1[playerNum]+" has Scored a Goal!");
                team1[playerNum].goal();
                
                break;
                
            case 2:
            case 3:
                // Team 2 wins a goal
                playerNum = randomSpan(6,MAX_TEAM_SIZE-1);
                System.out.println(team2[playerNum]+" has Scored a Goal!");
                team2[playerNum].goal();
                
                break;
        
            case 4: 
                // Penalty
                penalty();
                break;
        }
        
    }
    
    public static String winner(){
        /**
         *This method is used to figure out the winning team of the game.
         *
         *It returns a String of the winning team's name and also the MVP of the
         *game.
         *
         *@return String - The end of match winner name and the MVP.
        */
        
        String out = "";
        
        if(getTotalGoals(team1) > getTotalGoals(team2)){
            out = team1Name+" WINS!";
            out+= " | "+displayMVP(team1);
        } else if(getTotalGoals(team1) < getTotalGoals(team2)){
            out = team2Name+ " WINS!";
            out+= " | "+displayMVP(team2);
        } else {
            System.out.println("It's a TIE!");
        }
        
        return out;
    }
    
    public static void saveResults(){
        /**
         *This method is used to save the current match score to the match
         *history after every match.
        */
        int[] toAdd = new int[2];
        int[][] newMatches = new int[matches.length+1][];
        
        toAdd[0] = getTotalGoals(team1); 
        toAdd[1] = getTotalGoals(team2); 
        
        if(matches.length == 0){
            matches = new int[1][];
            matches[0] = toAdd;
        }
        
        else{
            for(int i = 0; i < matches.length;i++){
               newMatches[i]=matches[i];
            }
            newMatches[newMatches.length-1] = toAdd;

            matches = newMatches;
        }
    }
    
    public static String displayMatches(){
        /**
         *This method is used to display match history.
         *If the history is empty (No match played), then it will show no games.
         * 
         * @return String - The Match History.
         */
        String out="";
        
        if(matches.length == 0){
            out+="\nThere are no matches saved.";
        }
        
        else{
            for(int i = 0; i < matches.length;i++){

                 out+="\nMatch "+(i+1)+": "+team1Name+" vs "+team2Name+"\n";
                for(int j = 0; j < matches[i].length;j++){
                    out+=matches[i][j];

                    if(j == 0){
                        out+=" - ";
                    }
                }
            }
        }
        
        return out;
    }
    
    public static String displayTimer(int minutes){
        /**
         *This method is used to produce and format the game time. 
         *
         *@param int - The minutes of the game time.
         *
         *@return String - the formatted display of game time.
         */
        String out = "";
        int seconds = randomSpan(0,60);
        
        // To format as a "mm:ss" display.
        out = String.format("%02d", minutes)+":"+String.format("%02d", seconds);
                
        return out;
    }
    
    public static String displayScore(){
        /**
         *This method is used to produce and format the scores.
         *It makes sure to save the results as well by calling the
         *saveResults() method.
         * 
         *@return String - The score of the two teams.
        */
        
        saveResults();
        return "("+team1Name+": "+getTotalGoals(team1)+" | "+team2Name+": "+getTotalGoals(team2)+")";
    }
    
    public static String displayMVP(Player[] team){
        /**
         *This method is used to display the MVP of the team chosen.
         *This can be achieved by checking each player's goal count and comparing
         *who has the most goals.
         *
         *@param Player[] - The team which you want to see the MVP of.
         *
         *@return String - The MVP's name and the number of goals they had made.
        */
        String out = "";
        Player mvp = null;
        int mvpGoals = 0;
        
        for(Player player : team){
            if(player.getGoals() > mvpGoals){
                mvp = player;
                mvpGoals = player.getGoals();
            }
        }
        out = "MVP: "+mvp+" with "+mvpGoals+" Goals!";
     
        return out;
    }
    
    public static String displayPlayer(Player[] team, int num){
        /**
         *This method is used to display the player's stats.
         *
         *@param Player[] - The chosen team.
         *@param int - the player's team number.
         *
         *return String - The Player's game data.
         */
        return team[num].toString();
    }
    
    public static int getTotalGoals(Player[] team){
        /**
         * This method is used to get the total goals of the chosen team.
         *This is done by going through each player in the team and adding up all
         *their total goals made.
         *
         *@param Player[] - The team that you want to check the total goal of.
         *
         *@return int - the total number of goals the chosen team has made.
         */
        int sum=0;
        
        for(Player player : team){
            if(player.getGoals() > 0){
                sum += player.getGoals();
            }
        }
        
        return sum;
    }
    
    public static void playerGoal(Player player){
        /**
         *This method is used to add a goal to a player.
         *
         *@param Player - The chosen player to add a goal count to.
         */
        player.goal();
    }
    
    public static Player[] createTeam(String team){
        /**
         *This method is used to create a team by creating players and adding
         *them into the given team array.
         *
         *@param String - The name of the team.
         *
         *@return Player[] - An array of Players.
        */
        
        Player[] newTeam = new Player[MAX_TEAM_SIZE];

        for(int i = 0; i<MAX_TEAM_SIZE;i++){
            String roles = "";
            
            if(i == 0){
               roles = "Goal Keeper"; 
            }else if(i <= 5){
                roles = "Back";
            } else if (i <= 8){
                roles = "Center";
            } else if(i<=10){
                roles="Forward";
            }
            
            String name="";
            
            if(team.equalsIgnoreCase(team1Name)){
                name = playerNamesTeam1[i];
            } else if(team.equalsIgnoreCase(team2Name)){
                name = playerNamesTeam2[i];
            }
            
            newTeam[i] = new Player(name,roles,team);
        }
        
        return newTeam;
    }
    
    public static void penalty(){
        /**
         *This method simulates the penalty shoot out.
         *The team either gets penalty or gets a free kick.
         *
         *The method will randomly select a player from a team to be the kicker.
         *The kicker will have a chance to either land a goal or miss.
         *
         */
        
        int rng = randomSpan(1,2);
        
        if(rng == 1){
            // A shoot out for team 1.
            System.out.println("Penalty was given to "+team2Name);
            
            rng = randomSpan(4,team1.length-1);
            Player kicker = team1[rng];
            
            System.out.println(team1Name+": "+kicker.getName()+" gets a free kick.");
            
            rng = randomSpan(1,3);
            if(rng == 1){
                System.out.println(kicker.getName()+" scored a GOAL!");
                kicker.goal();
            } else {
                System.out.println(kicker.getName()+" missed his shot!");
            }
        } else{
            // A shoot out for team 2.
            System.out.println("Penalty was given to "+team1Name);
            
            rng = randomSpan(4,team2.length-1);
            Player kicker = team2[rng];
            
            System.out.println(team2Name+": "+kicker.getName()+" gets a free kick.");
            
            rng = randomSpan(1,3);
            if(rng == 1){
                System.out.println(kicker.getName()+" scored a GOAL!");
                kicker.goal();
            } else {
                System.out.println(kicker.getName()+" missed his shot!");
            }
        }
    }
    
    public static String displayTeam(Player[] team){
        /**
         *This method is used to display every Player in the team.
         *
         *@param Player[] - A team to display all the players.
         *
         *@return String - The name and position of each players in the whole team
         */
        String out="";
        
        for(int i = 0; i < team.length;i++){
            out += team[i];
            
            if(i < team.length-1){
                out+="\n";
            }
        }
        
        return out;
    }
    
    public static int randomSpan(int min, int max){
        /**
         *This method is used to generate a random number between the min number
         *to the max number as Integers.
         *
         *@param int - The smallest number in the span.
         *@param int - The largest number in the span.
         *
         *@return int - The random number generated between the two given inputs.
        */
        
        int out = 0;
        
        out = min + (int)(Math.random()*((max-min)+1));
        
        return out;
    }
    
}
