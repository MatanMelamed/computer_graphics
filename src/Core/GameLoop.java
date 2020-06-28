package Core;

public class GameLoop {

    // execution
    private static final int MAX_UPDATE = 5;
    private int updates;
    private boolean running;
    private long lastUpdateTime;

    // FPS handle
    private int targetFPS;
    private int timeBetweenUpdatesInNanoSec;
    private float timeBetweenUpdatesInSec;

    //region Singleton
    private static GameLoop instance = new GameLoop();

    public static GameLoop GetInstance() {return instance;}
    //endregion

    private GameLoop() {
        running = false;
        updates = 0;
        lastUpdateTime = 0;
        SetFPS(60);
    }

    public void SetFPS(int fps) {
        targetFPS = fps;
        timeBetweenUpdatesInNanoSec = 1000000000 / fps;
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
                        Thread.sleep((timeBetweenUpdatesInNanoSec - timeTaken) / 1000000);
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
        while (startUpdateTime - lastUpdateTime >= timeBetweenUpdatesInNanoSec) {

            GameManager.UpdateLogic(timeBetweenUpdatesInSec);

            lastUpdateTime += timeBetweenUpdatesInNanoSec;
            updates++;

            if (updates > MAX_UPDATE) {
                break;
            }
        }
    }

    private void updateGraphics() {
        GameManager.UpdateGraphics();
    }
}
