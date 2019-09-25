package cc.zanehq.development.factions.event.argument;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cc.zanehq.development.Base;
import cc.zanehq.development.factions.event.EventTimer;
import cc.zanehq.development.factions.event.EventType;
import cc.zanehq.development.factions.event.tracker.KothTracker;
import cc.zanehq.development.factions.faction.type.Faction;
import cc.zanehq.development.util.command.CommandArgument;

public class EventCancelArgument
  extends CommandArgument
{
  private final Base plugin;
  
  public EventCancelArgument(Base plugin)
  {
    super("cancel", "Cancels a running event", new String[] { "stop", "end" });
    this.plugin = plugin;
    this.permission = ("hcf.command.event.argument." + getName());
  }
  
  
  public String getUsage(String label)
  {
    return '/' + label + ' ' + getName();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    EventTimer eventTimer = this.plugin.getTimerManager().eventTimer;
    Faction eventFaction = eventTimer.getEventFaction();
    if (!eventTimer.clearCooldown())
    {
      sender.sendMessage(ChatColor.RED + "There is not a running event.");
      return true;
    }
    return true;
  }
}
