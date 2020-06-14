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
   
                // we need to read all the distinct symbols
                if(flag) {
                	 //Use comma as separator
                    String[] distinct_symbols = the_line.split(csv_splittted_by);
                    
                    //add the symbols in hashmap
                    for(int i=0; i<distinct_symbols.length; i++) Symbol.indices.put(distinct_symbols[i],i);
                	the_line = br.readLine();
                    flag = false;
                }
                
                String[] symbols = the_line.split(csv_splittted_by);

                //Create the reels -> the current line from csv represents a reel
                Reel reel = new Reel();

                //Create symbols with the info from .csv
                for(int i=0; i<symbols.length; i++){
                    switch(symbols[i]){
                        case("wild"):    reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Wild"));
                            break;
                        case("scatter"): reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Scatter"));
                            break;
                        default:         reel.insertSymbolInCurrentReel(new Symbol(i,symbols[i],"Symbol"));
                            break;
                    }
                }       
                //Add the current reel in slot machine model          
                slotMachine.addCurrentReelInModel(reel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }           
    }

}