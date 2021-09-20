package model.domain;

import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Subject {
	private static int idCounter = 1;
	private int id;
	private List<FoodCostPair> items;
	private String address;
	private String customerName;
	private String restaurantName;
	private Date orderDate;
	private List<Observer> observers = new ArrayList<>();

	public Order() {
		id=idCounter;
		idCounter++;
		this.items = new ArrayList<>();
		this.address = null;
		this.customerName = null;
		this.restaurantName = null;
		this.orderDate = null;
	}
	
	public Order(String address, String customerName, String restaurantName) {
		id=idCounter;
		idCounter++;
		this.items = new ArrayList<>();
		this.address = address;
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.orderDate = null;
	}
	
	public Order(String address, String customerName, String restaurantName, List<FoodCostPair> items, Date orderDate) {
		id=idCounter;
		idCounter++;
		this.items = items;
		this.address = address;
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.orderDate = orderDate;
	}
	public Order(int id, String address, String customerName, String restaurantName, List<FoodCostPair> items, Date orderDate) {
		this.id=id;
		this.items = items;
		this.address = address;
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.orderDate = orderDate;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<FoodCostPair> getItems() {
		return items;
	}

	public void setItems(List<FoodCostPair> items) {
		this.items = items;
	}
	
	public void addItemToOrder(FoodCostPair item) {
		items.add(item);
	}
	
	public void removeItemFromOrder(IFood item) {
		for (FoodCostPair foodCostPair : items) {
			if(foodCostPair.getFood() == item.decorate()) {
				items.remove(foodCostPair);
			}
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setPlaced() {
		this.orderDate = new Date();
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Serializable getOrderTotal() {
		double total = 0;
		for (FoodCostPair foodCostPair : items) {
			total += foodCostPair.getCost();
		}
		String s = String.format("%.2f", total);
		return s;
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