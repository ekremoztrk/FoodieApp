package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import model.domain.*;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;
import view.RestaurantFrame;

public class RestaurantController implements Observer {

	private SessionManager session;
	private Subject subject;
	private RestaurantFrame view;

	public RestaurantController(RestaurantFrame view, SessionManager session, User restaurant) {
		this.session = session;
		this.subject = restaurant;
		this.view = view;

		subject.register(this);

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenRestaurantsActionListener(new OpenRestaurantsListener());
		view.addOpenUserProfileActionListener(new OpenUserProfileListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenShoppingCartActionListener(new OpenShoppingCartListener());
	}

	private void setContentListeners() {
		Restaurant restaurant = ((Restaurant) this.subject);
		List<Menu> menu = restaurant.getMenu();
		for (Menu submenu : menu) {
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair item : items.keySet()) {
				view.addOpenFoodActionListener(new OpenFoodListener(item.getFood()), item.getFood());
			}
		}
	}


	class OpenFoodListener implements ActionListener {
		public String foodName;

		public OpenFoodListener(String foodName) {
			this.foodName = foodName;
		}
		
		public void actionPerformed(ActionEvent e) {
			session.foodPage(foodName,(User) subject);
		}
	}

	class OpenShoppingCartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.shoppingCartPage();
		}
	}
	
	class OpenRestaurantsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.restaurantsPage();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.loginPage();
		}
	}

	class OpenUserProfileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.userProfilePage();
		}
	}
	
	@Override
	public void update() {
		setContentListeners();
	}

	@Override
	public void addSubject(Subject sub) {
		this.subject = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.subject = null;
	}
}
