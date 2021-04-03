package space.wtnpmt.mcplugin.TCD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TotemCooldownCommand implements CommandExecutor {

	private final TotemCooldown plugin;

	public TotemCooldownCommand(TotemCooldown plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("totemcooldown") || cmd.getName().equalsIgnoreCase("tcd"))
				&& sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length < 1)
				return false;
			try {
				switch (args[0]) {
				case "settime":
					plugin.ticktime = Integer.valueOf(args[1]);
					player.sendMessage("new cooldown tick:" + plugin.ticktime);
					break;
				case "gettime":
					player.sendMessage("cooldown tick:" + plugin.ticktime);
					break;
				default:
					player.sendMessage("§4FORMAT ERROR.");
					break;
				}
			} catch (Exception e) {
				player.sendMessage("§4FORMAT ERROR.");
				return false;
			}
			return true;
		}
		return false;
	}

}
