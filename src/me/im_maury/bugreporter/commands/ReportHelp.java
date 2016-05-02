package me.im_maury.bugreporter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reporthelp")) {
            if (!sender.hasPermission("bugreporter.help")) {
                sender.sendMessage("§cThis command can be only performed by in-game players.");
                return true;
            }
            sender.sendMessage("§7~~~~~~~~ §8[§cBugReporter §cv1.0§8] by Im_Maury §7~~~~~~~~~");
            sender.sendMessage("§7/reportbug <text> - §bSends a bug report");
            sender.sendMessage("§7/reportlist - §bShows stored bug reports");
            sender.sendMessage("§7/reportdel <selector> <ID/playername> - §bDeletes bug reports. Available selectors: id, player");
            sender.sendMessage("§7/reporttp <id> - §bTeleports to a bug report's location");
            return true;
        }

        return false;
    }
}
