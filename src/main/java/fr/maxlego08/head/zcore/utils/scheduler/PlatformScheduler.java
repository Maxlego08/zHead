package fr.maxlego08.head.zcore.utils.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/** Routes tasks to the thread that owns their state, with a fallback for older Paper. */
public final class PlatformScheduler {

    private final Plugin plugin;
    private final boolean folia;
    private volatile boolean stopped;

    public PlatformScheduler(Plugin plugin) {
        this(plugin, isFolia());
    }

    PlatformScheduler(Plugin plugin, boolean folia) {
        this.plugin = plugin;
        this.folia = folia;
    }

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    public void runAsync(Runnable action) {
        if (!active()) return;
        if (folia) {
            Bukkit.getAsyncScheduler().runNow(plugin, task -> runIfActive(action));
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> runIfActive(action));
        }
    }

    /** For plugin/server state only; player inventories must use runPlayer. */
    public void runGlobal(Runnable action) {
        if (!active()) return;
        if (folia) {
            Bukkit.getGlobalRegionScheduler().execute(plugin, () -> runIfActive(action));
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> runIfActive(action));
        }
    }

    /** Always defers, including from inventory click callbacks. */
    public void runPlayer(Player player, Runnable action) {
        runPlayerLater(player, action, 1L);
    }

    public void runPlayerLater(Player player, Runnable action, long delayTicks) {
        if (!active() || player == null) return;
        Runnable guarded = () -> {
            if (active() && player.isOnline()) action.run();
        };
        long delay = Math.max(1L, delayTicks);
        if (folia) {
            // The entity scheduler follows teleports and discards tasks when the player retires.
            player.getScheduler().execute(plugin, guarded, null, delay);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, guarded, delay);
        }
    }

    public void stop() {
        stopped = true;
        if (folia) {
            Bukkit.getAsyncScheduler().cancelTasks(plugin);
            Bukkit.getGlobalRegionScheduler().cancelTasks(plugin);
        } else {
            Bukkit.getScheduler().cancelTasks(plugin);
        }
    }

    private boolean active() {
        return !stopped && plugin.isEnabled();
    }

    private void runIfActive(Runnable action) {
        if (active()) action.run();
    }
}
