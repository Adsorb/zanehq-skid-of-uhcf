package cc.zanehq.development.special.KING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import cc.zanehq.development.Base;
import cc.zanehq.development.util.BukkitUtils;




public class KingCommand implements CommandExecutor, TabCompleter {
	public static ArrayList<UUID> play = new ArrayList<UUID>();

	private final Base plugin;

	public static Player player;
	public static String kingName = "";
	public static String kingPrize = "";
	
	public KingCommand(Base plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("command.kingevent")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}

		if (args.length == 0 || args.length > 3) {
            sender.sendMessage(
                    "§7§m--------------------------------");
            sender.sendMessage(
                    "§3§lKing Event");
            sender.sendMessage(
                    "");
            sender.sendMessage(
            		"§3/kingevent start <player>§7 - §fStarts the King Event.");
            sender.sendMessage(
            		"§3/kingevent end §7- §fEnds the current king event.");
            sender.sendMessage(
            		"§3/kingevent prize <prize> §7- §fSets the prize for the current king event.");
            sender.sendMessage(
                    "§7§m--------------------------------");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("end")) {
					player = null;
					return true;
				
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}
		
		if (args.length == 2 && args[0].equalsIgnoreCase("prize")) {
			kingPrize = args[1].replaceAll("_", " ");
			sender.sendMessage(ChatColor.GREEN + "You have successfully set the prize to " + kingPrize);
			return true;
		}
		
		if (args.length == 2 && args[0].equalsIgnoreCase("prize")) {
			kingPrize = args[1].replaceAll("_", " ");
			sender.sendMessage(ChatColor.GREEN + "You have successfully set the prize to " + kingPrize);
			return true;
		}
		
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {

				   Player p = Bukkit.getPlayer(args[1]);
				   kingName = p.getName();
		        	Player player1 = Bukkit.getPlayer(KingCommand.kingName);

				   sender.sendMessage(ChatColor.GREEN + "You have successfully started the king event!");
				   Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + BukkitUtils.STRAIGHT_LINE_DEFAULT);
				   Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "");
				   Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "King Event");
				   Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "");
				   Bukkit.broadcastMessage(ChatColor.GOLD + " »§e King" + ChatColor.GRAY + ": " + ChatColor.WHITE + kingName);
				   Bukkit.broadcastMessage(ChatColor.GOLD + " »§e Location" + ChatColor.GRAY + ": " + ChatColor.WHITE + "x" + player1.getLocation().getBlockX() + ", y" + player1.getLocation().getBlockY() + ", z" + player1.getLocation().getBlockZ());
				   Bukkit.broadcastMessage(ChatColor.GOLD + " »§e Starting Health" + ChatColor.GRAY + ": " + ChatColor.WHITE + player1.getHealthScale());
				   Bukkit.broadcastMessage("");
				   Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + BukkitUtils.STRAIGHT_LINE_DEFAULT);
				      if(p == null) {
				            sender.sendMessage(ChatColor.RED + "That player is not online.");
				            return true;
				      }
				      player = p;
				      
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
