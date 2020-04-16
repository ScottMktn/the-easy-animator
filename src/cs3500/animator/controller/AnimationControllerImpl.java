package cs3500.animator.controller;

import cs3500.animator.misc.Utils;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.IAnimationView;

/**
 * The implementation for the controller in the model view controller pattern. Will delegate in
 * between the view and model.
 * The constructor for the controller consumes an IAnimationModel and an IAnimationView.
 * The only method inside of the implementation for the controller is play(),
 * which calls kick starts
 * the animation and displays it.
 */
public class AnimationControllerImpl implements IAnimationController {
  private IAnimationModel model;
  private IAnimationView view;

  /**
   * Basic constructor for the controller.
   * Takes in a non-null model and a non-null view and sets the private fields equal to them.
   *
   * @param model the animation model we want to model
   * @param view  the animation view we want to view
   * @throws IllegalArgumentException if the view or the model is null
   */
  public AnimationControllerImpl(IAnimationModel model, IAnimationView view) {
    Utils.requireNonNull(view, model);
    this.view = view;
    this.model = model;
  }

  @Override
  public void play() {
    this.view.display(this.model);
  }
}
