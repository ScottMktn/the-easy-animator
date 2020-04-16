package cs3500.animator.view;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.ReadOnlyIAnimationModel;
import cs3500.animator.shapes.IShape;

/**
 * This view represents the animation as a text description formatted to be compliant with SVG
 * syntax. This class's constructor consumes a integer "tempo" as the time measurement for the
 * SVGView needs to be in ticks per second. The constructor also takes in an Appendable out that
 * will be wrote to, as well as a width and height representing the canvas width and height.
 *
 * <p> The VisualView method "display()" will write to the Appendable using the information in
 * the model and format the text according the SVG compliance. </p>
 */
public class SVGView implements IAnimationView {
  private static final String XML_ELLIPSE = "ellipse";
  private static final String XML_RECTANGLE = "rect";
  private static HashMap<String, String> shapeNameToXMLTag;

  static {
    shapeNameToXMLTag = new HashMap<>();
    shapeNameToXMLTag.put("rec", XML_RECTANGLE);
    shapeNameToXMLTag.put("ell", XML_ELLIPSE);
  }


  private final String FOOTER = "</svg>";
  private final int tempo;
  private Appendable out;

  /**
   * Constructor for the text description view that is compliant with SVG-format.
   *
   * @param out   the reader
   * @param tempo the ticks per second of the animation
   */
  public SVGView(Appendable out, int tempo) {
    try {
      Utils.requireNonNegative(tempo);
      Utils.requireNonZero(tempo);

      this.out = Objects.requireNonNull(out);
      this.tempo = tempo;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(ErrorMessages.NullParameter.errorMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid height and/or width and/or tempo");
    }
  }

  @Override
  public void display(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);

    String header = "<svg height=\"" + model.getBounds().height +
            "\" width=\"" + model.getBounds().width + "\" " +
            "version=\"1.1\"" + " " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n";
    try {
      out.append(header);
      ArrayList<IAnimatedShape> shapes = model.getAllAnimatedShapes();
      for (IAnimatedShape shape : shapes) {
        out.append(this.animatedShapeToXML(shape));
      }
      out.append(this.FOOTER);
    } catch (IOException e) {
      throw new IllegalArgumentException(ErrorMessages.BadAppendable.errorMessage());
    }
  }


  /**
   * Converts the amount of ticks passed into to an equivalent amount of time using the tempo
   * variable.
   */
  private double convertTicksToSeconds(int ticks) {
    return ((double) ticks / tempo) * 1000;
  }

  /**
   * Formats a color object to the rgb attribute formatting needed by the SVG XML.
   *
   * @param color the color we want to get formatted.
   * @return a string that is simple "rgb(R, G, B)".
   */
  private String rgbXMLFormat(Color color) {
    return "rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
  }

  /**
   * Creates a single animation for an rectangle using the current shape as the start state and the
   * instruction as the end state.
   *
   * @param shape       the shape at which the animation is going to start.
   * @param instruction the end state for that shape.
   * @return a String of xml that has tags and animations for an rectangle.
   */
  private String rectangleAnimations(IShape shape, EventInstructions instruction) {
    double begin = convertTicksToSeconds(instruction.getStartTick());
    double duration = convertTicksToSeconds(instruction.getEndTick() - instruction.getStartTick());

    StringBuilder xml = new StringBuilder();

    xml.append(generateAnimateTag("x", begin, duration,
            Double.toString(shape.getPosition().getX()),
            Double.toString(instruction.getEndPosn().getX())));

    xml.append(generateAnimateTag("y", begin, duration,
            Double.toString(shape.getPosition().getY()),
            Double.toString(instruction.getEndPosn().getY())));

    xml.append(generateAnimateTag("width", begin, duration,
            Integer.toString(shape.getWidth()),
            Integer.toString(instruction.getEndWidth())));

    xml.append(generateAnimateTag("height", begin, duration,
            Integer.toString(shape.getHeight()),
            Integer.toString(instruction.getEndHeight())));

    xml.append(generateAnimateTag("fill", begin, duration,
            this.rgbXMLFormat(shape.getColor()),
            this.rgbXMLFormat(instruction.getEndColor())));

    xml.append("\t<animateTransform attributeName=\"transform\" type=\"rotate\" " +
            "from=\"" + shape.getAngle() + " " +
            (shape.getPosition().getX() + shape.getWidth() / 2) + " " +
            (shape.getPosition().getY() + shape.getHeight() / 2) + "\" " +
            "to=\"" + instruction.getEndAngle() + " " +
            (instruction.getEndPosn().getX() + instruction.getEndWidth() / 2) + " " +
            (instruction.getEndPosn().getY() + instruction.getEndHeight() / 2) + "\" " +
            "begin=\"" + begin + "ms\"" +
            " dur=\"" + duration + "ms\" " +
            "fill=\"freeze\" />\n");


    return xml.toString();
  }

