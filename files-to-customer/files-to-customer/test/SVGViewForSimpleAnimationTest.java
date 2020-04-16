import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;

import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.Triangle;
import cs3500.animator.view.SVGView;

import static junit.framework.TestCase.assertEquals;

/**
 * Houses all the tests for SVG simple animations.
 * Extends the MakeSimple so we dont copy and paste code.
 */
public class SVGViewForSimpleAnimationTest extends MakeSimpleAnimationTest {

  @Test
  public void simpleAnimationBaseCaseToSVGTest() {
    Appendable actualOut = new StringBuilder();
    SVGView view = new SVGView(actualOut, 5);
    view.display(new AnimationModelImpl());
    assertEquals("<svg height=\"1000\" width=\"1000\" " +
            "version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>", actualOut.toString());
  }

  @Test
  public void simpleAnimateSingleShapeEllipseToSVGTest() {
    Appendable actualOut = new StringBuilder();
    IAnimationModel modelSingleShape = new AnimationModelImpl();
    modelSingleShape.placeAnimatedShape(
            new AnimatedShape(
                    new Ellipse("blah",
                            new Position2D(2, 2),
                            10,
                            10, Color.BLACK)));
    SVGView view = new SVGView(actualOut, 5);
    view.display(modelSingleShape);
    assertEquals("<svg height=\"1000\" width=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"blah\" style=\"stroke-width:0;stroke:rgb(0,0,0)\">\n" +
            "</ellipse>\n" +
            "</svg>", actualOut.toString());
  }

  @Test
  public void simpleAnimateSingleShapeRectangleToSVGTest() {
    Appendable actualOut = new StringBuilder();
    IAnimationModel modelSingleShape = new AnimationModelImpl();
    modelSingleShape.placeAnimatedShape(
            new AnimatedShape(
                    new Rectangle("blah",
                            new Position2D(2, 2),
                            10,
                            10, Color.BLACK)));
    SVGView view = new SVGView(actualOut, 5);
    view.display(modelSingleShape);
    assertEquals("<svg height=\"1000\" width=\"1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"blah\" style=\"stroke-width:0;stroke:rgb(0,0,0)\">\n" +
            "</rect>\n" +
            "</svg>", actualOut.toString());
  }


