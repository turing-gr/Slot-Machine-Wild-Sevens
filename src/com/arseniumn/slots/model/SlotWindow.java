package com.arseniumn.slots.model;
import java.util.ArrayList;
import java.util.Random;

import com.arseniumn.slots.ui.WindowPrinter;

public class SlotWindow {

    private int m, n, totalBet, lineBet;
    private Symbol[][] W;
    private int[] RS;
    private int[][] P = {
	    		         {0,2,4,20,40},
	    		         {0,2,4,20,40},
			         	 {0,0,5,75,150},
			         	 {0,0,5,75,150},
		                 {0,0,12,120,200},
		                 {0,0,12,120,200},
		                 {0,5,50,500,1000}
			       };
    private int[][] L = {
	    		         {0,0,0,0,0},		
	    		         {1,1,1,1,1},
						 {2,2,2,2,2},
						 {0,1,2,1,0},		
						 {2,1,0,1,2},
						 {0,0,1,0,0},
						 {2,2,1,2,2},
						 {1,0,0,0,1},
						 {1,2,2,2,1},
						 {2,1,1,1,2}
    			       };
    private ArrayList<Coordinates> coords = new ArrayList<Coordinates>(); 
    private Random random = new Random();     
    private Symbol wild;
    
    public SlotWindow(int nReels, int nRows, int totalBet, int aLineBet){
    	
    	// The m rows
        this.m = nRows;

        // The n columns-reels
        this.n = nReels;
        
        // The totalbet
        this.totalBet = totalBet;
        
        // The linebet
        this.lineBet = aLineBet;
        
        // The 3x5 random window instance
        W = new Symbol[3][5];
        
        // The Reels' Stops
        RS = new int[]{0,0,0,0,0};        
    }

    public void generateStops(SlotMachine slotMachine){
    	
    	// An auxiliary variable that holds the current reel's (column) size
    	int current_size = 0;
    	
    	// Generate the random stops
        for(int i=0; i<RS.length; i++) {
        	current_size = slotMachine.getSlotMachine().get(i).getReel().size();
            RS[i] = random.nextInt(current_size);  
        }
        
        // Put the symbols from reels with the stops from RS matrix in window instance one
        for(int i=0; i<this.m; i++) {
			for(int j=0; j<this.RS.length; j++) {
				int stopIndexElement = this.RS[j];
				int size = slotMachine.getSlotMachine().get(j).getReel().size();
				W[i][j] = slotMachine.getSlotMachine().get(j).getSymbolFromReel((stopIndexElement+i)%size);
			}
        }
    }

    public double runSimulation(int coins){
    	double line_rule_prize = 0;
    	
    	// Save the wild coordinates in window instance
    	saveWildCoordinates();
    	    		
    	// Get the payment for each payline
    	line_rule_prize += getOnPaylinePrize();  		
    	
    	// Screen rule has no paylines, just find the prize and multiply it with totalbet and the n out of 5 combination payment
    	double screen_rule_prize = getScreenRuleCombinationPrize();
    	coords.clear();
    	double reward = line_rule_prize+screen_rule_prize;
    	if(coins!=-1) {
    		WindowPrinter printer = new WindowPrinter();
    		printer.printAll(W, totalBet, reward, (int) (coins+reward));
    	}
    	return reward;
    }

	private void saveWildCoordinates() {
		for(int i=0; i<this.m; i++) {
			for(int j=0; j<this.n; j++) { 
				if(this.W[i][j].getType().equalsIgnoreCase("Wild")) {
					coords.add(new Coordinates(i, j));	
					wild = this.W[i][j];
				}
			}
		}	
	}

	private double getOnPaylinePrize() {
    	double total_payline_prize = 0;
    	for(int i=0; i<L.length; i++) {
    		if(!W[L[i][0]][0].getType().equalsIgnoreCase("Scatter")) {
	        	int aPair = 0;
		    	for(int j=0; j<this.n-1; j++) {
		    		Symbol current_symbol = W[L[i][j]][j];
		    		Symbol next_symbol = W[L[i][j+1]][j+1];
					if(current_symbol.getName().equalsIgnoreCase(next_symbol.getName()) || next_symbol.getType().equalsIgnoreCase("Wild")) {
						W[L[i][j+1]][j+1] = current_symbol;
						aPair++;
					}
					else
						break;   			
		    	}
		
				// Get the payment for the n-combination of specific symbol
		    	if(aPair>0)
		    		total_payline_prize += (P[getIndex(i)][aPair]*this.lineBet);  	
		    	
		    	//int coins = -1;
		    	//if(coins!=-1) {
		    		//	WindowPrinter printer = new WindowPrinter();
		    		//	printer.printAll(W, totalBet, 0.0, (int) (coins+0.0));
		    		//}
		    	// Retrieve wild symbols on window instance
		    	retrieveWildSymbols();	
    		}
    	}
		return total_payline_prize;
    }
	
	private int getIndex(int aLine) {
		int line = aLine;
		
		//Get the symbol that set the pay out
		Symbol first_symbol_in_line = W[L[line][0]][0];
		
		//Find the symbol in hashmap to get the index-row in paytable
		return (Symbol.Map.get(first_symbol_in_line.getName()));
	}
	
	private void retrieveWildSymbols() {
		for(Coordinates coord:coords) 
			this.W[coord.getX()][coord.getY()] = wild;
	}

	private double getScreenRuleCombinationPrize() {
		int times = 1, aPair = 0;
		Symbol temp_scatter = null;
		int[] C = new int[] {0,0,0,0,0};
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.m; j++) {
				if((W[j][i].getType().equalsIgnoreCase("Scatter"))) {
					temp_scatter = W[j][i];
					C[i]++;
				}
			}
			if(C[i]>0) {	
				times *= C[i];
				aPair++;
			}
		}	
		if(aPair>0) return (P[Symbol.Map.get(temp_scatter.getName())][aPair-1]*times*totalBet);
		else return 0;	
    }
}