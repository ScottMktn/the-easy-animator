package cs3500.animator.provider.model;

/**
 * Currently valid Shapes.
 */
public class Shapes {

  private String name;
  private String type;


  /**
   * Creates a Shape.
   *
   */
  public Shapes(String name, String type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Sets the unique name for a shape.
   *
   * @param name the intended unique name.
   */
  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the unique name of a shape.
   *
   * @return the user-given name as a String.
   * @throws NullPointerException if a name has not been set for a shape.
   */
  public String getName() throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("No name has been specified for the Shape!");
    }
    return name;
  }

  public String getType() {
    return this.type;
  }
}