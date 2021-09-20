package app;

import controller.MainController;
import model.domain.FoodieService;
import view.FrameManager;
import view.MainFrame;

public class FoodieApp {
	FrameManager fm;
	FrameManager fm2;

	public FoodieApp() {
		this.fm = new FrameManager();
		this.fm2 = new FrameManager();
	}
	
	@SuppressWarnings("unused")
	public void start() {
		FoodieService model = new FoodieService();
        MainFrame view = new MainFrame(this.fm);
        MainController controller = new MainController(model, this.fm);
		MainController controller2 = new MainController(model, this.fm2);
	}
}