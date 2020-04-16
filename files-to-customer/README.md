## README for Assignment 8-views

For this assignment, we did not make any changes for our customers. Our customers did not message us back with any code change requests or any explanations. We believe this because we spent a substantial amount of time on Java documentation as well as making sure that every object had a clear and concise responsibility. 

We followed the Model-View-Controller design pattern and decoupled the responsibilities of our objects into the appropriate places. Because our code is decoupled, this made implementing and supporting new features extremely easy and simple. 

#### Files we sent to our customers
```
└── cs3500
    └── animator
        ├── controller
        │   ├── AnimationControllerImpl.java
        │   ├── IAnimationController.java
        │   └── InteractiveControllerImpl.java
        ├── misc
        │   ├── ErrorMessages.java
        │   ├── Position2D.java
        │   └── Utils.java
        ├── model
        │   ├── AnimatedShape.java
        │   ├── EventInstructions.java
        │   ├── IAnimatedShape.java
        │   ├── IAnimationModel.java
        │   └── ReadOnlyIAnimationModel.java
        ├── shapes
        │   ├── AShape.java
        │   ├── Ellipse.java
        │   ├── IShape.java
        │   ├── Rectangle.java
        │   └── Triangle.java
        ├── util
        │   ├── AnimationBuilder.java
        │   ├── AnimationModelBuilder.java
        │   └── AnimationReader.java
        └── view
            ├── AnimationPanel.java
            ├── Command.java
            ├── IAnimationView.java
            ├── IInteractiveView.java
            ├── InteractiveVisualView.java
            └── VisualView.java
```