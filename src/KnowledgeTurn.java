import java.util.ArrayList;


public class KnowledgeTurn {

	private final int turnPlayer;
	private final int turnNum;
	private final ArrayList<KnowledgeTurnComponent> components;
	
	public KnowledgeTurn(int turnPlayer, int turnNum) {
		this.turnPlayer = turnPlayer;
		this.turnNum = turnNum;
		components = new ArrayList<KnowledgeTurnComponent>();
	}

	public int getTurnPlayer() {
		return turnPlayer;
	}

	public int getTurnNum() {
		return turnNum;
	}

	public ArrayList<KnowledgeTurnComponent> getComponentsArrayList() {
		return components;
	}
	
	public KnowledgeTurnComponent getComponent(int index) {
		return components.get(index);
	}
	
	public void addComponent(KnowledgeTurnComponent component) {
		components.add(component);
	}
	
	public int getNumComponents() {
		return components.size();
	}

}
