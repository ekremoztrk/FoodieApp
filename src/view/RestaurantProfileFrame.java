package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.domain.Menu;
import model.domain.Restaurant;
import model.domain.User;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import model.utilities.ToppingPricePair;

public class RestaurantProfileFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject currentRestaurant;
	private FrameManager fm;
	private JPanel mainPanel;

	private JPanel leftSide;
	private JPanel content;

	private JButton logoutButton;
	private JButton orderHistoryButton;
	private JButton createMenuButton;

	private JButton changeNameButton;
	private JButton changeUsernameButton;
	private JButton changeAddressButton;
	private List<JButton> foodButtons;
	private List<JButton> removeMenuButtons;
	private List<JButton> addItemButtons;
	private List<JButton> removeItemButtons;

	public RestaurantProfileFrame(FrameManager fm, User currentRestaurant) {
		this.fm = fm;
		this.currentRestaurant = currentRestaurant;
		this.foodButtons = new ArrayList<>();
		this.removeMenuButtons = new ArrayList<>();
		this.addItemButtons = new ArrayList<>();
		this.removeItemButtons = new ArrayList<>();
		
		currentRestaurant.register(this);

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
		
		setLeftSide();
		setContent();
		getFrameManager().setNewPanel(mainPanel, "user");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Restaurant Profile", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton profilePageButton = new JButton("Restaurant Profile");
		profilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		profilePageButton.setPreferredSize(new Dimension(200, 50));
		profilePageButton.setEnabled(false);

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
		leftSide.add(profilePageButton, gbc);
		leftSide.add(orderHistoryButton, gbc);
		leftSide.add(logoutButton, gbc);
	}

	public void setContent() {
		Restaurant restaurant = ((Restaurant) this.currentRestaurant);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 10, 10, 10);
		
		JLabel restaurantName = new JLabel("<html><FONT SIZE=5><FONT COLOR=GREEN>Restaurant Name: </FONT>" + restaurant.getName() + "</FONT></html>");
		JButton changeNameButton = new JButton("Change Name");
		this.changeNameButton = changeNameButton;
		
		JLabel restaurantUsername = new JLabel("<html><FONT SIZE=5><FONT COLOR=GREEN>Restaurant Username: </FONT>" + restaurant.getUsername() + "</FONT></html>");
		JButton changeUsernameButton = new JButton("Change Username");
		this.changeUsernameButton = changeUsernameButton;
		
		JLabel restaurantAddress = new JLabel("<html><FONT SIZE=5><FONT COLOR=GREEN>Restaurant Address: </FONT>" + restaurant.getAddress() + "</FONT></html>");
		JButton changeAddressButton = new JButton("Change Address");
		this.changeAddressButton = changeAddressButton;
		
		JLabel menuLabel = new JLabel("<html><FONT SIZE=5 COLOR=GREEN>MENU</FONT></html>");
		JButton createMenuButton = new JButton("Create New Menu");
		this.createMenuButton = createMenuButton;
		
		panel.add(restaurantName, gbc);
		panel.add(changeNameButton, gbc);
		panel.add(restaurantUsername, gbc);
		panel.add(changeUsernameButton, gbc);
		panel.add(restaurantAddress, gbc);
		panel.add(changeAddressButton, gbc);
		
		JPanel menuTitlePanel = new JPanel();
		menuTitlePanel.add(menuLabel);
		menuTitlePanel.add(createMenuButton);
		panel.add(menuTitlePanel, gbc);
		
		List<Menu> menu = restaurant.getMenu();
		
		for (Menu submenu : menu) {
			JPanel submenuPanel = new JPanel(new GridBagLayout());
			JLabel submenuName = new JLabel("<html><FONT SIZE=5 COLOR=RED>" + submenu.getName() + "</FONT></html>");
			
			JButton removeMenuButton = new JButton("Remove This Menu");
			removeMenuButton.setName(submenu.getName());
			removeMenuButtons.add(removeMenuButton);
			
			JButton addItemButton = new JButton("Add Item To Menu");
			addItemButton.setName(submenu.getName());
			addItemButtons.add(addItemButton);
			
			JPanel submenuTitlePanel = new JPanel();
			submenuTitlePanel.add(addItemButton);
			submenuTitlePanel.add(submenuName);
			submenuTitlePanel.add(removeMenuButton);
			submenuPanel.add(submenuTitlePanel, gbc);
			submenuPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));

			Map<FoodCostPair, List<ToppingPricePair>> items = submenu.getItems();
			for (FoodCostPair item : items.keySet()) {
				JButton removeItemButton = new JButton("Remove This Item");
				removeItemButton.setName(item.getFood());
				removeItemButtons.add(removeItemButton);
				
				JButton foodButton = new JButton();
				ImageIcon icon = new ImageIcon("assets/" + item.getFood().toLowerCase() + ".jpg");

				foodButton.setName(item.getFood());
				foodButton.setIcon(icon);
				foodButton.setBackground(java.awt.Color.WHITE);
				foodButton.setPreferredSize(new Dimension(300, 300));
				foodButtons.add(foodButton);
				
				submenuPanel.add(foodButton, gbc);
				submenuPanel.add(removeItemButton, gbc);
				
				JLabel itemName = new JLabel("<html><FONT SIZE=4><FONT COLOR=RED>" + toTitleCase(item.getFood()) + "</FONT> $" + item.getCost() + "</FONT></html>");
				submenuPanel.add(itemName, gbc);
				
				String toppings = "None";
				List<ToppingPricePair> itemToppings = items.get(item);
				if(itemToppings.size() > 0) {
					toppings = "";
					for (ToppingPricePair topping : itemToppings) {
						toppings += toTitleCase(topping.getTopping()) + ", ";
					}
					toppings = toppings.substring(0, toppings.length() - 2);
				}
				
				JLabel availableToppings = new JLabel("<html><FONT SIZE=3 COLOR=RED>Available Toppings: </FONT>" + toppings + "</html>");
				availableToppings.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
				submenuPanel.add(availableToppings, gbc);
				
			}
			
			if (items.keySet().size() == 0) {
				submenuPanel.add(new JLabel("<html><FONT SIZE=4 COLOR=RED>This menu does not have any items right now.</FONT></html>"), gbc);
			}
			
			panel.add(submenuPanel, gbc);
		}
		
		if (menu.size() == 0) {
			panel.add(new JLabel("<html><FONT SIZE=4 COLOR=RED>This restaurant does not have any menus right now.</FONT></html>"), gbc);
		}

		content.removeAll();
		content.add(new JScrollPane(panel));
		getFrameManager().setNewPanel(mainPanel, "restaurant_profile");
	}

	public void addRemoveMenuActionListener(ActionListener actionListener, String menuName) {
		for (JButton jButton : removeMenuButtons) {
			if (jButton.getName().equals(menuName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}
	
	public void addRemoveItemActionListener(ActionListener actionListener, String itemName) {
		for (JButton jButton : removeItemButtons) {
			if (jButton.getName().equals(itemName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}
	
	public void addNewItemActionListener(ActionListener actionListener, String menuName) {
		for (JButton jButton : addItemButtons) {
			if (jButton.getName().equals(menuName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}

	public void addOpenFoodActionListener(ActionListener actionListener, String foodName) {
		for (JButton jButton : foodButtons) {
			if (jButton.getName().equals(foodName)) {
				jButton.addActionListener(actionListener);
			}
		}
	}
	
	public void addCreateMenuActionListener(ActionListener actionListener) {
		createMenuButton.addActionListener(actionListener);
	}
	
	public void addChangeNameActionListener(ActionListener actionListener) {
		changeNameButton.addActionListener(actionListener);
	}
	
	public void addChangeUsernameActionListener(ActionListener actionListener) {
		changeUsernameButton.addActionListener(actionListener);
	}
	
	public void addChangeAddressActionListener(ActionListener actionListener) {
		changeAddressButton.addActionListener(actionListener);
	}

	public void addOpenOrderHistoryActionListener(ActionListener actionListener) {
		orderHistoryButton.addActionListener(actionListener);
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
		this.currentRestaurant = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.currentRestaurant = null;
	}
}
