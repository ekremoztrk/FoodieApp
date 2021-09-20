package model.domain.pizza;

public abstract class PizzaDecorator implements IPizza {
	private IPizza pizza;

	public PizzaDecorator(IPizza pizza) {
		this.pizza = pizza;
	}

	@Override
	public double getCost() {
		return pizza.getCost();
	}

	@Override
	public String decorate() {
		return pizza.decorate();
	}
}
