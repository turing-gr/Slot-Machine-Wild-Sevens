package com.arseniumn.slots.model;
import java.util.ArrayList;

public class SlotMachine {

    private ArrayList<Reel> R;

    public SlotMachine(){
        this.R = new ArrayList<Reel>();
    }

    public void addCurrentReelInModel(Reel reel){
        this.R.add(reel);
    }

    public ArrayList<Reel> getSlotMachine(){
        return this.R;
    }
}