package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.ReadOnlyIAnimationModel;
import cs3500.animator.shapes.IShape;

/**
 * This view represents the animation as a text description. This class does not contain a tempo as
 * the time measurement for the text description will be in "ticks".
 *
 * <p> The TextView method "display()" will write to the appendable with the
 * shapes and their corresponding event instructions inside the model</p>
 */
public class TextView implements IAnimationView {
  private Appendable out;
  private java.awt.Rectangle bounds;

  /**
   * Basic constructor used to create a TextView.
   *
   * @param out   an appendable that the TextView will write ot
   */
  public TextView(Appendable out) {
    try {
      this.out = Objects.requireNonNull(out);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(ErrorMessages.NullParameter.errorMessage());
    }
  }

  /**
   * Generates a text description that describes each shape in the list of AnimatedShapes and
   * display's all of their movements.
   *
   * @param listOfAnimatedShapes the shapes we want to describe.
   * @return a string representation of the shapes and their EventInstructions.
   */
  private String getTextDescription(ArrayList<IAnimatedShape> listOfAnimatedShapes) {
    Utils.requireNonNull(listOfAnimatedShapes);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("canvas" + " " + bounds.x + " " + bounds.y + " "
            + bounds.width + " " + bounds.height + "\n");
    stringBuilder.append("# Describes the motion of the shapes between two moments of animation\n");
    stringBuilder.append("# i = the index id number of the shape\n");
    stringBuilder.append("# t = tick\n");
    stringBuilder.append("# (x, y) = the position of the shape\n");
    stringBuilder.append("# (w, h) = the dimensions of the shape\n");
    stringBuilder.append("# (r, g, b) = the color of the shape [0, 225]\n\n");

    String shapeIdFormat = "%3s%6s";
    String eventParamsFormat = "%5s%5s%5s%5s%5s%5s%5s%5s";
    stringBuilder.append(String.format("%30s%40s\n", "start", "end"));
    stringBuilder.append(String.format("#          %30s%40s\n",
            "--------------------------------------",
            "--------------------------------------"));

    stringBuilder.append(String.format(shapeIdFormat, "i", "Shape"));
    stringBuilder.append(String.format(eventParamsFormat,
            "t", "x", "y", "w", "h", "r", "g", "b"));
    stringBuilder.append(String.format(eventParamsFormat,
            "t", "x", "y", "w", "h", "r", "g", "b"));
    stringBuilder.append("\n");
    for (int i = 0; i < listOfAnimatedShapes.size(); i++) {
      // iterate through the event instructions and format it here
      IAnimatedShape animatedShape = listOfAnimatedShapes.get(i);
      IShape shape = animatedShape.getShape();
      ArrayList<EventInstructions> events = animatedShape.getEvents();
      eventParamsFormat = "%5s%5.0f%5.0f%5s%5s%5s%5s%5s";

      for (EventInstructions event : events) {
        stringBuilder.append(String.format(shapeIdFormat, (i + 1), shape.getName()));

        stringBuilder.append(String.format(eventParamsFormat,
                event.getStartTick(),
                shape.getPosition().getX(),
                shape.getPosition().getY(),
                shape.getWidth(),
                shape.getHeight(),
                shape.getColor().getRed(),
                shape.getColor().getGreen(),
                shape.getColor().getBlue()));

        // method that executes the event instructions on the shape
        shape.executeInstructions(event);

        stringBuilder.append(String.format(eventParamsFormat,
                event.getEndTick(),
                shape.getPosition().getX(),
                shape.getPosition().getY(),
                shape.getWidth(),
                shape.getHeight(),
                shape.getColor().getRed(),
                shape.getColor().getGreen(),
                shape.getColor().getBlue()));

        stringBuilder.append("\n");
      }

      stringBuilder.append(i + 1 < listOfAnimatedShapes.size() ? "\n" : "");
    }
    return stringBuilder.toString();
  }

  /**
   * Displays the view for the animation.
   */
  @Override
  public void display(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);
    bounds = model.getBounds();
    try {
      out.append(this.getTextDescription(model.getAllAnimatedShapes()));
    } catch (IOException e) {
      throw new IllegalArgumentException(ErrorMessages.BadAppendable.errorMessage());
    }
  }
}
