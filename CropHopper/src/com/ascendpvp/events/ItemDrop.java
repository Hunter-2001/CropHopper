package com.ascendpvp.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.ascendpvp.CropHopperMain;


public class ItemDrop implements Listener {

	CropHopperMain plugin;
	public ItemDrop(CropHopperMain plugin){
		this.plugin = plugin;
	}

	//Event purely to allow players to cactus in a chunk that contains a CropHopper without it instantly sucking it up
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getType() != Material.CACTUS) return;
		e.getItemDrop().setCustomName("1337CaCtUs");
	}
}
