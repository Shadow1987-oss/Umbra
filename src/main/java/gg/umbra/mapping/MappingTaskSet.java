package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.MappingTask;
import gg.umbra.notification.NotificationType;
import java.util.ArrayList;
import java.util.List;

public class MappingTaskSet {
    protected List<MappingTask> D = new ArrayList<MappingTask>();

    public void d() {
        int n = 0;
        for (MappingTask mappingTask : this.D) {
            ++n;
            if (mappingTask.isApplied()) continue;
            int n2 = 0;
            String phase = "prepare";
            try {
                mappingTask.prepare();
                phase = "transform";
                mappingTask.transform();
                phase = "serialize";
                mappingTask.serialize();
                phase = "commit";
                n2 = mappingTask.commit();
            }
            catch (Throwable throwable) {
                n2 = -1;
                Umbra.debugLog("Mapping task " + n + " failed during " + phase
                    + ": " + mappingTask.getClass().getName()
                    + " -> " + MappingTaskSet.targetName(mappingTask));
                Umbra.logThrowable(throwable);
            }
            if (n2 == 0) continue;
            String string = n2 + " " + n;
            if (n2 != -1) {
                Umbra.debugLog("Mapping task " + n + " commit returned " + n2
                    + ": " + mappingTask.getClass().getName()
                    + " -> " + MappingTaskSet.targetName(mappingTask));
            }
            if (Umbra.INSTANCE.getNotificationManager() == null) continue;
            Umbra.INSTANCE.getNotificationManager().show("Error with injection", "Please report to support:\nError code " + string + "\n\nSome features may not function", NotificationType.ALERT, 30000L, true);
        }
    }

    private static String targetName(MappingTask mappingTask) {
        Class targetClass = mappingTask.getTargetClass();
        return targetClass == null ? "<null>" : targetClass.getName();
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public void C() {
        for (MappingTask mappingTask : this.D) {
            mappingTask.rollback();
        }
    }
}
