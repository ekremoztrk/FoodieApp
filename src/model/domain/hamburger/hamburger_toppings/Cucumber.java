package model.domain.hamburger.hamburger_toppings;

import model.domain.hamburger.HamburgerDecorator;
import model.domain.hamburger.IHamburger;

public class Cucumber extends HamburgerDecorator {

    public Cucumber(IHamburger hamburger) {
        super(hamburger);
    }
    public String decorate() {
        return super.decorate() + decorateWithCucumber();
    }

    private String decorateWithCucumber() {
        return " with Cucumber";
    }
}
