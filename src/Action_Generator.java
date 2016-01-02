
public class Action_Generator extends Action {

	private final boolean addPower;
	
	public Action_Generator(int player, int actor, boolean addPower) {
		super(player, actor);
		this.addPower = addPower;
	}

	public boolean isAddPower() {
		return addPower;
	}

	@Override
	public boolean doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		Integer currentPower = game.getGameStateVar("generator");
		
		if (currentPower == null ||
				(currentPower < 1 && !isAddPower()) ||
				(currentPower > 1 && isAddPower()))
		{
			return false;
		}
		
		game.setGameStateVar("generator", currentPower + (isAddPower() ? 1 : -1));
		game.getKnowledgeHandler().notifyGameStateVarChange("generator");
		return true;
	}

	@Override
	public String getFriendlyName() {
		return isAddPower() ? "Add Power" : "Remove Power";
	}

}
