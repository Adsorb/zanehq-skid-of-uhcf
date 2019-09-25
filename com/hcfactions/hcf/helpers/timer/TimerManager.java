package com.hcfactions.hcf.helpers.timer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.hcfactions.hcf.Base;
import com.hcfactions.hcf.factions.event.EventTimer;
import com.hcfactions.hcf.factions.reboot.RebootTimer;
import com.hcfactions.hcf.factions.sale.SaleTimer;
import com.hcfactions.hcf.helpers.timer.type.AppleTimer;
import com.hcfactions.hcf.helpers.timer.type.ArcherTimer;
import com.hcfactions.hcf.helpers.timer.type.EnderPearlTimer;
import com.hcfactions.hcf.helpers.timer.type.GappleTimer;
import com.hcfactions.hcf.helpers.timer.type.HomeTimer;
import com.hcfactions.hcf.helpers.timer.type.LogoutTimer;
import com.hcfactions.hcf.helpers.timer.type.PvPTimerProtection;
import com.hcfactions.hcf.helpers.timer.type.PvpClassWarmupTimer;
import com.hcfactions.hcf.helpers.timer.type.SpawnTagTimer;
import com.hcfactions.hcf.helpers.timer.type.StuckTimer;
import com.hcfactions.hcf.helpers.timer.type.TeleportTimer;
import com.hcfactions.hcf.special.Christmas.ChristmasTimer;
import com.hcfactions.hcf.special.Thanksgiving.ThanksGivingTimer;
import com.hcfactions.hcf.special.keyall.KeyallTimer;
import com.hcfactions.hcf.util.Config;


public class TimerManager implements Listener {
	
	
    private final RebootTimer rebootTimer;
    private final SaleTimer saleTimer;
    private final ThanksGivingTimer thanksgivingtimer;
    private final KeyallTimer keyalltimer;
    private final ChristmasTimer christmastimer;
	public final SpawnTagTimer combatTimer;
    public final LogoutTimer logoutTimer;
    public final EnderPearlTimer enderPearlTimer;
    public final EventTimer eventTimer;
	public final GappleTimer gappleTimer;
    public final PvPTimerProtection invincibilityTimer;
    private final PvpClassWarmupTimer pvpClassWarmupTimer;
    public final HomeTimer home;
    public final AppleTimer appleTimer;
    public final StuckTimer stuckTimer;
	public RebootTimer getRebootTimer() {
		return rebootTimer;
	}
	
	public SaleTimer getSaleTimer() {
		return saleTimer;
	}
	
	public ThanksGivingTimer getThanksGivingTimer() {
		return thanksgivingtimer;
	}
	
	public ChristmasTimer getChristmasTimer() {
		return christmastimer;
	}
	
	public KeyallTimer getKeyallTimer() {
		return keyalltimer;
	}

	public final TeleportTimer teleportTimer;
    public final ArcherTimer archerTimer;
    private final Set<Timer> timers = new LinkedHashSet<>();
    private final JavaPlugin plugin;
    private Config config;

    public TimerManager(Base plugin) {
        (this.plugin = plugin).getServer().getPluginManager().registerEvents(this, plugin);
        this.registerTimer(this.enderPearlTimer = new EnderPearlTimer(plugin));
        this.registerTimer(this.home = new HomeTimer());
        this.registerTimer(this.logoutTimer = new LogoutTimer());
        this.registerTimer(this.gappleTimer = new GappleTimer(plugin));
        this.registerTimer(this.stuckTimer = new StuckTimer());
        this.registerTimer(this.invincibilityTimer = new PvPTimerProtection(plugin));
        this.registerTimer(this.combatTimer = new SpawnTagTimer(plugin));
        this.registerTimer(this.teleportTimer = new TeleportTimer(plugin));
        this.registerTimer(this.eventTimer = new EventTimer(plugin));
        this.registerTimer(this.archerTimer = new ArcherTimer(plugin));
        this.registerTimer(this.pvpClassWarmupTimer = new PvpClassWarmupTimer(plugin));
        this.registerTimer(this.rebootTimer = new RebootTimer(plugin));
        this.registerTimer(this.saleTimer = new SaleTimer(plugin));
        this.registerTimer(this.thanksgivingtimer = new ThanksGivingTimer(plugin));
        this.registerTimer(this.christmastimer = new ChristmasTimer(plugin));
        this.registerTimer(this.appleTimer = new AppleTimer(plugin));
        this.registerTimer(this.keyalltimer = new KeyallTimer(plugin));
        this.reloadTimerData();
    }

    public void registerTimer(Timer timer) {
        this.timers.add(timer);
        if (timer instanceof Listener) {
            this.plugin.getServer().getPluginManager().registerEvents((Listener) timer, this.plugin);
        }
    }

    public void unregisterTimer(Timer timer) {
        this.timers.remove(timer);
    }

    /**
     * Reloads the {@link Timer} data from storage.
     */
    public void reloadTimerData() {
        this.config = new Config(plugin, "timers");
        for (Timer timer : this.timers) {
            timer.load(this.config);
        }
    }

    /**
     * Saves the {@link Timer} data to storage.
     */
    public void saveTimerData() {
        for (Timer timer : this.timers) {
            timer.onDisable(this.config);
        }

        this.config.save();
    }

    public EventTimer getEventTimer() {
        return eventTimer;
    }

    public PvPTimerProtection getInvincibilityTimer() {
        return invincibilityTimer;
    }

    public SpawnTagTimer getCombatTimer() {
        return combatTimer;
    }

    public ArcherTimer getArcherTimer(){
    	return archerTimer;
    }
    
    
    public EnderPearlTimer getEnderPearlTimer() {
        return enderPearlTimer;
    }
    
    public GappleTimer getGappleTimer() {
        return gappleTimer;
    }

    public PvpClassWarmupTimer getPvpClassWarmupTimer() {
        return pvpClassWarmupTimer;
    }

    public AppleTimer getAppleTimer() {
    		return appleTimer;
    }
    
    public TeleportTimer getTeleportTimer() {
        return teleportTimer;
    }

    public StuckTimer getStuckTimer() {
        return stuckTimer;
    }

    public LogoutTimer getLogoutTimer() {
        return logoutTimer;
    }

    public Collection<Timer> getTimers() {
        return this.timers;
    }

}
