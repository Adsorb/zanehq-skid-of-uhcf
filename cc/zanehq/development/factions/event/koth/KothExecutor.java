package cc.zanehq.development.factions.event.koth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cc.zanehq.development.Base;
import cc.zanehq.development.factions.event.koth.argument.KothNextArgument;
import cc.zanehq.development.factions.event.koth.argument.KothScheduleArgument;
import cc.zanehq.development.factions.event.koth.argument.KothSetCapDelayArgument;
import cc.zanehq.development.factions.event.koth.argument.KothShowArgument;
import cc.zanehq.development.util.command.ArgumentExecutor;

public class KothExecutor
  extends ArgumentExecutor
{
  private final KothScheduleArgument kothScheduleArgument;
  
  public KothExecutor(Base plugin)
  {
    super("koth");
    addArgument(new KothNextArgument(plugin));
    addArgument(new KothShowArgument());
    addArgument(this.kothScheduleArgument = new KothScheduleArgument(plugin));
    addArgument(new KothSetCapDelayArgument(plugin));
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      this.kothScheduleArgument.onCommand(sender, command, label, args);
      return true;
    }
    return super.onCommand(sender, command, label, args);
  }
}
