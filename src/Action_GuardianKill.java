
public class Action_GuardianKill extends Action_Kill {

	public Action_GuardianKill(int player, int actor) {
		super(player, actor);
	}

	@Override
	public String getFriendlyName() {
		return "Use Rifle";
	}

}
