package gg.umbra.utils.render;

import gg.umbra.Umbra;
import gg.umbra.utils.SleepUtil;
import gg.umbra.utils.render.RemoteImageTextureCache;
import gg.umbra.utils.render.RemoteImageTextureManager;

class RemoteImageTextureCacheUpdateThread
extends Thread {
    final RemoteImageTextureManager manager;

    @Override
    public void run() {
        while (!Umbra.INSTANCE.isEnabled()) {
            SleepUtil.sleep(50L);
            for (Integer imageSize : RemoteImageTextureManager.getCaches(this.manager).keySet()) {
                ((RemoteImageTextureCache)RemoteImageTextureManager.getCaches(this.manager).get(imageSize)).processPendingDownloads();
            }
        }
    }

    RemoteImageTextureCacheUpdateThread(RemoteImageTextureManager manager) {
        this.manager = manager;
    }
}
