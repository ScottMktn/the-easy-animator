import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.misc.Position2D;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;

import static junit.framework.TestCase.assertEquals;


/**
 * Test that makes a simple animation with two objects on the screen. Testing only the
 * AnimationModelImpl.
 */
public class MakeSimpleAnimationTest {

  protected IAnimationModel model;

  // the shapes in the simple animation
  protected IShape rectangle;
  protected IShape ellipse;

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
    this.rectangle = new Rectangle("r0", new Position2D(200, 200),
            50, 100, new Color(255, 0, 0));
    this.ellipse = new Ellipse("e0", new Position2D(440, 70),
            120, 60, new Color(0, 0, 255));

    // Make a an animated shape object with the shape and its events
    this.model.placeAnimatedShape(new AnimatedShape(this.rectangle, this.getRectangleEvents()));
    this.model.placeAnimatedShape(new AnimatedShape(this.ellipse, this.getEllipseEvents()));
  }



  @Test
  public void testGettingShapesAtTick0() {
    this.model.getShapesAt(0);
    assertEquals(0, this.model.getShapesAt(0).size());
  }

  @Test
  public void testGettingShapesAtTick1() {
    assertEquals(1, this.model.getShapesAt(1).size());
  }

  @Test
  public void testGettingShapesAtTick10() {
    assertEquals(2, this.model.getShapesAt(10).size());
  }

  @Test
  public void testGettingShapesAtTick10AsString() {
    assertEquals("rec", this.model.getShapesAt(10).get(0).asString());
  }

  @Test
  public void testGettingShapesAtTick10CheckColor() {
    assertEquals(new Position2D(300, 300), this.model.getShapesAt(60).get(0).getPosition());
  }

  @Test
  public void testGettingShapesAtTick100() {
    assertEquals(0, this.model.getShapesAt(1000).size());
  }


}
