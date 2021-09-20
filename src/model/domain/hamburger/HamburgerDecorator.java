package model.domain.hamburger;

public class HamburgerDecorator implements IHamburger {

	private IHamburger hamburger;

	public HamburgerDecorator(IHamburger hamburger) {
		this.hamburger = hamburger;
	}
	
	@Override
	public double getCost() {
		return hamburger.getCost();
	}
	
	@Override
	public String decorate() {
		return hamburger.decorate();
	}
}
