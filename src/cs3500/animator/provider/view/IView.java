
package cs3500.animator.provider.view;

/**
 * The public facing functionality for an Animation's Views.
 */
public interface IView {

  /**
   * Displays or saves the View the User specifies.
   *
   * @param out the file to save to for SVG or Text View.
   */
  void display(String out);

  /**
   * Sets the animation's speed.
   *
   * @param ticksPerSec the user's desired speed for the Animation.
   */
  void setSpeed(int ticksPerSec);
}
