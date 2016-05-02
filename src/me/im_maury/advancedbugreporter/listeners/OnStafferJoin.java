package me.im_maury.advancedbugreporter.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static me.im_maury.advancedbugreporter.Main.getJsonManager;

public class OnStafferJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("advancedbugreporter.receive")) {
            List<JSONObject> unreadReports = new ArrayList<>();
            for (JSONObject obj : getJsonManager.getSuperObjects()) {
                if (!getJsonManager.getStaffer(obj.get("id").toString()).contains(p.getName())) {
                    unreadReports.add(obj);
                    getJsonManager.addStaffer(obj.get("id").toString(), p.getName());
                }
            }
            if (unreadReports.size() != 0) {
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 10);
                p.sendMessage("§aHey dude! You have some unread bug reports.");
                for (JSONObject report : unreadReports) {
                    p.sendMessage("§8[§cAdvancedBugReporter§8] §8[§2#" + report.get("id") + "§8] §8[§2" + report.get("name") + "§8] §7" + report.get("report"));
                }
            }
        }
    }
}


