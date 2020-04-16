package cs3500.animator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.ReadOnlyIAnimationModel;
import cs3500.animator.shapes.IShape;


/**
 * Represents the implementation for an interactive view. This view has the capabilities to start,
 * pause, resume, and restart the animation. The interactive view also adds the ability enable or
 * disable looping. And finally, the interactive view also has the capability to speed up or slow
 * down the tempo of the animation. NOTE: Inorder to use this properly you must call play first! To
 * ensure the View has a local copy of the model.
 */
public class InteractiveVisualView extends JFrame implements IInteractiveView, ActionListener {
  private final int DEFAULT_SPEED = 1;
  private final int TEXT_LEN = 10;
  JComboBox keyFrames;
  private Timer timer;
  private AnimationPanel animationPanel;
  // KF = kerFrame
  private JTextField tickTextFieldKF;
  private JTextField positionTextFieldKF;
  private JTextField dimensionTextFieldKF;
  private JTextField colorTextFieldKF;
  // AS = addShape
  private JTextField nameTextFieldAS;
  private JTextField tickTextFieldAS;
  private JTextField positionTextFieldAS;
  private JTextField dimensionTextFieldAS;
  private JTextField colorTextFieldAS;
  private JTextField fileTextField;

  private JTabbedPane tabbedPane;
  private JComboBox listOfShapes;
  private JComboBox shapesToAdd;
  private int tempo;
  private ReadOnlyIAnimationModel model;

  private final Dimension rightPanelDimension = new Dimension(380, 250);


  /**
   * We are hashing the commands to buttons to guarantee that every button has a command. This map
   * will store all the buttons in the UI, that is easily looked up by the command. the alternative
   * is to have a string, however this mitigates any typos that may be in our program.
   */
  private HashMap<Command, JButton> buttons;

  /**
   * Basic constructor for an interactive view. Takes in a tempo and checks to see if that tempo is
   * non negative.
   *
   * @param tempo the ticks per second of the animation
   * @throws IllegalArgumentException if the tempo is less than zero
   */
  public InteractiveVisualView(int tempo) {
    super();
    Utils.requireNonNegative(tempo);
    this.tempo = tempo;
    this.buttons = new HashMap<>();
    this.addButton(Command.values());

  }

  @Override
  public void display(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);
    this.model = model;


    this.setTitle("Excellence");
    this.setLayout(new BorderLayout());
    this.setBounds(model.getBounds());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.initAnimationPanel();
    this.initInteractivePanel(
            Command.START,
            Command.PAUSE,
            Command.RESTART,
            Command.TEMPO_DOUBLE,
            Command.TEMPO_HALF,
            Command.ENABLE,
            Command.DISABLE,
            Command.OPEN,
            Command.SAVE
    );
    this.initTabEditingPanel();
    this.timer = new Timer((int) (1000 * (1 / (double) tempo)), this);


