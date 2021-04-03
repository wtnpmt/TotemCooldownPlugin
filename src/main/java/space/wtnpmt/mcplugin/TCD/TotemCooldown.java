package space.wtnpmt.mcplugin.TCD;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TotemCooldown extends JavaPlugin implements Listener {

	protected int ticktime = 200;
	//protected String cooldown_message = null;
	private FileConfiguration config;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		config = getConfig();
		ticktime = config.getInt("ticktime");
		//cooldown_message = config.getString("inCooldownMessage");
		this.getCommand("totemcooldown").setExecutor(new TotemCooldownCommand(this));
		//this.getCommand("TCD").setExecutor(new TotemCooldownCommand(this));
		getServer().getPluginManager().registerEvents(new TotemCooldownListener(this), this);
		getLogger().info("TotemCooldown enabled. author:pumpkin_pmt.");
	}

	@Override
	public void onDisable() {
		config.set("ticktime", ticktime);
		saveConfig();
		getLogger().info("TotemCooldown disabled.");
	}
}
