package controller;

import model.domain.*;
import model.utilities.Observer;
import model.utilities.Subject;
import view.ShoppingCartFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingCartController implements Observer {
	private SessionManager session;
	private Subject subject;
	private ShoppingCartFrame view;
	private FoodieService foodieService;
	public ShoppingCartController(FoodieService foodieService, ShoppingCartFrame view, SessionManager session, Order currentOrder) {
		this.session = session;
		this.subject = currentOrder;
		this.view = view;
		this.foodieService = foodieService;

		if (currentOrder != null && currentOrder.getItems().size() > 0)
			currentOrder.register(this);

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addOpenRestaurantsActionListener(new ShoppingCartController.OpenRestaurantsListener());
		view.addOpenUserProfileActionListener(new ShoppingCartController.OpenUserProfileListener());
		view.addLogoutActionListener(new ShoppingCartController.LogoutListener());
	}

	private void setContentListeners() {
		if (subject != null && ((Order) subject).getItems().size() > 0)
			view.addPlaceOrderActionListener(new PlaceOrderListener((Order) subject));
	}

	class PlaceOrderListener implements ActionListener {
		Order currentOrder;

		public PlaceOrderListener(Order currentOrder) {
			this.currentOrder = currentOrder;
		}

		public void actionPerformed(ActionEvent e) {
			foodieService.placeOrder(session.getCurrentUser());
			session.userProfilePage();
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
