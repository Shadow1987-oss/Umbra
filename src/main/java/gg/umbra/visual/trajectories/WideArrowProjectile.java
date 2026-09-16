package gg.umbra.visual.trajectories;

import gg.umbra.visual.proj.ArrowProjectile;

public class WideArrowProjectile extends ArrowProjectile {
    @Override
    public float getCollisionRadius() {
        return 0.5f;
    }
}
