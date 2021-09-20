package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Customer;
import model.domain.FoodieService;
import model.domain.User;
import view.RestaurantsFrame;

public class RestaurantsController {

	private SessionManager session;
	private RestaurantsFrame view;
	private FoodieService model;

	public RestaurantsController(FoodieService model, RestaurantsFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		this.model = model;
		
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenProfileListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenShoppingCartActionListener(new OpenShoppingCartListener());
	}

	private void setContentListeners() {
		List<User> restaurants = ((FoodieService) model).getAllRestaurants();
		for (User restaurant : restaurants) {
			view.addOpenRestaurantActionListener(new OpenRestaurantListener(restaurant),restaurant.getName());
		}
	}

	class OpenRestaurantListener implements ActionListener {
		private User restaurant;
		
		public OpenRestaurantListener(User restaurant) {
			this.restaurant = restaurant;
		}
		
		public void actionPerformed(ActionEvent e) {
			Customer customer = (Customer) session.getCurrentUser();
			if (customer.getCurrentOrder() != null && customer.getCurrentOrder().getItems().size() > 0 && !customer.getCurrentOrder().getRestaurantName().equals(restaurant.getName())) {
				int result = view.showConfirmDialog("If you proceed to this restaurant, your shopping cart will be reset. Do you want to continue?", "idk");
				if (result == 0) {
					model.initializeOrder(session.getCurrentUser(), restaurant.getName());
					session.restaurantPage(restaurant);
				}
			} else {
				model.initializeOrder(session.getCurrentUser(), restaurant.getName());
				session.restaurantPage(restaurant);
			}
		}
	}
	
	class OpenShoppingCartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.shoppingCartPage();
		}
	}
	
	class OpenProfileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.userProfilePage();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.loginPage();
		}
	}
}
