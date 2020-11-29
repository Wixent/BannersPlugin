package net.enganxe.bannerplugin.events;

import net.enganxe.bannerplugin.Main;
import net.enganxe.bannerplugin.commands.BannerCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class LeaveEvent implements Listener {
    private final Main plugin;
    public LeaveEvent(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (BannerCommand.makingb.contains(p)){
            p.getInventory().clear();
            BannerCommand.makingb.remove(p);
        }

    }
}
