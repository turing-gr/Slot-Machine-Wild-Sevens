package com.arseniumn.slots.model;
import java.util.ArrayList;

public class Reel {
    private  ArrayList<Symbol> reels;

    public Reel(){
       this.reels = new ArrayList<Symbol>();
    }

    public void insertSymbolInCurrentReel(Symbol symbol){
        this.reels.add(symbol);
    }

    public Symbol getSymbolFromReel(int index){
        return this.reels.get(index);
    }

    public ArrayList<Symbol> getReels(){
        return this.reels;
    }
}