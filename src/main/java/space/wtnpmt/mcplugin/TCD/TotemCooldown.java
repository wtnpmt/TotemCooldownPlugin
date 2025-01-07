package space.wtnpmt.mcplugin.TCD;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
		getCommand("totemcooldown").setExecutor(new TotemCooldownCommand());
		// this.getCommand("TCD").setExecutor(new TotemCooldownCommand(this));
		getServer().getPluginManager().registerEvents(new TotemCooldownListener(), this);
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
