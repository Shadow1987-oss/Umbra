package gg.umbra.visual.proj;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.visual.proj.Projectile;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;

public class PotionProjectile
extends Projectile {
    public PotionProjectile() {
        super(new HashSet<Class>(Collections.singleton(MappedClasses.Zf)), new Color(255, 66, 249));
    }
}

