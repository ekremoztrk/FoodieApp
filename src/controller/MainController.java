package controller;

import model.domain.FoodieService;
import view.FrameManager;

public class MainController {

	private FoodieService model;
	private FrameManager fm;

	public MainController(FoodieService model, FrameManager fm) {
		this.model = model;
		this.fm = fm;
		
		SessionManager session = new SessionManager(null, this.fm, this.model);
		session.loginPage();
	}

}
