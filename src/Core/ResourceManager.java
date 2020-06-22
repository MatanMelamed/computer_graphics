package Core;

import Models.ImageResource;

import java.util.HashMap;

public class ResourceManager {

    private HashMap<String, ImageResource> imageResourcesCache;


    private static ResourceManager instance = new ResourceManager();

    private ResourceManager() {
        imageResourcesCache = new HashMap<>();
    }

    public static ImageResource GetImageResourceByName(String imageName) {
        ImageResource result = instance.imageResourcesCache.get(imageName);

        if (result == null) {
            result = new ImageResource(imageName);
            instance.imageResourcesCache.put(imageName, result);
        }

        return result;
    }


}
