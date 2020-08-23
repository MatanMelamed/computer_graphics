// Matan Melamed 205973613
package GameObjects.Prefabs;

import Core.Graphics.Renderer;
import Core.InputManager;
import GameObjects.GameObject;
import com.jogamp.newt.event.KeyEvent;

public class LookAtController extends GameObject {

    private float x, y, z;
    private float lookAtZ;

    public LookAtController() {
        x = 0;
        y = 2000;
        z = -5;
        lookAtZ = 1;
        InputManager.RegisterBinding(KeyEvent.VK_1, () -> x += InputManager.isPressed(KeyEvent.VK_ALT) ? -0.1f : 0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_2, () -> y += InputManager.isPressed(KeyEvent.VK_ALT) ? -0.1f : 0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_3, () -> z += InputManager.isPressed(KeyEvent.VK_ALT) ? -0.1f : 0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_4, () -> lookAtZ += InputManager.isPressed(KeyEvent.VK_ALT) ? -0.1f : 0.1f);
        InputManager.RegisterBinding(KeyEvent.VK_P, () -> System.out.println(String.format("%.2f %.2f %.2f %.2f", x, y, z, lookAtZ)));

    }

    @Override
    protected void Render() {
        super.Render();
        Renderer.GetGLU().gluLookAt(
                x, y, z,
                x, y, lookAtZ,
                0, 1, 0);
    }
}
