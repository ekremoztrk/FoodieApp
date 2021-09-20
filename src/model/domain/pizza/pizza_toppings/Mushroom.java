package model.domain.pizza.pizza_toppings;

import model.domain.pizza.IPizza;
import model.domain.pizza.PizzaDecorator;

public class Mushroom extends PizzaDecorator {

	private double cost;

    public Mushroom(IPizza pizza, double cost) {
        super(pizza);
        this.cost = cost;
    }

    public Mushroom(IPizza pizza) {
        super(pizza);
    }

    private String decorateWithMushroom() {
        return " + Mushroom";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithMushroom();
    }

    @Override
    public double getCost() {
    	return super.getCost() + cost;
	}
}
