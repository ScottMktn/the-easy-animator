package cs3500.animator.view;

/**
 * Represents all the Commands the View can send back to the controller to be executed.
 */
public enum Command {
  START("Start"),
  PAUSE("Pause"),
  RESTART("Restart"),
  TEMPO_DOUBLE("Tempo x2"),
  TEMPO_HALF("Tempo /2"),
  ENABLE("Enable"),
  DISABLE("Disable"),
  DELETE_SHAPE("Delete Shape"),
  DELETE_KEYFRAME("Delete Keyframe"),
  ADD_SHAPE("Add Shape"),
  ADD_KEYFRAME("Add KeyFrame"),
  ADD_LAYER("Add Layer"),
  SET_LAYER("Set Layer"),
  DELETE_LAYER("Delete Layer"),
  MOVE_LAYER_UP("Move up Layer"),
  MOVE_LAYER_DOWN("Move down Layer"),
  SET_VISIBLE("Set Visible"),
  OPEN("Open"),
  SAVE("Save");

  /**
   * String representation of the command.
   */
  private final String representation;

  /**
   * Initializes the Command with the string.
   * @param representation the String representation of the command.
   */
  Command(String representation) {
    this.representation = representation;
  }

  /**
   * The string representation for this command.
   */
  public String getString() {
    return this.representation;
  }


}
