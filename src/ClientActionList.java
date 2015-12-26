import java.util.ArrayList;

public class ClientActionList {
	
	private final ArrayList<ClientAction> actions;

	public ClientActionList() {
		actions = new ArrayList<ClientAction>();
	}

	public void addAction(ClientAction action) {
		actions.add(action);
	}
	
	public ClientAction getAction(int index) {
		return actions.get(index);
	}
	
	public ArrayList<ClientAction> getArrayList() {
		return actions;
	}

	public void addList(ClientActionList list) {
		for (ClientAction a : list.getArrayList()) {
			addAction(a);
		}
	}
	
	public int size() {
		return actions.size();
	}
	
	@Override
	public String toString() {
		String output = "";
		for (ClientAction a : actions) {
			output += a + "\n";
		}
		return output;
	}
}
