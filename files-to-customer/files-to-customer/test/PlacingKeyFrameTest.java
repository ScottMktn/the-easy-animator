import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.shapes.Rectangle;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * This houses all the tests for placing a KeyFrame in the model and also
 * tests for placing a keyframe in an AnimatedShape.
 */
public class PlacingKeyFrameTest {
  private IAnimationModel model;
  IAnimatedShape rect0;
  IAnimatedShape rect1;

  @Before
  public void setup() {
    this.model = new AnimationModelImpl();
    this.rect0 = new AnimatedShape(
            new Rectangle("rect0",
                    new Position2D(3, 3),
                    10, 10, Color.BLACK));

    this.rect1 = new AnimatedShape(
            new Rectangle("rect1",
                    new Position2D(3, 3),
                    10, 10, Color.BLACK));
    this.rect1.addEvent(
            new EventInstructions(1,
                    10,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));


  }

  private void placeKeyFrameAtT7(String name, IAnimationModel localModel) {
    localModel.placeKeyFrame(name, 7,
            new Position2D(1, 1), 1, 1, Color.black);
  }




  @Test
  public void placingKeyFrameOnEmptyModel() {
    try {
      this.placeKeyFrameAtT7("unknown", new AnimationModelImpl());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(ErrorMessages.ShapeNotInModel.errorMessage(), e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void placingKeyFrameSingleItemNoEvents() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    assertEquals(1, this.rect0.getEvents().size());

    assertEquals(7, this.rect0.getEvents().get(0).getEndTick());
    assertEquals(7, this.rect0.getEvents().get(0).getStartTick());
    assertEquals(new Position2D(1,1), this.rect0.getEvents().get(0).getEndPosn());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneAfterIt() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame(
            "rect0", 10,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(1, this.model.getAllAnimatedShapes().size());
    assertEquals(1, this.model.getAllAnimatedShapes().get(0).getEvents().size());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneAfterItCheckTicks() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame("rect0", 10,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(10, this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getEndTick());
    assertEquals(7, this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getStartTick());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneAfterItCheckValues() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame("rect0", 10,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(new Position2D(0,0),
            this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getEndPosn());
    assertEquals(new Position2D(1,1),
            this.model.getAllAnimatedShapes().get(0).getShape().getPosition());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneBeforeIt() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame(
            "rect0", 5,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(1, this.model.getAllAnimatedShapes().size());
    assertEquals(1, this.model.getAllAnimatedShapes().get(0).getEvents().size());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneBeforeItCheckTicks() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame("rect0", 5,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(7, this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getEndTick());
    assertEquals(5, this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getStartTick());

  }

  @Test
  public void placingKeyFrameSingleItemNoEventsThenAddingAnotherOneBeforeItCheckValues() {
    this.model.placeAnimatedShape(this.rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    this.model.placeKeyFrame("rect0", 5,new Position2D(0,0), 10, 10, Color.CYAN);

    assertEquals(new Position2D(1,1),
            this.model.getAllAnimatedShapes().get(0).getEvents().get(0).getEndPosn());
    assertEquals(new Position2D(0,0),
            this.model.getAllAnimatedShapes().get(0).getShape().getPosition());

  }

  @Test
  public void placingKeyFrameBeforeShapeExists() {

    rect0.addEvent(
            new EventInstructions(8,
                    18,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    assertEquals(2, this.rect0.getEvents().size());
  }

  @Test
  public void placingKeyFrameBeforeShapeExistsCheckOrder() {

    rect0.addEvent(
            new EventInstructions(8,
                    18,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    assertEquals(this.rect0.getEvents().get(0).getEndPosn(), new Position2D(1, 1));
    assertEquals(this.rect0.getEvents().get(1).getEndPosn(), new Position2D(7, 7));
  }

  @Test
  public void placingKeyFrameAfterShapeExists() {
    rect0.addEvent(
            new EventInstructions(3,
                    5,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    assertEquals(2, this.rect0.getEvents().size());
  }


  @Test
  public void placingKeyFrameAfterShapeExistsCheckOrder() {
    rect0.addEvent(
            new EventInstructions(3,
                    5,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);

    this.placeKeyFrameAtT7("rect0", this.model);

    assertEquals(this.rect0.getEvents().get(0).getEndPosn(), new Position2D(7, 7));
    assertEquals(this.rect0.getEvents().get(1).getEndPosn(), new Position2D(1, 1));
  }

  @Test
  public void placingKeyFrameOnExistingEndTickStamp() {
    rect0.addEvent(
            new EventInstructions(3,
                    7,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);
    try {
      this.placeKeyFrameAtT7("rect0", this.model);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(ErrorMessages.KeyFrameBadTick.errorMessage(), e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }


  @Test
  public void placingKeyFrameOnExistingStartTickStamp() {
    rect0.addEvent(
            new EventInstructions(7,
                    10,
                    new Position2D(7, 7),
                    90,
                    90,
                    Color.CYAN));
    this.model.placeAnimatedShape(rect0);
    try {
      this.placeKeyFrameAtT7("rect0", this.model);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(ErrorMessages.KeyFrameBadTick.errorMessage(), e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }


  @Test
  public void placeValidKeyframeTestEventCount() {
    this.model.placeAnimatedShape(rect1);
    this.placeKeyFrameAtT7("rect1", this.model);
    assertEquals(2,
            this.model.getAllAnimatedShapes().get(0).getEvents().size());
  }

  @Test
  public void placeValidKeyframeTestTicks() {
    this.model.placeAnimatedShape(rect1);
    this.placeKeyFrameAtT7("rect1", this.model);
    EventInstructions firstEvent = this.model.getAllAnimatedShapes()
            .get(0).getEvents().get(0);
    EventInstructions secondEvent = this.model.getAllAnimatedShapes()
            .get(0).getEvents().get(1);

    assertEquals(1, firstEvent.getStartTick());
    assertEquals(7, firstEvent.getEndTick());
    assertEquals(7, secondEvent.getStartTick());
    assertEquals(10, secondEvent.getEndTick());
  }

  @Test
  public void placeValidKeyframeTest() {
    this.model.placeAnimatedShape(rect1);
    this.placeKeyFrameAtT7("rect1", this.model);
    EventInstructions firstEvent = this.model.getAllAnimatedShapes()
            .get(0).getEvents().get(0);
    EventInstructions secondEvent = this.model.getAllAnimatedShapes()
            .get(0).getEvents().get(1);

    assertEquals(1, firstEvent.getEndHeight());
    assertEquals(1, firstEvent.getEndWidth());
    assertEquals(90, secondEvent.getEndHeight());
    assertEquals(90, secondEvent.getEndWidth());
  }


}
