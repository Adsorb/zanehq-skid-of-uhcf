package cc.zanehq.development.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.zanehq.development.Base;
import cc.zanehq.development.ConfigurationService;

public class PlayerVaultCommand implements CommandExecutor{
	private final Base plugin;

	public PlayerVaultCommand(Base plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You fat bastard..");
			return false;
		}

		Player player = (Player) sender;
		Player target = player;
		if(player.hasPermission("commands.pv.others") && args.length != 0){
			target = Bukkit.getPlayer(args[0]);

			if(target == null){
				player.sendMessage(ChatColor.RED + "Unknown player.");
				return true;
			}
		}

		player.openInventory(target.getEnderChest());
		player.sendMessage(ChatColor.WHITE + "Opened " + ChatColor.RED.toString() + ChatColor.BOLD + (player.equals(target) ? "your" : target.getName() + "'s") + ChatColor.WHITE + " player vault.");

		return true;
	}
}