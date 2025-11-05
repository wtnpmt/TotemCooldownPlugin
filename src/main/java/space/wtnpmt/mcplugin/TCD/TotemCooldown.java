package space.wtnpmt.mcplugin.TCD;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.papermc.paper.command.brigadier.Commands;

public class TotemCooldown extends JavaPlugin {

	protected volatile int ticktime = 200;
	private FileConfiguration config;
	private HashMap<UUID, Integer> cooldown_map = new HashMap<UUID, Integer>();

	public HashMap<UUID, Integer> getCooldownMap() {
		return cooldown_map;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		config = getConfig();
		ticktime = config.getInt("ticktime");

		var root = Commands.literal("totemcooldown");
		root.then(Commands.literal("settime")
				.then(Commands.argument("ticks", IntegerArgumentType.integer(0)).executes(ctx -> {
					ticktime = ctx.getArgument("ticks", int.class);
					Player player = (Player) ctx.getSource().getSender();
					getLogger().info(player.getName() + " set new cooldown tick:" + ticktime);
					try {
						autosave();
					} catch (Exception e) {
						getLogger().warning("TotemCooldown cannot save config.");
					}
					return Command.SINGLE_SUCCESS;
				})));
		root.then(Commands.literal("gettime")).executes(ctx -> {
			Player player = (Player) ctx.getSource().getSender();
			player.sendMessage("cooldown tick:" + ticktime);
			return Command.SINGLE_SUCCESS;
		});
		getLogger().info("TotemCooldown enabled. author:pumpkin_pmt.");
	}

	public void autosave() {
		config.set("ticktime", ticktime);
		saveConfig();
	}

	@Override
	public void onDisable() {
		autosave();
		getLogger().info("TotemCooldown disabled.");
	}

}
