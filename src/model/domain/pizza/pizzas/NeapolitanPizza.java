package model.domain.pizza.pizzas;

import model.domain.pizza.IPizza;

public class NeapolitanPizza implements IPizza {

	private double cost;

	public NeapolitanPizza(double cost) {
		this.cost = cost;
	}

	@Override
	public String decorate() {
		return "Neapolitan Pizza";
	}
	
	@Override
	public double getCost() {
		return this.cost;
	}
}
