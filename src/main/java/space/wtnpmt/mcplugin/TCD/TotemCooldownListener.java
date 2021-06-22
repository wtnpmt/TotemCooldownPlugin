package space.wtnpmt.mcplugin.TCD;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;

public class TotemCooldownListener implements Listener {

	protected final TotemCooldown plugin = JavaPlugin.getPlugin(TotemCooldown.class);
	private HashMap<UUID, Integer> cooldown_map = plugin.getCooldownMap();

	@EventHandler
	public void onEntityResurrect(EntityResurrectEvent event) {
		Entity target = event.getEntity();
		if (event.isCancelled())
			return;
		if (target instanceof Player) {
			Player player = (Player) target;
			if (player.hasPermission("totemcooldown.disable"))
				return;
			if (player.getCooldown(Material.TOTEM) > 0) {
				event.setCancelled(true);
			} else {
				player.setCooldown(Material.TOTEM, plugin.ticktime);
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().hasPermission("totemcooldown.disable"))
			return;
		Integer time = cooldown_map.get(event.getPlayer().getUniqueId());
		if (time == null || time <= 0)
			time = 0;
		event.getPlayer().setCooldown(Material.TOTEM, time);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (event.getPlayer().hasPermission("totemcooldown.disable"))
			return;
		cooldown_map.put(event.getPlayer().getUniqueId(), event.getPlayer().getCooldown(Material.TOTEM));
	}

}
