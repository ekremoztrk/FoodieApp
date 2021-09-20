package model.domain;

import java.util.*;
import model.data_access.IO;
import model.data_access.Repository;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;

public class FoodieService implements Observer, Subject {

	private Repository repo;
	private List<Observer> observers;
	private List<Subject> subjects;
	private IO io;
	
	public FoodieService() {
		io = new IO();
		this.repo = new Repository(io);
		setObservers(new ArrayList<Observer>());
		setSubjects(new ArrayList<Subject>());
		registerAll();
	}

	private void registerAll() {
		List<User> users = repo.getAllUsers();
		for (User user : users) {
			user.register(this);
		}
	}

	public void outputData(){
		repo.outputData();
	}
	
	public User login(String username, String password) throws IllegalArgumentException, IllegalStateException {
		User user = repo.findUserByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Invalid password.");
		}
		return user;
	}
	
	public void initializeOrder(User customer, String restaurantName){
		Customer user = (Customer) customer;
 		if (user.getCurrentOrder() == null || !user.getCurrentOrder().getRestaurantName().equals(restaurantName)) {
			user.initializeOrder(restaurantName);
		}
		outputData();
	}

	public void addToCart(User customer, IFood cartFood){
	    ((Customer) customer).addItemToOrder(cartFood);
	    outputData();
	}
	
	public void placeOrder(User user){
		Customer customer = (Customer) user;
		Order order = customer.getCurrentOrder();
		Restaurant restaurant = (Restaurant) repo.findUserByName(order.getRestaurantName());
		customer.placeOrder();
		restaurant.placeOrder(order);
		outputData();
	}
	
	public void changeNameOfUser(String newName, User currentUser) {
		currentUser.setName(newName);
		outputData();
	}

	public void changeUsernameOfUser(String newUsername, User currentUser) {
		currentUser.setUsername(newUsername);
		outputData();
	}

	public void changeAddressOfUser(String newAddress, User currentUser) {
		currentUser.setAddress(newAddress);
		outputData();
	}
	
	public List<User> getAllRestaurants() {
		return repo.getAllRestaurants();
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	// SUBJECT METHODS

	@Override
	public void register(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.add(obj);
			setObservers(observers);
		}
	}

	@Override
	public void unregister(Observer obj) {
		if (obj == null) {
			throw new NullPointerException("The given observer is null.");
		}
		List<Observer> observers = getObservers();
		if (!observers.contains(obj)) {
			observers.remove(obj);
			setObservers(observers);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observers = getObservers();
		for (Observer observer : observers) {
			observer.update();
		}
	}

	// OBSERVER METHODS

	@Override
	public void update() {
		notifyObservers();
	}

	@Override
	public void addSubject(Subject sub) {
		this.subjects.add(sub);
	}

	@Override
	public void removeSubject(Subject sub) {
		this.subjects.remove(sub);
	}

	public void removeMenuFromRestaurant(String menuName, Restaurant currentRestaurant) {
		List<Menu> menu = currentRestaurant.getMenu();
		for (Menu submenu : menu) {
			if (submenu.getName().equals(menuName)) {
				menu.remove(submenu);
				break;
			}
		}
		currentRestaurant.setMenu(menu);
		outputData();
	}
	
	public void addFoodToMenuOfRestaurant(String menuName, String foodName, double foodCost, Restaurant currentRestaurant) {
		currentRestaurant.createFoodAndAddToMenu(menuName, foodName, foodCost);
		outputData();
	}

	public void createMenuForRestaurant(String menuName, Restaurant restaurant) {
		Menu menu = new Menu(menuName + " Menu", new HashMap<>());
		restaurant.addMenu(menu);
		outputData();
	}

	public List<String> getAllMenuTypes() {
		return FactoryProvider.getTypes();
	}

	public List<String> getAllFoodTypesOfMenu(String menuName) {
		return FactoryProvider.getFoodTypes(menuName);
	}

	public void removeItemInMenuFromRestaurant(String menuName, String itemName, Restaurant restaurant) {
		restaurant.removeFoodFromMenu(menuName, itemName);
		outputData();
	}

	public void changeToppingCostForFoodForRestaurant(ToppingPricePair topping, String food, double cost, Restaurant restaurant) {
		restaurant.changeToppingCostForFood(topping, food, cost);
		outputData();
	}

	public void removeToppingFromFoodForRestaurant(ToppingPricePair topping, String food, Restaurant restaurant) {
		restaurant.removeToppingFromFood(topping, food);
		outputData();
	}

	public void changeFoodCostForRestaurant(String food, double cost, Restaurant restaurant) {
		restaurant.changeCostForFood(food, cost);
		outputData();
	}

	public List<String> getAllToppingsOfMenu(String menu) {
		return FactoryProvider.getToppingsOfMenu(menu);
	}

	public void addToppingToFoodOfRestaurant(String food, String topping, double cost, Restaurant restaurant) {
		restaurant.addToppingToFood(topping, food, cost);
		outputData();
	}
}