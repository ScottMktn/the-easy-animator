package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.InteractiveControllerImpl;
import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationModelBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.InteractiveVisualView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;

/**
 * Main methods that creates a model, loads the models appropriately with the animation contents
 * desired. A desired view is constructed and the two are passed into the controller. We then call
 * the play method on the controller, which begins the animation.
 */
public final class Excellence {
  /**
   * Main handler for excellence program. Entry point for the program.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    String fileName = null;
    String viewType = null;
    OutType outType = OutType.DEFAULT;
    int tempo = 1;

    // Iterate through the command line arguments to gather information
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          fileName = args[i + 1];
          i++;
          break;
        case "-view":
          viewType = args[i + 1];
          i++;
          break;
        case "-out":
          outType = outHandler(args[i + 1]);
          i++;
          break;
        case "-speed":
          tempo = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          throw new IllegalArgumentException("Poorly formatted command line args");
      }
    }

    // Input file and view must NOT be null after the command line is iterated through
    Utils.requireNonNull(fileName, viewType);

    // Initialize the model, view, and controller
    IAnimationModel model = initializeModel(fileName);
    IAnimationView view = initializeView(viewType, outType, tempo);

    IAnimationController controller = initializeController(model, view, viewType, tempo);

    // Start the animation
    controller.play();

    outType.close();
  }

  /**
   * Initializes the model by parsing the file and using a builder to populate the information.
   *
   * @param fileName a string representing the file name
   * @return a well-constructed IAnimationModel
   */
  private static IAnimationModel initializeModel(String fileName) {
    AnimationBuilder<IAnimationModel> builder = new AnimationModelBuilder();
    Readable inputFile;
    try {
      inputFile = new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    AnimationReader.parseFile(inputFile, builder);

    return builder.build();
  }

  /**
   * Initializes the view according to the viewType and its appropriate parameters.
   *
   * @param viewType the string representing a textView, visualView, or SVGView
   * @param outType  the enumeration representing the type of appendable
   * @param tempo    the ticks per second of the animation
   * @return a well-constructed IAnimationView based on the view type
   */
  private static IAnimationView initializeView(String viewType,
                                               OutType outType, int tempo) {
    Utils.requireNonNull(viewType, outType);
    switch (viewType) {
      case "text":
        return new TextView(outType.getAppendable());
      case "visual":
        return new VisualView(tempo);
      case "svg":
        return new SVGView(outType.getAppendable(), tempo);
      case "edit":
        return new InteractiveVisualView(tempo);
      default:
        throw new IllegalArgumentException(ErrorMessages.UnsupportedView.errorMessage());
    }
  }

  /**
   * Initializes the controller with the passed in AnimationModel and AnimationView.
   *
   * @param model the animation model
   * @param view  the animation view
   * @return a well-constructed IAnimationController
   */
  private static IAnimationController initializeController(IAnimationModel model,
                                                           IAnimationView view,
                                                           String viewType,
                                                           int tempo) {
    if (viewType.equals("edit")) {
      return new InteractiveControllerImpl(model, new InteractiveVisualView(tempo));
    }
    else {
      return new AnimationControllerImpl(model, view);
    }
  }

  /**
   * Represents the type of out for the animation.
   */
  private enum OutType {
    DEFAULT(System.out),
    FILE();

    private Appendable out;

    /**
     * Convenience constructor for an OutType.
     */
    OutType() {
      // Nothing to do here
    }

    /**
     * Basic OutType constructor that consumes an appendable.
     *
     * @param out the appendable
     */
    OutType(Appendable out) {
      this.out = out;
    }

    /**
     * Retrieves the appendable.
     *
     * @return the OutType's appendable
     */
    private Appendable getAppendable() {
      return this.out;
    }

    /**
     * Sets the appendable to the passed in appendable.
     *
     * @param out what we want our appendable to be
     * @return the OutType with the new appendable
     */
    private OutType setAppendable(Appendable out) {
      this.out = out;
      return this;
    }

    private void close() {
      if (this == FILE) {
        try {
          ((FileWriter) this.out).close();
        } catch (IOException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      }
    }
  }

  /**
   * Delegates to the appropriate out type based on the string s that follows the -out key.
   *
   * @param s the argument following -out in the string args
   * @return the corresponding outType to the string s
   */
  private static OutType outHandler(String s) {
    if (s.equals("System.out")) {
      return OutType.DEFAULT;
    } else {
      try {
        return OutType.FILE.setAppendable(new FileWriter(s));
      } catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }

}
