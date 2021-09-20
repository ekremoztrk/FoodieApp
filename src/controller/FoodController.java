package controller;

import model.domain.*;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;
import view.FoodFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodController implements Observer {

	private SessionManager session;
	private Subject subject;
	private boolean isRestaurant;
	private String food;
	private FoodieService model;
	private FoodFrame view;
	private List<ToppingPricePair> selectedToppings = new ArrayList<>();

	public FoodController(FoodieService model, FoodFrame view, SessionManager session, User restaurant, String food) {
		this.subject = restaurant;
		this.model = model;
		this.view = view;
		this.isRestaurant = restaurant.getId() == session.getCurrentUser().getId();
		this.session = session;
		this.food = food;

		restaurant.register(this);

		setSidebarListeners();
		setContentListeners();
	}

	private void setSidebarListeners() {
		if (isRestaurant) {
			view.addOpenRestaurantProfileActionListener(new OpenRestaurantProfileListener());
			view.addOpenOrderHistoryActionListener(new OpenOrderHistoryListener());
			view.addLogoutActionListener(new LogoutListener());
		} else {
			view.addOpenRestaurantsActionListener(new OpenRestaurantsListener());
			view.addOpenUserProfileActionListener(new OpenUserProfileListener());
			view.addLogoutActionListener(new LogoutListener());
			view.addOpenShoppingCartActionListener(new OpenShoppingCartListener());
		}
	}

	private void setContentListeners() {
		Restaurant restaurant = (Restaurant) subject;
		List<ToppingPricePair> toppings = new ArrayList<>();
		List<Menu> menus = restaurant.getMenu();
		for (Menu menu : menus) {
			Map<FoodCostPair, List<ToppingPricePair>> items = menu.getItems();
			for (FoodCostPair item : items.keySet()) {
				if (item.getFood().equals(food)) {
					toppings = items.get(item);
				}
			}
		}

		for (ToppingPricePair topping : toppings) {
			if (isRestaurant) {
			view.addChangeToppingCostActionListener(new ChangeToppingCostActionListener(topping), topping.getTopping());
			view.addRemoveToppingActionListener(new RemoveToppingCostActionListener(topping), topping.getTopping());
			} else {
				view.addSelectToppingActionListener(new SelectToppingActionListener(topping), topping.getTopping());
				view.addUnSelectToppingActionListener(new UnSelectToppingActionListener(topping), topping.getTopping());
			}
		}
		if (isRestaurant) {
			view.addAddToppingActionListener(new AddToppingActionListener());
			view.addChangeCostActionListener(new ChangeCostActionListener());
		} else {
			view.addAddToCartActionListener(new AddToCartActionListener());
		}
	}

	class AddToCartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double foodCost = 0;
			Restaurant restaurant = (Restaurant) subject;
			List<Menu> menu = restaurant.getMenu();
			for (Menu submenu : menu) {
				Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
				for (FoodCostPair pair : items.keySet()) {
					if (pair.getFood().equalsIgnoreCase(food)) {
						foodCost = pair.getCost();
					}
				}
			}
			IFood cartFood = ((Restaurant) restaurant).createFood(food, foodCost, selectedToppings);
			model.addToCart(session.getCurrentUser(), cartFood);
			session.restaurantPage(restaurant);
		}
	}

	class AddToppingActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Menu> menu = ((Restaurant) subject).getMenu();
    		List<String> joint = new ArrayList<>();
			
    		out:
			for (Menu submenu : menu) {
				Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
				for (FoodCostPair item : items.keySet()) {
					if (item.getFood().equalsIgnoreCase(food)) {
						List<String> allToppings = ((FoodieService) model).getAllToppingsOfMenu(submenu.getName().replace(" Menu", ""));
						
						outerLoop:
						for (int i = 0; i < allToppings.size(); i++) {
							String topping = allToppings.get(i);
							
							List<ToppingPricePair> tops = items.get(item);
							for (ToppingPricePair pair : tops) {
								if (pair.getTopping().equalsIgnoreCase(topping)) {
									continue outerLoop;
								}
							}
							joint.add(topping);
						}
						break out;
	    			}
				}
    		}
				
			String[] choices = joint.stream().toArray(String[]::new);
			
			if (choices.length == 0) {
				view.showMessage("No different topping to add.");
			} else {
				Object item = (view.showSelectDialog("Choose a topping to add: ", choices));
				if (item == null || item == "") {
					return;
				}
				
				Object cost = (view.showInputDialog("Enter the price of the topping: "));
				if (cost == null || cost == "") {
					return;
				}
				
				try {
					((FoodieService) model).addToppingToFoodOfRestaurant(food, item.toString(), Double.parseDouble(cost.toString()), (Restaurant) subject);
				} catch (IllegalStateException e1) {
					view.showMessage(e1.getMessage());
					return;
				}
			}
		}
	}
	
	class ChangeCostActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object cost = (view.showInputDialog("Enter the new cost of the item: "));
			if (cost == null || cost == "") {
				return;
			}
			
			try {
				((FoodieService) model).changeFoodCostForRestaurant(food, Double.parseDouble(cost.toString()), (Restaurant) subject);
			} catch (IllegalStateException e1) {
				view.showMessage(e1.getMessage());
				return;
			}
		}
	}
	
	class ChangeToppingCostActionListener implements ActionListener {
		public ToppingPricePair topping;

		public ChangeToppingCostActionListener(ToppingPricePair topping) {
			this.topping = topping;
		}

		public void actionPerformed(ActionEvent e) {
			Object cost = (view.showInputDialog("Enter the new cost of the topping: "));
			if (cost == null || cost == "") {
				return;
			}
			
			try {
				((FoodieService) model).changeToppingCostForFoodForRestaurant(topping, food, Double.parseDouble(cost.toString()), (Restaurant) subject);
			} catch (IllegalStateException e1) {
				view.showMessage(e1.getMessage());
				return;
			}
		}
	}
	
	class RemoveToppingCostActionListener implements ActionListener {
		public ToppingPricePair topping;

		public RemoveToppingCostActionListener(ToppingPricePair topping) {
			this.topping = topping;
		}

		public void actionPerformed(ActionEvent e) {
			((FoodieService) model).removeToppingFromFoodForRestaurant(topping, food, (Restaurant) subject);
		}
	}
	
	class SelectToppingActionListener implements ActionListener {
		public ToppingPricePair topping;

		public SelectToppingActionListener(ToppingPricePair topping) {
			this.topping = topping;

		}

		public void actionPerformed(ActionEvent e) {
			selectedToppings.add(topping);
		}
	}

	class UnSelectToppingActionListener implements ActionListener {
		public ToppingPricePair topping;

		public UnSelectToppingActionListener(ToppingPricePair topping) {
			this.topping = topping;

		}

		public void actionPerformed(ActionEvent e) {
			if (selectedToppings.contains(topping))
				selectedToppings.remove(topping);
		}
	}
	
	class OpenRestaurantProfileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.restaurantProfilePage();
		}
	}
	
	class OpenOrderHistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			session.orderHistoryPage();
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
