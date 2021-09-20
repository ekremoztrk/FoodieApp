package view;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -2898943439738426049L;
	private FrameManager fm;
	
	public MainFrame(FrameManager fm) {
		super("Outfit Rating MVC");
		this.fm = fm;
		
        @SuppressWarnings("unused")
		LoginFrame login = new LoginFrame(this.fm);
	}
}
