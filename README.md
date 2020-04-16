# ExCELlence Project 
Northeastern University

CS3500 - Object Oriented Design

Developers: Scott Nguyen, Justin Torre

The scope of this project is to create the four animations below using the Model-View-Controller Pattern: 
- SimpleAnimation
- Towers of Hanoi
- The Night Sky
- Big Bang and Crunch

# Brief Overview of Our Design
We used the Model View Controller pattern for this project. 

## Model - `IAnimationModel`
Responsible for carrying the data for the animation. Has the ability to add or remove key frames, shapes, etc. However, the model will never call these methods on itself, rather, the controller will delegate appropriately. Contains the list of `IAnimatedShape`'s. An animated shape contains an `IShape` as well as the shapes `EventInstructions`. 

## View - `IAnimationView`
 The view is solely responsible for displaying the data in the animation as a text description, a visual animation inside of a window, or a text compliant with SVG format. For the interactive viual view, the class implements action listener in order trigger any button events, key events, etc. The view has the ability to edit the panels within it, as well as the tempo, however, like the model, these methods will never be self called. The controller will be the one delegating to the view. 


## View - `IInteractiveView`
 This interactive view has methods that allows the controller to update and grab necessary fields within the Interactive View window. These functions allow a user to properly get the information for the actions needed when the action listener handler for the view is triggered.s


## Controller - `IAnimationController`
The controller mediates any information between the model and the view. The interactive controller implements Aciton Listener as well because it needs to be the listener for the events that occur in the view. The interactive controller has a method `actionPerformed ()` that delegates the appropriate calls to the model and view whenever a particular action event is made in the view. This delegation process is uses the Command enum to swithc through the particular commands in for the view. 

##### Avaliable Commands the controller can handle
``` java
public enum Command {
  START("Start"),
  PAUSE("Pause"),
  RESTART("Restart"),
  TEMPO_DOUBLE("Tempo x2"),
  TEMPO_HALF("Tempo /2"),
  ENABLE("Enable"),
  DISABLE("Disable"),
  DELETE_SHAPE("Delete Shape"),
  DELETE_KEYFRAME("Delete Keyframe"),
  ADD_SHAPE("Add Shape"),
  ADD_KEYFRAME("Add KeyFrame"),
  OPEN("Open"),
  SAVE("Save");
  ...
}
```

<img src="uml-easy-animator.png" alt="UML Not found">

### How to Generate An Animation From Terminal/Command Prompt
1) Download `Excellence.jar` and save the text file you want to animate in the same location.
2) For Window OS, open Command Prompt. For Mac OS or Linux, open Terminal.
3) Pass in a valid command line argument.

Example of a valid command line argument using Command Prompt or Terminal:
```
java -jar NameOfJARFile.jar -in smalldemo.txt -view text -out out -speed 2
```
#
### How To Generate An Animation Using A Text File Within The Program
1) Add a text file with the animation into the project folder. 
2) Pass in valid command line arguments
    - Note: A view can be one of: "text", "visual", or "svg"

Example of a valid command line argument:
```
-in hanoi.txt -view text -out System.out 
-speed 2
```

3) Open the `Excellence` class and run the `main()` method to display to animation

### Changes to the model from Assignment 5
In the IAnimationModel, we added a method `addEventInstructionToShape()` that takes in a String "name" and a EventInstructions "e". We did this so that the model had a convenient method to get a shape by its id (the "name"), and then update the event instructions with the new event instructions e. 

We created a `setBounds` and `getBounds` methods for the model and read only model respectively. The bounds represent the bounds at which the animation will be shown. We implemented these functions for a user of the model (ie, the view) to know the possible area shapes can be represented.  

Additionally, for future support we implemented a method to add key frames into a model called `placeKeyFrame`. This function takes in the necessary parameters for a new KeyFrame within the existing events of an AnimationShape. The function does so by looking at the keyframe's time stamp and splitting up the event into two events where the end of the first event is the keyframe and the start of the second is the keyframe. These functions can be found in the Model and AnimatedShape classes.

We also created a read only interface, `ReadOnlyIAnimationModel` to make sure that the views were getting a version of the view that was immutable and can not write back to the structure. The only methods that the read only model interface has are getter methods. Our original `IAnimationModel` extends the `ReadOnlyIAnimationModel` to inherit the getter methods. 

### Changes to the model from Assignment 6
In the `IAnimationModel`, we implemented a new method `setListOfAnimatedShapes()` that sets the list of animated shapes in the model to the passed in array list of animated shapes. We needed to implement this in order for the `restart()` method in the `IInteractiveView` to be accurately implemented. The `setListOfAnimatedShapes()` method is used in the `InteractiveControllerImpl` class in order to return the list of animated shapes in the model back to its original state. 

We also implemented a method `deleteShape(String id)` that deleted a shape in the list of animated shapes based on the string id given to it. We created this method in order for the controller to have a method available to it that can delete a shape when needed. 

