package com.arseniumn.slots.model;
import java.util.ArrayList;

public class Reel {
    private  ArrayList<Symbol> reel;

    public Reel(){
       this.reel = new ArrayList<Symbol>();
    }

    public void insertSymbolInCurrentReel(Symbol symbol){
        this.reel.add(symbol);
    }

    public Symbol getSymbolFromReel(int index){
        return this.reel.get(index);
    }

    public ArrayList<Symbol> getReel(){
        return this.reel;
    }
}