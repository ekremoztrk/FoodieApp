package model.utilities;

public interface Observer {

	public void update();
	
	public void addSubject(Subject sub);
	
	public void removeSubject(Subject sub);
}
