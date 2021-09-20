package model.domain.pizza.pizzas;

import model.domain.pizza.IPizza;

public class NewYorkStylePizza implements IPizza {

	private double cost;

	public NewYorkStylePizza(double cost) {
		this.cost = cost;
	}

	@Override
    public String decorate() {
        return "New York Style Pizza";
    }
	
	@Override
	public double getCost() {
		return this.cost;
	}
}
