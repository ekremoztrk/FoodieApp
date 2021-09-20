package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -7798292689167507569L;
	private JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);;
	private JButton loginButton = new JButton("Login");
	private FrameManager fm;
	
	public LoginFrame(FrameManager fm) {
		this.fm = fm;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        content.add(new JLabel("Username: "), gbc);
        content.add(usernameField, gbc);
        content.add(new JLabel("Password: "), gbc);
        content.add(passwordField, gbc);
        content.add(loginButton, gbc);
        
        panel.add(content);
        getFrameManager().setNewPanel(panel, "login");
	}
	
    public void addLoginActionListener(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    public char[] getPassword() {
        return this.passwordField.getPassword();
    }

    public FrameManager getFrameManager() {
        return this.fm;
    }

    public void showMessage(String message) { 
    	JOptionPane.showMessageDialog(getFrameManager().getFrame(), message);
    }
}
