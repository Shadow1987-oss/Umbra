package gg.umbra.config;

import gg.umbra.config.ProfileModuleSnapshot;
import gg.umbra.config.ProfileSnapshot;
import java.util.Comparator;

public class ProfileModuleSnapshotOrderComparator
implements Comparator<ProfileModuleSnapshot> {
    @Override
    public int compare(ProfileModuleSnapshot left, ProfileModuleSnapshot right) {
        return Integer.compare(right.getSortPriority(), left.getSortPriority());
    }
}
