package com.hcfactions.hcf;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcfactions.hcf.config.PlayerData;
import com.hcfactions.hcf.config.PotionLimiterData;
import com.hcfactions.hcf.config.WorldData;
import com.hcfactions.hcf.factions.balance.EconomyCommand;
import com.hcfactions.hcf.factions.balance.EconomyManager;
import com.hcfactions.hcf.factions.balance.FlatFileEconomyManager;
import com.hcfactions.hcf.factions.balance.PayCommand;
import com.hcfactions.hcf.factions.balance.ShopSignListener;
import com.hcfactions.hcf.factions.classes.PvpClassManager;
import com.hcfactions.hcf.factions.classes.archer.ArcherClass;
import com.hcfactions.hcf.factions.classes.bard.BardRestorer;
import com.hcfactions.hcf.factions.classes.type.RogueClass;
import com.hcfactions.hcf.factions.combatlog.CombatLogListener;
import com.hcfactions.hcf.factions.combatlog.CustomEntityRegistration;
import com.hcfactions.hcf.factions.deathban.Deathban;
import com.hcfactions.hcf.factions.deathban.DeathbanListener;
import com.hcfactions.hcf.factions.deathban.DeathbanManager;
import com.hcfactions.hcf.factions.deathban.FlatFileDeathbanManager;
import com.hcfactions.hcf.factions.deathban.lives.LivesExecutor;
import com.hcfactions.hcf.factions.deathban.lives.StaffReviveCommand;
import com.hcfactions.hcf.factions.event.CaptureZone;
import com.hcfactions.hcf.factions.event.EventExecutor;
import com.hcfactions.hcf.factions.event.EventScheduler;
import com.hcfactions.hcf.factions.event.conquest.ConquestExecutor;
import com.hcfactions.hcf.factions.event.eotw.EOTWHandler;
import com.hcfactions.hcf.factions.event.eotw.EotwCommand;
import com.hcfactions.hcf.factions.event.eotw.EotwListener;
import com.hcfactions.hcf.factions.event.faction.CapturableFaction;
import com.hcfactions.hcf.factions.event.faction.ConquestFaction;
import com.hcfactions.hcf.factions.event.faction.KothFaction;
import com.hcfactions.hcf.factions.event.glmountain.GlowstoneMountain;
import com.hcfactions.hcf.factions.event.koth.KothExecutor;
import com.hcfactions.hcf.factions.faction.FactionExecutor;
import com.hcfactions.hcf.factions.faction.FactionManager;
import com.hcfactions.hcf.factions.faction.FactionMember;
import com.hcfactions.hcf.factions.faction.FlatFileFactionManager;
import com.hcfactions.hcf.factions.faction.claim.Claim;
import com.hcfactions.hcf.factions.faction.claim.ClaimHandler;
import com.hcfactions.hcf.factions.faction.claim.ClaimWandListener;
import com.hcfactions.hcf.factions.faction.claim.Subclaim;
import com.hcfactions.hcf.factions.faction.type.ClaimableFaction;
import com.hcfactions.hcf.factions.faction.type.EndPortalFaction;
import com.hcfactions.hcf.factions.faction.type.Faction;
import com.hcfactions.hcf.factions.faction.type.GlowstoneFaction;
import com.hcfactions.hcf.factions.faction.type.PlayerFaction;
import com.hcfactions.hcf.factions.faction.type.RoadFaction;
import com.hcfactions.hcf.factions.faction.type.SpawnFaction;
import com.hcfactions.hcf.factions.reboot.RebootCommand;
import com.hcfactions.hcf.factions.reboot.RebootListener;
import com.hcfactions.hcf.factions.sale.SaleCommand;
import com.hcfactions.hcf.factions.sale.SaleListener;
import com.hcfactions.hcf.factions.sotw.SotwCommand;
import com.hcfactions.hcf.factions.sotw.SotwListener;
import com.hcfactions.hcf.factions.sotw.SotwTimer;
import com.hcfactions.hcf.factions.stattracker.OreTrackerListener;
import com.hcfactions.hcf.factions.stattracker.StatTrackListener;
import com.hcfactions.hcf.helpers.commands.BroadcastCommand;
import com.hcfactions.hcf.helpers.commands.ClearChatCommand;
import com.hcfactions.hcf.helpers.commands.ClearCommand;
import com.hcfactions.hcf.helpers.commands.CobbleCommand;
import com.hcfactions.hcf.helpers.commands.CoordsCommand;
import com.hcfactions.hcf.helpers.commands.CraftCommand;
import com.hcfactions.hcf.helpers.commands.CrowbarCommand;
import com.hcfactions.hcf.helpers.commands.EnchantCommand;
import com.hcfactions.hcf.helpers.commands.EndPortalCommand;
import com.hcfactions.hcf.helpers.commands.FFACommand;
import com.hcfactions.hcf.helpers.commands.FamousCommand;
import com.hcfactions.hcf.helpers.commands.FeedCommand;
import com.hcfactions.hcf.helpers.commands.FixCommand;
import com.hcfactions.hcf.helpers.commands.FlyCommand;
import com.hcfactions.hcf.helpers.commands.FreezeCommand;
import com.hcfactions.hcf.helpers.commands.GMCCommand;
import com.hcfactions.hcf.helpers.commands.GMSCommand;
import com.hcfactions.hcf.helpers.commands.GameModeCommand;
import com.hcfactions.hcf.helpers.commands.GiveCommand;
import com.hcfactions.hcf.helpers.commands.GodCommand;
import com.hcfactions.hcf.helpers.commands.GoppleCommand;
import com.hcfactions.hcf.helpers.commands.HatCommand;
import com.hcfactions.hcf.helpers.commands.HealCommand;
import com.hcfactions.hcf.helpers.commands.HelpCommand;
import com.hcfactions.hcf.helpers.commands.HelpopCommand;
import com.hcfactions.hcf.helpers.commands.InvSeeCommand;
import com.hcfactions.hcf.helpers.commands.ItemCommand;
import com.hcfactions.hcf.helpers.commands.KillCommand;
import com.hcfactions.hcf.helpers.commands.LFACommand;
import com.hcfactions.hcf.helpers.commands.LFFCommand;
import com.hcfactions.hcf.helpers.commands.LagCommand;
import com.hcfactions.hcf.helpers.commands.ListCommand;
import com.hcfactions.hcf.helpers.commands.LockdownCommand;
import com.hcfactions.hcf.helpers.commands.LogoutCommand;
import com.hcfactions.hcf.helpers.commands.MapKitCommand;
import com.hcfactions.hcf.helpers.commands.MediaChatCommand;
import com.hcfactions.hcf.helpers.commands.MessageCommand;
import com.hcfactions.hcf.helpers.commands.MiscCommands;
import com.hcfactions.hcf.helpers.commands.MoreCommand;
import com.hcfactions.hcf.helpers.commands.OreStatsCommand;
import com.hcfactions.hcf.helpers.commands.PanicCommand;
import com.hcfactions.hcf.helpers.commands.PartnerCommand;
import com.hcfactions.hcf.helpers.commands.PingCommand;
import com.hcfactions.hcf.helpers.commands.PlayTimeCommand;
import com.hcfactions.hcf.helpers.commands.PlayerVaultCommand;
import com.hcfactions.hcf.helpers.commands.PvpTimerCommand;
import com.hcfactions.hcf.helpers.commands.RandomCommand;
import com.hcfactions.hcf.helpers.commands.RefundCommand;
import com.hcfactions.hcf.helpers.commands.RenameCommand;
import com.hcfactions.hcf.helpers.commands.ReplyCommand;
import com.hcfactions.hcf.helpers.commands.ReportCommand;
import com.hcfactions.hcf.helpers.commands.ResetCommand;
import com.hcfactions.hcf.helpers.commands.SetBorderCommand;
import com.hcfactions.hcf.helpers.commands.SetCommand;
import com.hcfactions.hcf.helpers.commands.SetKBCommand;
import com.hcfactions.hcf.helpers.commands.SkullCommand;
import com.hcfactions.hcf.helpers.commands.SlowChatCommand;
import com.hcfactions.hcf.helpers.commands.SpawnCommand;
import com.hcfactions.hcf.helpers.commands.SpawnerCommand;
import com.hcfactions.hcf.helpers.commands.StaffChatCommand;
import com.hcfactions.hcf.helpers.commands.StaffModeCommand;
import com.hcfactions.hcf.helpers.commands.StatsCommand;
import com.hcfactions.hcf.helpers.commands.SudoCommand;
import com.hcfactions.hcf.helpers.commands.TLCommand;
import com.hcfactions.hcf.helpers.commands.TeamspeakCommand;
import com.hcfactions.hcf.helpers.commands.TeleportAllCommand;
import com.hcfactions.hcf.helpers.commands.TeleportCommand;
import com.hcfactions.hcf.helpers.commands.TeleportHereCommand;
import com.hcfactions.hcf.helpers.commands.TicksCommand;
import com.hcfactions.hcf.helpers.commands.ToggleMessageCommand;
import com.hcfactions.hcf.helpers.commands.TopCommand;
import com.hcfactions.hcf.helpers.commands.VanishCommand;
import com.hcfactions.hcf.helpers.commands.WorldCommand;
import com.hcfactions.hcf.helpers.commands.YoutubeCommand;
import com.hcfactions.hcf.helpers.listener.AutoSmeltOreListener;
import com.hcfactions.hcf.helpers.listener.BookDeenchantListener;
import com.hcfactions.hcf.helpers.listener.BorderListener;
import com.hcfactions.hcf.helpers.listener.BottledExpListener;
import com.hcfactions.hcf.helpers.listener.ChatListener;
import com.hcfactions.hcf.helpers.listener.CoreListener;
import com.hcfactions.hcf.helpers.listener.CrowbarListener;
import com.hcfactions.hcf.helpers.listener.DeathListener;
import com.hcfactions.hcf.helpers.listener.DeathMessageListener;
import com.hcfactions.hcf.helpers.listener.ElevatorListener;
import com.hcfactions.hcf.helpers.listener.EnderPearlFix;
import com.hcfactions.hcf.helpers.listener.EntityLimitListener;
import com.hcfactions.hcf.helpers.listener.ExpMultiplierListener;
import com.hcfactions.hcf.helpers.listener.FactionListener;
import com.hcfactions.hcf.helpers.listener.FactionsCoreListener;
import com.hcfactions.hcf.helpers.listener.FoundDiamondsListener;
import com.hcfactions.hcf.helpers.listener.FurnaceSmeltSpeederListener;
import com.hcfactions.hcf.helpers.listener.GodListener;
import com.hcfactions.hcf.helpers.listener.KitMapListener;
import com.hcfactions.hcf.helpers.listener.LoginEvent;
import com.hcfactions.hcf.helpers.listener.MediaChatListener;
import com.hcfactions.hcf.helpers.listener.OreCountListener;
import com.hcfactions.hcf.helpers.listener.PearlGlitchListener;
import com.hcfactions.hcf.helpers.listener.PlayTimeManager;
import com.hcfactions.hcf.helpers.listener.PotEffectUHC;
import com.hcfactions.hcf.helpers.listener.PotionLimitListener;
import com.hcfactions.hcf.helpers.listener.SignSubclaimListener;
import com.hcfactions.hcf.helpers.listener.SkullListener;
import com.hcfactions.hcf.helpers.listener.StaffChatListener;
import com.hcfactions.hcf.helpers.listener.StaffModeListener;
import com.hcfactions.hcf.helpers.listener.UnRepairableListener;
import com.hcfactions.hcf.helpers.listener.VanishListener;
import com.hcfactions.hcf.helpers.listener.WorldListener;
import com.hcfactions.hcf.helpers.listener.fixes.ArmorFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.BeaconStrengthFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.BlockHitFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.BlockJumpGlitchFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.BoatGlitchFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.BookQuillFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.CommandBlocker;
import com.hcfactions.hcf.helpers.listener.fixes.DupeGlitchFix;
import com.hcfactions.hcf.helpers.listener.fixes.EnchantLimitListener;
import com.hcfactions.hcf.helpers.listener.fixes.EnderChestRemovalListener;
import com.hcfactions.hcf.helpers.listener.fixes.HungerFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.InfinityArrowFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.NaturalMobSpawnFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.PexCrashFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.PortalListener;
import com.hcfactions.hcf.helpers.listener.fixes.StrengthListener;
import com.hcfactions.hcf.helpers.listener.fixes.SyntaxBlocker;
import com.hcfactions.hcf.helpers.listener.fixes.VoidGlitchFixListener;
import com.hcfactions.hcf.helpers.listener.fixes.WeatherFixListener;
import com.hcfactions.hcf.helpers.reclaim.ReclaimManager;
import com.hcfactions.hcf.helpers.scoreboard.ScoreboardHandler;
import com.hcfactions.hcf.helpers.tablist.TablistManager;
import com.hcfactions.hcf.helpers.tablist.tablist.TablistAdapter;
import com.hcfactions.hcf.helpers.timer.TimerExecutor;
import com.hcfactions.hcf.helpers.timer.TimerManager;
import com.hcfactions.hcf.special.Christmas.ChristmasCommand;
import com.hcfactions.hcf.special.KING.KingCommand;
import com.hcfactions.hcf.special.KING.KingListener;
import com.hcfactions.hcf.special.Thanksgiving.ThanksGivingCommand;
import com.hcfactions.hcf.special.inventory.implementation.ClaimSettingsInventory;
import com.hcfactions.hcf.special.keyall.KeyallCommand;
import com.hcfactions.hcf.special.signs.EventSignListener;
import com.hcfactions.hcf.special.signs.KitSignListener;
import com.hcfactions.hcf.user.ConsoleUser;
import com.hcfactions.hcf.user.FactionUser;
import com.hcfactions.hcf.user.UserManager;
import com.hcfactions.hcf.util.SignHandler;
import com.hcfactions.hcf.util.base.BasePlugins;
import com.hcfactions.hcf.util.base.Cooldowns;
import com.hcfactions.hcf.util.base.Message;
import com.hcfactions.hcf.util.base.ServerHandler;
import com.hcfactions.hcf.util.itemdb.ItemDb;
import com.hcfactions.hcf.util.itemdb.SimpleItemDb;
import com.hcfactions.hcf.visualise.ProtocolLibHook;
import com.hcfactions.hcf.visualise.VisualiseHandler;
import com.hcfactions.hcf.visualise.WallBorderListener;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.util.com.google.common.base.Joiner;

