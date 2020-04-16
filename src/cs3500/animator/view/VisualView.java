package cs3500.animator.view;


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import cs3500.animator.misc.Utils;
import cs3500.animator.model.ReadOnlyIAnimationModel;

/**
 * This view represents the animation as a windowed visual animation. This class's constructor
 * consumes a integer tempo as the time measurement for the visual animation needs to be in ticks
 * per second.
 *
 * <p> The VisualView method "display()" will start the timer and call setVisible(true),
 * which will display the animation.</p>
 */
public class VisualView extends JFrame implements IAnimationView, ActionListener {
  private Timer timer;
  private AnimationPanel panel;

  /**
   * Constructor for a visual animation view. Sets the frame up and initializes private vars. Note
   * that the IAnimatedShapes contain both the shape and its corresponding event instructions.
   *
   * @param tempo the ticks per second in the animation.
   */
  public VisualView(int tempo) {
    super();

    Utils.requireNonNegative(tempo);
    // Set the timer for the animation based on the passed in tempo
    this.timer = new Timer((int) (1000 * (1 / (double) tempo)), this);


  }

  @Override
  public void display(ReadOnlyIAnimationModel model) {
    Utils.requireNonNull(model);
    this.panel = new AnimationPanel(model);

    // Setup for the frame
    setTitle("The Easy Excellence - Esketit");
    setBounds(model.getBounds());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    // Initialize the panel
    this.panel.setPreferredSize(new Dimension(model.getBounds().width, model.getBounds().height));
    this.panel.setBounds(model.getBounds());
    add(new JScrollPane(panel), BorderLayout.CENTER);
    pack();

    timer.start(); // start the timer in the animation
    setVisible(true); // displays the frame
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    this.panel.increment();
    this.panel.repaint();
    this.panel.revalidate();
  }
}
