package model.domain.pizza.pizza_toppings;

import model.domain.pizza.IPizza;
import model.domain.pizza.PizzaDecorator;

public class Olive extends PizzaDecorator {

	private double cost;

    public Olive(IPizza pizza, double cost) {
        super(pizza);
        this.cost = cost;
    }

    public Olive(IPizza pizza) {
        super(pizza);
    }
   
    private String decorateWithOlive() {
        return " + Olive";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithOlive();
    }
    
    @Override
    public double getCost() {
    	return super.getCost() + cost;
	}
}