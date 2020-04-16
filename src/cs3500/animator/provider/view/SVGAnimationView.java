
package cs3500.animator.provider.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.provider.model.Motion;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.util.Canvas;

/**
 * Generates a Svg formatted view of the user-created animation.
 * This view can either be outputted to terminal (System.out)
 * or saved to a provided file name as a .svg file.
 */
public class SVGAnimationView implements IView {

  private final ArrayList<Motion> changes;
  private final ArrayList<Shapes> shapes;
  private final Canvas canvas;
  private int speed;

  /**
   * Creates an SVG formatted view.
   *
   * @param changes the list of Motions to be Animated.
   * @param shapes the Shapes to be Animated.
   * @param canvas the Canvas size for the Animation.
   */
  public SVGAnimationView(ArrayList<Motion> changes, ArrayList<Shapes> shapes, Canvas canvas) {
    this.changes = changes;
    this.shapes = shapes;
    this.canvas = canvas;
    this.speed = 1; // default case.
  }

  @Override
  public void display(String out) {
    StringBuilder svgOutput = new StringBuilder();
    svgOutput.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\""
        + " xmlns=\"http://www.w3.org/2000/svg\"> \n", this.canvas.w, this.canvas.h));
    for (Shapes shape : this.shapes) {
      String type = shape.getType();
      String key = shape.getName();
      boolean firstLine = true;
      for (Motion change : this.changes) {
        if (key.equals(change.getName()) && firstLine) {
          svgShapeFormat(svgOutput, type, change);
          firstLine = false;
        }
        if (key.equals(change.getName())) {
          svgMotionFormat(svgOutput, type, change);
        }
      }
      String type2 = "</ellipse>\n\n";
      if (type.equals("rectangle")) {
        type2 = "</rect>\n\n";
      }
      svgOutput.append(type2);
    }
    svgOutput.append("\n</svg>");
    if (out.equals("")) {
      System.out.println(svgOutput.toString());
    } else {
      save(out, svgOutput.toString());
    }
  }

  /**
   * Formats a Motion in proper SVG format.
   *
   * @param svgOutput the intended file name for the SVG view, if any.
   * @param type the type of shape.
   * @param m the motion to be formatted.
   */
  private void svgMotionFormat(StringBuilder svgOutput, String type, Motion m) {
    String x;
    String y;
    String w;
    String h;
    int speedConverter = 1000 / speed;
    switch (type) {
      case "rectangle":
        x = " attributeName=\"x\"";
        y = " attributeName=\"y\"";
        w = " attributeName=\"width\"";
        h = " attributeName=\"height\"";
        break;
      case "ellipse":
        x = " attributeName=\"cx\"";
        y = " attributeName=\"cy\"";
        w = " attributeName=\"rx\"";
        h = " attributeName=\"ry\"";
        break;
      default:
        throw new IllegalArgumentException("Give us a shape you square");
    }

    int finalTime = changes.get(changes.size() - 1).getEndTime();
    String freezeRemove = " fill=\"freeze\" />";

    if (finalTime == m.getEndTime()) {
      freezeRemove = " fill=\"remove\" />";
    }

    String animate = (String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\"",
        m.getStartTime() * speedConverter, m.getEndTime() * speedConverter));

    if (m.getStartX() != m.getEndX()) {
      svgOutput.append(animate).append(x).append(String.format(" from=\"%d\" to=\"%d\"",
          m.getStartX(), m.getEndX()));
      svgOutput.append(freezeRemove).append("\n");
    }

    if (m.getStartY() != m.getEndY()) {
      svgOutput.append(animate).append(y).append(String.format(" from=\"%d\" to=\"%d\"",
          m.getStartY(), m.getEndY()));
      svgOutput.append(freezeRemove).append("\n");
    }

    if (m.getStartingWidth() != m.getEndingWidth()) {
      svgOutput.append(animate).append(w).append(String.format(" from=\"%d\" to=\"%d\"",
          m.getStartingWidth(), m.getEndingHeight()));
      svgOutput.append(freezeRemove).append("\n");
    }

    if (m.getStartingHeight() != m.getEndingHeight()) {
      svgOutput.append(animate).append(h).append(String.format(" from=\"%d\" to=\"%d\"",
          m.getEndingWidth(), m.getEndingHeight()));
      svgOutput.append(freezeRemove).append("\n");
    }

    if (!m.getOldColor().equals(m.getNewColor())) {
      svgOutput.append(animate).append(" attributeName=\"fill\"").append(String.format(" "
              + "from=\"rgb(%d, %d, %d)\"", m.getOldColor().getRed(), m.getOldColor().getGreen(),
          m.getOldColor().getBlue())).append(String.format(" to=\"rgb(%d, %d, %d)\"",
          m.getNewColor().getRed(), m.getNewColor().getGreen(),
          m.getNewColor().getBlue())).append(freezeRemove).append("\n");
    }
  }

  /**
   * Formats a shape into proper SVG format.
   *
   * @param svgOutput the intended file name to be saved to, if any.
   * @param type the type of shape.
   * @param m the Motion to be formatted.
   */
  private void svgShapeFormat(StringBuilder svgOutput, String type, Motion m) {
    if (type.equals("rectangle")) {
      svgOutput.append(String.format("<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" "
              + "fill=\"rgb(%d, %d, %d)\" visibility=\"visible\">\n",
          m.getName(), m.getStartX(), m.getStartY(), m.getStartingWidth(),
          m.getStartingHeight(), m.getOldColor().getRed(),
          m.getOldColor().getGreen(), m.getOldColor().getBlue()));
    } else if (type.equals("ellipse")) {
      svgOutput.append(String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
              + "fill=\"rgb(%d, %d, %d)\" visibility=\"visible\">\n",
          m.getName(), m.getStartX(), m.getStartY(), m.getStartingWidth(),
          m.getStartingHeight(), m.getOldColor().getRed(),
          m.getOldColor().getGreen(), m.getOldColor().getBlue()));
    }
  }

  @Override
  public void setSpeed(int s) {
    if (s > 0 && speed <= 1000) {
      this.speed = s;
    } else {
      throw new IllegalArgumentException("cant divide by zero");
    }
  }

  /**
   * Saves a SVG formatted view to a provided file.
   *
   * @param out the file's name.
   * @param output the output to be written to file.
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
