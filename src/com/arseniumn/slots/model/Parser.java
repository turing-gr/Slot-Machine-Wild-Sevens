package com.arseniumn.slots.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    public void parse(SlotMachine slotMachine){

        String csvFile = "data_slots.csv";
        String the_line = "";
        String csv_splittted_by = ",";
        boolean flag = true;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((the_line = br.readLine()) != null) { 
   
                // Read all the distinct symbols
                if(flag) {
                	 //Use comma as separator
                    String[] distinct_symbols = the_line.split(csv_splittted_by);
                    
                    // Add the symbols in hashmap
                    for(int i=0; i<distinct_symbols.length; i++) Symbol.Map.put(distinct_symbols[i],i);
                	the_line = br.readLine();
                    flag = false;
                }
                
                String[] symbols = the_line.split(csv_splittted_by);

                // Create the reels -> the current line from .csv file represents a reel
                Reel reel = new Reel();

                // Create symbols with the info from .csv file
                for(int i=0; i<symbols.length; i++){
                    switch(symbols[i]){
                        case("symb_wild"):    
                        	reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Wild"));
                            break;
                        case("sym_scatt"): 
                        	reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Scatter"));
                            break;
                        default:         
                        	reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Symbol"));
                            break;
                    }
                }       
                // Add the current reel in slot machine model          
                slotMachine.addCurrentReelInModel(reel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }           
    }

}