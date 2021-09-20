package model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.ToppingPricePair;

public class Restaurant extends User {

	private List<Menu> menu;
	private List<Observer> observers;

	public Restaurant(String name, String username, String password, String address, List<Order> orderHistory,
			List<Menu> menu) {
		super(name, username, password, address, orderHistory);
		this.menu = menu;
		this.observers = new ArrayList<>();
	}

	public Restaurant(int id, String name, String username, String password, String address, List<Order> orderHistory,
			List<Menu> menu) {
		super(id, name, username, password, address, orderHistory);
		this.menu = menu;
		this.observers = new ArrayList<>();
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
		notifyObservers();
	}

	public void addMenu(Menu newMenu) {
		List<Menu> menu = getMenu();
		menu.add(newMenu);
		setMenu(menu);
	}
	
	public void placeOrder(Order order) {
		List<Order> orders = getOrderHistory();
		orders.add(order);
		setOrderHistory(orders);
		notifyObservers();
	}

	@SuppressWarnings("unused")
	public IFood createFood(String foodName, double cost, List<ToppingPricePair> toppings) {
		for (Menu menu : menu) {
			for (FoodCostPair item : menu.getItems().keySet()) {
				if (menu.getItemNames().contains(foodName)) {
					FoodFactory factory = FactoryProvider.getFactory(menu.getName().replaceAll(" .*", ""));
					IFood food = (IFood) factory.create(foodName, cost, toppings);
					return food;
				}
			}
		}
		return null;
	}

	public void createFoodAndAddToMenu(String menuName, String foodName, double cost) {
		for (Menu submenu : menu) {
			if (submenu.getName().equalsIgnoreCase(menuName)) {
				Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();

				FoodFactory factory = FactoryProvider.getFactory(submenu.getName().replaceAll(" .*", ""));
				IFood food = (IFood) factory.create(foodName, cost, new ArrayList<>());
				FoodCostPair pair = new FoodCostPair(food);
				
				items.put(pair, new ArrayList<>());
				submenu.setItems(items);
				notifyObservers();
			}
		}
	}
	
	public void removeFoodFromMenu(String menuName, String foodName) {
		List<Menu> menu = getMenu();
		for (Menu submenu : menu) {
			if (submenu.getName().equals(menuName)) {
				Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
				for (FoodCostPair pair : items.keySet()) {
					if (pair.getFood().equalsIgnoreCase(foodName)) {
						items.remove(pair);
						notifyObservers();
						return;
					}
				}
			}
		}
	}
	
	public void changeCostForFood(String food, double cost) {
		List<Menu> menu = getMenu();
		for (Menu submenu : menu) {
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair pair : items.keySet()) {
				if (pair.getFood().equalsIgnoreCase(food)) {
					pair.setCost(cost);
					notifyObservers();
					return;
				}
			}
		}
	}
	
	public void changeToppingCostForFood(ToppingPricePair topping, String food, double cost) {
		List<Menu> menu = getMenu();
		for (Menu submenu : menu) {
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair pair : items.keySet()) {
				if (pair.getFood().equalsIgnoreCase(food)) {
					List<ToppingPricePair> toppings = items.get(pair);
					for (ToppingPricePair top : toppings) {
						if (top.getTopping().equals(topping.getTopping())) {
							top.setCost(cost);
							notifyObservers();
							return;
						}
					}
				}
			}
		}
	}
	
	public void addToppingToFood(String topping, String food, double cost) {
		List<Menu> menu = getMenu();
		for (Menu submenu : menu) {
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair pair : items.keySet()) {
				if (pair.getFood().equalsIgnoreCase(food)) {
					List<ToppingPricePair> toppings = items.get(pair);
					ToppingPricePair newPair = new ToppingPricePair(cost, topping);
					toppings.add(newPair);
					notifyObservers();
					return;
				}
			}
		}
	}

	public void removeToppingFromFood(ToppingPricePair topping, String food) {
		List<Menu> menu = getMenu();
		for (Menu submenu : menu) {
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair pair : items.keySet()) {
				if (pair.getFood().equalsIgnoreCase(food)) {
					List<ToppingPricePair> toppings = items.get(pair);
					for (ToppingPricePair top : toppings) {
						if (top.getTopping().equals(topping.getTopping())) {
							toppings.remove(top);
							notifyObservers();
							return;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void register(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = this.observers;
		if (!observers.contains(obj)) {
			observers.add(obj);
			this.observers = observers;
		}
	}

	@Override
	public void unregister(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = this.observers;
		if (!observers.contains(obj)) {
			observers.remove(obj);
			this.observers = observers;
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observers = this.observers;
		for (Observer observer : observers) {
			observer.update();
		}
	}
}