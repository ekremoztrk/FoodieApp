package model.domain.hamburger;

import model.domain.FoodFactory;
import model.domain.hamburger.hamburger_toppings.*;
import model.domain.hamburger.hamburgers.BeefBurger;
import model.domain.hamburger.hamburgers.BigMacHamburger;
import model.domain.hamburger.hamburgers.TurkeyBurger;
import model.domain.hamburger.hamburgers.VeggieBurger;
import model.utilities.ToppingPricePair;

import java.util.ArrayList;
import java.util.List;

public class HamburgerFactory implements FoodFactory {

    @Override
    public IHamburger create(String type, double burgerCost, List<ToppingPricePair> toppings) {
        IHamburger hamburger = null;
        if (containsIgnoreCase(type, "big mac") || containsIgnoreCase(type, "bigmac")) {
            hamburger = new BigMacHamburger(burgerCost);
        }
        else if (containsIgnoreCase(type, "beef")) {
            hamburger = new BeefBurger(burgerCost);
        }
        else if (containsIgnoreCase(type, "turkey")) {
            hamburger = new TurkeyBurger(burgerCost);
        }
        else if (containsIgnoreCase(type, "veggie")) {
            hamburger = new VeggieBurger(burgerCost);
        }
        for (ToppingPricePair topping : toppings) {
            if (topping.getTopping().equalsIgnoreCase("mayonnaise")) {
                hamburger = new Mayonnaise(hamburger,topping.getCost());
            }
            else if (topping.getTopping().equalsIgnoreCase("cheese")) {
                hamburger = new Cheese(hamburger,topping.getCost());
            }
            else if (topping.getTopping().equalsIgnoreCase("pickles")) {
                hamburger = new Pickles(hamburger,topping.getCost());
            }
            else if (topping.getTopping().equalsIgnoreCase("lettuce")) {
                hamburger = new Lettuce(hamburger,topping.getCost());
            }
            else if (topping.getTopping().equalsIgnoreCase("onion")) {
                hamburger = new Onion(hamburger,topping.getCost());
            }
            else if (topping.getTopping().equalsIgnoreCase("tomato")) {
                hamburger = new Tomato(hamburger,topping.getCost());
            }
        }
        return hamburger;
    }
    
    public static List<String> getFoodTypes() {
		List<String> types = new ArrayList<>();
		types.add("Beef Burger");
		types.add("Big Mac Hamburger");
		types.add("Turkey Burger");
		types.add("Veggie Burger");
		return types;
	}
    
	public static List<String> getToppings() {
		List<String> toppings = new ArrayList<>();
		toppings.add("Cheese");
		toppings.add("Cucumber");
		toppings.add("Lettuce");
		toppings.add("Mayonnaise");
		toppings.add("Onion");
		toppings.add("Pickles");
		toppings.add("Tomato");
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