  @Test
  public void simpleAnimationToSVGTest() {
    Appendable actualOut = new StringBuilder();
    SVGView view = new SVGView(actualOut, 5);
    view.display(this.model);
    assertEquals("<svg height=\"1000\" width=\"1000\" version=\"1.1\" xmlns=\"http://www." +
            "w3.org/2000/svg\">\n" +
            "<rect id=\"r0\" style=\"stroke-width:0;stroke:rgb(0,0,0)\">\n" +

            "\t<!-- EventInstruction 1 -->\n" +

            "\t<animate attributeName=\"x\" attributeType=\"XML\" begin=\"200.0ms\" dur=\"" +
            "1800.0ms\" fill=\"freeze\" from=\"200.0\" to=\"200.0\"/>\n" +
            "\t<animate attributeName=\"y\" attributeType=\"XML\" begin=\"200.0ms\" dur=\"" +
            "1800.0ms\" fill=\"freeze\" from=\"200.0\" to=\"200.0\"/>\n" +
            "\t<animate attributeName=\"width\" attributeType=\"XML\" begin=\"200.0ms\" d" +
            "ur=\"1800.0ms\" fill=\"freeze\" from=\"50\" to=\"50\"/>\n" +
            "\t<animate attributeName=\"height\" attributeType=\"XML\" begin=\"200.0ms\" " +
            "dur=\"1800.0ms\" fill=\"freeze\" from=\"100\" to=\"100\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"200.0ms\" du" +
            "r=\"1800.0ms\" fill=\"freeze\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\"/>\n" +
            "\t<!-- EventInstruction 2 -->\n" +

            "\t<animate attributeName=\"x\" attributeType=\"XML\" begin=\"2000.0ms\" dur=" +
            "\"8000.0ms\" fill=\"freeze\" from=\"200.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"y\" attributeType=\"XML\" begin=\"2000.0ms\" dur=" +
            "\"8000.0ms\" fill=\"freeze\" from=\"200.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"width\" attributeType=\"XML\" begin=\"2000.0ms\" " +
            "dur=\"8000.0ms\" fill=\"freeze\" from=\"50\" to=\"50\"/>\n" +
            "\t<animate attributeName=\"height\" attributeType=\"XML\" begin=\"2000.0ms\"" +
            " dur=\"8000.0ms\" fill=\"freeze\" from=\"100\" to=\"100\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"2000.0ms\" d" +
            "ur=\"8000.0ms\" fill=\"freeze\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\"/>\n" +
            "\t<!-- EventInstruction 3 -->\n" +

            "\t<animate attributeName=\"x\" attributeType=\"XML\" begin=\"10000.0ms\" dur" +
            "=\"200.0ms\" fill=\"freeze\" from=\"300.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"y\" attributeType=\"XML\" begin=\"10000.0ms\" dur" +
            "=\"200.0ms\" fill=\"freeze\" from=\"300.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"width\" attributeType=\"XML\" begin=\"10000.0ms\"" +
            " dur=\"200.0ms\" fill=\"freeze\" from=\"50\" to=\"50\"/>\n" +
            "\t<animate attributeName=\"height\" attributeType=\"XML\" begin=\"10000.0ms\"" +
            " dur=\"200.0ms\" fill=\"freeze\" from=\"100\" to=\"100\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"10000.0ms\" " +
            "dur=\"200.0ms\" fill=\"freeze\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\"/>\n" +
            "\t<!-- EventInstruction 4 -->\n" +

            "\t<animate attributeName=\"x\" attributeType=\"XML\" begin=\"10200.0ms\" dur" +
            "=\"3800.0ms\" fill=\"freeze\" from=\"300.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"y\" attributeType=\"XML\" begin=\"10200.0ms\" dur" +
            "=\"3800.0ms\" fill=\"freeze\" from=\"300.0\" to=\"300.0\"/>\n" +
            "\t<animate attributeName=\"width\" attributeType=\"XML\" begin=\"10200.0ms\"" +
            " dur=\"3800.0ms\" fill=\"freeze\" from=\"50\" to=\"25\"/>\n" +
            "\t<animate attributeName=\"height\" attributeType=\"XML\" begin=\"10200.0ms\"" +
            " dur=\"3800.0ms\" fill=\"freeze\" from=\"100\" to=\"100\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"10200.0ms\" " +
            "dur=\"3800.0ms\" fill=\"freeze\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\"/>\n" +
            "\t<!-- EventInstruction 5 -->\n" +

            "\t<animate attributeName=\"x\" attributeType=\"XML\" begin=\"14000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"300.0\" to=\"200.0\"/>\n" +
            "\t<animate attributeName=\"y\" attributeType=\"XML\" begin=\"14000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"300.0\" to=\"200.0\"/>\n" +
            "\t<animate attributeName=\"width\" attributeType=\"XML\" begin=\"14000.0ms\"" +
            " dur=\"6000.0ms\" fill=\"freeze\" from=\"25\" to=\"25\"/>\n" +
            "\t<animate attributeName=\"height\" attributeType=\"XML\" begin=\"14000.0ms\"" +
            " dur=\"6000.0ms\" fill=\"freeze\" from=\"100\" to=\"100\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"14000.0ms\" " +
            "dur=\"6000.0ms\" fill=\"freeze\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\"/>\n" +
            "</rect>\n" +

            "<ellipse id=\"e0\" style=\"stroke-width:0;stroke:rgb(0,0,0)\">\n" +

            "\t<!-- EventInstruction 1 -->\n" +

            "\t<animate attributeName=\"cx\" attributeType=\"XML\" begin=\"1200.0ms\" dur" +
            "=\"2800.0ms\" fill=\"freeze\" from=\"500.0\" to=\"500.0\"/>\n" +
            "\t<animate attributeName=\"cy\" attributeType=\"XML\" begin=\"1200.0ms\" dur" +
            "=\"2800.0ms\" fill=\"freeze\" from=\"100.0\" to=\"100.0\"/>\n" +
            "\t<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"1200.0ms\" dur" +
            "=\"2800.0ms\" fill=\"freeze\" from=\"60\" to=\"60\"/>\n" +
            "\t<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"1200.0ms\" dur" +
            "=\"2800.0ms\" fill=\"freeze\" from=\"30\" to=\"30\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"1200.0ms\" d" +
            "ur=\"2800.0ms\" fill=\"freeze\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 0, 255)\"/>\n" +
            "\t<!-- EventInstruction 2 -->\n" +

            "\t<animate attributeName=\"cx\" attributeType=\"XML\" begin=\"4000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"500.0\" to=\"500.0\"/>\n" +
            "\t<animate attributeName=\"cy\" attributeType=\"XML\" begin=\"4000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"100.0\" to=\"280.0\"/>\n" +
            "\t<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"4000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"60\" to=\"60\"/>\n" +
            "\t<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"4000.0ms\" dur" +
            "=\"6000.0ms\" fill=\"freeze\" from=\"30\" to=\"30\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"4000.0ms\" d" +
            "ur=\"6000.0ms\" fill=\"freeze\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 0, 255)\"/>\n" +
            "\t<!-- EventInstruction 3 -->\n" +

            "\t<animate attributeName=\"cx\" attributeType=\"XML\" begin=\"10000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"500.0\" to=\"500.0\"/>\n" +
            "\t<animate attributeName=\"cy\" attributeType=\"XML\" begin=\"10000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"280.0\" to=\"400.0\"/>\n" +
            "\t<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"10000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"60\" to=\"60\"/>\n" +
            "\t<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"10000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"30\" to=\"30\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"10000.0ms\" " +
            "dur=\"4000.0ms\" fill=\"freeze\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 170, 85)\"/>\n" +
            "\t<!-- EventInstruction 4 -->\n" +

            "\t<animate attributeName=\"cx\" attributeType=\"XML\" begin=\"14000.0ms\" du" +
            "r=\"2000.0ms\" fill=\"freeze\" from=\"500.0\" to=\"500.0\"/>\n" +
            "\t<animate attributeName=\"cy\" attributeType=\"XML\" begin=\"14000.0ms\" du" +
            "r=\"2000.0ms\" fill=\"freeze\" from=\"400.0\" to=\"400.0\"/>\n" +
            "\t<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"14000.0ms\" du" +
            "r=\"2000.0ms\" fill=\"freeze\" from=\"60\" to=\"60\"/>\n" +
            "\t<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"14000.0ms\" du" +
            "r=\"2000.0ms\" fill=\"freeze\" from=\"30\" to=\"30\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"14000.0ms\" " +
            "dur=\"2000.0ms\" fill=\"freeze\" from=\"rgb(0, 170, 85)\" to=\"rgb(0, 255, 0)\"/>\n" +
            "\t<!-- EventInstruction 5 -->\n" +

            "\t<animate attributeName=\"cx\" attributeType=\"XML\" begin=\"16000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"500.0\" to=\"500.0\"/>\n" +
            "\t<animate attributeName=\"cy\" attributeType=\"XML\" begin=\"16000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"400.0\" to=\"400.0\"/>\n" +
            "\t<animate attributeName=\"rx\" attributeType=\"XML\" begin=\"16000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"60\" to=\"60\"/>\n" +
            "\t<animate attributeName=\"ry\" attributeType=\"XML\" begin=\"16000.0ms\" du" +
            "r=\"4000.0ms\" fill=\"freeze\" from=\"30\" to=\"30\"/>\n" +
            "\t<animate attributeName=\"fill\" attributeType=\"XML\" begin=\"16000.0ms\" " +
            "dur=\"4000.0ms\" fill=\"freeze\" from=\"rgb(0, 255, 0)\" to=\"rgb(0, 255, 0)\"/>\n" +
            "</ellipse>\n" +
            "</svg>", actualOut.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new SVGView(new StringBuilder(), 24).display(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new SVGView(null, 24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTempo() {
    new SVGView(new StringBuilder(), -24);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailingAppendable() {
    Appendable actualOut = new FailingAppendable();
    SVGView view = new SVGView(actualOut, 24);
    view.display(this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnsupportedShape() {
    IShape triangle = new Triangle("Triangle", new Position2D(0, 0),
            50, 100, new Color(255, 0, 0));
    this.model.placeAnimatedShape(new AnimatedShape(triangle, new ArrayList<EventInstructions>()));

    Appendable actualOut = new StringBuilder();
    SVGView view = new SVGView(actualOut, 24);
    view.display(this.model);
  }
}
