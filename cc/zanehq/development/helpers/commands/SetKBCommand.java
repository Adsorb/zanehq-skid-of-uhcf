package cc.zanehq.development.helpers.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cc.zanehq.development.util.base.BaseCommand;

public class SetKBCommand extends BaseCommand {
	public SetKBCommand() {
		super("setkb", "Sets the kb.");
		setAliases(new String[] { "setknockback", "kb" });
		setUsage("/(command)");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "knockback 0.9 0.86 1");
		Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Knockback has been set!");
		return true;
	}


}