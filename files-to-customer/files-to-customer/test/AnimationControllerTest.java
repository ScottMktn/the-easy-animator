import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

import cs3500.animator.controller.AnimationControllerImpl;
import cs3500.animator.controller.InteractiveControllerImpl;
import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.ReadOnlyIAnimationModel;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.view.Command;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveVisualView;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Houses tests for the classes that implement the IAnimationController interface.
 */
public class AnimationControllerTest {
  private IAnimationModel testModel;
  private IInteractiveView testInteractiveView;
  private IAnimationView testAnimationView;
  private int testTempo;
  private IInteractiveView customTempView;
  private InteractiveControllerImpl controllerWithCustomView;


  // purposefully made class types to access actionPerformed method
  private AnimationControllerImpl testAnimationController;
  private InteractiveControllerImpl testInteractiveController;

  @Before
  public void setup() {
    this.initCustomTempView();
    this.testTempo = 1;
    this.testModel = new AnimationModelImpl();
    this.testAnimationView = new VisualView(testTempo);
    this.testInteractiveView = new InteractiveVisualView(testTempo);
    this.testAnimationController = new AnimationControllerImpl(
            this.testModel, this.testAnimationView);
    this.testInteractiveController = new InteractiveControllerImpl(
            this.testModel, this.testInteractiveView);

    this.controllerWithCustomView = new InteractiveControllerImpl(
            this.testModel,
            this.customTempView
    );

  }

  @Test
  public void testNullActionPerformedForIntCtrl() {
    try {
      this.testInteractiveController.actionPerformed(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail("" + testTempo);
    }
  }


  private void initCustomTempView() {
    customTempView = new IInteractiveView() {
      @Override
      public void setListener(ActionListener listener) {
        // nothing to do here
      }

      @Override
      public void pause() {
        // nothing to do here
      }

      @Override
      public void start() {
        // nothing to do here
      }

      @Override
      public void restart() {
        // nothing to do here
      }

      @Override
      public void doubleTempo() {
        // nothing to do here
      }

      @Override
      public void halveTempo() {
        // nothing to do here
      }

      @Override
      public String getDeleteDropDownString() {
        return "r0";
      }

      @Override
      public void loopHandler(boolean b) {
        // nothing to do here
      }

      @Override
      public void updateComboBox() {
        // nothing to do here
      }

      @Override
      public EventInstructions getKeyEventParams() throws IllegalArgumentException {
        return new EventInstructions(
                0, 0,
                new Position2D(10, 10), 1, 1, Color.CYAN);
      }

      @Override
      public int selectedKeyFrame() {
        return 0;
      }

      @Override
      public void showDialogBox(String message) {
        // nothing to do here
      }

      @Override
      public String shapeTypeDropDown() {
        return "Rectangle";
      }

      @Override
      public String shapeNameToAdd() {
        return "r0";
      }

      @Override
      public String getFileFromUser() {
        return "temp.txt";
      }

      @Override
      public void display(ReadOnlyIAnimationModel model) {
        // nothing to do here
      }
    };
  }

  @Test
  public void testValidActionEvents() {
    this.testModel.placeAnimatedShape(
            new AnimatedShape(
                    new Rectangle(
                            "r0",
                            new Position2D(1, 1),
                            1,
                            2,
                            Color.WHITE
                    )
            )
    );
    this.testModel.addEventInstructionToShape("r0",
            new EventInstructions(
                    10,
                    10,
                    new Position2D(1, 1),
                    1,
                    2,
                    Color.WHITE
            ));

    this.controllerWithCustomView.actionPerformed(
            new ActionEvent(this.testInteractiveView, 0,
                    Command.ADD_KEYFRAME.getString())
    );

    assertEquals(1, this.testModel.getAllAnimatedShapes().get(0).getEvents().size());
    assertEquals(0,
            this.testModel.getAllAnimatedShapes().get(0).getEvents().get(0).getStartTick());
    assertEquals(10,
            this.testModel.getAllAnimatedShapes().get(0).getEvents().get(0).getEndTick());
  }

  @Test
  public void testAddKeyFrame() {
    for (String actionEvent : Arrays.stream(Command.values())
            .map(e -> e.getString()).collect(Collectors.toList())) {
      try {
        this.controllerWithCustomView.actionPerformed(
                new ActionEvent(this.testInteractiveView, 0, actionEvent));
      } catch (Exception e) {
        fail(e.getMessage() + actionEvent);
      }
    }
  }

  @Test
  public void testInvalidActionEvent() {
    this.controllerWithCustomView.play();
    try {
      this.testInteractiveController.actionPerformed(
              new ActionEvent(this.testInteractiveView, 0, "INVALID"));
    } catch (IllegalArgumentException e) {
      assertEquals("The action event passed in is not a valid action",
              ErrorMessages.UnsupportedActionEvent.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testWellFormedAnimationController() {
    assertNotNull(this.testAnimationController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationControllerNullModel() {
    new AnimationControllerImpl(null, testAnimationView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAnimationControllerNullView() {
    new AnimationControllerImpl(testModel, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInteractiveControllerNullModel() {
    new InteractiveControllerImpl(null, testInteractiveView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInteractiveControllerNullView() {
    new InteractiveControllerImpl(testModel, null);
  }
}








