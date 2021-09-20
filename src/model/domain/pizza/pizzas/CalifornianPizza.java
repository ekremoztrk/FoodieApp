package model.domain.pizza.pizzas;

import model.domain.pizza.IPizza;

public class CalifornianPizza implements IPizza {

	private double cost;

	public CalifornianPizza(double cost) {
		this.cost = cost;
	}

	@Override
    public String decorate() {
        return "Californian Pizza";
    }
	
	@Override
	public double getCost() {
		return this.cost;
	}


}
