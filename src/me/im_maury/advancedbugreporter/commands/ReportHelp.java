package me.im_maury.advancedbugreporter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reporthelp")) {
            if (!sender.hasPermission("advancedbugreporter.help")) {
                sender.sendMessage("§cThis command can be only performed by in-game players.");
                return true;
            }
            sender.sendMessage("§7~~~~~~ §8[§cAdvancedBugReporter §cv0.1.1§8] by Im_Maury §7~~~~~~~");
            sender.sendMessage("§7/reportbug <text> - §bSends a bug report");
            sender.sendMessage("§7/reportlist - §bShows stored bug reports");
            sender.sendMessage("§7/reportdel <selector> <values> - §bDeletes bug reports. Available selectors: id, player, all");
            sender.sendMessage("§7/reporttp <id> - §bTeleports to a bug report's location");
            return true;
        }

        return false;
    }
}
