package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.domain.Order;
import model.domain.User;
import model.utilities.FoodCostPair;
import model.utilities.Observer;
import model.utilities.Subject;

public class OrdersFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject subject;
	private FrameManager fm;
	private JPanel mainPanel;

	private JPanel leftSide;
	private JPanel content;

	private JButton restaurantPageButton;
	private JButton logoutButton;

	public OrdersFrame(FrameManager fm, User currentRestaurant) {
		this.fm = fm;
		this.subject = currentRestaurant;

		subject.register(this);

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
		getFrameManager().setNewPanel(mainPanel, "userprofile");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Order History", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton restaurantPageButton = new JButton("Restaurant Profile");
		restaurantPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		restaurantPageButton.setPreferredSize(new Dimension(200, 50));
		this.restaurantPageButton = restaurantPageButton;

		JButton orderHistoryButton = new JButton("Order History");
		orderHistoryButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		orderHistoryButton.setPreferredSize(new Dimension(200, 50));
		orderHistoryButton.setEnabled(false);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(200, 50));
		this.logoutButton = logoutButton;

		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(restaurantPageButton, gbc);
		leftSide.add(orderHistoryButton, gbc);
		leftSide.add(logoutButton, gbc);
	}

	public void setContent() {
		User user = ((User) this.subject);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 10, 5, 10);

		List<Order> orders = user.getOrderHistory();
		Collections.sort(orders, new Comparator<Order>() {
		    @Override
		    public int compare(Order o1, Order o2) {
		        return o2.getOrderDate().compareTo(o1.getOrderDate());
		    }
		});

		JPanel ordersPanel = new JPanel(new GridBagLayout());
		for (Order order : orders) {
			JPanel orderPanel = new JPanel(new GridBagLayout());
			JLabel customerName = new JLabel("<html><FONT COLOR=RED>Customer:</FONT> " + order.getCustomerName() + "</html>");
			JLabel orderDate = new JLabel("<html><FONT COLOR=RED>Order Date:</FONT> " + order.getOrderDate() + "</html>");
			JLabel orderAddress = new JLabel("<html><FONT COLOR=RED>Order Address:</FONT> " + order.getAddress() + "</html>");
			JLabel totalCost = new JLabel("<html><FONT COLOR=RED>Total Cost:</FONT> $" + order.getOrderTotal() + "</html>");
			JLabel items = new JLabel("<html><FONT COLOR=RED>Items:</FONT></html>");
			
			orderPanel.add(customerName, gbc);
			orderPanel.add(orderDate, gbc);
			orderPanel.add(orderAddress, gbc);
			orderPanel.add(totalCost, gbc);
			orderPanel.add(items, gbc);
			for (FoodCostPair pair : order.getItems()) {
				JPanel itemPanel = new JPanel();
				itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.PAGE_AXIS));
				itemPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));
				JLabel itemName = new JLabel("<html><FONT COLOR=RED>Item:</FONT> " + pair.getFood() + "</html>");
				JLabel itemCost = new JLabel("<html><FONT COLOR=RED>Cost:</FONT> $" +String.format("%.2f", pair.getCost()) + "</html>");
				itemPanel.add(itemName, gbc);
				itemPanel.add(itemCost, gbc);
				Dimension d = itemPanel.getPreferredSize();
				d.width = 310;
				d.height = 70;
				itemPanel.setPreferredSize(d);
				orderPanel.add(itemPanel, gbc);
			}
			
			orderPanel.setBorder(new RoundedLineBorder(Color.BLACK, 1, 10, true));
			ordersPanel.add(orderPanel, gbc);
		}

		panel.add(ordersPanel, gbc);

		content.removeAll();
		content.add(new JScrollPane(panel));
		getFrameManager().setNewPanel(mainPanel, "userprofile");
	}
	
	public void addOpenRestaurantProfileActionListener(ActionListener actionListener) {
		restaurantPageButton.addActionListener(actionListener);
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
