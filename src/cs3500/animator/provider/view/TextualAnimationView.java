package cs3500.animator.provider.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.provider.model.Motion;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.SimpleAnimationImpl;
import cs3500.animator.provider.util.Canvas;

/**
 * Generates a textual view of the user-created animation.
 * This view can either be outputted to terminal (System.out)
 * or saved to a provided file name as a .txt file.
 */
public class TextualAnimationView implements IView {

  private final ArrayList<Motion> changes;
  private final ArrayList<Shapes> shapes;
  private final Canvas canvas;

  /**
   * Creates a Textual View of a Model.
   *
   * @param changes the list of Motions to be Animated.
   * @param shapes the Shapes to be Animated.
   * @param canvas the size of the Canvas for the Animation.
   */
  public TextualAnimationView(ArrayList<Motion> changes, ArrayList<Shapes> shapes, Canvas canvas) {
    this.changes = changes;
    this.shapes = shapes;
    this.canvas = canvas;
  }

  @Override
  public void display(String out) throws IllegalArgumentException {
    StringBuilder textOutput = new StringBuilder();
    textOutput.append(canvas.getCanvasString());
    for (int i = 0; i < shapes.size(); i++) {
      String key = shapes.get(i).getName();
      String type = shapes.get(i).getType();
      textOutput.append("\n");
      textOutput.append(String.format("shape %s %s", key, type));
      if (i == 0) {
        textOutput.append("\n");
      }
      SimpleAnimationImpl.modelToString(textOutput, key, changes);
    }
    if (out == null || out.equals("")) {
      System.out.println(textOutput.toString());
    } else {
      save(out, textOutput.toString());
    }
  }

  @Override
  public void setSpeed(int ticksPerSec) {
    // no speed support for Textual View.
  }

  /**
   * Saves a formatted text view to a file.
   *
   * @param out the file's intended name.
   * @param output the formatted output to be written to file.
   * @throws IllegalArgumentException if the file cannot be created.
   */
  private void save(String out, String output) throws IllegalArgumentException {
    // The FileWriter constructor throws IOException, which must be caught.
    PrintWriter outputMaker;
    try {
      outputMaker = new PrintWriter(new FileWriter(out));
      outputMaker.println(output);
    } catch (IOException fw) {
      throw new IllegalArgumentException("How did you get here?");
    }
    outputMaker.close();
  }
}
