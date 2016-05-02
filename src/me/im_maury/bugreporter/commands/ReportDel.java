package me.im_maury.bugreporter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;

import static me.im_maury.bugreporter.Main.getJsonManager;

public class ReportDel implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reportdel")) {
            if (!sender.hasPermission("bugreporter.del")) {
                sender.sendMessage("§cYou don't have enough permissions to perform this command.");
                return true;
            }
            if (args.length < 1) {
                return false;
            }
            if (getJsonManager.getRoot().size() == 0) {
                sender.sendMessage("§8[§cBugReporter§8] §cThere are no bug reports.");
                return true;
            }
            int numberReports = getJsonManager.getRoot().size();
            switch (args[0]) {
                case "id":
                    int idsDeleted = 0;
                    if (args.length < 2) return false;
                    for (int i = 1; i < args.length; i++) {
                        for (JSONObject obj : getJsonManager.getSuperObjects()) {
                            if (obj.get("id").equals(args[i])) {
                                getJsonManager.getRoot().remove(obj);
                                idsDeleted++;
                            }
                        }
                        if (numberReports == getJsonManager.getRoot().size()) {
                            sender.sendMessage("§8[§cBugReporter§8] §cID " + '"' + "§4" + args[i] + "§c" + '"' + " matches with no bug reports.");
                        }
                    }
                    sender.sendMessage("§8[§cBugReporter§8] §a" + idsDeleted + " §bbug reports deleted.");
                    getJsonManager.save();
                    break;
                case "player":
                    int playersDeleted = 0;
                    if (args.length < 2) return false;
                    for (int i = 1; i < args.length; i++) {
                        for (JSONObject obj : getJsonManager.getSuperObjects()) {
                            if (obj.get("name").equals(args[i])) {
                                getJsonManager.getRoot().remove(obj);
                                playersDeleted++;
                            }
                        }
                        if (numberReports == getJsonManager.getRoot().size()) {
                            sender.sendMessage("§8[§cBugReporter§8] §cPlayer " + '"' + "§4" + args[i] + "§c" + '"' + " matches with no bug reports.");
                        }
                    }
                    sender.sendMessage("§8[§cBugReporter§8] §a" + playersDeleted + " §bbug reports deleted.");
                    getJsonManager.save();
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }
}
