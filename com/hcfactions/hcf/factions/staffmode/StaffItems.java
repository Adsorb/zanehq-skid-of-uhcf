package com.hcfactions.hcf.factions.staffmode;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.hcfactions.hcf.helpers.commands.StaffModeCommand;

import net.md_5.bungee.api.ChatColor;

public class StaffItems {

	@SuppressWarnings("deprecation")
	public static void modItems(Player p) {
		Inventory inv = p.getInventory();

		inv.clear();

		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemStack book = new ItemStack(Material.BOOK);
		ItemStack tp = new ItemStack(397, 1, (short) 3);
		ItemStack vanish = new ItemStack(351, 1, (short) 10);
		ItemStack freeze = new ItemStack(Material.ICE);

		ItemMeta compassMeta = compass.getItemMeta();
		ItemMeta bookMeta = book.getItemMeta();
		ItemMeta eggMeta = tp.getItemMeta();
		ItemMeta vanishMeta = vanish.getItemMeta();
		ItemMeta freezeMeta = freeze.getItemMeta();
		

		compassMeta.setDisplayName("§bTeleporter");
		bookMeta.setDisplayName("§bInventory");
		eggMeta.setDisplayName("§bRandom TP");
		vanishMeta.setDisplayName("§bVanish: §aOn");
		freezeMeta.setDisplayName("§bFreeze");

		compassMeta.setLore(Arrays.asList("§7RMB to JUMP to a location", "§7LMB to PHASE to a location"));
		bookMeta.setLore(Arrays.asList("§7LMB on a player to check their inventory"));
		eggMeta.setLore(Arrays.asList("§7LMB to randomly teleport to people"));
		freezeMeta.setLore(Arrays.asList("§7LMB to freeze people"));
		
		compass.setItemMeta(compassMeta);
		book.setItemMeta(bookMeta);
		tp.setItemMeta(eggMeta);
		vanish.setItemMeta(vanishMeta);
		freeze.setItemMeta(freezeMeta);

		inv.setItem(0, compass);
		inv.setItem(1, book);
		inv.setItem(2, freeze);
		inv.setItem(7, tp);
		inv.setItem(8, vanish);
	}

	public static void saveInventory(Player p) {
		StaffModeCommand.armorContents.put(p.getName(), p.getInventory().getArmorContents());
		StaffModeCommand.inventoryContents.put(p.getName(), p.getInventory().getContents());
	}

	public static void loadInventory(Player p) {
		p.getInventory().clear();

		p.getInventory().setContents((ItemStack[]) StaffModeCommand.inventoryContents.get(p.getName()));
		p.getInventory().setArmorContents((ItemStack[]) StaffModeCommand.armorContents.get(p.getName()));

		StaffModeCommand.inventoryContents.remove(p.getName());
		StaffModeCommand.armorContents.remove(p.getName());
	}

}