We had to revise how we placed keyframes into our model as well. The original implementation for adding a key fram only allowed the user to add the key frame inbetween the start and end of an `EventInstruction`. We had to allow the user to be able to add out side of the event instruction, so rather than refactoring our EventInstruction we created a code and flow that worked around our existing implementation. We did this by mocking keyframes using our eventInstruction structure. Our AnimatedShapes will contain a single key frame that is an event instruction where the start and end time are the same. Then when a new keyframe is added it simply expands that single instruction into a longer instruction. Then when we operate key frames accordingly. 


Another change we implemented in the ViewPanel where the shapes are animated on, is that we included a Looping field that allows the animation to be played back after the animation has terminated. We dynamically find the the max range on every 300th tick, to reduce latency in our animation.

### Provider Code Review
#### Model Review
In our provider's model package, we noticed some glaring errors that violate design principles that we learned from class. One of the violations that we identified was that the `List<Shapes> shapes` and `List<Motion> changes` fields were public. This isn't good design because there are potential mutation issues and we also ran into problems implementing our adapter since many of the methods in the provider's code rely on calling field of fields. 

Another problem that we identified was with the method set ups themselves. There are 4 public methods inside of their model implementation that are not in the interface that the model implements. Furthermore, one of the methods in the model implementation, `modelToString()` is static and should not be. 

#### View Review
There were also a few errors in the view implementations that we identified in their code. One of the issues that we saw was that their `display()` method had too many responsibilities. The documentation for the method says that `display()` "displays or views the View the user specifies". However, the sheer number of lines is an indicator that the method is responsible for much more including initializing the panels, buttons, animation frame, and key frames. 

Another issue that we identified when reviewing our provider's code was that they did not follow the Model-View-Controller design pattern. They do not have a controller in their source code and their `EditAnimationView` plays the part of the controller in their design, which is a problem as an object should have a singular responsibility and their view has two, that of the view and of the controller. 

To reference the first paragraph in the model review section, another clear violation of design principle was that they accessed field of fields inside of their view implementation. For the reasons mentioned above, this is not good design, and it leaves room for potential mutation issues down the road as the program becomes more and more complex. 

And the biggest issue that we noticed in their view implementation was that they were assigning the `SimpleAnimationImpl` as the model in the view as opposed to using the interface type `ISimpleAnimation`. This is a design flaw as their `EditAnimationView` is hard-coded to the class-type `SimpleAnimationImpl`. This is not future-proof as having to support different models down the road can be difficult as the `EditAnimationView` is designed specifically for the `SimpleAnimationImpl`. This is clearly not in conduct with the model-view-controller design pattern. 

##### View Pros

* It was very easy to implement their view and interface it with a controller since all the callbacks are implemented in the view. This is not the best design practice since the view technically should not modify the model. However, this didn't impede our ability to use our new model implementation that uses our model as a backend. All of the public callbacks used by the view (including the ones that modified the view) we simply overrided them in our `SourceToProviderModel`

### Experience Review
This assignment proved to be a mixed bag of good and bad experiences. One of the good experiences that we had was in supporting our customers with our code. We did not have to edit any of the files that we sent to them and enjoyed explaining any confusions that they had with the code. Another one of the good experiences that we had was in the actual adapting implementation process. We saw the fruition of our design choices because it was very straight forward to adapt our models and create a new view implementation. Because of our purposeful decoupled design, scaling up was relatively easy and simple. 

However, there were also some negative experiences that we had thoughout assignment 8. The most significant negative experiences that we had was with asking our provider for code explanations or proposed code changes. The process was lengthy and email chains would go on and on without any real productive outcomes. We received our code late and were hinered by late email replies to our questions. As a result, the overhead took a lot of time from us. Potentially, a two-way provider/customer relationship would have been beneficial as you would only have to coordinate with one other team. 

For the future, we think the above change would be useful for us as we are already interfacing so much with one group, and so we could support each other both ways. Another potential change to the assignment that we think could be useful is making the assignment page more specific. We had to read over the assignment a few times before we really understood exactly what we had to do for the submission. Clarity in what the assignment is looking for would go a long way for all students. And a third change that we think would be useful is going over the homework assignments a little more in class. We spend so much time on the homeworks that going over them in class after they are due to make sure that provider code is functional would be beneficial. 

#### Extra notes/Road blocks and Solutions
* We got their editor view to display using our design, but it is not fully functional.
  * They did not fully implement the features required from the previous assignments and we technically were not supposed to have access to the implementation of the model so we went ahead and did the best we could
  * The frame displays entirely, with buttons and the panel displaying the animation. However, some of the functionalities like deleting shapes requires us to mutate the model in some way and they did not technically give us a working way to do this.

* Their Keyframing and modifing shape features did not work in their current implementation, so in our version their was no way to get it working. 
  * As a side note, they do have lambdas that attempt to implement these features. However, they access fields of the model and directly mutate them so there was not a clean way to abstract these, and since the original implementation using their model still doesn't work with the given edit view we didn't see a reason to ask them to fix their field of field issues since their were other logical issues in their code. 
  * <b>SOLUTION: </b> Only implement what is needed. It seems like there was an attempt to have some abstraction with functions like `getShapesList()` HOWEVER this function is never called in their view, so we simply throw `UnsupportedOperationException` since this is not within the scope of this assignment.







"# the-easy-animator" 
