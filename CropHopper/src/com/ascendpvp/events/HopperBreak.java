package com.ascendpvp.events;

import java.io.IOException;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import com.ascendpvp.CropHopperMain;
import com.ascendpvp.utils.Helpers;

public class HopperBreak implements Listener {

	CropHopperMain plugin;
	public HopperBreak(CropHopperMain plugin){
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHopperBreak(BlockBreakEvent e) {

		//Basic checks
		if(e.isCancelled()) return;
		if(e.getBlock() == null) return;
		Block blockBroke = e.getBlock();
		Player p = e.getPlayer();
		if(blockBroke.getType() != Material.HOPPER) return;

		int chunkX = blockBroke.getChunk().getX();
		int chunkZ = blockBroke.getChunk().getZ();
		int hopperX = blockBroke.getX();
		int hopperY = blockBroke.getY();
		int hopperZ = blockBroke.getZ();
		String hopperSave = String.valueOf(chunkX) + String.valueOf(chunkZ);
		int configX = plugin.cfg.getInt("hopperlocs." + hopperSave + "." + "x");
		int configY = plugin.cfg.getInt("hopperlocs." + hopperSave + "." + "y");
		int configZ = plugin.cfg.getInt("hopperlocs." + hopperSave + "." + "z");

		//Determine whether the hopper broken is a CropHopper
		if(plugin.cfg.getString("hopperlocs." + hopperSave) == null) return;
		if(configX == hopperX && configY == hopperY && configZ == hopperZ) {
			//Cancel BlockBreakEvent and create CropHopper item
			ItemStack cropHopper = new ItemStack(Material.HOPPER);
			help.nameItemLore(cropHopper, help.cc(plugin.getConfig().getString("hopper_name")), help.cc(plugin.getConfig().getString("hopper_lore")));
			e.setCancelled(true);
			//Update values in .yml
			plugin.cfg.set("hopperlocs." + hopperSave, null);
			try {
				plugin.cfg.save(plugin.f);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			if(p.getGameMode() != GameMode.CREATIVE) {
				e.getBlock().getWorld().dropItem(blockBroke.getLocation(), cropHopper);
			}
			e.getBlock().setType(Material.AIR);
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.hopper_break_success")));
		}
	}
}
