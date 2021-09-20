package view;

import model.domain.Order;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShoppingCartFrame extends JFrame implements Observer {
	private static final long serialVersionUID = -4853864434524144396L;
	private Subject subject;
	private FrameManager fm;
	private JPanel mainPanel;

	private JPanel leftSide;
	private JPanel content;

	private JButton restaurantsButton;
	private JButton profilePageButton;
	private JButton logoutButton;
	private JButton placeOrderButton;

	public ShoppingCartFrame(FrameManager fm, Order currentOrder) {
		this.fm = fm;
		this.subject = currentOrder;

		if (currentOrder != null)
			currentOrder.register(this);

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
		getFrameManager().setNewPanel(mainPanel, "restaurant");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Restaurant", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton restaurantsButton = new JButton("Restaurants");
		restaurantsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		restaurantsButton.setPreferredSize(new Dimension(200, 50));
		this.restaurantsButton = restaurantsButton;

		JButton shoppingCartButton = new JButton("Shopping Cart");
		shoppingCartButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		shoppingCartButton.setPreferredSize(new Dimension(200, 50));
		shoppingCartButton.setEnabled(false);

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

	public void setContent() {
		Order order = ((Order) this.subject);
		JPanel panel = new JPanel(new GridBagLayout());
		if (order != null && order.getItems().size() > 0) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.insets = new Insets(10, 10, 10, 10);

			JPanel orderPanel = new JPanel(new GridBagLayout());
			JLabel restaurantName = new JLabel(
					"<html><FONT COLOR=RED>Restaurant:</FONT> " + order.getRestaurantName() + "</html>");
			JLabel orderAddress = new JLabel(
					"<html><FONT COLOR=RED>Order Address:</FONT> " + order.getAddress() + "</html>");
			JLabel totalCost = new JLabel(
					"<html><FONT COLOR=RED>Total Cost:</FONT> $" + order.getOrderTotal() + "</html>");
			JLabel items = new JLabel("<html><FONT COLOR=RED>Items:</FONT></html>");

			orderPanel.add(restaurantName, gbc);
			orderPanel.add(orderAddress, gbc);
			orderPanel.add(totalCost, gbc);
			orderPanel.add(items, gbc);
			for (FoodCostPair pair : order.getItems()) {
				JPanel itemPanel = new JPanel();
				itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
				itemPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));
				JLabel itemName = new JLabel("<html><FONT COLOR=RED>Item:</FONT> " + pair.getFood() + "</html>");
				JLabel itemCost = new JLabel(
						"<html><FONT COLOR=RED>Cost:</FONT> $" + String.format("%.2f", pair.getCost()) + "</html>");
				itemPanel.add(itemName, gbc);
				itemPanel.add(itemCost, gbc);
				Dimension d = itemPanel.getPreferredSize();
				d.width = 310;
				d.height = 70;
				itemPanel.setPreferredSize(d);
				orderPanel.add(itemPanel, gbc);
			}

			placeOrderButton = new JButton();
			placeOrderButton.setText("Place Order");
			orderPanel.add(placeOrderButton);

			orderPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));
			panel.add(orderPanel);
		} else {
			JLabel infoText = new JLabel("<html><FONT COLOR=RED>There is no order for now.</FONT> </html>");
			panel.add(infoText);
		}

		content.removeAll();
		content.add(new JScrollPane(panel));
		getFrameManager().setNewPanel(mainPanel, "user");
	}

	public void addPlaceOrderActionListener(ActionListener actionListener) {
		placeOrderButton.addActionListener(actionListener);
	}

	public void addOpenRestaurantsActionListener(ActionListener actionListener) {
		restaurantsButton.addActionListener(actionListener);
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
