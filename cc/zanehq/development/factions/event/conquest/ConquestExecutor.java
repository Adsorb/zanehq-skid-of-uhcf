package cc.zanehq.development.factions.event.conquest;

import cc.zanehq.development.Base;
import cc.zanehq.development.util.command.ArgumentExecutor;

public class ConquestExecutor
  extends ArgumentExecutor
{
  public ConquestExecutor(Base plugin)
  {
    super("conquest");
    addArgument(new ConquestSetpointsArgument(plugin));
  }
}
