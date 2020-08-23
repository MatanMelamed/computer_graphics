Matan Melamed 205973613
The game is about exiting a maze of each level before the monster does.
The game starts after the player turns around to face the monster,
then the wall that was in front of him will dissapear and he cound start the maze.

Application Structure
Core - the package that contains application components
GameFramework starts the GameLoop which make calls per FPS.
GameLoop invokes GameFramework calls of Update and Render,
Rendering will go as:
GameLoop -> GameFramework -> Canvas display -> Renderer display -> GameManager Render -> app rendering 

Renderer is the GLEventListener
Graphics class encapsulates the openGL API of drawing for appication drawing
Sound - package that contains the sound components.
InputManager manages the input

entities in game are GameObject which can contain children and components.
Rendering one will render its components and then its children. Same goes for update.
Prefabs are custom game objects which answer a certain need.

Level manager manages the leveling and setting the levels.
lastly Models have usefull models for app such as Transform, Vector3D, ImageResource, WavefrontObjectLoader.

This project was built by Matan Melamed except 
WavefrontObjectLoader_DisplayList class
&
BaseLogging class

Any question will gladly be answered at melamed.matan1@gmail.com

Thank you for the course!

