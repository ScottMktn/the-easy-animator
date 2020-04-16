package cs3500.animator.misc;

/**
 * Represents any of the error messages that may occur inside of the animation.
 * Error Messages are enumerations because they are one of data.
 * Each Error Message has a field "errorStr" which is a string description of the error. Users can
 * call the method errorMessage() to retrieve the string.
 */
public enum ErrorMessages {
  InvalidEventAdded("The added event's start time must be the same as the last end time."),
  DuplicateShape("Shape already exists in this model!"),
  RatioOutofBounds("The provided ratio is out of bounds, check java doc."),
  BadTickValue("Shape doesn't exist at given tick."),
  BadAppendable("The append bad bad."),
  NullParameter("Invalid null parameter passed in"),
  UnsupportedView("This view is unsupported"),
  ShapeNotInModel("The shape doesn't exist at this time!"),
  KeyFrameBadTick("Bad tick value preventing new keyframe.\nEvent probably exists at this frame!"),
  UnsupportedActionEvent("The action event passed in is not a valid action"),
  DoesNotSupportLayer("This model does not support layers");



  private final String errorStr;

  /**
   * Constructor for the ErrorMessage Enum.
   */
  ErrorMessages(String errorStr) {
    this.errorStr = errorStr;
  }

  /**
   * Getter for errorStr.
   */
  public String errorMessage() {
    return this.errorStr;
  }

  /**
   * Getter for errorStr but appends to the end of the error message.
   */
  public String errorMessage(String... params) {
    return this.errorStr + String.join("", params);
  }
}
