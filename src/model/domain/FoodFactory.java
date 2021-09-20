package model.domain;

import model.utilities.ToppingPricePair;

import java.util.List;

public interface FoodFactory {

	public IFood create(String type, double typeCost, List<ToppingPricePair> toppings);
}