public class Base extends JavaPlugin implements CommandExecutor {

	private CombatLogListener combatLogListener;
	
	public CombatLogListener getCombatLogListener() {
		return this.combatLogListener;
	}
		
	@Getter
	private Chat chat;
	
	@Getter
	private ClaimSettingsInventory claimSettings;
	
	public void onEnable() {
		plugin = this;

		BasePlugins.getPlugin().init(this);
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		conf = new File(getDataFolder(), "config.yml");
		WorldData.getInstance().setup(this);
		PlayerData.getInstance().setup(this);
		PotionLimiterData.getInstance().setup(this);
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.GREEN.toString() + ChatColor.ITALIC + "uHCF is now enabled.");
		ProtocolLibHook.hook(this);
		CustomEntityRegistration.registerCustomEntities();
		Plugin wep = Bukkit.getPluginManager().getPlugin("WorldEdit");
		this.craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		this.worldEdit = (((wep instanceof WorldEditPlugin)) && (wep.isEnabled()) ? (WorldEditPlugin) wep : null);
		
		registerConfiguration();
		if (!new HwidProtection(Base.config.getString("HWID"), "http://bonjour3254.000webhostapp.com/verify.php", this).register())
        return;
		registerCommands();
		registerManagers();
		registerListeners();
		this.claimSettings = new ClaimSettingsInventory(this);
		if ((ConfigurationService.TAB_LIST) == true) {         
			new TablistManager(this, new TablistAdapter(this), TimeUnit.SECONDS.toMillis((long) 1.0));
		}
		Cooldowns.createCooldown("report_cooldown");
		Cooldowns.createCooldown("helpop_cooldown");
		Cooldowns.createCooldown("archer_speed_cooldown");
		Cooldowns.createCooldown("archer_jump_cooldown");
		Cooldowns.createCooldown("rogue_speed_cooldown");
		Cooldowns.createCooldown("rogue_jump_cooldown");
		Cooldowns.createCooldown("rogue_cooldown");
		Cooldowns.createCooldown("lff_cooldown");
		Cooldowns.createCooldown("lfa_cooldown");
		
