package model.domain;

import model.utilities.FoodCostPair;
import model.utilities.ToppingPricePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu {

	private static int idCounter=0;
	private int id;
	private String name;
	private Map<FoodCostPair, List<ToppingPricePair>> items;
	
	public Menu(String name, Map<FoodCostPair, List<ToppingPricePair>> items) {
		this.id=idCounter;
		idCounter++;
		this.name = name;
		this.items = items;
	}
	public Menu(int id,String name, Map<FoodCostPair, List<ToppingPricePair>> items) {
		this.id=id;
		this.name = name;
		this.items = items;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<FoodCostPair, List<ToppingPricePair>> getItems() {
		return items;
	}

	public void setItems(Map<FoodCostPair, List<ToppingPricePair>> items) {
		this.items = items;
	}

	public List<String> getItemNames(){
		List<String> itemNames= new ArrayList<>();
		for(FoodCostPair item : getItems().keySet())
			itemNames.add(item.getFood());
		return itemNames;
	}
}
