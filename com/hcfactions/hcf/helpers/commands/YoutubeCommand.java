package com.hcfactions.hcf.helpers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.hcfactions.hcf.Base;


public class YoutubeCommand implements CommandExecutor{
	
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command");
            return true;
        }
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "Youtube rank Requirements");
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "Sub Requirement" + " � " + ChatColor.WHITE + Base.getPlugin(Base.class).getConfig().getString("youtube_subs"));
        sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.GOLD + "View Requirement" +" � " + ChatColor.WHITE + Base.getPlugin(Base.class).getConfig().getString("youtube_views"));
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        return false;
    }
}
