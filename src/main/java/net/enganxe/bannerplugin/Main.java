package net.enganxe.bannerplugin;

import net.enganxe.bannerplugin.commands.BannerCommand;
import net.enganxe.bannerplugin.datafile.Data;
import net.enganxe.bannerplugin.events.CreatorEvents;
import net.enganxe.bannerplugin.events.LeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Data config;
    @Override
    public void onEnable() {
        this.config = new Data(this);
        new BannerCommand(this);
        new LeaveEvent(this);
        new CreatorEvents(this);
        BannerCommand.makingb.clear();
        BannerCommand.bgived.clear();
        getLogger().info("BannerPlugin by Wixent has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BannerPlugin by Wixent has been disabled");
    }
}
