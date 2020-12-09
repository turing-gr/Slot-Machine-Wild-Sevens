package com.arseniumn.slots.model;
import java.util.HashMap;

public class Symbol {
	
    private String name,type;
    private int id;
    public static HashMap<String,Integer> Map = new HashMap<String,Integer>();

    public Symbol(int anId, String aName, String aType){
    	this.id = anId;
        this.name = aName;
        this.type = aType;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public HashMap<String, Integer> getIndices() {
    	return Map;
    }

    public int getId() {
    	return id;
    }

    public void setId(int id) {
    	this.id = id;
    } 
}