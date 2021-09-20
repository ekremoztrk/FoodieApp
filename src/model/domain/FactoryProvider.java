package model.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.domain.hamburger.HamburgerFactory;
import model.domain.pizza.PizzaFactory;

public class FactoryProvider {
	
	public static FoodFactory getFactory(String choice){
        
        if(choice.equalsIgnoreCase("pizza")) {
            return (FoodFactory) new PizzaFactory();
        }
        else if(choice.equalsIgnoreCase("burger")) {
            return (FoodFactory) new HamburgerFactory();
        }
        return null;
    }
	
	public static List<String> getTypes() {
		return Arrays.asList("Pizza", "Burger");
	}
	
	public static List<String> getFoodTypes() {
		List<String> types = new ArrayList<>();
		List<String> burgerTypes = HamburgerFactory.getFoodTypes();
		List<String> pizzaTypes = PizzaFactory.getFoodTypes();
		types.addAll(burgerTypes);
		types.addAll(pizzaTypes);
		return types;
	}
	
	public static List<String> getFoodTypes(String menu) {
		if (menu.equalsIgnoreCase("pizza")) {
			return PizzaFactory.getFoodTypes();
		} else if (menu.equalsIgnoreCase("burger")) {
			return HamburgerFactory.getFoodTypes();
		}
		return new ArrayList<>();
	}

	public static List<String> getToppingsOfMenu(String menu) {
		if (menu.equalsIgnoreCase("pizza")) {
			return PizzaFactory.getToppings();
		} else if (menu.equalsIgnoreCase("burger")) {
			return HamburgerFactory.getToppings();
		}
		return new ArrayList<>();
	}
}
