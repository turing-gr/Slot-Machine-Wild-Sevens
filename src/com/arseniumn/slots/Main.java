package com.arseniumn.slots;
import java.util.Scanner;

import com.arseniumn.slots.model.Parser;
import com.arseniumn.slots.model.SlotMachine;
import com.arseniumn.slots.model.SlotWindow;

public class Main {
	
	private static final int NUMBER_OF_PAYLINES = 10;
	private static final int BET = 1;
	private static final int TOTAL_BET = BET * NUMBER_OF_PAYLINES;

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        //Slot machine model creation
        SlotMachine slot_machine = new SlotMachine();
        
        
        //Create parser and insert the data
        Parser parser = new Parser();
        parser.parse(slot_machine);

        //Create the slot window where the user plays (reels,rows)
        SlotWindow slot_window = new SlotWindow(5,3,TOTAL_BET);
        
		long sum = 0;
		double temp_reward=0;
		int COINS = 100000;
		boolean choice = true;
		Scanner in = new Scanner(System.in);
		
        while(choice) {      	
        	System.out.println("====================================");
    		System.out.println("|| Menu || Welcome to Wild Sevens ||");
    		System.out.println("====================================");
    		System.out.println("What would you like to do?");
    		System.out.println("1.Spin & Win!");
    		System.out.println("2.Monte Carlo Simulation");
    		System.out.println("3.Exit");
    		int mode = in.nextInt();
        	//Spin and Win
        	if(mode==1) {
	        		//substract the totalbet
	        		COINS -= TOTAL_BET;
	        		
	        		//play the game
	        		slot_window.generateStops(slot_machine);
	        		temp_reward = (long) slot_window.runSimulation(COINS);      		
	        		
	        		//add the temp_reward
	        		COINS += temp_reward;
        	}	        	
	        else if(mode==2) {
	        		System.out.print("Give the number of steps:");
	        		long NUMBER_OF_STEPS = in.nextLong();
	        		for(int i=0; i<NUMBER_OF_STEPS; i++) {
		        		slot_window.generateStops(slot_machine);
		        		temp_reward = (long) slot_window.runSimulation(-1);
		                sum += temp_reward;
	        		}
	        		System.out.println("RTP = "+(double) sum/(NUMBER_OF_STEPS*NUMBER_OF_PAYLINES) * 100+"%");
	        }
	        else if(mode ==3) {
	        		choice = false;
	        		System.out.println("See ya!");
	        }

	        else {
	        	System.out.println("Not a valid choice, please try again!");
	        }
    }
        
        
    }
}