  /**
   * Creates a single animation for an ellipse using the current shape as the start state and the
   * instruction as the end state.
   *
   * @param shape       the shape at which the animation is going to start.
   * @param instruction the end state for that shape.
   * @return a String of xml that has tags and animations for an ellipse.
   */
  private String ellipseAnimations(IShape shape, EventInstructions instruction) {
    double begin = convertTicksToSeconds(instruction.getStartTick());
    double duration = convertTicksToSeconds(instruction.getEndTick() - instruction.getStartTick());

    StringBuilder xml = new StringBuilder();

    String xLeftStart = Double.toString(shape.getPosition().getX() + shape.getWidth() / 2.0);
    String xLeftEnd = Double.toString(
            instruction.getEndPosn().getX() + instruction.getEndWidth() / 2.0);
    xml.append(generateAnimateTag("cx", begin, duration, xLeftStart, xLeftEnd));

    String yTopStart = Double.toString(shape.getPosition().getY() + shape.getHeight() / 2.0);
    String yTopEnd = Double.toString(
            instruction.getEndPosn().getY() + instruction.getEndHeight() / 2.0);
    xml.append(generateAnimateTag("cy", begin, duration, yTopStart, yTopEnd));

    String widthStart = Integer.toString(shape.getWidth() / 2);
    String widthEnd = Integer.toString(instruction.getEndWidth() / 2);
    xml.append(generateAnimateTag("rx", begin, duration, widthStart, widthEnd));

    String heightStart = Integer.toString(shape.getHeight() / 2);
    String heightEnd = Integer.toString(instruction.getEndHeight() / 2);
    xml.append(generateAnimateTag("ry", begin, duration, heightStart, heightEnd));

    xml.append(generateAnimateTag("fill", begin, duration,
            this.rgbXMLFormat(shape.getColor()),
            this.rgbXMLFormat(instruction.getEndColor())));

    xml.append("\t<animateTransform attributeName=\"transform\" type=\"rotate\" " +
            "from=\"" + shape.getAngle() + " " + xLeftStart + " " + yTopStart + "\" " +
            "to=\"" + instruction.getEndAngle() + " " + xLeftEnd + " " + yTopEnd + "\" " +
            "begin=\"" + begin + "ms\"" +
            " dur=\"" + duration + "ms\" " +
            "fill=\"freeze\" />\n");

    return xml.toString();
  }

  /**
   * Delegates which shape conversion method to use to convert the shape to XML.
   *
   * @param shape the shape we want to convert into XML
   * @return a String XML-formatted text description
   */
  private String animatedShapeToXML(IAnimatedShape shape) {
    Objects.requireNonNull(shape);
    String shapeAsString = shape.getShape().asString();
    if (!shapeNameToXMLTag.containsKey(shapeAsString)) {
      throw new IllegalArgumentException("Do not have support for the given shape");
    }
    return this.convertShapeToXML(shape, shapeNameToXMLTag.get(shapeAsString));
  }

  /**
   * Converts a shape and its event instructions into XML format.
   *
   * @param shape     the rectangle that we want to convert into XML
   * @param shapeType the String rep of what Shape we have
   * @return a String XML-formatted text description
   */
  private String convertShapeToXML(IAnimatedShape shape, String shapeType) {
    Objects.requireNonNull(shape);
    StringBuilder xml = new StringBuilder();

    ArrayList<EventInstructions> instructions = shape.getEvents();
    // temp Shape is a copy of the shape - used to hold state of previous for the
    // foreach loop
    IShape tempShape = shape.getShape();

    xml.append("<" + shapeType + " id=\"" + tempShape.getName()
            + "\" style=\"stroke-width:0;stroke:rgb(0,0,0)\">\n");
    for (int i = 0; i < instructions.size(); i++) {
      EventInstructions instruction = instructions.get(i);
      xml.append("\t<!-- EventInstruction " + (i + 1) + " -->\n");

      if (shapeType == XML_ELLIPSE) {
        xml.append(ellipseAnimations(tempShape, instruction));
      } else if (shapeType == XML_RECTANGLE) {
        xml.append(rectangleAnimations(tempShape, instruction));
      }
      tempShape.executeInstructions(instruction);
    }

    xml.append("</" + shapeType + ">\n");

    return xml.toString();
  }

  /**
   * Generates the XML line with the appropriate formatting using the parameters.
   *
   * @param attName the attribute name
   * @param begin   the start time of the animation
   * @param dur     the time length of the animation
   * @param from    the initial data
   * @param to      the end data
   * @return a properly XML formatted string
   */
  private String generateAnimateTag(String attName,
                                    double begin, double dur, String from, String to) {
    return "\t<animate " +
            "attributeName=\"" + attName + "\" " +
            "attributeType=\"XML\" " +
            "begin=\"" + begin + "ms\" " +
            "dur=\"" + dur + "ms\" " +
            "fill=\"freeze\" " +
            "from=\"" + from + "\" " +
            "to=\"" + to + "\"/>\n";
  }
}
