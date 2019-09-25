package com.hcfactions.hcf.factions.sale;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.hcfactions.hcf.Base;
import com.hcfactions.hcf.helpers.timer.GlobalTimer;
import com.hcfactions.hcf.helpers.timer.event.TimerExpireEvent;

import net.md_5.bungee.api.ChatColor;

public class SaleTimer extends GlobalTimer implements Listener {

	private final Base plugin;
	
	public SaleTimer(Base plugin) {
		super("Store Sale",  TimeUnit.SECONDS.toMillis(1L));
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExpire(TimerExpireEvent event) {
		if (event.getTimer() == this) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &7The store-wide &a&lsale &7has ended.");
		}
	}
	
	@Override
	public String getScoreboardPrefix() {
		return ChatColor.GREEN.toString() + ChatColor.BOLD.toString();
	}
	
}
