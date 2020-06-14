package com.arseniumn.slots.model;
import java.util.ArrayList;
import java.util.Random;

import com.arseniumn.slots.ui.WindowPrinter;

public class SlotWindow {

    private int reels,rows,totalbet,line;
    private Symbol[][] slot_window;
    private int[] reel_stops;
    private int[][] paytable ={{0,0,60,200,400},
    						   {0,0,40,80,240},
							   {0,8,20,60,120},
							   {0,4,6,40,80},
							   {0,2,4,20,40},
							   {0,2,4,20,40},							   
							   {0,2,4,40,800}};  
    
    private int[][] paylines = {{0,0,0,0,0},		
								{1,1,1,1,1},
								{2,2,2,2,2},
								{0,1,2,1,0},		
								{2,1,0,1,2},
								{0,0,1,0,0},
								{2,2,1,2,2},
							    {1,0,0,0,1},
							    {1,2,2,2,1},
							    {2,1,1,1,2}};
    
    private ArrayList<Coordinates> coords = new ArrayList<Coordinates>(); 
    
    //Hold the reference to wild symbol
    private Symbol wild_symbol;
    
    public SlotWindow(int nReels, int nRows, int totalBet){
        this.reels = nReels;
        this.rows = nRows;
        this.totalbet = totalBet;
        slot_window = new Symbol[3][5];
        reel_stops = new int[]{0,0,0,0,0};        
    }

    public void generateStops(SlotMachine slotMachine){
        Random random = new Random();     
        //Generate the random stops
        for(int i=0; i<reel_stops.length; i++){
            reel_stops[i] = random.nextInt(slotMachine.getSlotMachine().get(i).getReels().size());
        }      
        //Try to not get stops that make our slot window get out of bounds coordinates
        for(int i=0; i<this.rows; i++) {
			for(int j=0; j<this.reel_stops.length; j++) {	
				if((this.reel_stops[j]+i)==slotMachine.getSlotMachine().get(i).getReels().size()) {
					this.reel_stops[j] = -1;
					slot_window[i][j] = slotMachine.getSlotMachine().get(j).getSymbolFromReel(reel_stops[j]+i);		
				}
				else {
					slot_window[i][j] = slotMachine.getSlotMachine().get(j).getSymbolFromReel(reel_stops[j]+i);		
				}
			}
		}
    }

    public double runSimulation(int coins){
    	double line_rule_prize = 0;
    	for(line=0; line<paylines.length; line++) {
    		
    		//Get the payment for each payline
    		line_rule_prize += getOnLineCombinationPrize();  		
    		
    		//For each payline we need to get back the wilds to its indices, that's why we created the Coordinates
    		//Back from substitution - flag = false
    		WildTransformation(false);
    	}
    	
    	//Screen rule has no paylines, just find the prize and multiply it with totalbet and the n out of 5 combination payment
		double screen_rule_prize = getScreenRuleCombinationPrize();
    	
    	//Clear arraylist to clear old coordinates of wild symbols
    	coords.clear();
    	
    	double reward = line_rule_prize+screen_rule_prize;
    	
    	if(coins!=-1) {
    		WindowPrinter printer = new WindowPrinter();
    		printer.printAll(slot_window, totalbet, reward, (int) (coins+reward));
    	}

    	return (reward);
    }


