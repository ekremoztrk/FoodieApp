package model.domain.pizza.pizza_toppings;

import model.domain.pizza.IPizza;
import model.domain.pizza.PizzaDecorator;

public class Mozzarella extends PizzaDecorator {

	private double cost;
	
    public Mozzarella(IPizza pizza,double cost) {
        super(pizza);
        this.cost = cost;
    }
    
    private String decorateWithMozzarella() {
        return " + Mozzarella";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithMozzarella();
    }
    
    @Override
    public double getCost() {
    	return super.getCost() + cost;
	}
}
