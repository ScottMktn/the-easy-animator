import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.misc.Position2D;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.Triangle;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.fail;

/**
 * Tests the AnimatedShape class, by building Animated shapes.
 */
public class AnimatedShapeTest {
  IAnimatedShape animatedShape0;
  IShape triangle0;
  EventInstructions validEvent;

  @Before
  public void setup() {
    this.triangle0 = new Triangle("t0",
            new Position2D(3, 3), 10, 4, new Color(255));
    this.animatedShape0 = new AnimatedShape(triangle0);
    this.validEvent = new EventInstructions(2, 7, new Position2D(10, 10),
            10, 10, new Color(255, 0, 0));
  }


  @Test
  public void testRemovingKeyFrameFromShapeInvalidIndex() {
    try {
      this.animatedShape0.removeKeyFrame(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The integer: -1.0 is less than zero", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeInvalidIndex1() {
    this.animatedShape0.addEvent(this.validEvent);
    try {
      this.animatedShape0.removeKeyFrame(2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("KeyEvent Doesn't exist at this time", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeFromFirstFrameIfThereIsNon() {
    try {
      this.animatedShape0.removeKeyFrame(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The shape must have 1 keyframe to know it's start", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeFromSecondFrameWithMultipleInstructions() {
    this.animatedShape0.addEvent(this.validEvent);
    this.animatedShape0.addEvent(new EventInstructions(
            7, 10, new Position2D(20, 10),
            10, 10, new Color(255, 0, 0)
    ));
    this.animatedShape0.addEvent(new EventInstructions(
            10, 20, new Position2D(30, 10),
            10, 10, new Color(255, 0, 0)
    ));

    this.animatedShape0.removeKeyFrame(0);

    assertEquals(2, this.animatedShape0.getEvents().size());

    assertEquals(this.animatedShape0.getEvents().get(0).getEndPosn(), new Position2D(20, 10));
    assertEquals(this.animatedShape0.getEvents().get(1).getEndPosn(), new Position2D(30, 10));

  }


  @Test
  public void testRemovingKeyFrameFromShapeFromSecondFrameWithMultipleInstructions1() {
    this.animatedShape0.addEvent(this.validEvent);
    this.animatedShape0.addEvent(new EventInstructions(
            7, 10, new Position2D(20, 10),
            10, 10, new Color(255, 0, 0)
    ));
    this.animatedShape0.addEvent(new EventInstructions(
            10, 20, new Position2D(30, 10),
            10, 10, new Color(255, 0, 0)
    ));

    this.animatedShape0.addEvent(new EventInstructions(
            20, 30, new Position2D(40, 10),
            10, 10, new Color(255, 0, 0)
    ));

    this.animatedShape0.removeKeyFrame(2);

    assertEquals(3, this.animatedShape0.getEvents().size());

    assertEquals(this.animatedShape0.getEvents().get(0).getEndPosn(), new Position2D(10, 10));
    assertEquals(this.animatedShape0.getEvents().get(1).getEndPosn(), new Position2D(30, 10));

    assertEquals(this.animatedShape0.getEvents().get(2).getEndPosn(), new Position2D(40, 10));
  }


  @Test
  public void testRemovingKeyFrameFromShapeFromSecondFrameWithMultipleInstructions0() {
    this.animatedShape0.addEvent(new EventInstructions(
            7, 10, new Position2D(20, 10),
            10, 10, new Color(255, 0, 0)
    ));

    this.animatedShape0.removeKeyFrame(1);

    assertEquals(1, this.animatedShape0.getEvents().size());

    assertEquals(7, this.animatedShape0.getEvents().get(0).getEndTick());

    assertEquals(7, this.animatedShape0.getEvents().get(0).getStartTick());

    assertEquals(this.animatedShape0.getEvents().get(0).getEndPosn(),
            this.animatedShape0.getShape().getPosition());

  }


  @Test
  public void testRemovingKeyFrameFromShapeFromSecondFrame() {

    this.animatedShape0.addEvent(this.validEvent);
    assertEquals(this.animatedShape0.getEvents().size(), 1);

    this.animatedShape0.removeKeyFrame(0);

    assertEquals(this.animatedShape0.getEvents().size(), 0);

    assertEquals(this.animatedShape0.getShape().getPosition(), new Position2D(10, 10));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsTick() {
    assertEquals(0, animatedShape0.getEvents().size());
    animatedShape0.addEvent(validEvent);
    assertEquals(1, animatedShape0.getEvents().size());
    animatedShape0.getShapeAtTick(8); // should throw the exception here
  }

  @Test
  public void testSimpleConstructor() {
    Rectangle rec0 = new Rectangle("r0",
            new Position2D(3, 3), 10, 4, new Color(255));
    AnimatedShape as0 = new AnimatedShape(rec0);
    assertEquals(as0.getShape().getPosition(), rec0.getPosition());
    assertEquals(as0.getShape().asString(), rec0.asString());
  }

  @Test
  public void testInvalidGetShapeAtTickWithNoEvents() {
    try {
      this.animatedShape0.getShapeAtTick(1);
      fail();
    } catch (Exception e) {
      assertEquals("Shape doesn't exist at given tick.", e.getMessage());
    }
  }

  @Test
  public void testInvalidGetShapeAtTickNegative() {
    EventInstructions moveTri = new EventInstructions(
            1,
            10,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri);

    try {
      this.animatedShape0.getShapeAtTick(-1);
      fail();
    } catch (Exception e) {
      assertEquals("Shape doesn't exist at given tick.", e.getMessage());
    }
  }

  @Test
  public void testGetShapeAtTick() {
    EventInstructions moveTri = new EventInstructions(
            1,
            10,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri);
    assertEquals("t0", this.animatedShape0.getShapeAtTick(5).getName());
  }

  @Test
  public void testInvalidGetShapeAtTick1PassTickRange() {
    EventInstructions moveTri = new EventInstructions(
            1,
            10,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri);

    try {
      this.animatedShape0.getShapeAtTick(11);
      fail();
    } catch (Exception e) {
      assertEquals("Shape doesn't exist at given tick.", e.getMessage());
    }
  }

  @Test
  public void testSimpleConstructorWithEventInstructions() {
    Rectangle rec0 = new Rectangle("r0",
            new Position2D(3, 3), 10, 4, new Color(255));

    EventInstructions moveRec = new EventInstructions(
            10,
            70,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    ArrayList<EventInstructions> events = new ArrayList<>();
    events.add(moveRec);
    AnimatedShape as0 = new AnimatedShape(rec0, events);

    // Test immutabilitiy
    assertNotSame(as0.getEvents(), events);

    assertEquals(as0.getEvents().size(), events.size());

    assertEquals(as0.getEvents().get(0).getEndTick(), events.get(0).getEndTick());
    assertEquals(as0.getEvents().get(0).getEndPosn(), events.get(0).getEndPosn());

    assertEquals(as0.getShape().getPosition(), rec0.getPosition());
    assertEquals(as0.getShape().asString(), rec0.asString());
  }


  @Test
  public void testImmutabilityWithShape() {
    EventInstructions moveTri = new EventInstructions(
            0,
            1,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.triangle0.executeInstructions(moveTri);
    assertNotSame(this.animatedShape0.getShape().getPosition(), this.triangle0.getPosition());
  }


  @Test
  public void testImmutabilityWithEvents() {
    EventInstructions moveTri = new EventInstructions(
            0,
            1,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri);
    // This is not the same because the overrides method was not overridden in EvenInstructions
    // however, this proves that the two objects are not the same.
    assertNotSame(this.animatedShape0.getEvents().get(0), moveTri);
  }

  @Test
  public void testAddingInstructions() {
    assertEquals(this.animatedShape0.getEvents().size(), 0);

    EventInstructions moveTri = new EventInstructions(
            0,
            1,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri);

    assertEquals(this.animatedShape0.getEvents().size(), 1);
    assertEquals(moveTri.getEndColor().getRGB(),
            this.animatedShape0.getEvents().get(0).getEndColor().getRGB());
    assertEquals(moveTri.getEndHeight(), this.animatedShape0.getEvents().get(0).getEndHeight());
  }

  @Test
  public void testAddingTwoInstructionsSameEndSameStart() {
    assertEquals(0, this.animatedShape0.getEvents().size());

    EventInstructions moveTri0 = new EventInstructions(
            6,
            15,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri0);

    assertEquals(1, this.animatedShape0.getEvents().size());
    EventInstructions moveTri1 = new EventInstructions(
            15,
            280,
            new Position2D(90, 5),
            8,
            4,
            new Color(80));

    this.animatedShape0.addEvent(moveTri1);

    assertEquals(this.animatedShape0.getEvents().size(), 2);
    assertEquals(moveTri0.getEndColor().getRGB(),
            this.animatedShape0.getEvents().get(0).getEndColor().getRGB());
    assertEquals(moveTri0.getEndHeight(), this.animatedShape0.getEvents().get(0).getEndHeight());
    assertEquals(moveTri1.getEndColor().getRGB(),
            this.animatedShape0.getEvents().get(1).getEndColor().getRGB());
    assertEquals(moveTri1.getEndTick(), this.animatedShape0.getEvents().get(1).getEndTick());
  }

  @Test
  public void testAddingTwoInstructionsEndAndStartTimeHaveAGap() {
    assertEquals(0, this.animatedShape0.getEvents().size());

    EventInstructions moveTri0 = new EventInstructions(
            6,
            15,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri0);

    assertEquals(1, this.animatedShape0.getEvents().size());
    EventInstructions moveTri1 = new EventInstructions(
            25,
            280,
            new Position2D(90, 5),
            8,
            4,
            new Color(80));

    try {
      this.animatedShape0.addEvent(moveTri1);
      fail();
    } catch (Exception e) {
      assertEquals(e.getMessage(), "The added event's start time must " +
              "be the same as the last end time.End: 15 Start: 25");
    }
    assertEquals(this.animatedShape0.getEvents().size(), 1);
  }


  @Test
  public void testAddingTwoInstructionsEndAndStartTimeHaveADifferentTime() {
    assertEquals(0, this.animatedShape0.getEvents().size());

    EventInstructions moveTri0 = new EventInstructions(
            4,
            10,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri0);

    assertEquals(1, this.animatedShape0.getEvents().size());
    EventInstructions moveTri1 = new EventInstructions(
            1,
            3,
            new Position2D(90, 5),
            8,
            4,
            new Color(80));

    try {
      this.animatedShape0.addEvent(moveTri1);
      fail();
    } catch (Exception e) {
      assertEquals(e.getMessage(), "The added event's start time must " +
              "be the same as the last end time.End: 10 Start: 1");
    }
    assertEquals(this.animatedShape0.getEvents().size(), 1);
  }

  @Test
  public void testAddingTwoInstructionsEndAndStartTimeOverLap() {
    assertEquals(0, this.animatedShape0.getEvents().size());

    EventInstructions moveTri0 = new EventInstructions(
            6,
            15,
            new Position2D(5, 5),
            10,
            4,
            new Color(255));
    this.animatedShape0.addEvent(moveTri0);

    assertEquals(1, this.animatedShape0.getEvents().size());
    EventInstructions moveTri1 = new EventInstructions(
            10,
            280,
            new Position2D(90, 5),
            8,
            4,
            new Color(80));

    try {
      this.animatedShape0.addEvent(moveTri1);
      fail();
    } catch (Exception e) {
      assertEquals(e.getMessage(), "The added event's start time must " +
              "be the same as the last end time.End: 15 Start: 10");
    }
    assertEquals(this.animatedShape0.getEvents().size(), 1);
  }
}



