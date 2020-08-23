// Matan Melamed 205973613
package Core;

public class GameLoop {

    // execution
    private static final int MAX_UPDATE = 5;
    private int updates;
    private boolean running;
    private long lastUpdateTime;

    // FPS handle
    private int targetFPS;
    private float timeBetweenUpdatesInNanoSec;
    private float timeBetweenUpdatesInSec;

    //region Singleton
    private static GameLoop instance = new GameLoop();

    public static GameLoop GetInstance() {return instance;}
    //endregion

    private GameLoop() {
        running = false;
        updates = 0;
        lastUpdateTime = System.nanoTime();
        SetFPS(144);
    }

    private void SetFPS(int fps) {
        targetFPS = fps;
        timeBetweenUpdatesInNanoSec = 1000000000f / fps;
        timeBetweenUpdatesInSec = 1f / fps;
    }

    public void Start() {

        Thread thread = new Thread(() -> {
            running = true;

            while (running) {
                long startUpdateTime = System.nanoTime();

                updateLogic(startUpdateTime);
                updateGraphics();

                long timeTaken = System.nanoTime() - startUpdateTime;
                if (timeBetweenUpdatesInNanoSec > timeTaken) {
                    try {
                        Thread.sleep((long) ((timeBetweenUpdatesInNanoSec - timeTaken) / 1000000f));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        );
        thread.setName("GameLoop");
        thread.start();
    }

    private void updateLogic(long startUpdateTime) {
        updates = 0;

        /*
            If the difference between now and the last time we updated is still greater than the amount of time
            we supposed to wait between model updates it means there are more model updates that need to be done.
         */
        float deltaTime = startUpdateTime - lastUpdateTime;
        while (deltaTime >= timeBetweenUpdatesInNanoSec) {

            GameFramework.Update(deltaTime / 1000000f);

//            lastUpdateTime += timeBetweenUpdatesInNanoSec;
            lastUpdateTime = System.nanoTime();
            updates++;

            if (updates > MAX_UPDATE) {
                break;
            }
            deltaTime = startUpdateTime - lastUpdateTime;
        }
    }

    private void updateGraphics() {
        GameFramework.Render();
    }
}
