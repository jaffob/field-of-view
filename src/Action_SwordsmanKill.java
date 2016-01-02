
public class Action_SwordsmanKill extends Action_Kill {

	public Action_SwordsmanKill(int player, int actor) {
		super(player, actor);
	}

	@Override
	public String getFriendlyName() {
		return "Use Sword";
	}
}