		new BukkitRunnable() {
			public void run() {
				Base.this.saveData();
				getServer().broadcastMessage(ChatColor.GREEN + "Saving data...");
			}
		}.runTaskTimerAsynchronously(this, TimeUnit.SECONDS.toMillis(20L), TimeUnit.SECONDS.toMillis(20L));
		
	    new BukkitRunnable() {

	        @Override
	        public void run() {
	          @SuppressWarnings("deprecation")
			String players = Arrays.stream(Bukkit.getOnlinePlayers())
	              .filter(player -> player.hasPermission("top.rank") && !player.isOp() && !player.hasPermission("*"))
					.map(Player::getName)
					.collect(Collectors.joining(", "));

	          if (players.isEmpty()) {
	        	  players = "None";
	          } 
	          
				for (String onlinedonator : Base.config.getStringList("Online-Donator-Broadcast")) {
					getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', onlinedonator).replace("%player%", players)); }

	        }
	      }.runTaskTimer(this, 60L, 20 * 60 * 5);
	    }
	
	private void saveData() {
		this.combatLogListener.removeCombatLoggers();
		this.deathbanManager.saveDeathbanData();
		this.economyManager.saveEconomyData();
		this.factionManager.saveFactionData();
		this.playTimeManager.savePlaytimeData();
		this.userManager.saveUserData();
		this.signHandler.cancelTasks(null);

		PlayerData.getInstance().saveConfig();
	}

