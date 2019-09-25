package com.hcfactions.hcf.special.KING;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.hcfactions.hcf.Base;
import com.hcfactions.hcf.helpers.timer.GlobalTimer;
import com.hcfactions.hcf.helpers.timer.event.TimerExpireEvent;


public class KingTimer extends GlobalTimer implements Listener {

	private final Base plugin;
	
	public KingTimer(Base plugin) {
		super("Key-All",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The &a&lKeyAll &7has happened.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr giveallkey " + KingCommand.player + " 1");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.GOLD.toString() + ChatColor.BOLD.toString();
	}
	
}
