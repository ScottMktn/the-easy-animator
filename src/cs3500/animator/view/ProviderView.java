package cs3500.animator.view;

import cs3500.animator.misc.Utils;
import cs3500.animator.model.SourceToProviderModel;
import cs3500.animator.provider.view.EditAnimationView;
import cs3500.animator.provider.view.IView;
import cs3500.animator.model.ReadOnlyIAnimationModel;

/**
 * Represents the implementation for the editor view that was given to us from our customers.
 * This object takes in our customer's view as a parameter in its constructor and has a private
 * field inside of it representing the customer's editor view. One instance of this object
 * represents a view in the model-view-controller pattern. NOTE: Not all of the functionalities
 * required from the assignment are properly implemented because our customer's code was not
 * complete and comprehensive.
 */
public class ProviderView implements IAnimationView {
  private IView providerView;

  /**
   * Basic constructor that takes in an adapted model and passes it into our customer's editor view.
   * @param model the adapted source to provider model
   * @throws IllegalArgumentException if the model is null
   */
  public ProviderView(SourceToProviderModel model) {
    Utils.requireNonNull(model);
    this.providerView = new EditAnimationView(model, model.getCanvas());
  }

  @Override
  public void display(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);
    this.providerView.display("");
  }
}
