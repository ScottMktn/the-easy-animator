package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Collectors;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.shapes.IShape;

/**
 * Represents the implementation for model in the model view controller design pattern. Will deliver
 * list of shapes at a given time and any interactions in the animation. Contains 4 methods inside
 * of it: -placeAnimatedShape(IAnimatedShape) -getShapesAt(int tick) -getAllAnimatedShapes()
 * -AddEventInstructionToShape The animation model contains a list of IAnimatedShapes, which are
 * objects that contain a shape and its corresponding event instructions.
 */
public class AnimationModelImpl implements IAnimationModel {
  /**
   * We decided to use an AnimatedShape object over a hashmap to avoid potential key issues. Key
   * issues could arise from any mutations that we might do in the future
   */
  private ArrayList<IAnimatedShape> listOfAnimatedShapes;

  private java.awt.Rectangle bounds = new java.awt.Rectangle(1000, 1000);


  /**
   * Constructor for an abstract animation model. This will never be created or instantiated.
   */
  public AnimationModelImpl() {
    this.listOfAnimatedShapes = new ArrayList<>();
  }

  /**
   * Copy constructor.
   *
   * @param model the animation model we want to copy
   */
  public AnimationModelImpl(IAnimationModel model) {
    Utils.requireNonNull();
    this.listOfAnimatedShapes = model.getAllAnimatedShapes();
    this.bounds = model.getBounds();
  }

  @Override
  public void setListOfAnimatedShapes(ArrayList<IAnimatedShape> animatedShapes) {
    Utils.requireNonNull(animatedShapes);
    this.listOfAnimatedShapes = animatedShapes;
  }


  @Override
  public void deleteShape(String id) {
    Utils.requireNonNull(id);
    for (int i = 0; i < this.listOfAnimatedShapes.size(); i++) {
      if (this.listOfAnimatedShapes.get(i).getShape().getName().equals(id)) {
        this.listOfAnimatedShapes.remove(i);
        break;
      } else {
        // don't do anything
      }
    }
  }

  /**
   * Places the passed in shape in the list of shapes. A newly added shape must not have the same
   * name as a shape already added to this model.
   *
   * @param animatedShape the shape that we are adding to the list of shapes
   */
  @Override
  public void placeAnimatedShape(IAnimatedShape animatedShape) {
    Utils.requireNonNull(animatedShape);
    boolean localListContainAnimatedShape = this.listOfAnimatedShapes
            .stream()
            .filter(as -> as.getShape()
                    .getName()
                    .equals(animatedShape.getShape().getName()))
            .collect(Collectors.toList()).size() != 0;
    if (localListContainAnimatedShape) {
      throw new IllegalArgumentException(ErrorMessages.DuplicateShape.errorMessage());
    }
    listOfAnimatedShapes.add(animatedShape);
  }

  @Override
  public void addEventInstructionToShape(String name, EventInstructions e) {
    Utils.requireNonNull(name, e);
    for (IAnimatedShape animatedShape : listOfAnimatedShapes) {
      if (animatedShape.getShape().getName().equals(name)) {
        animatedShape.addEvent(e);
        break;
      }
    }
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    this.bounds = new java.awt.Rectangle(x, y, width, height);
  }

  @Override
  public void placeKeyFrame(String name, int t, Position2D p, int w, int h, Color c) {
    Utils.requireNonNull(name, p, c);
    for (IAnimatedShape s : this.listOfAnimatedShapes) {
      if (s.getShape().getName().equals(name)) {
        s.placeKeyFrame(t, p, w, h, c);
        return;
      } else {
        // nothing to do here
      }
    }

    throw new IllegalArgumentException(ErrorMessages.ShapeNotInModel.errorMessage());
  }

  @Override
  public void removeKeyFrame(String name, int index) {
    for (IAnimatedShape shape : this.listOfAnimatedShapes) {
      if (name.equals(shape.getShape().getName())) {
        shape.removeKeyFrame(index);
        return;
      }
    }
    throw new IllegalArgumentException("Shape doesn't exist in model");
  }


  @Override
  public java.awt.Rectangle getBounds() {
    return new java.awt.Rectangle(this.bounds);
  }


  /**
   * Checks to see if the shape is visible at the given tick.
   *
   * @param shape the shape that we want to check
   * @param tick  the tick that we want to check if the shape exists at
   * @return a boolean if the shape exists at the tick
   */
  private boolean shapeExistsAt(IAnimatedShape shape, int tick) {
    Utils.requireNonNull(shape);
    Utils.requireNonNegative(tick);
    ArrayList<EventInstructions> events = shape.getEvents();
    return events.size() != 0
            && events.get(0).getStartTick() <= tick
            && events.get(events.size() - 1).getEndTick() >= tick;
  }

  @Override
  public ArrayList<IShape> getShapesAt(int tick) {
    ArrayList<IShape> shapesAtGivenTick = new ArrayList<>();

    for (IAnimatedShape shape : this.listOfAnimatedShapes) {
      if (this.shapeExistsAt(shape, tick)) {
        shapesAtGivenTick.add(shape.getShapeAtTick(tick));
      } else {
        // do nothing if the shape doesn't exist at a given time
      }
    }
    return shapesAtGivenTick;
  }

  @Override
  public ArrayList<IShape> getShapesFromLayerAtTick(int layer, int tick) {
    return this.getShapesAt(tick);
  }

  @Override
  public ArrayList<IAnimatedShape> getAllAnimatedShapes() {
    ArrayList<IAnimatedShape> returnShapes = new ArrayList<>();
    for (IAnimatedShape shape : listOfAnimatedShapes) {
      returnShapes.add(new AnimatedShape(shape));
    }
    return returnShapes;
  }


  @Override
  public void setCurrentLayer(int layer) {
    if (layer != 0) {
      throw new UnsupportedOperationException(ErrorMessages.DoesNotSupportLayer.errorMessage());
    }
  }

  @Override
  public void addLayer() {
    // do nothing
  }

  @Override
  public void setLayerState(int layer, boolean enabled) {
    throw new UnsupportedOperationException(ErrorMessages.DoesNotSupportLayer.errorMessage());
  }

  @Override
  public void deleteLayer(int layer) {
    // do nothing
  }

  @Override
  public void moveCurrentLayer(int direction) {
    throw new UnsupportedOperationException(ErrorMessages.DoesNotSupportLayer.errorMessage());
  }

  @Override
  public int getCurrentLayer() {
    return 0;
  }

  @Override
  public int layerCount() {
    return 1;
  }

  @Override
  public boolean isEnabled(int layer) {
    return true;
  }
}
