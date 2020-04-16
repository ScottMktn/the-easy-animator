package cs3500.animator.controller;

/**
 * This represents a controller in the model view controller design pattern. Will mediate between
 * the animation model and the animation view. This interface contains one method: -play : which
 * kickstarts the animation.
 * The controller will be called in the main method and consume an IAnimationModel and an
 * IAnimationView.
 */
public interface IAnimationController {

  /**
   * Starts the Animation when this method is called.
   * Calls the view of the controller and sets the visible to true.
   */
  void play();
}
