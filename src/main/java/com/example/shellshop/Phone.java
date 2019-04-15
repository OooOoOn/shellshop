package com.example.shellshop;

/**
 * Created by OooOoOn on 08/04/2019.
 */

public class Phone {
	
	private String model;
	private String manufacturer;
	private int weight;
	
	public Phone(String model, String manufacturer, int weight){
		this.model = model;
		this.manufacturer = manufacturer;
		this.weight = weight;
	}
	
	public String getModel() {
        return model;
    }
	
    public String getManufacturer() {
        return manufacturer;
    }
    
    public int getWeight() {
        return weight;
    }
		
}