	private double getOnLineCombinationPrize() {
    	int combination = 0;
    	double line_rule_prize=0;
    	
    	//Look for wild and substitute e.g. symbol_01 - wild - wild - symbol_02 - symbol_03 
    	//becomes symbol_01 - symbol_01 - symbol_01 - symbol_02 - symbol_03
    	if(isThereWild()) {
    		
    		//Find the coordinations of each wild symbol
    		findCoordsOfSymbols();
    		
    		//Substitution - true flag
    		WildTransformation(true);
    	}

    	//Search for n out of 5 e.g. 4-symbol_01
		//5 out of 5
		if(slot_window[paylines[line][0]][0].getName().equalsIgnoreCase(slot_window[paylines[line][1]][1].getName()) && slot_window[paylines[line][1]][1]==slot_window[paylines[line][2]][2] && slot_window[paylines[line][2]][2]==slot_window[paylines[line][3]][3] && slot_window[paylines[line][3]][3]==slot_window[paylines[line][4]][4]) {
			combination = 5;
		}
		else if(slot_window[paylines[line][0]][0].getName().equalsIgnoreCase(slot_window[paylines[line][1]][1].getName()) && slot_window[paylines[line][1]][1]==slot_window[paylines[line][2]][2] && slot_window[paylines[line][2]][2]==slot_window[paylines[line][3]][3]){
			combination = 4;
		}
		else if(slot_window[paylines[line][0]][0].getName().equalsIgnoreCase(slot_window[paylines[line][1]][1].getName()) && slot_window[paylines[line][1]][1]==slot_window[paylines[line][2]][2]){
			combination = 3;
		}
		else if(slot_window[paylines[line][0]][0].getName().equalsIgnoreCase(slot_window[paylines[line][1]][1].getName())){
			combination = 2;
		}	

		if(combination>1) {
			//Get the payment for the n-combination of specific symbol
			line_rule_prize = paytable[getIndex()][combination-1];
		}
			
		return line_rule_prize;
    }


  
	private boolean isThereWild() {
    	boolean flag = false;
    	outerloop:  
		for(int x=0; x<this.reels; x++) {
			if(this.slot_window[paylines[line][x]][x].getName().equalsIgnoreCase("symb_wild")) {
				wild_symbol = this.slot_window[paylines[line][x]][x];
				flag = true;
				break outerloop;
			}
		}  	
    	return flag;
    }
    
    private void findCoordsOfSymbols() {
		for(int x=0; x<this.reels; x++) {
			if(this.slot_window[this.paylines[line][x]][x].getName().equalsIgnoreCase("symb_wild")) {
				coords.add(new Coordinates(this.paylines[line][x],x));
			}
		}
    	
	}
    
    private void WildTransformation(boolean transformation) {
    	for(Coordinates coordinations : coords) {
    		
    		//coords of wild
    		int temp_y = coordinations.getY();
    		int temp_x = coordinations.getX();
    		
    		if(transformation) {
    			//Set the index_before symbol as wild except for scatter   			
    			//if it's not scatter then substitute
    			if(!slot_window[this.paylines[line][temp_x-1]][temp_x-1].getName().equalsIgnoreCase("scatter_s")) {
            		slot_window[temp_y][temp_x] = slot_window[this.paylines[line][temp_x-1]][temp_x-1]; 
    			} 			
    		}
    		else {
    			//Set the index before as wild
        		slot_window[temp_y][temp_x] = wild_symbol; 	
    		}   				
    	}
  	}



	@SuppressWarnings("unused")
	private double getScreenRuleCombinationPrize() {
		double screen_rule_prize = 0;
		int mult_scatter = 1,counter = 0;
		Symbol temp_scatter = null;
		int[] counters = new int[] {0,0,0,0,0};
		for(int x=0; x<this.reels; x++) {
			for(int y=0; y<this.rows; y++) {
				if((slot_window[y][x].getName().equalsIgnoreCase("scatter_s"))) {
					temp_scatter = slot_window[y][x];
					counters[x]++;
				}
			}
			if(counters[x]>0) {	
				mult_scatter *= counters[x];
				counter++;
			}
		}	
		if(counter>0) return (paytable[Symbol.indices.get(temp_scatter.getName())][counter-1]*mult_scatter*totalbet);
		else return 0;	
    }

	private int getIndex() {
		//Get the symbol that set the pay out
		Symbol first_symbol_in_line = slot_window[paylines[line][0]][0];
		//Find the symbol in hashmap to get the index-row in paytable
		return (Symbol.indices.get(first_symbol_in_line.getName()));
	}


}