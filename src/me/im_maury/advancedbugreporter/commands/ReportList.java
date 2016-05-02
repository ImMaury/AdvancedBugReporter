package me.im_maury.advancedbugreporter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

import static me.im_maury.advancedbugreporter.Main.getJsonManager;

public class ReportList implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("reportlist")) {
            if (!sender.hasPermission("advancedbugreporter.list")) {
                sender.sendMessage("§cYou don't have enough permissions to perform this command.");
                return false;
            }
            if (getJsonManager.getRoot().size() == 0) {
                sender.sendMessage("§8[§cBugReporter§8] §cThere are no bug reports.");
                return true;
            }
            sender.sendMessage("§8[§cBugReporter§8] §bBug reports are shown below.");
            for (JSONObject obj : getJsonManager.getSuperObjects()) {
                sender.sendMessage("§8[§2#" + obj.get("id") + "§8] §8[§2" + obj.get("name") + "§8] §7" + obj.get("report"));
            }
        }
        return true;
    }
}