package Core.Graphics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class NextRenderExecutioner {

    private static BlockingQueue<Runnable> requests = new LinkedBlockingDeque<>();

    public static void AddRequestToNextRender(Runnable r) {
        requests.add(r);
    }

    public static void BeginRendering() {
        for (Runnable r : requests) {
            r.run();
            requests.remove(r);
        }
    }
}
