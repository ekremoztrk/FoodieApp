package model.domain.pizza;

import java.util.ArrayList;
import java.util.List;

import model.domain.FoodFactory;
import model.domain.pizza.pizza_toppings.*;
import model.domain.pizza.pizzas.CalifornianPizza;
import model.domain.pizza.pizzas.NeapolitanPizza;
import model.domain.pizza.pizzas.NewYorkStylePizza;
import model.domain.pizza.pizzas.SicilianPizza;
import model.utilities.ToppingPricePair;

public class PizzaFactory implements FoodFactory {

	@Override
	public IPizza create(String type, double pizzaCost, List<ToppingPricePair> toppings) {
		IPizza pizza = null;
		if (containsIgnoreCase(type, "neapolitan")) {
			pizza = new NeapolitanPizza(pizzaCost);
		}
		else if (containsIgnoreCase(type, "californian")) {
			pizza = new CalifornianPizza(pizzaCost);
		}
		else if (containsIgnoreCase(type, "new york") || containsIgnoreCase(type, "newyork")) {
			pizza = new NewYorkStylePizza(pizzaCost);
		}
		else if (containsIgnoreCase(type, "sicilian")) {
			pizza = new SicilianPizza(pizzaCost);
		}
		for (ToppingPricePair topping : toppings) {
			if (topping.getTopping().equalsIgnoreCase("pepperoni")) {
				pizza = new Pepperoni(pizza,topping.getCost());
			}
			else if (topping.getTopping().equalsIgnoreCase("mozzarella")) {
				pizza = new Mozzarella(pizza,topping.getCost());
			}
			else if (topping.getTopping().equalsIgnoreCase("corn")) {
				pizza = new Corn(pizza,topping.getCost());
			}
			else if (topping.getTopping().equalsIgnoreCase("mushroom")) {
				pizza = new Mushroom(pizza,topping.getCost());
			}
			else if (topping.getTopping().equalsIgnoreCase("olive")) {
				pizza = new Olive(pizza,topping.getCost());
			}
		}
		
		return pizza;
	}
	
	public static List<String> getFoodTypes() {
		List<String> types = new ArrayList<>();
		types.add("Neapolitan Pizza");
		types.add("Californian Pizza");
		types.add("New York Style Pizza");
		types.add("Sicilian Pizza");
		return types;
	}
	
	public static List<String> getToppings() {
		List<String> toppings = new ArrayList<>();
		toppings.add("Corn");
		toppings.add("Mozarella");
		toppings.add("Mushroom");
		toppings.add("Olive");
		toppings.add("Pepperoni");
		return toppings;
	}
	
	private boolean containsIgnoreCase(String src, String what) {
	    final int length = what.length();
	    if (length == 0)
	        return true;

	    final char firstLo = Character.toLowerCase(what.charAt(0));
	    final char firstUp = Character.toUpperCase(what.charAt(0));

	    for (int i = src.length() - length; i >= 0; i--) {
	        final char ch = src.charAt(i);
	        if (ch != firstLo && ch != firstUp)
	            continue;

	        if (src.regionMatches(true, i, what, 0, length))
	            return true;
	    }

	    return false;
	}
}
