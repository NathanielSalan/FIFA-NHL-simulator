/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nathan
 */
public class Player {
    
    private String name,roles,team;
    private int goals = 0;
    
    public Player(){
        
    }
    
    public Player(String name,String roles,String team){
        /** 
         * This is the Player class constructor.
         *
         *@param String - The Player's name.
         *@param String - The Player's role.
         *@param String - The Player's Team name.
         *
         */
        this.name = name;
        this.roles = roles;
        this.team = team;
    }
    
    public String getName(){
        /**
         * This is a getter method for the Player's name.
         *
         *@return String - The Player's name.
         */
        return this.name;
    }
    
    public String getRoles(){
        /** 
         * This is a getter method for the Player's role.
         *
         * @param String - The Player's role.
         */
        return this.roles;
    }
    
    public int getGoals(){
        /** 
         * This is a getter method for the Player's goals.
         *
         * @param int - The number of Goals.
         */
        return this.goals;
    }
    
    public void setGoal(int goals){
        /**
         * This is a setter method for the Player's goals.
         * 
         * @param int - The number of goals to set the Player to have.
         */
        this.goals = goals;
    }
    
    public void goal(){
        /**
         * This method adds a goal to the Player's goal counter.
         */
        this.goals++;
    }
    
    //ToString
    public String toString(){
        return team+" - "+name+"("+roles+")";
    }
    
}
