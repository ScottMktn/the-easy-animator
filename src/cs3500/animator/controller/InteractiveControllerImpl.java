package cs3500.animator.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.LayeredModelImpl;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationModelBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.Command;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.TextView;

/**
 * The implementation for the controller in the model view controller pattern. Will delegate in
 * between the view and model. The constructor for the controller consumes an IAnimationModel and an
 * IAnimationView. The only method inside of the implementation for the controller is play(), which
 * calls kick starts the animation and displays it. NOTE: Inorder to use this properly you must call
 * play first! To ensure the View has a local copy of the model.
 */
public class InteractiveControllerImpl implements IAnimationController, ActionListener {
  private IAnimationModel model;
  private IInteractiveView view;
  private IAnimationModel copy;

  /**
   * Basic constructor for the controller. Takes in a non-null model and non-null view and sets the
   * private model and view to them. The constructor also sets the view's listener to this
   * interactive controller. The constructor also creates a copy of the original passed in animation
   * model and sets the copy to it to keep track of the original state. To make sure that the copy
   * is not mutated, it is of interface type ReadOnlyIAnimationModel.
   *
   * @param model the animation model
   * @param view  the animation view
   * @throws IllegalArgumentException if the model or the view is null
   */
  public InteractiveControllerImpl(IAnimationModel model, IInteractiveView view) {
    Utils.requireNonNull(view, model);
    this.view = view;
    this.model = model;
    this.view.setListener(this);
    this.copy = new LayeredModelImpl(model);
  }

  @Override
  public void play() {
    this.view.display(this.model);
  }

  private IShape buildIShape(String type, String name, EventInstructions e) {
    IShape s;
    switch (type) {
      case "Ellipse":
        s = new Ellipse(name, new Position2D(0, 0), 0, 0, Color.WHITE);
        break;
      case "Rectangle":
        s = new Rectangle(name, new Position2D(0, 0), 0, 0, Color.WHITE);
        break;
      default:
        throw new IllegalArgumentException("Unsupported shape!");
    }
    s.executeInstructions(e);
    return s;
  }

  private void addKeyFrameFromEventInstruction(String name, EventInstructions key) {
    this.model.placeKeyFrame(
            name, key.getEndTick(), key.getEndPosn(),
            key.getEndWidth(), key.getEndHeight(), key.getEndColor());
  }

  /**
   * Restarts the model to it's original state using this.copy.
   */
  private void restartModel() {
    int lc = this.model.layerCount();
    for (int i = 0; i < lc; i++) {
      this.model.deleteLayer(0);
    }
    for (int i = 0; i < this.copy.layerCount(); i++) {
      this.model.addLayer();
      this.model.setCurrentLayer(i);
      this.copy.setCurrentLayer(i);
      this.copy.getAllAnimatedShapes().stream()
              .forEach(as -> this.model.placeAnimatedShape(new AnimatedShape(as)));
    }
    this.view.restart();
  }

