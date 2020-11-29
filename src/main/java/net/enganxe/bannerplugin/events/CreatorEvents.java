package net.enganxe.bannerplugin.events;

import net.enganxe.bannerplugin.Main;
import net.enganxe.bannerplugin.commands.BannerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CreatorEvents implements Listener {
    private final Main plugin;
    public CreatorEvents(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (!BannerCommand.makingb.contains(p)){
            return;
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!BannerCommand.makingb.contains(p)){
            return;
        }
        e.setCancelled(true);
    }

}
