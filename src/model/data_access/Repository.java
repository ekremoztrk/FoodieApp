package model.data_access;

import java.util.ArrayList;
import java.util.List;
import model.domain.Customer;
import model.domain.Restaurant;
import model.domain.User;

public class Repository {

	private List<User> users;
	private IO io;
	public Repository(IO io) {
		this.io = io;
		users = io.inputUsers();
	}

	public User findUserByUsername(String username) throws IllegalStateException {
		for (User user : getAllUsers()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		throw new IllegalStateException("No user with the given username exists.");
	}
	
	public User findUserByName(String name) throws IllegalStateException {
		for (User user : getAllUsers()) {
			if (user.getName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		throw new IllegalStateException("No user with the given name exists.");
	}

	public List<User> getAllRestaurants() {
		List<User> restaurants = new ArrayList<>();
		for(User user: users){
			if(user instanceof Restaurant)
				restaurants.add(user);
		}

		return restaurants;
	}
	
	public List<User> getAllCustomers() {
		List<User> customers = new ArrayList<>();
		for(User user: users){
			if(user instanceof Customer)
				customers.add(user);
		}
		return customers;

	}
	
	public List<User> getAllUsers() {
		return users;
	}

	public void outputData(){
		io.outputUsers(users);
	}
}
