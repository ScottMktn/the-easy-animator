package cs3500.animator.view;

import java.awt.event.ActionListener;

import cs3500.animator.model.EventInstructions;

/**
 * Represents an animation view that adds the capabilities to start, pause, resume, and
 * restart the animation. The interactive view also adds the ability enable or disable looping.
 * And finally, the interactive view also has the capability to speed up or slow down the
 * tempo of the animation.
 */

public interface IInteractiveView extends IAnimationView {

  /**
   * Set the listener for any actions.
   * @throws IllegalArgumentException if the listener is null
   */
  void setListener(ActionListener listener);

  /**
   * Pauses the timer in the animation for the view.
   */
  void pause();

  /**
   * Starts the timer in the animation for the view.
   */
  void start();

  /**
   * Restarts the entire animation for the view.
   * Recalls back to the original state of the animation
   * and sets the tempo to the default tempo (1).
   */
  void restart();

  /**
   * Doubles the tempo of the animation.
   * Handles integer overflow errors by checking to see if the tempo * 2 is greater than the
   * integer max value.
   */
  void doubleTempo();

  /**
   * Halves the tempo of the animation.
   * If halving the tempo will decrease it below 1,
   * then the tempo will be the DEFAULT TEMPO (1).
   */
  void halveTempo();

  /**
   * Gets the current string in the drop down combo box.
   * @return a string representing the currently selected shape
   */
  String getDeleteDropDownString();

  /**
   * Either enables or disables looping depending on the current state of the animation.
   * Passing in a true value enables the loop, and false disables the loop.
   */
  void loopHandler(boolean b);

  /**
   * Updates the comboBox using the shapes in the existing model.
   */
  void updateComboBox();

  /**
   * Gets the fields from the user and populates it nicely into an event instruction object that
   * serves as a tuple.
   * @return the user's inputs.
   * @throws IllegalArgumentException if the user input is invalid.
   */
  EventInstructions getKeyEventParams() throws IllegalArgumentException;

  /**
   * The current selected keyFrame by index.
   */
  int selectedKeyFrame();

  /**
   * Displays a dialog box with the message shown.
   * @param message we want to display back to the user.
   */
  void showDialogBox(String message);


  /**
   * The Selected Shape Type for when adding a new shape.
   * The drop down is populated with a string,
   * "Ellipse" and "Rectangle"
   * @return the selected shape type in the dropdown.
   */
  String shapeTypeDropDown();

  /**
   * For the shape name we want to add this method helps
   * get the text field text.
   * @return the shape's name we want to add.
   */
  String shapeNameToAdd();

  /**
   * Opens a file explorer where the user can select a file.
   * @return the file path the user selects.
   */
  String getFileFromUser();

  /**
   * The selected layer in the layer tab.
   * @return which number layer that is selected.
   */
  int getSelectedLayer();

  /**
   * Determines if the visible checkbox is checked or not.
   * @return the checkbox's check state.
   */
  boolean getVisibleIndicator();

  /**
   * Sets the visible indicator for whether or not the layer that is
   * selected is currently visible.
   */
  void setVisibleIndicator(boolean value);
}
