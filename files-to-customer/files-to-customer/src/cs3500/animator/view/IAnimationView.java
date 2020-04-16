package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyIAnimationModel;

/**
 * This represents the view in the model view controller pattern. The view is solely responsible for
 * displaying the animation in different ways.
 *
 * <p>The display() method behaves differently for the different implementations
 * of the IAnimationView. </p>
 */
public interface IAnimationView {

  /**
   * Displays the view for the animation using the model.
   *
   * <p> A view can be displayed in three ways: </p>
   *
   * <p> -Text View : A textual description of the view. </p>
   *
   * <p> -Visual View : An actual animation of the view that plays inside of a window. </p>
   *
   * <p> -SVG View : A textual view formatted to be compliant with SVG syntax.</p>
   * @param model The model we want to represent for displaying this view.
   * @throws IllegalArgumentException if the model is null
   */
  void display(ReadOnlyIAnimationModel model);
}
