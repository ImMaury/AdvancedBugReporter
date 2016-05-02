package me.im_maury.advancedbugreporter.commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.im_maury.advancedbugreporter.Main.getJsonManager;

public class ReportTp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reporttp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cThis command can be only performed by in-game players.");
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("advancedbugreporter.tp")) {
                p.sendMessage("§cYou don't have enough permissions to perform this command.");
                return true;

            }
            if (args.length != 1) {
                return false;
            }
            Location reportLocation = getJsonManager.getLocation(args[0]);
            if (reportLocation != null) {
                p.teleport(reportLocation);
                p.sendMessage("§8[§cBugReporter§8] §bTeleported to ID §c" + args[0] + "§b's location.");
                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 10);
            } else {
                sender.sendMessage("§8[§cBugReporter§8] §cID " + '"' + "§4" + args[0] + "§c" + '"' + " matches with no bug reports.");
            }
            return true;
        }
        return false;
    }
}
