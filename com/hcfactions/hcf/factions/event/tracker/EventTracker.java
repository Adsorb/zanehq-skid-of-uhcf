package com.hcfactions.hcf.factions.event.tracker;

import org.bukkit.entity.Player;

import com.hcfactions.hcf.factions.event.CaptureZone;
import com.hcfactions.hcf.factions.event.EventTimer;
import com.hcfactions.hcf.factions.event.EventType;
import com.hcfactions.hcf.factions.event.faction.EventFaction;

public abstract interface EventTracker
{
  public abstract EventType getEventType();
  
  public abstract void tick(EventTimer paramEventTimer, EventFaction paramEventFaction);
  
  public abstract void onContest(EventFaction paramEventFaction, EventTimer paramEventTimer);
  
  public abstract boolean onControlTake(Player paramPlayer, CaptureZone paramCaptureZone);
  
  public abstract boolean onControlLoss(Player paramPlayer, CaptureZone paramCaptureZone, EventFaction paramEventFaction);
  
  public abstract void stopTiming();
}
