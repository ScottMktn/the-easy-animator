import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.TextView;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Houses all the tests for the textual view implementation.
 */
public class TextViewForSimpleAnimationTest {
  private IAnimationModel model;
  // the shapes in the simple animation

  /**
   * FOR SETUP.
   */
  private ArrayList<EventInstructions> getRectangleEvents() {
    ArrayList<EventInstructions> events = new ArrayList<EventInstructions>();
    Color red = new Color(255, 0, 0);
    EventInstructions eventOne = new EventInstructions(
            1, 10,
            new Position2D(200, 200), 50, 100, red);
    EventInstructions eventTwo = new EventInstructions(
            10, 50,
            new Position2D(300, 300), 50, 100, red);
    EventInstructions eventThree = new EventInstructions(
            50, 51,
            new Position2D(300, 300), 50, 100, red);
    EventInstructions eventFour = new EventInstructions(
            51, 70,
            new Position2D(300, 300), 25, 100, red);
    EventInstructions eventFive = new EventInstructions(
            70, 100,
            new Position2D(200, 200), 25, 100, red);

    events.add(eventOne);
    events.add(eventTwo);
    events.add(eventThree);
    events.add(eventFour);
    events.add(eventFive);
    return events;
  }

  /**
   * FOR SETUP.
   */
  private ArrayList<EventInstructions> getEllipseEvents() {
    ArrayList<EventInstructions> events = new ArrayList<EventInstructions>();
    Color blue = new Color(0, 0, 255);
    Color greenBlue = new Color(0, 170, 85);
    Color green = new Color(0, 255, 0);

    EventInstructions eventOne = new EventInstructions(
            6, 20,
            new Position2D(440, 70), 120, 60, blue);
    EventInstructions eventTwo = new EventInstructions(
            20, 50,
            new Position2D(440, 250), 120, 60, blue);
    EventInstructions eventThree = new EventInstructions(
            50, 70,
            new Position2D(440, 370), 120, 60, greenBlue);
    EventInstructions eventFour = new EventInstructions(
            70, 80,
            new Position2D(440, 370), 120, 60, green);
    EventInstructions eventFive = new EventInstructions(
            80, 100,
            new Position2D(440, 370), 120, 60, green);

    events.add(eventOne);
    events.add(eventTwo);
    events.add(eventThree);
    events.add(eventFour);
    events.add(eventFive);
    return events;
  }

  @Before
  public void setup() {
    this.model = new AnimationModelImpl();
    // the initial starting positions of the shapes
    Rectangle rectangle = new Rectangle("r0", new Position2D(200, 200),
            50, 100, new Color(255, 0, 0));
    Ellipse ellipse = new Ellipse("e0", new Position2D(440, 70),
            120, 60, new Color(0, 0, 255));

    // Make a an animated shape object with the shape and its events
    this.model.placeAnimatedShape(new AnimatedShape(rectangle, this.getRectangleEvents()));
    this.model.placeAnimatedShape(new AnimatedShape(ellipse, this.getEllipseEvents()));
  }

  @Test
  public void testNullModel() {
    try {
      new TextView(new StringBuilder()).display(null);
      fail("Should have failed!");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in", e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testNullOutAppendable() {
    try {
      new TextView(null);
      fail("Should have failed!");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in", e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailingAppendable() {
    String output =
            "# Describes the motion of the shapes between two moments of animation\n" +
                    "# i = the index id number of the shape\n" +
                    "# t = tick\n" +
                    "# (x, y) = the position of the shape\n" +
                    "# (w, h) = the dimensions of the shape\n" +
                    "# (r, g, b) = the color of the shape [0, 225]\n" +
                    "\n" +
                    "                         start                        " +
                    "             end\n" +
                    "#          --------------------------------------  " +
                    "--------------------------------------\n" +
                    "  i Shape    t    x    y    w    h    r    g    b    " +
                    "t    x    y    w    h    r    g    b\n" +
                    "  1   rec    1  200  200   50  100  255    0    0   " +
                    "10  200  200   50  100  255    0    0\n" +
                    "  1   rec   10  200  200   50  100  255    0    0  " +
                    " 50  300  300   50  100  255    0    0\n" +
                    "  1   rec   50  300  300   50  100  255    0    0  " +
                    " 51  300  300   50  100  255    0    0\n" +
                    "  1   rec   51  300  300   50  100  255    0    0  " +
                    " 70  300  300   25  100  255    0    0\n" +
                    "  1   rec   70  300  300   25  100  255    0    0  " +
                    "100  200  200   25  100  255    0    0\n" +
                    "\n" +
                    "  2   ell    6  440   70  120   60    0    0  255  " +
                    " 20  440   70  120   60    0    0  255\n" +
                    "  2   ell   20  440   70  120   60    0    0  255  " +
                    " 50  440  250  120   60    0    0  255\n" +
                    "  2   ell   50  440  250  120   60    0    0  255  " +
                    " 70  440  370  120   60    0  170   85\n" +
                    "  2   ell   70  440  370  120   60    0  170   85  " +
                    " 80  440  370  120   60    0  255    0\n" +
                    "  2   ell   80  440  370  120   60    0  255    0  " +
                    "100  440  370  120   60    0  255    0\n";

    Appendable actualOutput = new FailingAppendable();
    IAnimationView textView = new TextView(actualOutput);
    textView.display(this.model);
  }

  @Test
  public void testGetStringOutput() {
    String output = "canvas 0 0 1000 1000\n" +
            "# Describes the motion of the shapes between two moments of animation\n" +
            "# i = the index id number of the shape\n" +
            "# t = tick\n" +
            "# (x, y) = the position of the shape\n" +
            "# (w, h) = the dimensions of the shape\n" +
            "# (r, g, b) = the color of the shape [0, 225]\n" +
            "\n" +
            "                         start                                     end\n" +
            "#          --------------------------------------  " +
            "--------------------------------------\n" +
            "  i Shape    t    x    y    w    h    r    g    b  " +
            "  t    x    y    w    h    r    g    b\n" +
            "  1    r0    1  200  200   50  100  255    0    0  " +
            " 10  200  200   50  100  255    0    0\n" +
            "  1    r0   10  200  200   50  100  255    0    0  " +
            " 50  300  300   50  100  255    0    0\n" +
            "  1    r0   50  300  300   50  100  255    0    0  " +
            " 51  300  300   50  100  255    0    0\n" +
            "  1    r0   51  300  300   50  100  255    0    0  " +
            " 70  300  300   25  100  255    0    0\n" +
            "  1    r0   70  300  300   25  100  255    0    0  " +
            "100  200  200   25  100  255    0    0\n" +
            "\n" +

            "  2    e0    6  440   70  120   60    0    0  255  " +
            " 20  440   70  120   60    0    0  255\n" +
            "  2    e0   20  440   70  120   60    0    0  255  " +
            " 50  440  250  120   60    0    0  255\n" +
            "  2    e0   50  440  250  120   60    0    0  255  " +
            " 70  440  370  120   60    0  170   85\n" +
            "  2    e0   70  440  370  120   60    0  170   85  " +
            " 80  440  370  120   60    0  255    0\n" +
            "  2    e0   80  440  370  120   60    0  255    0  " +
            "100  440  370  120   60    0  255    0\n";

    Appendable actualOutput = new StringBuilder();
    IAnimationView textView = new TextView(actualOutput);
    textView.display(this.model);
    assertEquals(output, actualOutput.toString());
  }
}
