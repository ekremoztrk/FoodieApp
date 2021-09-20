package model.domain.hamburger.hamburger_toppings;

import model.domain.hamburger.HamburgerDecorator;
import model.domain.hamburger.IHamburger;

public class Tomato  extends HamburgerDecorator {

	private double cost;

    public Tomato(IHamburger hamburger, double cost) {
        super(hamburger);
        this.cost = cost;
    }

    public Tomato(IHamburger hamburger) {
        super(hamburger);
    }

    private String decorateWithTomato() {
        return " + Tomato";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithTomato();
    }
    
    @Override
    public double getCost() {
		return super.getCost() + cost;
	}
}
