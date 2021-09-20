package view;

import model.domain.Menu;
import model.domain.Restaurant;
import model.domain.User;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject subject;
	private boolean isRestaurant;
	private String food;

	private FrameManager fm;
	private JPanel mainPanel;

	private JPanel leftSide;
	private JPanel content;

	private JButton restaurantsButton;
	private JButton shoppingCartButton;
	private JButton profilePageButton;
	private JButton logoutButton;
	private JButton restaurantProfilePageButton;
	private JButton orderHistoryButton;

	private JButton changeCostButton;
	private JButton addToppingButton;
	private JButton addToCartButton;
	private List<JRadioButton> selectButtons;
	private List<JRadioButton> unSelectButtons;
	private List<JButton> removeToppingButtons;
	private List<JButton> changeToppingCostButtons;

	public FoodFrame(FrameManager fm, String food, User restaurant, User currentUser) {
		this.fm = fm;
		this.subject = restaurant;
		this.isRestaurant = currentUser.getId() == restaurant.getId();
		this.food = food;
		this.selectButtons = new ArrayList<>();
		this.unSelectButtons = new ArrayList<>();
		this.removeToppingButtons = new ArrayList<>();
		this.changeToppingCostButtons = new ArrayList<>();

		restaurant.register(this);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.leftSide = leftSide;

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		this.content = content;

		mainPanel.add(this.leftSide);
		mainPanel.add(this.content);
		this.mainPanel = mainPanel;

		if (isRestaurant) {
			setLeftSideForRestaurant();
		} else {
			setLeftSide();
		}
		setContent();
		getFrameManager().setNewPanel(mainPanel, "food");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Food", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton restaurantsButton = new JButton("Restaurants");
		restaurantsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		restaurantsButton.setPreferredSize(new Dimension(200, 50));
		this.restaurantsButton = restaurantsButton;

		JButton shoppingCartButton = new JButton("Shopping Cart");
		shoppingCartButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		shoppingCartButton.setPreferredSize(new Dimension(200, 50));
		this.shoppingCartButton = shoppingCartButton;

		JButton profilePageButton = new JButton("My Profile");
		profilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		profilePageButton.setPreferredSize(new Dimension(200, 50));
		this.profilePageButton = profilePageButton;

		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(200, 50));
		this.logoutButton = logoutButton;

		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(restaurantsButton, gbc);
		leftSide.add(profilePageButton, gbc);
		leftSide.add(shoppingCartButton, gbc);
		leftSide.add(logoutButton, gbc);
	}

	public void setLeftSideForRestaurant() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Food", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton restaurantProfilePageButton = new JButton("Restaurant Profile");
		restaurantProfilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		restaurantProfilePageButton.setPreferredSize(new Dimension(200, 50));
		this.restaurantProfilePageButton = restaurantProfilePageButton;

		JButton orderHistoryButton = new JButton("Order History");
		orderHistoryButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		orderHistoryButton.setPreferredSize(new Dimension(200, 50));
		this.orderHistoryButton = orderHistoryButton;
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(200, 50));
		this.logoutButton = logoutButton;

		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(restaurantProfilePageButton, gbc);
		leftSide.add(orderHistoryButton, gbc);
		leftSide.add(logoutButton, gbc);
	}
	
	public void setContent() {
		Restaurant restaurant = (Restaurant) subject;
		List<ToppingPricePair> toppings = new ArrayList<>();
		double foodCost = 0;
		List<Menu> menus = restaurant.getMenu();
		for (Menu menu : menus) {
			Map<FoodCostPair, List<ToppingPricePair>> items = menu.getItems();
			for (FoodCostPair item : items.keySet()) {
				if (item.getFood().equals(food)) {
					foodCost = item.getCost();
					toppings = items.get(item);
				}
			}
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel foodImage = new JLabel();
		ImageIcon icon = new ImageIcon("assets/" + food + ".jpg");
		
		JLabel name = new JLabel("<html><FONT SIZE=5 COLOR=RED>" + food + "</FONT></html>");
		JLabel cost = new JLabel("<html><FONT SIZE=5><FONT COLOR=RED>Cost: </FONT>$" + foodCost + "</FONT></html>");
		JButton changeCostButton = new JButton("Change Food Cost");
		this.changeCostButton = changeCostButton;
		
		JButton addToppingButton = new JButton("Add Topping");
		this.addToppingButton = addToppingButton;

		foodImage.setIcon(icon);
		foodImage.setBackground(java.awt.Color.WHITE);
		foodImage.setPreferredSize(new Dimension(320, 320));
		
		panel.add(foodImage, gbc);
		panel.add(name, gbc);
		panel.add(cost, gbc);
		if (isRestaurant) {
			panel.add(changeCostButton, gbc);
			panel.add(addToppingButton, gbc);
		}
		
		for (ToppingPricePair topping : toppings) {
			JPanel toppingPanel = new JPanel();
			toppingPanel.setLayout(new GridLayout(1, 2));

			JLabel toppingName = new JLabel("<html><FONT SIZE=5><FONT COLOR=RED>" + toTitleCase(topping.getTopping()) + "</FONT> $" + topping.getCost() + "</FONT></html>");
			toppingPanel.add(toppingName);

			JButton removeToppingButton = new JButton("Remove Topping");
			removeToppingButton.setName(topping.getTopping());
			removeToppingButtons.add(removeToppingButton);
			
			JButton changeToppingCostButton = new JButton("Change Topping Cost");
			changeToppingCostButton.setName(topping.getTopping());
			changeToppingCostButtons.add(changeToppingCostButton);
			
			JRadioButton yesButton = new JRadioButton("Yes");
			JRadioButton noButton = new JRadioButton("No", true);

			// ... Create a button group and add the buttons.
			ButtonGroup bgroup = new ButtonGroup();
			bgroup.add(yesButton);
			bgroup.add(noButton);
			// ... Arrange buttons vertically in a panel
			JPanel radioPanel = new JPanel();
			yesButton.setName(topping.getTopping() + "Select");
			noButton.setName(topping.getTopping() + "UnSelect");
			radioPanel.setLayout(new GridLayout(1, 2));
			radioPanel.add(yesButton);
			radioPanel.add(noButton);
			// ... Add a titled border to the button panel.
			radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
			selectButtons.add(yesButton);
			unSelectButtons.add(noButton);
			toppingPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));
			
			if (isRestaurant) {
				toppingPanel.add(changeToppingCostButton);
				toppingPanel.add(removeToppingButton);
			} else {
				toppingPanel.add(radioPanel);
			}

			panel.add(toppingPanel, gbc);
		}
		
		addToCartButton = new JButton();
		addToCartButton.setBackground(java.awt.Color.WHITE);
		addToCartButton.setText("Add To Cart");
		if (!isRestaurant) {
			panel.add(addToCartButton);
		}

		content.removeAll();
		content.add(new JScrollPane(panel));
		getFrameManager().setNewPanel(mainPanel, "food");
	}

	public void addAddToppingActionListener(ActionListener actionListener) {
		addToppingButton.addActionListener(actionListener);
	}
	
	public void addChangeCostActionListener(ActionListener actionListener) {
		changeCostButton.addActionListener(actionListener);
	}
	
	public void addAddToCartActionListener(ActionListener actionListener) {
		addToCartButton.addActionListener(actionListener);
	}
	
	public void addRemoveToppingActionListener(ActionListener actionListener, String toppingName) {
		for (JButton removeToppingButton : removeToppingButtons) {
			if (removeToppingButton.getName().equals(toppingName)) {
				removeToppingButton.addActionListener(actionListener);
			}
		}
	}
	
	public void addChangeToppingCostActionListener(ActionListener actionListener, String toppingName) {
		for (JButton changeToppingCostButton : changeToppingCostButtons) {
			if (changeToppingCostButton.getName().equals(toppingName)) {
				changeToppingCostButton.addActionListener(actionListener);
			}
		}
	}

	public void addSelectToppingActionListener(ActionListener actionListener, String toppingName) {
		for (JRadioButton selectButton : selectButtons) {
			if (selectButton.getName().equals(toppingName + "Select")) {
				selectButton.addActionListener(actionListener);
			}
		}
	}

	public void addUnSelectToppingActionListener(ActionListener actionListener, String toppingName) {
		for (JRadioButton unSelectButton : unSelectButtons) {
			if (unSelectButton.getName().equals(toppingName + "UnSelect")) {
				unSelectButton.addActionListener(actionListener);
			}
		}
	}

	public void addOpenRestaurantProfileActionListener(ActionListener actionListener) {
		restaurantProfilePageButton.addActionListener(actionListener);
	}
	
	public void addOpenOrderHistoryActionListener(ActionListener actionListener) {
		orderHistoryButton.addActionListener(actionListener);
	}
	
	public void addOpenRestaurantsActionListener(ActionListener actionListener) {
		restaurantsButton.addActionListener(actionListener);
	}

	public void addOpenShoppingCartActionListener(ActionListener actionListener) {
		shoppingCartButton.addActionListener(actionListener);
	}

	public void addOpenUserProfileActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addLogoutActionListener(ActionListener actionListener) {
		logoutButton.addActionListener(actionListener);
	}

	public FrameManager getFrameManager() {
		return this.fm;
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(getFrameManager().getFrame(), message);
	}
	
	public String showInputDialog(String message) {
		return JOptionPane.showInputDialog(getFrameManager().getFrame(), message);
	}
	
	public Object showSelectDialog(String message, Object[] choices) {
		return JOptionPane.showInputDialog(getFrameManager().getFrame(), message, null, JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[0]);
	}
	
	private String toTitleCase(String givenString) {
	    String[] arr = givenString.split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }          
	    return sb.toString().trim();
	}  

	@Override
	public void update() {
		setContent();
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