	public void onDisable() {
		this.pvpClassManager.onDisable();
		this.scoreboardHandler.clearBoards();
		this.deathbanManager.saveDeathbanData();
		this.economyManager.saveEconomyData();
		this.factionManager.saveFactionData();
		this.playTimeManager.savePlaytimeData();
		this.userManager.saveUserData();
		StaffModeCommand.onDisableMod();
		saveData();
		plugin = null;
	}

	private void registerConfiguration() {
		ConfigurationSerialization.registerClass(CaptureZone.class);
		ConfigurationSerialization.registerClass(Deathban.class);
		ConfigurationSerialization.registerClass(Claim.class);
		ConfigurationSerialization.registerClass(ConsoleUser.class);
		ConfigurationSerialization.registerClass(Subclaim.class);
		ConfigurationSerialization.registerClass(FactionUser.class);
		ConfigurationSerialization.registerClass(ClaimableFaction.class);
		ConfigurationSerialization.registerClass(ConquestFaction.class);
		ConfigurationSerialization.registerClass(CapturableFaction.class);
		ConfigurationSerialization.registerClass(KothFaction.class);
		ConfigurationSerialization.registerClass(GlowstoneFaction.class);
		ConfigurationSerialization.registerClass(EndPortalFaction.class);
		ConfigurationSerialization.registerClass(Faction.class);
		ConfigurationSerialization.registerClass(FactionMember.class);
		ConfigurationSerialization.registerClass(PlayerFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.class);
		ConfigurationSerialization.registerClass(SpawnFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.NorthRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.EastRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.SouthRoadFaction.class);
		ConfigurationSerialization.registerClass(RoadFaction.WestRoadFaction.class);
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new OreStatsCommand(), this);
		manager.registerEvents(new GodListener(), this);
		manager.registerEvents(new VanishListener(), this);
		manager.registerEvents(new ArcherClass(this), this);
		manager.registerEvents(new RogueClass(this), this);
		manager.registerEvents(new PotionLimitListener(this), this);
		manager.registerEvents(new LoginEvent(), this);
		manager.registerEvents(new DupeGlitchFix(), this);
		manager.registerEvents(new PortalListener(this), this);
		manager.registerEvents(new WeatherFixListener(), this);
		manager.registerEvents(this.combatLogListener = new CombatLogListener(this), this);
		manager.registerEvents(new NaturalMobSpawnFixListener(), this);
		manager.registerEvents(new AutoSmeltOreListener(), this);
		manager.registerEvents(new BlockHitFixListener(), this);
		manager.registerEvents(new BlockJumpGlitchFixListener(), this);
		manager.registerEvents(new CommandBlocker(), this);
		manager.registerEvents(new BoatGlitchFixListener(), this);
		manager.registerEvents(new PotEffectUHC(), this);
		manager.registerEvents(new BookDeenchantListener(), this);
		manager.registerEvents(new PexCrashFixListener(this), this);
		manager.registerEvents(new BookQuillFixListener(this), this);
		manager.registerEvents(new BorderListener(), this);
		manager.registerEvents(new KingListener(), this);
		manager.registerEvents(new ChatListener(this), this);
		manager.registerEvents(new StaffChatListener(), this);
		manager.registerEvents(new ClaimWandListener(this), this);
		manager.registerEvents(new BottledExpListener(), this);
		manager.registerEvents(new CoreListener(this), this);
		manager.registerEvents(new CrowbarListener(this), this);
		manager.registerEvents(new DeathListener(this), this);
		manager.registerEvents(new ElevatorListener(this), this);
		manager.registerEvents(new DeathMessageListener(this), this);
	if (ConfigurationService.KIT_MAP == false) {
		
	  manager.registerEvents(new DeathbanListener(this), this);
		
		}
		manager.registerEvents(new EnchantLimitListener(), this);
		manager.registerEvents(new EnderChestRemovalListener(), this);
		manager.registerEvents(new FlatFileFactionManager(this), this);
		manager.registerEvents(new MediaChatListener(), this);
		manager.registerEvents(new StrengthListener(), this);
		manager.registerEvents(new ArmorFixListener(), this);
		manager.registerEvents(new EotwListener(this), this);
		manager.registerEvents(new EventSignListener(), this);
		manager.registerEvents(new ExpMultiplierListener(), this);
		manager.registerEvents(new FactionListener(this), this);
		manager.registerEvents(new FoundDiamondsListener(this), this);
		manager.registerEvents(new FurnaceSmeltSpeederListener(), this);
		manager.registerEvents(new KitMapListener(this), this);
		manager.registerEvents(new InfinityArrowFixListener(), this);
		manager.registerEvents(new HungerFixListener(), this);
		manager.registerEvents(new PearlGlitchListener(this), this);
		manager.registerEvents(new FactionsCoreListener(this), this);
		manager.registerEvents(new PearlGlitchListener(this), this);
		manager.registerEvents(new EnderPearlFix(this), this);
		manager.registerEvents(new SignSubclaimListener(this), this);
		manager.registerEvents(new CobbleCommand(), this);
		manager.registerEvents(new EndPortalCommand(getPlugin()), this);
		manager.registerEvents(new ShopSignListener(this), this);
		manager.registerEvents(new SkullListener(), this);
		manager.registerEvents(new BeaconStrengthFixListener(this), this);
		manager.registerEvents(new VoidGlitchFixListener(), this);
		manager.registerEvents(new WallBorderListener(this), this);
		manager.registerEvents(this.playTimeManager, this);
		manager.registerEvents(new WorldListener(this), this);
		manager.registerEvents(new UnRepairableListener(), this);
		manager.registerEvents(new StaffModeListener(), this);
		manager.registerEvents(new SyntaxBlocker(), this);
		manager.registerEvents(new OreTrackerListener(), this);
		manager.registerEvents(new OreCountListener(this), this);
		manager.registerEvents(new SotwListener(this), this);
		manager.registerEvents(new KitSignListener(), this);
		manager.registerEvents(new RebootListener(), this);
		manager.registerEvents(new SaleListener(), this);
		manager.registerEvents(new EntityLimitListener(this), this);
		manager.registerEvents(new StatTrackListener(), this);
	}

	private void registerCommands() {

		getCommand("top").setExecutor(new TopCommand());
		getCommand("list").setExecutor(new ListCommand());
		getCommand("setborder").setExecutor(new SetBorderCommand());
		getCommand("hat").setExecutor(new HatCommand());
		getCommand("world").setExecutor(new WorldCommand());
		getCommand("endportal").setExecutor(new EndPortalCommand(getPlugin()));
		getCommand("fix").setExecutor(new FixCommand());
		getCommand("setkb").setExecutor(new SetKBCommand());
		getCommand("enchant").setExecutor(new EnchantCommand());
		getCommand("freeze").setExecutor(new FreezeCommand(this));
		getCommand("staffrevive").setExecutor(new StaffReviveCommand(this));
		getCommand("lag").setExecutor(new LagCommand());
		getCommand("broadcast").setExecutor(new BroadcastCommand());
		getCommand("togglemessage").setExecutor(new ToggleMessageCommand());
		getCommand("reply").setExecutor(new ReplyCommand());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		getCommand("pv").setExecutor(new PlayerVaultCommand(this));
		getCommand("setspawn").setExecutor(new SpawnCommand());
		getCommand("ping").setExecutor(new PingCommand());
		getCommand("togglemessage").setExecutor(new ToggleMessageCommand());
		getCommand("teleportall").setExecutor(new TeleportAllCommand());
		getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		getCommand("give").setExecutor(new GiveCommand());
		getCommand("gamemode").setExecutor(new GameModeCommand());
		getCommand("item").setExecutor(new ItemCommand());
		getCommand("lockdown").setExecutor(new LockdownCommand(this));
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("invsee").setExecutor(new InvSeeCommand(this));
		getCommand("god").setExecutor(new GodCommand());
		getCommand("gms").setExecutor(new GMSCommand());
		getCommand("gmc").setExecutor(new GMCCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("sotw").setExecutor(new SotwCommand(this));
		getCommand("random").setExecutor(new RandomCommand(this));
		getCommand("conquest").setExecutor(new ConquestExecutor(this));
		getCommand("crowbar").setExecutor(new CrowbarCommand());
		getCommand("economy").setExecutor(new EconomyCommand(this));
		getCommand("eotw").setExecutor(new EotwCommand(this));
		getCommand("event").setExecutor(new EventExecutor(this));
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("helpop").setExecutor(new HelpopCommand());
		getCommand("faction").setExecutor(new FactionExecutor(this));
		getCommand("playtime").setExecutor(new PlayTimeCommand(this));
		getCommand("gopple").setExecutor(new GoppleCommand(this));
		getCommand("cobble").setExecutor(new CobbleCommand());
		getCommand("koth").setExecutor(new KothExecutor(this));
		getCommand("lives").setExecutor(new LivesExecutor(this));
		getCommand("logout").setExecutor(new LogoutCommand(this));
		getCommand("more").setExecutor(new MoreCommand());
		getCommand("panic").setExecutor(new PanicCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("pay").setExecutor(new PayCommand(this));
		getCommand("pvptimer").setExecutor(new PvpTimerCommand(this));
		getCommand("LFF").setExecutor(new LFFCommand(this));
		getCommand("LFA").setExecutor(new LFACommand(this));
		getCommand("refund").setExecutor(new RefundCommand());
		getCommand("staffchat").setExecutor(new StaffChatCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("FFA").setExecutor(new FFACommand());
		getCommand("timer").setExecutor(new TimerExecutor(this));
		getCommand("kill").setExecutor(new KillCommand());
		getCommand("ores").setExecutor(new OreStatsCommand());
		getCommand("help").setExecutor(new HelpCommand());
		getCommand("rename").setExecutor(new RenameCommand());
		getCommand("teamspeak").setExecutor(new TeamspeakCommand());
		getCommand("coords").setExecutor(new CoordsCommand());
		getCommand("kingevent").setExecutor(new KingCommand(this));
		getCommand("fsay").setExecutor(new MiscCommands());
		getCommand("mapkit").setExecutor(new MapKitCommand(this));
		getCommand("staffmode").setExecutor(new StaffModeCommand());
		getCommand("spawner").setExecutor(new SpawnerCommand());
		getCommand("set").setExecutor(new SetCommand(this));
		getCommand("ci").setExecutor(new ClearCommand());
		getCommand("mediachat").setExecutor(new MediaChatCommand());
		getCommand("copyinv").setExecutor(new MiscCommands());
		getCommand("teleport").setExecutor(new TeleportCommand());
		getCommand("clearchat").setExecutor(new ClearChatCommand());
		getCommand("skull").setExecutor(new SkullCommand());
		getCommand("craft").setExecutor(new CraftCommand());
		getCommand("reset").setExecutor(new ResetCommand());
		getCommand("sudo").setExecutor(new SudoCommand());
		getCommand("tl").setExecutor(new TLCommand());
		getCommand("reboot").setExecutor(new RebootCommand(plugin));
		getCommand("sale").setExecutor(new SaleCommand(plugin));
		getCommand("thanksgiving").setExecutor(new ThanksGivingCommand(plugin));
		getCommand("keyall").setExecutor(new KeyallCommand(plugin));
		getCommand("christmas").setExecutor(new ChristmasCommand(plugin));
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("glowstone").setExecutor(new GlowstoneMountain(this));
		getCommand("youtube").setExecutor(new YoutubeCommand());
		getCommand("partner").setExecutor(new PartnerCommand());
		getCommand("famous").setExecutor(new FamousCommand());
		getCommand("ticks").setExecutor(new TicksCommand());
		getCommand("slowchat").setExecutor(new SlowChatCommand(this));

		Map<String, Map<String, Object>> map = getDescription().getCommands();
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
			PluginCommand command = getCommand((String) entry.getKey());
			command.setPermission("command." + (String) entry.getKey());
			command.setPermissionMessage(ChatColor.RED.toString() + "You do not have permission to use this command.");
		}
	}

	private void registerManagers() {
		this.claimHandler = new ClaimHandler(this);
		this.deathbanManager = new FlatFileDeathbanManager(this);
		this.economyManager = new FlatFileEconomyManager(this);
		this.eotwHandler = new EOTWHandler(this);
		this.eventScheduler = new EventScheduler(this);
		this.factionManager = new FlatFileFactionManager(this);
		this.itemDb = new SimpleItemDb(this);
		this.playTimeManager = new PlayTimeManager(this);
		this.pvpClassManager = new PvpClassManager(this);
		this.timerManager = new TimerManager(this);
		this.scoreboardHandler = new ScoreboardHandler(this);
		this.userManager = new UserManager(this);
		this.visualiseHandler = new VisualiseHandler();
		this.sotwTimer = new SotwTimer();
		this.message = new Message(this);
		this.signHandler = new SignHandler(this);
		this.reclaimManager = new ReclaimManager(this);
		new BardRestorer(this);
	}

	public Message getMessage() {
		return this.message;
	}

	public ItemDb getItemDb() {
		return this.itemDb;
	}

	public Random getRandom() {
		return this.random;
	}

	public PlayTimeManager getPlayTimeManager() {
		return this.playTimeManager;
	}

	public WorldEditPlugin getWorldEdit() {
		return this.worldEdit;
	}

	public ClaimHandler getClaimHandler() {
		return this.claimHandler;
	}

	public SotwTimer getSotwTimer() {
		return this.sotwTimer;
	}

	public SignHandler getSignHandler() {
		return this.signHandler;
	}

	public ConfigurationService getConfiguration() {
		return this.configuration;
	}

	public DeathbanManager getDeathbanManager() {
		return this.deathbanManager;
	}

	public VanishListener getVanish() {
		return this.vanish;
	}

	public EconomyManager getEconomyManager() {
		return this.economyManager;
	}

	public EOTWHandler getEotwHandler() {
		return this.eotwHandler;
	}

	public FactionManager getFactionManager() {
		return this.factionManager;
	}

	public PvpClassManager getPvpClassManager() {
		return this.pvpClassManager;
	}

	public ScoreboardHandler getScoreboardHandler() {
		return this.scoreboardHandler;
	}

	public TimerManager getTimerManager() {
		return this.timerManager;
	}

	public UserManager getUserManager() {
		return this.userManager;
	}

	public VisualiseHandler getVisualiseHandler() {
		return this.visualiseHandler;
	}

	public Base() {
		this.random = new Random();
	}

	public ServerHandler getServerHandler() {
		return this.serverHandler;
	}

	public static Base getPlugin() {
		return plugin;
	}

	public static Base getInstance() {
		return instance;
	}

	public static String getReaming(long millis) {
		return getRemaining(millis, true, true);
	}

	public String getCraftBukkitVersion() {
		return this.craftBukkitVersion;
	}

	public static String getRemaining(long millis, boolean milliseconds) {
		return getRemaining(millis, milliseconds, true);
	}

	public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
		if ((milliseconds) && (duration < MINUTE)) {
			return DurationFormatUtils.formatDuration(duration, (duration > HOUR ? "HH:" : "") + "mm:ss");
		}
		return DurationFormatUtils.formatDuration(duration, (duration >= HOUR ? "HH:" : "") + "mm:ss");
	}

	public static File conf;
	public static FileConfiguration config;
	private String craftBukkitVersion;
	public static Base instance;
	private ConfigurationService configuration;
	private static final long MINUTE = TimeUnit.MINUTES.toMillis(1L);
	private static final long HOUR = TimeUnit.HOURS.toMillis(1L);
	private static Base plugin;
	public static Plugin pl;
	private ServerHandler serverHandler;
	public BukkitRunnable clearEntityHandler;
	public BukkitRunnable announcementTask;
	private Message message;
	@Getter private ReclaimManager reclaimManager;

	public EventScheduler eventScheduler;
	public static final Joiner SPACE_JOINER = Joiner.on(' ');
	public static final Joiner COMMA_JOINER = Joiner.on(", ");
	private Random random;
	private PlayTimeManager playTimeManager;
	private WorldEditPlugin worldEdit;
	private ClaimHandler claimHandler;
	private ItemDb itemDb;

	private DeathbanManager deathbanManager;
	private EconomyManager economyManager;
	private EOTWHandler eotwHandler;
	private FactionManager factionManager;
	private PvpClassManager pvpClassManager;
	private VanishListener vanish;
	private ScoreboardHandler scoreboardHandler;
	private SotwTimer sotwTimer;
	private TimerManager timerManager;
	private UserManager userManager;
	private VisualiseHandler visualiseHandler;
	private SignHandler signHandler;

}
