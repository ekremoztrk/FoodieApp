package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.OrdersFrame;

public class OrdersController {

	private SessionManager session;
	private OrdersFrame view;

	public OrdersController(OrdersFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		
		setSidebarListeners();
	}
	
	private void setSidebarListeners() {
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenRestaurantProfileActionListener(new OpenRestaurantProfileListener());
	}
	
	class OpenRestaurantProfileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.restaurantProfilePage();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.loginPage();
		}
	}
}
