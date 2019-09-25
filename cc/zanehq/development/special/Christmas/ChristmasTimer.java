package cc.zanehq.development.special.Christmas;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import cc.zanehq.development.Base;
import cc.zanehq.development.helpers.timer.GlobalTimer;
import cc.zanehq.development.helpers.timer.event.TimerExpireEvent;
import net.md_5.bungee.api.ChatColor;

public class ChristmasTimer extends GlobalTimer implements Listener {

	private final Base plugin;
	
	public ChristmasTimer(Base plugin) {
		super("Christmas Sale",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The Christmas &a&lsale &7has ended.");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.WHITE.toString() + ChatColor.BOLD.toString();
	}
	
}
