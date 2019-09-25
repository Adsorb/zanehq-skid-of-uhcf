package cc.zanehq.development.factions.event.tracker;

import org.bukkit.entity.Player;

import cc.zanehq.development.factions.event.CaptureZone;
import cc.zanehq.development.factions.event.EventTimer;
import cc.zanehq.development.factions.event.EventType;
import cc.zanehq.development.factions.event.faction.EventFaction;

public abstract interface EventTracker
{
  public abstract EventType getEventType();
  
  public abstract void tick(EventTimer paramEventTimer, EventFaction paramEventFaction);
  
  public abstract void onContest(EventFaction paramEventFaction, EventTimer paramEventTimer);
  
  public abstract boolean onControlTake(Player paramPlayer, CaptureZone paramCaptureZone);
  
  public abstract boolean onControlLoss(Player paramPlayer, CaptureZone paramCaptureZone, EventFaction paramEventFaction);
  
  public abstract void stopTiming();
}
