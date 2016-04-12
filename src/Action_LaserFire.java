
public class Action_LaserFire extends Action {

	public Action_LaserFire(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doAction(FieldOfView game) {
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Fire Lasers";
	}

}
