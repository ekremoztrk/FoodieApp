package model.domain.hamburger.hamburger_toppings;

import model.domain.hamburger.HamburgerDecorator;
import model.domain.hamburger.IHamburger;

public class Lettuce extends HamburgerDecorator {

	private double cost;

    public Lettuce(IHamburger hamburger, double cost) {
        super(hamburger);
        this.cost = cost;
    }

    public Lettuce(IHamburger hamburger) {
        super(hamburger);
    }
    
    private String decorateWithLettuce() {
        return " + Lettuce";
    }
    
    @Override
    public String decorate() {
        return super.decorate() + decorateWithLettuce();
    }
    
    @Override
    public double getCost() {
		return super.getCost() + cost;
	}
}
