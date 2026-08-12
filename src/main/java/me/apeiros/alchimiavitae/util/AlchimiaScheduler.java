package me.apeiros.alchimiavitae.util;

import javax.annotation.Nonnull;

import org.bukkit.Location;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

/**
 * Small scheduling bridge for AlchimiaVitae's world-bound animation work.
 *
 * <p>Slimefun Legacy owns the Paper/Folia scheduler abstraction, so addon code
 * can schedule at a block location without depending on FoliaLib or directly
 * touching Bukkit's global scheduler.</p>
 */
public final class AlchimiaScheduler {

    private AlchimiaScheduler() {}

    public static void runPhases(@Nonnull Location location, long periodTicks, @Nonnull Runnable... phases) {
        Location anchor = location.clone();

        for (int i = 0; i < phases.length; i++) {
            Runnable phase = phases[i];
            long delay = periodTicks * i;

            if (delay == 0L) {
                Slimefun.getSchedulerService().runAt(anchor, phase);
            } else {
                Slimefun.getSchedulerService().runAtLater(anchor, phase, delay);
            }
        }
    }
}
