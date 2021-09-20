package model.domain;

import java.util.List;
import model.utilities.Subject;

public abstract class User implements Subject {
	private static int idCounter = 1;
	private int id;
	private String name;
	private String username;
	private String password;
	private String address;
	private List<Order> orderHistory;

	public User(String name, String username, String password, String address, List<Order> orderHistory) {
		id=idCounter;
		idCounter++;
		this.name = name;
		this.username = username;
		this.password = password;
		this.address = address;
		this.orderHistory = orderHistory;
	}
	public User(int id, String name, String username, String password, String address, List<Order> orderHistory) {
		this.id=id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.address = address;
		this.orderHistory = orderHistory;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		notifyObservers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		notifyObservers();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		notifyObservers();
	}
	
	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		notifyObservers();
	}

	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<Order> orderHistory) {
		this.orderHistory = orderHistory;
		notifyObservers();
	}

	public Order addOrder(Order order){
		orderHistory.add(order);
		return order;
	}
}