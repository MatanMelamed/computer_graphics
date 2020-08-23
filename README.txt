Maze Runner
The game is about running through a maze before the monster does.
The game starts after the player turns around to face the monster,
then the wall that was in front of him will dissapear and he could start the maze.

How to play:
Use W,A,S,D to move horizontally.
Use mouse to look around.
Use F1 to pause.
Use F2 to force level 2.

If you want to cheat you can move vertically using space and shift.
Game may not function well with these movements.

Gameplay:
After a few seconds since level ended, you will see victory  \ defeat screen for a few seconds and then another level will begin.
If you won the last level you will move to the next level \ back to level 1.
If you lost the last level you will repeat it.

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

Things to note:
Monster always rotate towards you!
Each level has a different maze!
Before level starts the is a level presentation and count down after it!

Thank you for the course!

