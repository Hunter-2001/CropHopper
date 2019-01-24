package com.ascendpvp.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.ascendpvp.CropHopperMain;
import com.ascendpvp.utils.Helpers;

public class GiveHopper implements CommandExecutor {
	
	CropHopperMain plugin;
	public GiveHopper(CropHopperMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic checks
		if(!cmd.getName().equalsIgnoreCase("crophopper")) return false;
		if(!sender.hasPermission("crophopper.give")) {
			sender.sendMessage(help.cc(plugin.getConfig().getString("messages.no_permission")));
			return false;
		}
		if(args.length != 1) {
			sender.sendMessage(help.cc(plugin.getConfig().getString("messages.incorrect_usage")));
			return false;
		}
		if(Bukkit.getPlayer(args[0]) == null) {
			sender.sendMessage(help.cc(plugin.getConfig().getString("messages.player_offline")));
			return false;
		}
		
		//Give player a custom hopper
		Player target = Bukkit.getPlayer(args[0]);
		ItemStack cropHopper = new ItemStack(Material.HOPPER);
		help.nameItemLore(cropHopper, help.cc(plugin.getConfig().getString("hopper_name")), help.cc(plugin.getConfig().getString("hopper_lore")));
		target.getInventory().addItem(cropHopper);
		sender.sendMessage(help.cc(plugin.getConfig().getString("messages.crophopper_success")));
		target.sendMessage(help.cc(plugin.getConfig().getString("messages.crophopper_given")));		
		
		return false;
	}
}
