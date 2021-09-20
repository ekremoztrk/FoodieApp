package model.domain.hamburger.hamburger_toppings;

import model.domain.hamburger.HamburgerDecorator;
import model.domain.hamburger.IHamburger;

public class Cheese extends HamburgerDecorator {
	
	private double cost;

    public Cheese(IHamburger hamburger, double cost) {
        super(hamburger);
        this.cost = cost;
    }

    public Cheese(IHamburger hamburger) {
        super(hamburger);
    }

    private String decorateWithCheese() {
        return " + Cheese";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithCheese();
    }
    
    @Override
    public double getCost() {
		return super.getCost() + cost;
	}
}
