package com.hcfactions.hcf.special.Christmas;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.hcfactions.hcf.Base;
import com.hcfactions.hcf.ConfigurationService;
import com.hcfactions.hcf.factions.sale.SaleListener;
import com.hcfactions.hcf.helpers.timer.event.TimerExpireEvent;
import com.hcfactions.hcf.util.BukkitUtils;
import com.hcfactions.hcf.util.DurationFormatter;
import com.hcfactions.hcf.util.JavaUtils;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;

public class ChristmasCommand implements CommandExecutor, TabCompleter {

	private final Base plugin;

	public ChristmasCommand(Base plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("command.sale")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}

		if (args.length == 0 || args.length > 2) {
            sender.sendMessage(
                    "�9�m--------------------------------");
            sender.sendMessage(
            		"�c/christmas start <duration> - Starts the Christmas sale timer.");
            sender.sendMessage(
            		"�c/christmas end - Ends the current sale timer.");
            sender.sendMessage(
                    "�9�m--------------------------------");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("end")) {
				if (plugin.getTimerManager().getChristmasTimer().clearCooldown()) {
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					Bukkit.broadcastMessage(ChatColor.GREEN + "The store-wide sale has been cancelled!");
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {
				long duration = JavaUtils.parse(args[1]);

				if (duration == -1L) {
					sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is an invalid duration.");
					return true;
				}

				if (duration < 1000L) {
					sender.sendMessage(ChatColor.RED + "Christmas Sale time must last for at least 20 ticks.");
					return true;
				}

				ChristmasTimer christmastimer = plugin.getTimerManager().getChristmasTimer();

				if (!christmastimer.setRemaining(duration, true)) {
					sender.sendMessage(ChatColor.RED + "The sale is already on.");
					return true;
				}

				sender.sendMessage(ChatColor.GREEN + "Started sale timer for "
						+ DurationFormatUtils.formatDurationWords(duration, true, true) + ".");

				Bukkit.broadcastMessage(ChatColor.GRAY + "The Christmas" + ChatColor.GREEN.toString() + ChatColor.BOLD + "sale " + ChatColor.GRAY + "has started at " + ChatColor.DARK_GREEN + ConfigurationService.DONATE_URL
						+ ChatColor.GRAY + " for " + DurationFormatUtils.formatDurationWords(duration, true, true) + ChatColor.GRAY + ".");
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Collections.emptyList();
	}
}
