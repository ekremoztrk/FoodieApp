package model.domain.pizza.pizza_toppings;

import model.domain.pizza.IPizza;
import model.domain.pizza.PizzaDecorator;

public class Pepperoni extends PizzaDecorator {
	
	private double cost;

	public Pepperoni(IPizza pizza, double cost) {
		super(pizza);
		this.cost = cost;
	}

	public Pepperoni(IPizza pizza) {
		super(pizza);
	}
	
	private String decorateWithPepperoni() {
		return " + Pepperoni";
	}
	
	@Override
	public String decorate() {
		return super.decorate() + decorateWithPepperoni();
	}
	
	@Override
	public double getCost() {
		return super.getCost() + cost;
	}
}