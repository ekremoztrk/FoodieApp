package model.domain.pizza.pizzas;

import model.domain.pizza.IPizza;

public class SicilianPizza  implements IPizza {

	private double cost;

	public SicilianPizza(double cost) {
		this.cost = cost;
	}

	@Override
    public String decorate() {
        return "Sicilian Pizza";
    }
	
	@Override
	public double getCost() {
		return this.cost;
	}
}
