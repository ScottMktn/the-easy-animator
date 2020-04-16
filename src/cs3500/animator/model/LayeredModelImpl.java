package cs3500.animator.model;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.shapes.IShape;

/**
 * The design approach behind this model is to have multiple models composed into this model so that
 * each model is treated as an independent layer.
 */
public class LayeredModelImpl implements IAnimationModel {
  /**
   * Represents a single layer, basically a model with an enabled flag.
   */
  private class Layer {
    private final IAnimationModel model;
    private boolean enabled;

    /**
     * Builds a layer and populates it with the params.
     *
     * @param model   the model we want to represent for the data of the layer.
     * @param enabled the flag for determining if this model is enabled
     */
    public Layer(IAnimationModel model, boolean enabled) {
      this.model = model;
      this.enabled = enabled;
    }


    /**
     * The model inside this layer.
     */
    private IAnimationModel getModel() {
      return this.model;
    }

    /**
     * Determines if this layer is enabled.
     *
     * @return a boolean flag if the layer is enabled.
     */
    private boolean getEnabled() {
      return this.enabled;
    }

    /**
     * Enables or disables this layer.
     *
     * @param enabled the flag to set this layer.
     */
    private void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

  }

  private HashMap<Integer, Layer> layers;
  private int currentLayer;


  /**
   * Constructs a Layered Model implementation that holds models as layers.
   */
  public LayeredModelImpl() {
    this.layers = new HashMap<>();
    this.currentLayer = 0;
    this.layers.put(this.currentLayer, new Layer(new AnimationModelImpl(), true));

  }

  /**
   * A copy constructor that will duplicate the model passed in.
   */
  public LayeredModelImpl(IAnimationModel model) {
    this.layers = new HashMap<>();
    this.currentLayer = 0;
    for (int i = 0; i < model.layerCount(); i++) {
      this.addLayer();
      this.setCurrentLayer(i);
      model.setCurrentLayer(i);
      for (IAnimatedShape as : model.getAllAnimatedShapes()) {
        this.placeAnimatedShape(new AnimatedShape(as));
      }
    }
  }


  /**
   * Gets the current model based on the currentLayer variable.
   *
   * @return the model that this lay represents.
   */
  private IAnimationModel currentModel() {
    return this.layers.get(this.currentLayer).getModel();
  }

  @Override
  public int getCurrentLayer() {
    return this.currentLayer;
  }

  @Override
  public void setCurrentLayer(int layer) {
    if (layer >= this.layerCount()) {
      throw new IllegalArgumentException("Invalid Layer");
    }
    this.currentLayer = layer;
  }

  @Override
  public int layerCount() {
    return this.layers.size();
  }

  @Override
  public boolean isEnabled(int layer) {
    return this.layers.get(layer).getEnabled();
  }

  @Override
  public void addLayer() {
    if (this.layerCount() == 0) {
      this.layers.put(this.currentLayer, new Layer(new AnimationModelImpl(), true));
      return;
    }
    IAnimationModel m = new AnimationModelImpl();
    java.awt.Rectangle b = this.getBounds();
    m.setBounds((int) b.getX(), (int) b.getY(), b.width, b.height);
    this.layers.put(this.layerCount(), new Layer(m, true));
  }

  @Override
  public void setLayerState(int layer, boolean enabled) {
    this.layers.get(layer).setEnabled(enabled);
  }

  @Override
  public void deleteLayer(int layer) {
    Utils.requireNonNegative(layer);
    if (layer >= this.layerCount()) {
      throw new IllegalArgumentException("invalid layer");
    }
    int count = this.layerCount();
    this.layers.remove(layer);
    for (int i = layer + 1; i < count; i++) {
      Layer temp = this.layers.get(i);
      this.layers.remove(i);
      this.layers.put(i - 1, temp);
    }
    if (this.layerCount() == 0) {
      this.addLayer();
      this.setCurrentLayer(0);
      throw new IllegalArgumentException("You can't have 0 layers!\n" +
              "We removed the contents of Layer 0");
    }
    if (this.getCurrentLayer() >= this.layerCount()) {
      this.setCurrentLayer(this.layerCount() - 1);
    }
  }

  @Override
  public void moveCurrentLayer(int direction) {
    int total = this.currentLayer + direction;
    if (!this.layers.containsKey(total)) {
      throw new IllegalArgumentException("invalid direction");
    }

    Layer temp = this.layers.get(total);
    this.layers.replace(total, this.layers.get(this.currentLayer));
    this.layers.replace(this.currentLayer, temp);
  }

  @Override
  public void setListOfAnimatedShapes(ArrayList<IAnimatedShape> animatedShapeArrayList) {
    this.currentModel().setListOfAnimatedShapes(animatedShapeArrayList);
  }

  @Override
  public void deleteShape(String id) {
    this.currentModel().deleteShape(id);
  }

  @Override
  public void placeAnimatedShape(IAnimatedShape animatedShape) {
    this.currentModel().placeAnimatedShape(animatedShape);
  }

  @Override
  public void addEventInstructionToShape(String name, EventInstructions e) {
    this.currentModel().addEventInstructionToShape(name, e);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    this.currentModel().setBounds(x, y, width, height);
  }

  @Override
  public void placeKeyFrame(String name, int t, Position2D p, int w, int h, Color c) {
    this.currentModel().placeKeyFrame(name, t, p, w, h, c);
  }

  @Override
  public void removeKeyFrame(String name, int index) {
    this.currentModel().removeKeyFrame(name, index);
  }


  @Override
  public ArrayList<IShape> getShapesAt(int tick) {
    return this.currentModel().getShapesAt(tick);
  }

  @Override
  public ArrayList<IShape> getShapesFromLayerAtTick(int layer, int tick) {
    return this.layers.get(layer).getModel().getShapesAt(tick);
  }

  @Override
  public ArrayList<IAnimatedShape> getAllAnimatedShapes() {
    return this.currentModel().getAllAnimatedShapes();
  }

  @Override
  public Rectangle getBounds() {
    return this.currentModel().getBounds();
  }
}
