package cc.zanehq.development;

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

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import cc.zanehq.development.config.PlayerData;
import cc.zanehq.development.config.WorldData;
import cc.zanehq.development.factions.balance.EconomyCommand;
import cc.zanehq.development.factions.balance.EconomyManager;
import cc.zanehq.development.factions.balance.FlatFileEconomyManager;
import cc.zanehq.development.factions.balance.PayCommand;
import cc.zanehq.development.factions.balance.ShopSignListener;
import cc.zanehq.development.factions.classes.PvpClassManager;
import cc.zanehq.development.factions.classes.archer.ArcherClass;
import cc.zanehq.development.factions.classes.bard.BardRestorer;
import cc.zanehq.development.factions.classes.type.RogueClass;
import cc.zanehq.development.factions.combatlog.CombatLogListener;
import cc.zanehq.development.factions.combatlog.CustomEntityRegistration;
import cc.zanehq.development.factions.deathban.Deathban;
import cc.zanehq.development.factions.deathban.DeathbanListener;
import cc.zanehq.development.factions.deathban.DeathbanManager;
import cc.zanehq.development.factions.deathban.FlatFileDeathbanManager;
import cc.zanehq.development.factions.deathban.lives.LivesExecutor;
import cc.zanehq.development.factions.deathban.lives.StaffReviveCommand;
import cc.zanehq.development.factions.event.CaptureZone;
import cc.zanehq.development.factions.event.EventExecutor;
import cc.zanehq.development.factions.event.EventScheduler;
import cc.zanehq.development.factions.event.conquest.ConquestExecutor;
import cc.zanehq.development.factions.event.eotw.EOTWHandler;
import cc.zanehq.development.factions.event.eotw.EotwCommand;
import cc.zanehq.development.factions.event.eotw.EotwListener;
import cc.zanehq.development.factions.event.faction.CapturableFaction;
import cc.zanehq.development.factions.event.faction.ConquestFaction;
import cc.zanehq.development.factions.event.faction.KothFaction;
import cc.zanehq.development.factions.event.glmountain.GlowstoneMountain;
import cc.zanehq.development.factions.event.koth.KothExecutor;
import cc.zanehq.development.factions.faction.FactionExecutor;
import cc.zanehq.development.factions.faction.FactionManager;
import cc.zanehq.development.factions.faction.FactionMember;
import cc.zanehq.development.factions.faction.FlatFileFactionManager;
import cc.zanehq.development.factions.faction.claim.Claim;
import cc.zanehq.development.factions.faction.claim.ClaimHandler;
import cc.zanehq.development.factions.faction.claim.ClaimWandListener;
import cc.zanehq.development.factions.faction.claim.Subclaim;
import cc.zanehq.development.factions.faction.type.ClaimableFaction;
import cc.zanehq.development.factions.faction.type.EndPortalFaction;
import cc.zanehq.development.factions.faction.type.Faction;
import cc.zanehq.development.factions.faction.type.GlowstoneFaction;
import cc.zanehq.development.factions.faction.type.PlayerFaction;
import cc.zanehq.development.factions.faction.type.RoadFaction;
import cc.zanehq.development.factions.faction.type.SpawnFaction;
import cc.zanehq.development.factions.reboot.RebootCommand;
import cc.zanehq.development.factions.reboot.RebootListener;
import cc.zanehq.development.factions.sale.SaleCommand;
import cc.zanehq.development.factions.sale.SaleListener;
import cc.zanehq.development.factions.sotw.SotwCommand;
import cc.zanehq.development.factions.sotw.SotwListener;
import cc.zanehq.development.factions.sotw.SotwTimer;
import cc.zanehq.development.factions.stattracker.OreTrackerListener;
import cc.zanehq.development.factions.stattracker.StatTrackListener;
import cc.zanehq.development.helpers.commands.BroadcastCommand;
import cc.zanehq.development.helpers.commands.ClearChatCommand;
import cc.zanehq.development.helpers.commands.ClearCommand;
import cc.zanehq.development.helpers.commands.CobbleCommand;
import cc.zanehq.development.helpers.commands.CoordsCommand;
import cc.zanehq.development.helpers.commands.CraftCommand;
import cc.zanehq.development.helpers.commands.CrowbarCommand;
import cc.zanehq.development.helpers.commands.EnchantCommand;
import cc.zanehq.development.helpers.commands.EndPortalCommand;
import cc.zanehq.development.helpers.commands.FFACommand;
import cc.zanehq.development.helpers.commands.FamousCommand;
import cc.zanehq.development.helpers.commands.FeedCommand;
import cc.zanehq.development.helpers.commands.FixCommand;
import cc.zanehq.development.helpers.commands.FlyCommand;
import cc.zanehq.development.helpers.commands.FreezeCommand;
import cc.zanehq.development.helpers.commands.GMCCommand;
import cc.zanehq.development.helpers.commands.GMSCommand;
import cc.zanehq.development.helpers.commands.GameModeCommand;
import cc.zanehq.development.helpers.commands.GiveCommand;
import cc.zanehq.development.helpers.commands.GodCommand;
import cc.zanehq.development.helpers.commands.GoppleCommand;
import cc.zanehq.development.helpers.commands.HatCommand;
import cc.zanehq.development.helpers.commands.HealCommand;
import cc.zanehq.development.helpers.commands.HelpCommand;
import cc.zanehq.development.helpers.commands.HelpopCommand;
import cc.zanehq.development.helpers.commands.InvSeeCommand;
import cc.zanehq.development.helpers.commands.ItemCommand;
import cc.zanehq.development.helpers.commands.KillCommand;
import cc.zanehq.development.helpers.commands.LFACommand;
import cc.zanehq.development.helpers.commands.LFFCommand;
import cc.zanehq.development.helpers.commands.LagCommand;
import cc.zanehq.development.helpers.commands.ListCommand;
import cc.zanehq.development.helpers.commands.LockdownCommand;
import cc.zanehq.development.helpers.commands.LogoutCommand;
import cc.zanehq.development.helpers.commands.MapKitCommand;
import cc.zanehq.development.helpers.commands.MediaChatCommand;
import cc.zanehq.development.helpers.commands.MessageCommand;
import cc.zanehq.development.helpers.commands.MiscCommands;
import cc.zanehq.development.helpers.commands.MoreCommand;
import cc.zanehq.development.helpers.commands.OreStatsCommand;
import cc.zanehq.development.helpers.commands.PanicCommand;
import cc.zanehq.development.helpers.commands.PartnerCommand;
import cc.zanehq.development.helpers.commands.PingCommand;
import cc.zanehq.development.helpers.commands.PlayTimeCommand;
import cc.zanehq.development.helpers.commands.PlayerVaultCommand;
import cc.zanehq.development.helpers.commands.PvpTimerCommand;
import cc.zanehq.development.helpers.commands.RandomCommand;
import cc.zanehq.development.helpers.commands.RefundCommand;
import cc.zanehq.development.helpers.commands.RenameCommand;
import cc.zanehq.development.helpers.commands.ReplyCommand;
import cc.zanehq.development.helpers.commands.ReportCommand;
import cc.zanehq.development.helpers.commands.ResetCommand;
import cc.zanehq.development.helpers.commands.SetBorderCommand;
import cc.zanehq.development.helpers.commands.SetCommand;
import cc.zanehq.development.helpers.commands.SetKBCommand;
import cc.zanehq.development.helpers.commands.SkullCommand;
import cc.zanehq.development.helpers.commands.SlowChatCommand;
import cc.zanehq.development.helpers.commands.SpawnCommand;
import cc.zanehq.development.helpers.commands.SpawnerCommand;
import cc.zanehq.development.helpers.commands.StaffChatCommand;
import cc.zanehq.development.helpers.commands.StaffModeCommand;
import cc.zanehq.development.helpers.commands.StatsCommand;
import cc.zanehq.development.helpers.commands.SudoCommand;
import cc.zanehq.development.helpers.commands.TLCommand;
import cc.zanehq.development.helpers.commands.TeamspeakCommand;
import cc.zanehq.development.helpers.commands.TeleportAllCommand;
import cc.zanehq.development.helpers.commands.TeleportCommand;
import cc.zanehq.development.helpers.commands.TeleportHereCommand;
import cc.zanehq.development.helpers.commands.TicksCommand;
import cc.zanehq.development.helpers.commands.ToggleMessageCommand;
import cc.zanehq.development.helpers.commands.TopCommand;
import cc.zanehq.development.helpers.commands.VanishCommand;
import cc.zanehq.development.helpers.commands.WorldCommand;
import cc.zanehq.development.helpers.commands.YoutubeCommand;
import cc.zanehq.development.helpers.listener.AutoSmeltOreListener;
import cc.zanehq.development.helpers.listener.BookDeenchantListener;
import cc.zanehq.development.helpers.listener.BorderListener;
import cc.zanehq.development.helpers.listener.BottledExpListener;
import cc.zanehq.development.helpers.listener.ChatListener;
import cc.zanehq.development.helpers.listener.CoreListener;
import cc.zanehq.development.helpers.listener.CrowbarListener;
import cc.zanehq.development.helpers.listener.DeathListener;
import cc.zanehq.development.helpers.listener.DeathMessageListener;
import cc.zanehq.development.helpers.listener.ElevatorListener;
import cc.zanehq.development.helpers.listener.EnderPearlFix;
import cc.zanehq.development.helpers.listener.EntityLimitListener;
import cc.zanehq.development.helpers.listener.ExpMultiplierListener;
import cc.zanehq.development.helpers.listener.FactionListener;
import cc.zanehq.development.helpers.listener.FactionsCoreListener;
import cc.zanehq.development.helpers.listener.FoundDiamondsListener;
import cc.zanehq.development.helpers.listener.FurnaceSmeltSpeederListener;
import cc.zanehq.development.helpers.listener.GodListener;
import cc.zanehq.development.helpers.listener.KitMapListener;
import cc.zanehq.development.helpers.listener.LoginEvent;
import cc.zanehq.development.helpers.listener.MediaChatListener;
import cc.zanehq.development.helpers.listener.OreCountListener;
import cc.zanehq.development.helpers.listener.PearlGlitchListener;
import cc.zanehq.development.helpers.listener.PlayTimeManager;
import cc.zanehq.development.helpers.listener.PotEffectUHC;
import cc.zanehq.development.helpers.listener.PotionLimitListener;
import cc.zanehq.development.helpers.listener.SignSubclaimListener;
import cc.zanehq.development.helpers.listener.SkullListener;
import cc.zanehq.development.helpers.listener.StaffChatListener;
import cc.zanehq.development.helpers.listener.StaffModeListener;
import cc.zanehq.development.helpers.listener.UnRepairableListener;
import cc.zanehq.development.helpers.listener.VanishListener;
import cc.zanehq.development.helpers.listener.WorldListener;
import cc.zanehq.development.helpers.listener.fixes.ArmorFixListener;
import cc.zanehq.development.helpers.listener.fixes.BeaconStrengthFixListener;
import cc.zanehq.development.helpers.listener.fixes.BlockHitFixListener;
import cc.zanehq.development.helpers.listener.fixes.BlockJumpGlitchFixListener;
import cc.zanehq.development.helpers.listener.fixes.BoatGlitchFixListener;
import cc.zanehq.development.helpers.listener.fixes.BookQuillFixListener;
import cc.zanehq.development.helpers.listener.fixes.CommandBlocker;
import cc.zanehq.development.helpers.listener.fixes.DupeGlitchFix;
import cc.zanehq.development.helpers.listener.fixes.EnchantLimitListener;
import cc.zanehq.development.helpers.listener.fixes.EnderChestRemovalListener;
import cc.zanehq.development.helpers.listener.fixes.HungerFixListener;
import cc.zanehq.development.helpers.listener.fixes.InfinityArrowFixListener;
import cc.zanehq.development.helpers.listener.fixes.NaturalMobSpawnFixListener;
import cc.zanehq.development.helpers.listener.fixes.PexCrashFixListener;
import cc.zanehq.development.helpers.listener.fixes.PortalListener;
import cc.zanehq.development.helpers.listener.fixes.StrengthListener;
import cc.zanehq.development.helpers.listener.fixes.SyntaxBlocker;
import cc.zanehq.development.helpers.listener.fixes.VoidGlitchFixListener;
import cc.zanehq.development.helpers.listener.fixes.WeatherFixListener;
import cc.zanehq.development.helpers.reclaim.ReclaimManager;
import cc.zanehq.development.helpers.scoreboard.ScoreboardHandler;
import cc.zanehq.development.helpers.tablist.TablistManager;
import cc.zanehq.development.helpers.tablist.tablist.TablistAdapter;
import cc.zanehq.development.helpers.timer.TimerExecutor;
import cc.zanehq.development.helpers.timer.TimerManager;
import cc.zanehq.development.special.Christmas.ChristmasCommand;
import cc.zanehq.development.special.DoubleKey.DKeyCommand;
import cc.zanehq.development.special.KING.KingCommand;
import cc.zanehq.development.special.KING.KingListener;
import cc.zanehq.development.special.Summer.SummerCommand;
import cc.zanehq.development.special.TripleKey.TKeyCommand;
import cc.zanehq.development.special.inventory.implementation.ClaimSettingsInventory;
import cc.zanehq.development.special.keyall.KeyallCommand;
import cc.zanehq.development.special.keysale.KeysaleCommand;
import cc.zanehq.development.special.signs.EventSignListener;
import cc.zanehq.development.special.signs.KitSignListener;
import cc.zanehq.development.user.ConsoleUser;
import cc.zanehq.development.user.FactionUser;
import cc.zanehq.development.user.UserManager;
import cc.zanehq.development.util.SignHandler;
import cc.zanehq.development.util.base.BasePlugins;
import cc.zanehq.development.util.base.Cooldowns;
import cc.zanehq.development.util.base.Message;
import cc.zanehq.development.util.base.ServerHandler;
import cc.zanehq.development.util.itemdb.ItemDb;
import cc.zanehq.development.util.itemdb.SimpleItemDb;
import cc.zanehq.development.visualise.ProtocolLibHook;
import cc.zanehq.development.visualise.VisualiseHandler;
import cc.zanehq.development.visualise.WallBorderListener;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.util.com.google.common.base.Joiner;

public class Base extends JavaPlugin implements CommandExecutor {

	private CombatLogListener combatLogListener;
	
	public CombatLogListener getCombatLogListener() {
		return this.combatLogListener;
	}
		


// this is a skid and requires you to use pex reee

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
//		PotionLimiterData.getInstance().setup(this);
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
		getCommand("summer").setExecutor(new SummerCommand(plugin));
		getCommand("keyall").setExecutor(new KeyallCommand(plugin));
		getCommand("keysale").setExecutor(new KeysaleCommand(plugin));
		getCommand("christmas").setExecutor(new ChristmasCommand(plugin));
		getCommand("doublekey").setExecutor(new DKeyCommand(plugin));
		getCommand("triplekey").setExecutor(new TKeyCommand(plugin));
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
