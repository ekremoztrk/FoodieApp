package controller;

import model.domain.*;
import view.*;

import java.util.List;

@SuppressWarnings("unused")
public class SessionManager {
	private User currentUser;
	private FrameManager fm;
	private FoodieService model;

	public SessionManager(User currentUser, FrameManager fm, FoodieService model) {
		this.setCurrentUser(currentUser);
		this.fm = fm;
		this.model = model;
	}

	public void loginPage() {
		LoginFrame loginView = new LoginFrame(fm);
		LoginController loginController = new LoginController(model, loginView, this);
	}

	public void restaurantsPage() {
		RestaurantsFrame restaurantsView = new RestaurantsFrame(model, fm);
		RestaurantsController restaurantsController = new RestaurantsController(model, restaurantsView, this);
	}
	
	public void orderHistoryPage() {
		OrdersFrame ordersView = new OrdersFrame(fm, currentUser);
		OrdersController ordersController = new OrdersController(ordersView, this);
	}

	public void userProfilePage() {
		UserProfileFrame userView = new UserProfileFrame(fm, currentUser);
		UserProfileController userController = new UserProfileController(model, userView, this);
	}

	public void restaurantProfilePage() {
		 RestaurantProfileFrame restaurantProfileView = new RestaurantProfileFrame(fm, currentUser);
		 RestaurantProfileController restaurantProfileController = new RestaurantProfileController(model, restaurantProfileView, this);
	}

	public void restaurantPage(User restaurant) {
		RestaurantFrame restaurantView = new RestaurantFrame(fm, restaurant);
		RestaurantController restaurantController = new RestaurantController(restaurantView, this, restaurant);
	}

	public void foodPage(String food, User restaurant) {
		FoodFrame foodView = new FoodFrame(fm, food, restaurant, currentUser);
		FoodController foodController = new FoodController(model, foodView, this, restaurant, food);
	}

	public void shoppingCartPage() {
		ShoppingCartFrame shoppingCartView = new ShoppingCartFrame(fm, ((Customer) currentUser).getCurrentOrder());
		ShoppingCartController shoppingCartController = new ShoppingCartController(model,shoppingCartView, this,
				((Customer) currentUser).getCurrentOrder());
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}