    pack();
    this.start();
    setVisible(true); // displays the frame
  }

  /**
   * Adds a new button object to the array list of CMD. Constructs the button with the given button
   * name as the name, and also sets the action command as the button name.
   *
   * @param commands all of the button names that we want to create a button for.
   */
  private void addButton(Command... commands) {
    for (Command cmd : commands) {
      JButton button = new JButton(cmd.getString());
      button.setActionCommand(button.getText());
      this.buttons.put(cmd, button);
    }
  }

  @Override
  public void setListener(ActionListener listener) {
    Utils.requireNonNull(listener);
    for (JButton button : this.buttons.values()) {
      button.addActionListener(listener);
    }
  }

  /**
   * Initializes the interactive panel with the given button names. Adds all of the CMD with the
   * given button name into the interactive panel.
   *
   * @param buttonNames the names of the button names we want to add to the interactive panel.
   */
  private void initInteractivePanel(Command... buttonNames) {
    JPanel interactivePanel = new JPanel();
    this.fileTextField = new JTextField(15);
    Arrays.stream(buttonNames).forEach((button) ->
            interactivePanel.add(this.buttons.get(button)));
    interactivePanel.add(this.fileTextField);
    this.add(interactivePanel, BorderLayout.SOUTH);
  }

  /**
   * Initializes all the objects in the add shape panel.
   */
  public void initAddShapeObjects() {
    this.nameTextFieldAS = new JTextField(TEXT_LEN);
    this.tickTextFieldAS = new JTextField(TEXT_LEN);
    this.positionTextFieldAS = new JTextField(TEXT_LEN);
    this.dimensionTextFieldAS = new JTextField(TEXT_LEN);
    this.colorTextFieldAS = new JTextField(TEXT_LEN);
    this.shapesToAdd = new JComboBox(new Object[]{"Ellipse", "Rectangle"});
  }

  /**
   * The inner grid dimensions for based off the rightPanelDimension.
   */
  private Dimension getGridDimension() {
    return new Dimension(
            this.rightPanelDimension.width - 40,
            this.rightPanelDimension.height - 60
    );
  }

  /**
   * Builds the Panel that allows a user to add shapes to the model. Contains textFields and
   * ComboBox of the different types of shapes the user can construct.
   */
  private JPanel buildAddShapePanel() {
    JPanel topPanel = new JPanel(new FlowLayout());
    JPanel addShape = new JPanel(new GridLayout(7, 2));
    addShape.setPreferredSize(this.getGridDimension());

    // Row 1)
    addShape.add(new JLabel("Shape Type: "));
    addShape.add(this.shapesToAdd);

    // Row 2)
    addShape.add(new JLabel("Name: "));
    addShape.add(this.nameTextFieldAS);

    // Row 3-6)
    JTextField[] temp = new JTextField[]{
        this.tickTextFieldAS,
        this.positionTextFieldAS,
        this.dimensionTextFieldAS,
        this.colorTextFieldAS
    };
    this.addShapeFeaturesToPanel(addShape, temp);

    // Row 7)
    addShape.add(new JLabel(""));
    addShape.add(this.buttons.get(Command.ADD_SHAPE));
    topPanel.add(addShape);
    return topPanel;
  }

  @Override
  public EventInstructions getKeyEventParams() throws IllegalArgumentException {
    try {
      boolean asSel = this.tabbedPane.getSelectedIndex() == 0;
      JTextField tickText = asSel ? this.tickTextFieldAS : this.tickTextFieldKF;
      JTextField posText = asSel ? this.positionTextFieldAS : this.positionTextFieldKF;
      JTextField dimText = asSel ? this.dimensionTextFieldAS : this.dimensionTextFieldKF;
      JTextField rgbText = asSel ? this.colorTextFieldAS : this.colorTextFieldKF;

      String[] p = posText.getText().split(",");
      String[] d = dimText.getText().split(",");
      String[] rgb = rgbText.getText().split(",");
      return new EventInstructions(
              0,
              (int) Double.parseDouble(tickText.getText()),
              new Position2D(Double.parseDouble(p[0]), Double.parseDouble(p[1])),
              (int) Double.parseDouble(d[0]), (int) Double.parseDouble(d[1]),
              new Color((int) Double.parseDouble(rgb[0]), (int) Double.parseDouble(rgb[1]),
                      (int) Double.parseDouble(rgb[2]))
      );
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Invalid input!");
      throw new IllegalArgumentException("Invalid Entry on GUI");
    }
  }

  @Override
  public int selectedKeyFrame() {
    return this.keyFrames.getSelectedIndex();
  }

  @Override
  public void showDialogBox(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public String shapeTypeDropDown() {
    return String.valueOf(this.shapesToAdd.getSelectedItem());
  }

  @Override
  public String shapeNameToAdd() {
    return this.nameTextFieldAS.getText();
  }

  @Override
  public String getFileFromUser() {
    return fileTextField.getText();
  }

  /**
   * Updates the keyFrames ComboBox with the keyframes for the selected shape.
   *
   * @return the function that will execute when the ActionListener is triggered.
   */
  private ActionListener buildListOfShapesActionListener() {
    return (e) -> {
      this.keyFrames.removeAllItems();
      this.model.getAllAnimatedShapes().stream()
              .filter(s -> s.getShape().getName().equals(listOfShapes.getSelectedItem()))
              .forEach(shape -> {
                String keyAt = "Keyframe at: ";
                if (shape.getEvents().size() > 0) {
                  keyFrames.addItem(keyAt + shape.getEvents().get(0).getStartTick());
                }
                for (EventInstructions event : shape.getEvents()) {
                  keyFrames.addItem(keyAt + event.getEndTick());
                }
              });
    };
  }

  /**
   * Listener used for viewing a keyFrame. Populates the textFields in the keyFrame Panel that
   * correspond with the selected keyFrame in the comboBox.
   *
   * @return the function that will execute when the ActionListener is triggered.
   */
  private ActionListener viewKeyFrameListener() {
    return (z) -> {
      this.model.getAllAnimatedShapes().stream()
              .filter(s -> s.getShape().getName().equals(listOfShapes.getSelectedItem()))
              .forEach(shape -> {
                if (keyFrames.getSelectedIndex() == 0) {
                  IShape s = shape.getShape();
                  this.tickTextFieldKF.setText("" + shape.getEvents().get(0).getStartTick());
                  this.positionTextFieldKF.setText(
                          s.getPosition().getX() + "," + s.getPosition().getY());
                  this.dimensionTextFieldKF.setText(s.getWidth() + "," + s.getHeight());
                  this.colorTextFieldKF.setText(s.getColor().getRed() + ","
                          + s.getColor().getGreen() + "," + s.getColor().getBlue());
                } else {
                  EventInstructions e = shape
                          .getEvents()
                          .get(keyFrames.getSelectedIndex() - 1);
                  this.tickTextFieldKF.setText("" + e.getEndTick());
                  this.positionTextFieldKF.setText(
                          e.getEndPosn().getX() + "," + e.getEndPosn().getY());
                  this.dimensionTextFieldKF.setText(e.getEndWidth() + "," + e.getEndHeight());
                  this.colorTextFieldKF.setText(
                          e.getEndColor().getRed() + "," + e.getEndColor().getGreen()
                                  + "," + e.getEndColor().getBlue());
                }
              });
    };
  }

  /**
   * Adds the textFields to the panel. Helper functions that populates text fields for both editing
   * tabs in the view.
   *
   * @param panel      that we want to add the textFields to.
   * @param textFields the textFields we are linking to panel. Must be 4 items long.
   */
  private void addShapeFeaturesToPanel(JPanel panel, JTextField[] textFields) {
    if (textFields.length != 4) {
      throw new IllegalArgumentException("Incorrect text field size");
    }
    String[] labels = {
        "Start Tick: ",
        "Position: x, y",
        "Dimensions: w, h",
        "Color: r, g, b"
    };
    for (int i = 0; i < textFields.length; i++) {
      panel.add(new JLabel(labels[i]));
      panel.add(textFields[i]);
    }
  }

  /**
   * Initialize the Objects needed for the the KeyFrame Panel.
   */
  private void initKeyFrameObjects() {
    this.listOfShapes = new JComboBox();
    this.keyFrames = new JComboBox();
    this.tickTextFieldKF = new JTextField(TEXT_LEN);
    this.positionTextFieldKF = new JTextField(TEXT_LEN);
    this.dimensionTextFieldKF = new JTextField(TEXT_LEN);
    this.colorTextFieldKF = new JTextField(TEXT_LEN);

  }

  /**
   * Constructs the JPanel used to edit the keyframes and existing shapes in the animation. Panel
   * consists of Text fields and multiple combo boxes to do the job. The panel is intended to be put
   * into the right side panel, so the Dimension is based off of that.
   */
  public JPanel buildKeyframeEditPanel() {
    JPanel topPanel = new JPanel(new FlowLayout());
    JPanel keyFrameFeatures = new JPanel(new GridLayout(7, 2));
    keyFrameFeatures.setPreferredSize(this.getGridDimension());

    // Row 1)
    this.listOfShapes.addActionListener(this.buildListOfShapesActionListener());
    keyFrameFeatures.add(this.listOfShapes);
    this.buttons.get(Command.DELETE_KEYFRAME).setText("Delete");
    keyFrameFeatures.add(this.buttons.get(Command.DELETE_SHAPE));

    // Row 2)
    // --- Col 1)
    keyFrameFeatures.add(keyFrames);
    // --- Col 2)
    JPanel keyFrameTopButtons = new JPanel(new GridLayout(1, 2));
    keyFrameTopButtons.add(this.buttons.get(Command.DELETE_KEYFRAME));
    JButton viewKeyFrame = new JButton("View");
    viewKeyFrame.addActionListener(this.viewKeyFrameListener());
    keyFrameTopButtons.add(viewKeyFrame);
    keyFrameFeatures.add(keyFrameTopButtons);

    // Row 3-6)
    this.addShapeFeaturesToPanel(keyFrameFeatures,
            new JTextField[]{
                this.tickTextFieldKF,
                this.positionTextFieldKF,
                this.dimensionTextFieldKF,
                this.colorTextFieldKF
            });

    // Row 7)
    keyFrameFeatures.add(new JLabel(""));
    keyFrameFeatures.add(this.buttons.get(Command.ADD_KEYFRAME));

    this.updateComboBox();
    topPanel.add(keyFrameFeatures);
    return topPanel;
  }

  /**
   * Initializes the right hand side panel containing the shape edit functions.
   */
  private void initTabEditingPanel() {
    this.initKeyFrameObjects();
    this.initAddShapeObjects();
    this.tabbedPane = new JTabbedPane();
    this.tabbedPane.setPreferredSize(this.rightPanelDimension);//this.model.getBounds().height));
    this.tabbedPane.add(this.buildAddShapePanel(), "Add Shape");
    this.tabbedPane.add(this.buildKeyframeEditPanel(), "Edit Shapes");
    this.add(this.tabbedPane, BorderLayout.EAST);
  }


  @Override
  public void updateComboBox() {
    this.listOfShapes.removeAllItems();
    for (String s : initListOfShapesArray(model)) {
      this.listOfShapes.addItem(s);
    }
  }


  /**
   * Initializes the array of strings representing the id's of the shapes in the animation.
   *
   * @return an array of strings containing the string id's
   */
  private String[] initListOfShapesArray(ReadOnlyIAnimationModel model) {
    int size = model.getAllAnimatedShapes().size();
    String[] temp = new String[size];
    for (int i = 0; i < size; i++) {
      temp[i] = model.getAllAnimatedShapes().get(i).getShape().getName();
    }
    return temp;
  }

  /**
   * Initializes the bottom panel containing the animation edit functions.
   */
  private void initAnimationPanel() {
    this.animationPanel = this.buildAnimationPanel(model);
    this.add(new JScrollPane(this.animationPanel), BorderLayout.CENTER);
  }

  @Override
  public void pause() {
    this.timer.stop();
  }

  @Override
  public void start() {
    this.timer.start();
  }

  /**
   * Sets the tempo to a valid tempo value.
   *
   * @param tempo sets the timer using the tempo and updates the tempo field
   */
  private void setTempo(int tempo) {
    this.tempo = tempo < 1 ? 1 : tempo;
    this.timer.setDelay((int) (1000 * (1 / (double) this.tempo)));
  }

  @Override
  public void restart() {
    pause();
    this.animationPanel.setCurTick(0);
    this.setTempo(DEFAULT_SPEED);
    updateComboBox();
    start();
  }

  @Override
  public void doubleTempo() {
    if ((this.tempo * 2) < this.tempo) {
      this.setTempo(this.tempo);
    } else {
      this.setTempo(this.tempo * 2);
      // nothing to do here!
    }
  }

  @Override
  public void halveTempo() {
    this.setTempo(this.tempo / 2);
  }

  @Override
  public String getDeleteDropDownString() {
    return String.valueOf(this.listOfShapes.getSelectedItem());
  }

  @Override
  public void loopHandler(boolean b) {
    this.animationPanel.setLooping(b);
  }

  /**
   * Builds the animation panel using the model as the guidelines.
   *
   * @param model a read only animation model
   * @return a well-constructed animation panel
   */
  private AnimationPanel buildAnimationPanel(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);
    AnimationPanel tempPanel = new AnimationPanel(model);
    tempPanel.setPreferredSize(
            new Dimension(model.getBounds().width, model.getBounds().height));
    tempPanel.setBounds(model.getBounds());
    return tempPanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Utils.requireNonNull(e);
    this.animationPanel.increment();
    this.animationPanel.repaint();
    this.animationPanel.revalidate();
  }
}
