package com.hcfactions.hcf.factions.event.conquest;

import com.hcfactions.hcf.Base;
import com.hcfactions.hcf.util.command.ArgumentExecutor;

public class ConquestExecutor
  extends ArgumentExecutor
{
  public ConquestExecutor(Base plugin)
  {
    super("conquest");
    addArgument(new ConquestSetpointsArgument(plugin));
  }
}
