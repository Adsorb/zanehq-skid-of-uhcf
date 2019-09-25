package cc.zanehq.development.factions.sotw;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import cc.zanehq.development.Base;

public class SotwListener implements Listener {

    private final Base plugin;

    public SotwListener(Base plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            final Player oponent = (Player)e.getDamager();
            if (this.plugin.getSotwTimer().getSotwRunnable() != null && SotwCommand.enabled.contains(oponent.getUniqueId()) && !SotwCommand.enabled.contains(player.getUniqueId())) {
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
                oponent.sendMessage("�eYou are not permitted to hit this player, they do not have their �6�lSOTW �epaused.");
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
                e.setCancelled(true);
            }
            else if (this.plugin.getSotwTimer().getSotwRunnable() != null && !SotwCommand.enabled.contains(oponent.getUniqueId()) && SotwCommand.enabled.contains(player.getUniqueId())) {
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
                oponent.sendMessage("�eYou are not permitted to hit �6�l" + player.getName() + "�e.");
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
                e.setCancelled(true);
            }
            else if (this.plugin.getSotwTimer().getSotwRunnable() != null  && !SotwCommand.enabled.contains(oponent.getUniqueId()) && !SotwCommand.enabled.contains(player.getUniqueId())) {
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
            	oponent.sendMessage("�eYou cannot hit players whilst sotw is active if you would like to execute �6�l/sotw enable�e.");
            	oponent.sendMessage(ChatColor.GRAY + "�m----------------------------------");
            	e.setCancelled(true);
            }
           
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && this.plugin.getSotwTimer().getSotwRunnable() != null) {
            final Player player = (Player)event.getEntity();
            if (SotwCommand.enabled.contains(player.getUniqueId())) {
                event.setCancelled(false);
                return;
            }
            if (event.getCause() != EntityDamageEvent.DamageCause.SUICIDE && this.plugin.getSotwTimer().getSotwRunnable() != null) {
                event.setCancelled(true);
            }
        }
    }
}


