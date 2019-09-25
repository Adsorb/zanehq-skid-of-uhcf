package cc.zanehq.development.special.Summer;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import cc.zanehq.development.Base;
import cc.zanehq.development.helpers.timer.GlobalTimer;
import cc.zanehq.development.helpers.timer.event.TimerExpireEvent;
import net.md_5.bungee.api.ChatColor;

public class SummerTimer extends GlobalTimer implements Listener {

	private final Base plugin;
	
	public SummerTimer(Base plugin) {
		super("Summer Sale",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The Summer &a&lsale &7has ended.");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.YELLOW.toString() + ChatColor.BOLD.toString();
	}
	
}
