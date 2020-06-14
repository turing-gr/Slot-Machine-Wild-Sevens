package com.arseniumn.slots.model;
import java.util.ArrayList;

public class SlotMachine {

    private ArrayList<Reel> slot_machine;

    public SlotMachine(){
        this.slot_machine = new ArrayList<Reel>();
    }

    public void addCurrentReelInModel(Reel reel){
        this.slot_machine.add(reel);
    }

    public ArrayList<Reel> getSlotMachine(){
        return this.slot_machine;
    }
}