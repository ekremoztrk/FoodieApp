package model.domain;

import model.utilities.FoodCostPair;
import model.utilities.Observer;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
	
	private Order currentOrder;
	private List<Observer> observers;

	public Customer(int id, String name, String username, String password, String address, List<Order> orderHistory) {
		super(id, name, username, password, address, orderHistory);
		this.observers = new ArrayList<>();
	}
	
	public Customer(String name, String username, String password, String address, List<Order> orderHistory) {
		super(name, username, password, address, orderHistory);
		this.observers = new ArrayList<>();
	}

	public void initializeOrder(String restaurantName) {
		this.currentOrder = new Order(getAddress(), getName(), restaurantName);
		notifyObservers();
	}
	
	public Order getCurrentOrder() {
		return currentOrder;
	}
	
	public void setCurrentOrder(Order order) {
		this.currentOrder = order;
		notifyObservers();
	}
	
	public void addItemToOrder(IFood food) {
		currentOrder.addItemToOrder(new FoodCostPair(food));
		notifyObservers();
	}
	
	public void removeItemFromOrder(IFood food) {
		currentOrder.removeItemFromOrder(food);
		notifyObservers();
	}
	
	public void placeOrder() {
		currentOrder.setPlaced();
		getOrderHistory().add(currentOrder);
		currentOrder = null;
		notifyObservers();
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