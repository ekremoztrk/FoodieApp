package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.domain.*;
import model.utilities.Observer;
import model.utilities.Subject;
import view.UserProfileFrame;

public class UserProfileController implements Observer {

	private SessionManager session;
	private UserProfileFrame view;
	private FoodieService model;
	private Subject subject;

	public UserProfileController(FoodieService model, UserProfileFrame view, SessionManager session) {
		this.model = model;
		this.session = session;
		this.view = view;
		this.subject = session.getCurrentUser();
		
		subject.register(this);
		
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenRestaurantsActionListener(new OpenRestaurantsListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenShoppingCartActionListener(new OpenShoppingCartListener());
	}
	
	private void setContentListeners() {
		view.addChangeNameActionListener(new ChangeNameListener());
		view.addChangeUsernameActionListener(new ChangeUsernameListener());
		view.addChangeAddressActionListener(new ChangeAddressListener());
	}
	
	class ChangeNameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newName = view.showInputDialog("Enter new name:");
        	if (newName == null || newName == "")  {
        		return;
        	}
        	newName = newName.trim();
			((FoodieService) model).changeNameOfUser(newName, session.getCurrentUser());
        }
    }
	
	class ChangeUsernameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newUsername = view.showInputDialog("Enter new username:");
        	if (newUsername == null || newUsername == "")  {
        		return;
        	}
        	newUsername = newUsername.trim();
			((FoodieService) model).changeUsernameOfUser(newUsername, session.getCurrentUser());
        }
    }
	
	class ChangeAddressListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newAddress = view.showInputDialog("Enter new address:");
        	if (newAddress == null || newAddress == "")  {
        		return;
        	}
        	newAddress = newAddress.trim();
			((FoodieService) model).changeAddressOfUser(newAddress, session.getCurrentUser());
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
