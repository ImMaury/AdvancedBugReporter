package me.im_maury.advancedbugreporter.commands;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static me.im_maury.advancedbugreporter.Main.getJsonManager;

public class ReportBug implements CommandExecutor {

    private String getId() {
        if (getJsonManager.getRoot().size() != 0) {
            List<Integer> idsList = new ArrayList<>();
            for (JSONObject obj : getJsonManager.getSuperObjects()) {
                idsList.add(Integer.valueOf(obj.get("id").toString()));
            }
            Collections.sort(idsList);
            Integer currentId = 0;
            for (Integer id : idsList) {
                if (!id.equals(currentId)) {
                    return currentId.toString();
                }
                currentId++;
            }
        }
        return String.valueOf(getJsonManager.getRoot().size());
    }

    @SuppressWarnings("unchecked")
    private String createNewReport(final String name, final String uuid, final String report, final Location rawLoc) {
        final String id = getId();
        getJsonManager.getRoot().add(getJsonManager.newJSONObject(new HashMap() {{
            put("id", id);
            put("name", name);
            put("uuid", uuid);
            put("report", report);
            put("haveseen", new JSONArray());
            put("coordinates", getJsonManager.newJSONObject(new HashMap() {{
                put("world", rawLoc.getWorld().getName());
                put("x", rawLoc.getX());
                put("y", rawLoc.getY());
                put("z", rawLoc.getZ());
            }}));

        }}));
        getJsonManager.save();
        return id;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reportbug")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cThis command can be only performed by in-game players.");
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("advancedbugreporter.report")) {
                p.sendMessage("§cYou don't have enough permissions to perform this command.");
                return true;

            }
            if (args.length < 1) {
                return false;
            }
            String reportText = "";
            for (String arg : args) {
                reportText += arg + " ";
            }
            String id = createNewReport(p.getName(), p.getUniqueId().toString(), reportText.substring(0, reportText.length() - 1), p.getLocation());
            p.sendMessage("§8[§cAdvancedBugReporter§8] §bReport sent!");
            if (Bukkit.getVersion().contains("1.9")) {
                p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 10, 10);
            } else {
                p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 10, 10);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("advancedbugreporter.receive")) {
                    if (Bukkit.getVersion().contains("1.9")) {
                        p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 10, 10);
                    } else {
                        p.playSound(p.getLocation(), Sound.valueOf("NOTE_PLING"), 10, 10);
                    }
                    player.sendMessage("§8[§cAdvancedBugReporter§8] §8[§2#" + id + "§8] §8[§2" + p.getName() + "§8] §7" + reportText);
                    getJsonManager.addStaffer(id, player.getName());
                }
            }
            return true;
        }
        return false;
    }

}
