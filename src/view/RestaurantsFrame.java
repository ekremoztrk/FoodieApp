package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.domain.FoodieService;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;

public class RestaurantsFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject model;
	private FrameManager fm;

	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;

	private JButton profilePageButton;
	private JButton shoppingCartButton;
	private JButton logoutButton;

	private List<JButton> restaurantButtons;

	public RestaurantsFrame(FoodieService model, FrameManager fm) {
		this.fm = fm;
		this.restaurantButtons = new ArrayList<>();
		this.model = model;

		model.register(this);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.leftSide = leftSide;

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.setName("content");
		this.content = content;

		mainPanel.add(this.leftSide);
		mainPanel.add(this.content);
		mainPanel.setName("mainpanel");
		this.mainPanel = mainPanel;
		
		setLeftSide();
		setContent();
		getFrameManager().setNewPanel(mainPanel, "restaurants");
	}

	public void setLeftSide() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel titleLabel = new JLabel("Foodie", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

		JLabel pageLabel = new JLabel("Restaurants", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));

		JButton restaurantsButton = new JButton("Restaurants");
		restaurantsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		restaurantsButton.setPreferredSize(new Dimension(200, 50));
		restaurantsButton.setEnabled(false);
		
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

	public void setContent() {
		FoodieService model = (FoodieService) this.model;
		List<User> restaurants = model.getAllRestaurants();

		JPanel cards = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(20, 10, 10, 10);
		for (User restaurant : restaurants) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			String restaurantName = restaurant.getName();

			JButton restaurantButton = new JButton(restaurantName);
			restaurantButton.setName(restaurant.getName());
			restaurantButtons.add(restaurantButton);

			panel.setPreferredSize(new Dimension(350, 70));
			restaurantButton.setPreferredSize(new Dimension(300, 50));

			panel.add(restaurantButton);

			cards.add(panel, gbc);
		}

		content.removeAll();
		content.add(new JScrollPane(cards));
		if(getFrameManager().getCurrentPage().equals("restaurants")) {
			getFrameManager().setNewPanel(mainPanel, "restaurants");
		}
	}

	public void addOpenRestaurantActionListener(ActionListener actionListener,String name) {
		for (JButton jButton : restaurantButtons) {
			if(jButton.getName().equals(name))
				jButton.addActionListener(actionListener);
		}
	}

	public void addOpenProfileActionListener(ActionListener actionListener) {
		profilePageButton.addActionListener(actionListener);
	}

	public void addOpenShoppingCartActionListener(ActionListener actionListener) {
		shoppingCartButton.addActionListener(actionListener);
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

	public int showConfirmDialog(String message, String title) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		return JOptionPane.showConfirmDialog(getFrameManager().getFrame(), message, title, dialogButton);
	}
	
	@Override
	public void update() {
		setContent();
	}

	@Override
	public void addSubject(Subject sub) {
		this.model = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.model = null;
	}
}
