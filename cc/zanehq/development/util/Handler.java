package cc.zanehq.development.util;

import cc.zanehq.development.Base;

public class Handler
{
  private Base plugin;
  
  public Handler(Base paramNotorious) {
    this.plugin = paramNotorious;
  }
  
  public void enable() {}
  
  public void disable() {}
  
  public Base getInstance() {
    return this.plugin;
  }
}
