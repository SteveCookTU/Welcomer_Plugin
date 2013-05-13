package com.imaboy321.welcomer;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	public static main plugin;
	Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " has been enabled!");
		getConfig().addDefault("prefix", "&a[&bWelcomer&a]&f");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " has been disabled!");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String args[]) {
		String prefix = getConfig().getString("prefix").replaceAll("&", "§");
		final Player player = (Player) sender;

		if (commandLabel.equalsIgnoreCase("welcome")) {
			if (sender instanceof Player) {
				if (player.isOp()
						|| player
								.hasPermission(new Permissions().canPerformCommand)) {

					ItemStack inhand = player.getItemInHand();
					int amhand = inhand.getAmount();
					Material mhand = inhand.getType();
					int newamount = amhand - 1;
					String name = inhand.getType().toString();

					if (args.length == 0) {
						player.sendMessage(prefix + ChatColor.DARK_RED
								+ "Please Use /welcome <player> <amountGiving>");
						player.sendMessage(prefix
								+ ChatColor.DARK_RED
								+ "The Item Given to Them is The Item in Your Hand!");
					} else if (args.length == 1) {
						Player targetPlayer = player.getServer().getPlayer(
								args[0]);
						if (inhand.getType() != Material.AIR) {
							targetPlayer.sendMessage(prefix
									+ player.getDisplayName() + ChatColor.AQUA
									+ " Would Like to Welcome You with a "
									+ name + "!");
							targetPlayer.getInventory().addItem(
									new ItemStack(mhand, 1));
							getServer().broadcastMessage(
									prefix + player.getDisplayName()
											+ ChatColor.AQUA + " Has Welcomed "
											+ ChatColor.RESET
											+ targetPlayer.getDisplayName()
											+ ChatColor.AQUA + " With A "
											+ name);
							if (amhand == 1) {
								player.getInventory().remove(
										new ItemStack(mhand, 1));
							} else {
								player.getItemInHand().setAmount(newamount);
							}

						} else {
							player.sendMessage(prefix + ChatColor.DARK_RED
									+ "You Can Not Welcome Them With Air!");
						}
					} else if (args.length == 2) {
						int amount = Integer.parseInt(args[1]);
						int newamount2 = amhand - amount;
						Player targetPlayer = player.getServer().getPlayer(
								args[0]);
						if (inhand.getType() != Material.AIR
								&& amount <= inhand.getAmount()) {
							targetPlayer.sendMessage(prefix
									+ player.getDisplayName() + ChatColor.AQUA
									+ " Would Like to Welcome You with "
									+ amount + " " + name + "!");
							targetPlayer.getInventory().addItem(
									new ItemStack(mhand, amount));
							getServer().broadcastMessage(
									prefix + player.getDisplayName()
											+ ChatColor.AQUA + " Has Welcomed "
											+ ChatColor.RESET
											+ targetPlayer.getDisplayName()
											+ ChatColor.AQUA + " With "
											+ amount + " " + name);

							if (amhand == amount) {
								player.getInventory().remove(
										new ItemStack(mhand, amount));
							} else {
								player.getItemInHand().setAmount(newamount2);
							}
						} else {
							player.sendMessage(prefix
									+ ChatColor.DARK_RED
									+ "You Can Not Welcome Them With Air nor Give Them More Than You Have!");
						}
					} else {
						player.sendMessage(prefix + ChatColor.DARK_RED
								+ "Please Use /welcome <player> <amountGiving>");
						player.sendMessage(prefix
								+ ChatColor.DARK_RED
								+ "The Item Given to Them is The Item in Your Hand!");
					}
				} else {
					player.sendMessage(prefix + ChatColor.DARK_RED
							+ " You Can Not Welcome People!");
				}
			} else {
				sender.sendMessage(prefix + ChatColor.DARK_RED
						+ "This Command is For Players!");
			}
		}

		if (commandLabel.equalsIgnoreCase("welcomer")) {
			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "--------" + prefix
						+ ChatColor.RED + "--------");
				player.sendMessage(ChatColor.GOLD
						+ "/welcomer reload - reload the welcomer config");
				player.sendMessage(ChatColor.GOLD
						+ "/welcome <playername> <amountGiven> - welcome a player with a certain amount of the item held");

			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.isOp() || player.hasPermission(new Permissions().canReloadPlugin)) {
						reloadConfig();
						player.sendMessage(prefix + ChatColor.GREEN
								+ " has been reloaded!");
						return true;
					}else {
						player.sendMessage(prefix + ChatColor.DARK_RED + "You do not have the correct permissions!");
					}
				} else {
					player.sendMessage(prefix + ChatColor.DARK_RED
							+ "Please use /welcomer for the commands!");
				}
			} else {
				player.sendMessage(prefix + ChatColor.DARK_RED
						+ "Please use /welcomer for the commands!");
			}
		}

		return false;
	}
}
