package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.domain.FoodieService;
import model.domain.Menu;
import model.domain.Restaurant;
import model.domain.User;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;
import view.RestaurantProfileFrame;

public class RestaurantProfileController implements Observer {

	private SessionManager session;
	private RestaurantProfileFrame view;
	private FoodieService model;
	private Subject currentRestaurant;

	public RestaurantProfileController(FoodieService model, RestaurantProfileFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		this.currentRestaurant = session.getCurrentUser();
		this.model = model;

		currentRestaurant.register(this);
		
		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOrderHistoryActionListener(new OpenOrderHistoryListener());
	}

	private void setContentListeners() {
		view.addChangeNameActionListener(new ChangeNameListener());
		view.addChangeUsernameActionListener(new ChangeUsernameListener());
		view.addChangeAddressActionListener(new ChangeAddressListener());
		view.addCreateMenuActionListener(new CreateMenuListener());
		List<Menu> menu = ((Restaurant) currentRestaurant).getMenu();
		for (Menu submenu : menu) {
			view.addRemoveMenuActionListener(new RemoveMenuListener(submenu.getName()), submenu.getName());
			view.addNewItemActionListener(new AddItemListener(submenu.getName()), submenu.getName());
			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair item : items.keySet()) {
				view.addOpenFoodActionListener(new OpenFoodListener(item.getFood(),item.getCost()), item.getFood());
				view.addRemoveItemActionListener(new RemoveItemListener(submenu.getName(), item.getFood()), item.getFood());
			}
		}
	}
	
	class OpenFoodListener implements ActionListener {
		public String foodName;
		public double foodCost;

		public OpenFoodListener(String foodName, double foodCost) {
			this.foodName = foodName;
			this.foodCost = foodCost;
		}
		
		public void actionPerformed(ActionEvent e) {
			session.foodPage(foodName, (User) currentRestaurant);
		}
	}
	
	class RemoveMenuListener implements ActionListener {
		private String menuName;
		
		public RemoveMenuListener(String menuName) {
			this.menuName = menuName;
		}
		
        public void actionPerformed(ActionEvent e) {
			((FoodieService) model).removeMenuFromRestaurant(menuName, (Restaurant) currentRestaurant);
        }
    }
	
	class RemoveItemListener implements ActionListener {
		private String menuName;
		private String itemName;
		
		public RemoveItemListener(String menuName, String itemName) {
			this.menuName = menuName;
			this.itemName = itemName;
		}
		
        public void actionPerformed(ActionEvent e) {
        	((FoodieService) model).removeItemInMenuFromRestaurant(menuName, itemName, (Restaurant) currentRestaurant);
        }
    }
	
	class AddItemListener implements ActionListener {
		private String menuName;
		
		public AddItemListener(String menuName) {
			this.menuName = menuName;
		}
		
        public void actionPerformed(ActionEvent e) {
        	List<Menu> menu = ((Restaurant) currentRestaurant).getMenu();
        	List<String> allFoodTypes = ((FoodieService) model).getAllFoodTypesOfMenu(menuName.replace(" Menu", ""));
    		List<String> joint = new ArrayList<>();
			
			outerLoop:
			for (int i = 0; i < allFoodTypes.size(); i++) {
				String type = allFoodTypes.get(i);
				for (Menu submenu : menu) {
					Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
					for (FoodCostPair item : items.keySet()) {
						if (item.getFood().equalsIgnoreCase(type)) {
		    				continue outerLoop;
		    			}
					}
	    			
	    		}
				joint.add(type);
			}
			String[] choices = joint.stream().toArray(String[]::new);
			
			if (choices.length == 0) {
				view.showMessage("No different item to add.");
			} else {
				Object item = (view.showSelectDialog("Choose an item to add: ", choices));
				if (item == null || item == "") {
					return;
				}
				
				Object cost = (view.showInputDialog("Enter the price of the item: "));
				if (cost == null || cost == "") {
					return;
				}
				
				try {
					((FoodieService) model).addFoodToMenuOfRestaurant(menuName, item.toString(), Double.parseDouble(cost.toString()), (Restaurant) currentRestaurant);
				} catch (IllegalStateException e1) {
					view.showMessage(e1.getMessage());
					return;
				}
			}
        }
    }
	
	class CreateMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	List<Menu> menu = ((Restaurant) currentRestaurant).getMenu();
        	List<String> allMenuTypes = ((FoodieService) model).getAllMenuTypes();
        	List<String> joint = new ArrayList<>();
			
			outerLoop:
			for (int i = 0; i < allMenuTypes.size(); i++) {
				String type = allMenuTypes.get(i);
				for (Menu submenu : menu) {
	    			if (submenu.getName().replace(" Menu", "").equals(type)) {
	    				continue outerLoop;
	    			}
	    		}
				joint.add(type);
			}
			String[] choices = joint.stream().toArray(String[]::new);
			
			if (choices.length == 0) {
				view.showMessage("No different menu type to add.");
			} else {
				Object response = (view.showSelectDialog("Choose a menu to add:", choices));
				if (response == null || response == "") {
					return;
				}
				String menuName = response.toString();
				try {
					((FoodieService) model).createMenuForRestaurant(menuName, (Restaurant) currentRestaurant);
				} catch (IllegalStateException e1) {
					view.showMessage(e1.getMessage());
					return;
				}
			}
        }
    }
	
	class ChangeNameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newName = view.showInputDialog("Enter new name:");
        	if (newName == null || newName == "")  {
        		return;
        	}
        	newName = newName.trim();
			((FoodieService) model).changeNameOfUser(newName, (User) currentRestaurant);
        }
    }
	
	class ChangeUsernameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newUsername = view.showInputDialog("Enter new username:");
        	if (newUsername == null || newUsername == "")  {
        		return;
        	}
        	newUsername = newUsername.trim();
			((FoodieService) model).changeUsernameOfUser(newUsername, (User) currentRestaurant);
        }
    }
	
	class ChangeAddressListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newAddress = view.showInputDialog("Enter new address:");
        	if (newAddress == null || newAddress == "")  {
        		return;
        	}
        	newAddress = newAddress.trim();
			((FoodieService) model).changeAddressOfUser(newAddress, (User) currentRestaurant);
        }
    }
	
	class OpenOrderHistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.orderHistoryPage();
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
		this.currentRestaurant = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.currentRestaurant = null;
	}
}
