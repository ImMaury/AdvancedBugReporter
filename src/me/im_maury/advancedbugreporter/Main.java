package me.im_maury.advancedbugreporter;

import me.im_maury.advancedbugreporter.commands.*;
import me.im_maury.advancedbugreporter.filemanager.JsonManager;
import me.im_maury.advancedbugreporter.listeners.OnStafferJoin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static JsonManager getJsonManager = new JsonManager("plugins/AdvancedBugReporter/reports.json");

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OnStafferJoin(), this);
        getCommand("reportbug").setExecutor(new ReportBug());
        getCommand("reportlist").setExecutor(new ReportList());
        getCommand("reportdel").setExecutor(new ReportDel());
        getCommand("reporttp").setExecutor(new ReportTp());
        getCommand("reporthelp").setExecutor(new ReportHelp());
        getLogger().info("[AdvancedBugReporter] Plugin enabled!");
    }

    public void onDisable() {
        //getJsonManager.save();
        getLogger().info("[AdvancedBugReporter] Plugin disabled!");
    }

}
