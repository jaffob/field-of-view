import java.util.ArrayList;

public class ActionList {
	
	private final ArrayList<Action> actions;

	public ActionList() {
		actions = new ArrayList<Action>();
	}

	public void addAction(Action action) {
		actions.add(action);
	}
	
	public Action getAction(int index) {
		return actions.get(index);
	}
	
	public ArrayList<Action> getArrayList() {
		return actions;
	}

	public void addList(ActionList list) {
		for (Action a : list.getArrayList()) {
			addAction(a);
		}
	}
	
	public int size() {
		return actions.size();
	}
	
	@Override
	public String toString() {
		String output = "";
		for (Action a : actions) {
			output += a + "\n";
		}
		return output;
	}
}
