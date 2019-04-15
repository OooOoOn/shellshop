package com.example.shellshop;

/**
 * Created by OooOoOn on 08/04/2019.
 */

public class Shell {
	
	private String color;
	private int price;
	private String model;
	private String name;
	private int id;
	private String description;
	
	public Shell(String color, int price, String model, String name, int id, String description){
		this.color = color;
		this.price = price;
		this.model = model;
		this.name = name;
		this.id = id;
		this.description = description;
	}
	
	public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
}
