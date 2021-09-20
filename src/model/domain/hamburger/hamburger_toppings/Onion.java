package model.domain.hamburger.hamburger_toppings;

import model.domain.hamburger.HamburgerDecorator;
import model.domain.hamburger.IHamburger;

public class Onion extends HamburgerDecorator {

	private double cost;

    public Onion(IHamburger hamburger, double cost) {
        super(hamburger);
        this.cost = cost;
    }

    public Onion(IHamburger hamburger) {
        super(hamburger);
    }

    private String decorateWithOnion() {
        return " + Onion";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithOnion();
    }
    
    @Override
    public double getCost() {
		return super.getCost() + cost;
	}
}