  /**
   * Opens a file and loads the contents of the file into the new model.
   *
   * @param openPath the path of the file we want to read in.
   */
  private void openFile(String openPath) {
    AnimationBuilder<IAnimationModel> builder = new AnimationModelBuilder();
    Readable inputFile;
    try {
      inputFile = new FileReader(openPath);
      AnimationReader.parseFile(inputFile, builder);

      for (int i = 0; i < this.model.layerCount(); i++) {
        this.model.setCurrentLayer(0);
        this.model.getAllAnimatedShapes().stream().forEach(s ->
                this.model.deleteShape(s.getShape().getName())
        );
      }

      IAnimationModel newModel = builder.build();
      while (newModel.layerCount() > this.model.layerCount()) {
        this.model.addLayer();
      }
      for (int i = 0; i < newModel.layerCount(); i++) {
        newModel.setCurrentLayer(0);
        this.model.setCurrentLayer(i);
        newModel.getAllAnimatedShapes().forEach(s ->
                this.model.placeAnimatedShape(s)
        );
      }
    } catch (FileNotFoundException z) {
      this.view.showDialogBox(z.getMessage());
    } catch (Exception z) {
      throw new IllegalArgumentException(z.getMessage());
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Utils.requireNonNull(e);
    Command command = null;

    for (Command c : Command.values()) {
      if (c.getString().equals(e.getActionCommand())) {
        command = c;
        break;
      }
    }

    Utils.requireNonNull(command);


    switch (command) {
      case PAUSE:
        this.view.pause();
        break;
      case START:
        this.view.start();
        break;
      case RESTART:
        this.restartModel();
        break;
      case TEMPO_DOUBLE:
        this.view.doubleTempo();
        break;
      case TEMPO_HALF:
        this.view.halveTempo();
        break;
      case ENABLE:
        this.view.loopHandler(true);
        break;
      case DISABLE:
        this.view.loopHandler(false);
        break;
      case OPEN:
        String openPath = this.view.getFileFromUser();
        this.openFile(openPath);
        this.view.updateComboBox();
        break;
      case SAVE:
        String savePath = this.view.getFileFromUser();
        try {
          new TextView(new FileWriter(savePath)).display(this.model);
        } catch (Exception z) {
          this.view.showDialogBox("Invalid File Name");
        }
        break;
      case DELETE_SHAPE:
        String shapeID = this.view.getDeleteDropDownString();
        this.model.deleteShape(shapeID);
        this.view.updateComboBox();
        break;
      case ADD_KEYFRAME:
        try {
          this.addKeyFrameFromEventInstruction(this.view.getDeleteDropDownString(),
                  this.view.getKeyEventParams());
        } catch (Exception exception) {
          //do nothing
        }
        this.view.updateComboBox();
        break;
      case DELETE_KEYFRAME:
        try {
          this.model.removeKeyFrame(
                  this.view.getDeleteDropDownString(),
                  this.view.selectedKeyFrame());
          this.view.updateComboBox();
        } catch (Exception exception) {
          this.view.showDialogBox("Cannot Delete\n" + exception.getMessage());
        }
        break;
      case ADD_SHAPE:
        try {
          this.model.placeAnimatedShape(new AnimatedShape(
                  this.buildIShape(
                          this.view.shapeTypeDropDown(),
                          this.view.shapeNameToAdd(),
                          this.view.getKeyEventParams()
                  )));
          this.addKeyFrameFromEventInstruction(
                  this.view.shapeNameToAdd(), this.view.getKeyEventParams());
          this.view.updateComboBox();
        } catch (Exception exception) {
          //do nothing
        }
        break;
      case ADD_LAYER:
        try {
          this.model.addLayer();
          this.view.updateComboBox();
        } catch (Exception exception) {
          this.view.showDialogBox(exception.getMessage());
        }
        break;
      case SET_LAYER:
        try {
          this.model.setCurrentLayer(this.view.getSelectedLayer());
          this.view.setVisibleIndicator(this.model.isEnabled(this.view.getSelectedLayer()));
        } catch (Exception exception) {
          //do nothing
        }
        break;
      case SET_VISIBLE:
        try {
          this.model.setLayerState(this.view.getSelectedLayer(), this.view.getVisibleIndicator());
        } catch (Exception exception) {
          this.view.showDialogBox(exception.getMessage());
        }
        break;
      case DELETE_LAYER:
        try {
          this.model.deleteLayer(this.view.getSelectedLayer());
          this.view.updateComboBox();
        } catch (Exception exception) {
          this.view.showDialogBox(exception.getMessage());
        }
        break;

      case MOVE_LAYER_DOWN:
        try {
          this.model.setCurrentLayer(this.view.getSelectedLayer());
          this.model.moveCurrentLayer(-1);
        } catch (Exception exception) {
          this.view.showDialogBox(exception.getMessage());
        }
        break;
      case MOVE_LAYER_UP:
        try {
          this.model.setCurrentLayer(this.view.getSelectedLayer());
          this.model.moveCurrentLayer(1);
        } catch (Exception exception) {
          this.view.showDialogBox(exception.getMessage());
        }
        break;
      default:
        throw new IllegalArgumentException(
                ErrorMessages.UnsupportedActionEvent.errorMessage() + e.getActionCommand());
    }
  }
}
