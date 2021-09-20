package view;
import javax.swing.*;
import java.awt.*;

public class FrameManager {

    private JFrame frame;
    private String currentPage;

    public FrameManager() {
        this.frame = new JFrame();
        frame = new JFrame("Foodie");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setNewPanel(JPanel panel, String currentPage){
        frame.getContentPane().removeAll();
        setCurrentPage(currentPage);
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
}
