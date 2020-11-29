package net.enganxe.bannerplugin.commands;

import net.enganxe.bannerplugin.Main;
import net.enganxe.bannerplugin.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class BannerCommand implements CommandExecutor {
    private Main plugin;
    public static List<Player> makingb = new ArrayList<Player>();
    public static List<Player> bgived = new ArrayList<Player>();
    public static Inventory saveinv;

    public BannerCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("banner").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (!sender.hasPermission("banner.use")){
            sender.sendMessage(Utils.chat("&cYou dont have permission to execute this command"));
            return true;
        }
        if (args.length == 0){
            if (!makingb.contains(p)) {
                makingb.add(p);
                saveinv = p.getInventory();
                Inventory inv = p.getInventory();
                inv.clear();
                GiveItem(p);
                p.sendMessage(Utils.chat("&aWe gave you the items for make a custom banner, do /banner help to see all commands"));
                p.sendMessage(Utils.chat("&aDo /banner save with your custom banner in your main hand for save it"));
            } else {
                p.sendMessage(Utils.chat("&cYou are already making a custom banner, do /banner help to see all commands"));
                return true;
            }
        } else if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("save")){
                if (!makingb.contains(p)) {
                    p.sendMessage(Utils.chat("&cYou are not making a custom banner"));
                    return true;
                } else {
                    if (p.getInventory().getItemInMainHand().getType() == Material.BLACK_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.WHITE_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.ORANGE_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.MAGENTA_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.LIGHT_BLUE_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.YELLOW_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.LIME_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.PINK_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.GRAY_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.LIGHT_GRAY_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.CYAN_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.PURPLE_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.BLUE_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.BROWN_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.GREEN_BANNER ||
                            p.getInventory().getItemInMainHand().getType() == Material.RED_BANNER)
                    {
                            ItemStack b = p.getInventory().getItemInMainHand();
                            String pname = p.getName();
                            BannerMeta banner = (BannerMeta) b.getItemMeta();
                            assert banner != null;
                            Main.config.getConfig().set(pname + ".material", b.getType());
                            Main.config.getConfig().set(pname + ".pattern", banner.getPatterns());
                            Main.config.saveConfig();
                            p.sendMessage(Utils.chat("&aCustom Banner Saved"));
                            makingb.remove(p);
                            p.getInventory().clear();
                    } else {
                        p.sendMessage(Utils.chat("&cYou need to be with a banner in your main hand"));
                    }
                }
            }
            if (args[0].equalsIgnoreCase("reset")){
                if (!makingb.contains(p)) {
                    p.sendMessage(Utils.chat("&cYou are not making a custom banner"));
                    return true;
                } else {
                    p.sendMessage(Utils.chat("&aReset Banner Items"));
                    p.getInventory().clear();
                    GiveItem(p);

                }
            }
            if (args[0].equalsIgnoreCase("leave")){
                if (!makingb.contains(p)) {
                    p.sendMessage(Utils.chat("&cYou are not making a custom banner"));
                    return true;
                } else {
                    p.sendMessage(Utils.chat("&aLeaving Banner editor..."));
                    p.getInventory().clear();
                    makingb.remove(p);
                }
            }
            if (args[0].equalsIgnoreCase("help")){
                p.sendMessage(Utils.chat("&6Banner Commands"));
                p.sendMessage(Utils.chat("&e-/banner: Create a custom banner"));
                p.sendMessage(Utils.chat("&e-/banner save: Save your custom banner"));
                p.sendMessage(Utils.chat("&e-/banner give: Gives your custom banner"));
                p.sendMessage(Utils.chat("&e-/banner reset: Reset the Items of custom banner creator"));
                p.sendMessage(Utils.chat("&e-/banner leave: Leave from custom banner creator"));
                p.sendMessage(Utils.chat("&e-/banner reload: Reload Data File"));
            }
            if (args[0].equalsIgnoreCase("clear")){
                if (sender.hasPermission("banner.admin")){
                    bgived.clear();
                    sender.sendMessage(Utils.chat("&aSetting all Players gived banners to 0..."));
                }
            }
            if (args[0].equalsIgnoreCase("give")){
                if (!Main.config.getConfig().contains(p.getName())){
                    p.sendMessage(Utils.chat("&cYou did not create your custom banner, do /banner help to see all commands"));
                    return true;
                }
                if (bgived.contains(p)){
                    p.sendMessage(Utils.chat("&cWe already give to you the Custom banner"));
                    return true;
                }
                Inventory inv = p.getInventory();
                ItemStack banner = new ItemStack((Material) Main.config.getConfig().get(p.getName() + ".material"), 1);
                BannerMeta bmeta = (BannerMeta) banner.getItemMeta();
                String pname = p.getName();
                assert bmeta != null;
                bmeta.setPatterns((List<Pattern>) Main.config.getConfig().getList(pname + ".pattern"));
                banner.setItemMeta(bmeta);
                bgived.add(p);
                inv.addItem(banner);
                p.sendMessage(Utils.chat("&aBanner Gived!"));
            }
            if (args[0].equalsIgnoreCase("reload")){
                if (sender.hasPermission("banner.admin")){
                    Main.config.reloadConfig();
                    sender.sendMessage(Utils.chat("&aBanner Data reloaded"));
                }
            }
        }

        return false;
    }

    public void GiveItem(Player p){
        //banners
        ItemStack a = new ItemStack(Material.WHITE_BANNER, 1);
        ItemStack b = new ItemStack(Material.ORANGE_BANNER, 1);
        ItemStack c = new ItemStack(Material.MAGENTA_BANNER, 1);
        ItemStack d = new ItemStack(Material.LIGHT_BLUE_BANNER, 1);
        ItemStack e = new ItemStack(Material.YELLOW_BANNER, 1);
        ItemStack f = new ItemStack(Material.LIME_BANNER, 1);
        ItemStack g = new ItemStack(Material.PINK_BANNER, 1);
        ItemStack h = new ItemStack(Material.GRAY_BANNER, 1);
        ItemStack i = new ItemStack(Material.LIGHT_GRAY_BANNER, 1);
        ItemStack j = new ItemStack(Material.CYAN_BANNER, 1);
        ItemStack k = new ItemStack(Material.PURPLE_BANNER, 1);
        ItemStack l = new ItemStack(Material.BLUE_BANNER, 1);
        ItemStack m = new ItemStack(Material.BROWN_BANNER, 1);
        ItemStack n = new ItemStack(Material.GREEN_BANNER, 1);
        ItemStack o = new ItemStack(Material.RED_BANNER, 1);
        ItemStack pe = new ItemStack(Material.BLACK_BANNER, 1);
        //patterns
        ItemStack pattern1 = new ItemStack(Material.FLOWER_BANNER_PATTERN, 64);
        ItemStack pattern2 = new ItemStack(Material.CREEPER_BANNER_PATTERN, 64);
        ItemStack pattern3 = new ItemStack(Material.SKULL_BANNER_PATTERN, 64);
        ItemStack pattern4 = new ItemStack(Material.MOJANG_BANNER_PATTERN, 64);
        ItemStack pattern5 = new ItemStack(Material.GLOBE_BANNER_PATTERN, 64);
        ItemStack pattern6 = new ItemStack(Material.PIGLIN_BANNER_PATTERN, 64);
        // dyes
        ItemStack dye = new ItemStack(Material.WHITE_DYE, 64);
        ItemStack dye1 = new ItemStack(Material.ORANGE_DYE, 64);
        ItemStack dye2 = new ItemStack(Material.MAGENTA_DYE, 64);
        ItemStack dye3 = new ItemStack(Material.LIGHT_BLUE_DYE, 64);
        ItemStack dye4 = new ItemStack(Material.YELLOW_DYE, 64);
        ItemStack dye5 = new ItemStack(Material.LIME_DYE, 64);
        ItemStack dye6 = new ItemStack(Material.PINK_DYE, 64);
        ItemStack dye7 = new ItemStack(Material.GRAY_DYE, 64);
        ItemStack dye8 = new ItemStack(Material.LIGHT_GRAY_DYE, 64);
        ItemStack dye9 = new ItemStack(Material.CYAN_DYE, 64);
        ItemStack dye10 = new ItemStack(Material.PURPLE_DYE, 64);
        ItemStack dye11 = new ItemStack(Material.BLUE_DYE, 64);
        ItemStack dye12 = new ItemStack(Material.BROWN_DYE, 64);
        ItemStack dye13 = new ItemStack(Material.GREEN_DYE, 64);
        ItemStack dye14 = new ItemStack(Material.RED_DYE, 64);
        ItemStack dye15 = new ItemStack(Material.BLACK_DYE, 64);
        Inventory inv = p.getInventory();
        //givear items unu
        inv.addItem(a);
        inv.addItem(b);
        inv.addItem(c);
        inv.addItem(d);
        inv.addItem(e);
        inv.addItem(f);
        inv.addItem(g);
        inv.addItem(h);
        inv.addItem(i);
        inv.addItem(j);
        inv.addItem(k);
        inv.addItem(l);
        inv.addItem(m);
        inv.addItem(n);
        inv.addItem(o);
        inv.addItem(pe);
        // patterns
        inv.addItem(pattern1);
        inv.addItem(pattern2);
        inv.addItem(pattern3);
        inv.addItem(pattern4);
        inv.addItem(pattern5);
        inv.addItem(pattern6);
        //dyes
        inv.addItem(dye);
        inv.addItem(dye1);
        inv.addItem(dye2);
        inv.addItem(dye3);
        inv.addItem(dye4);
        inv.addItem(dye5);
        inv.addItem(dye6);
        inv.addItem(dye7);
        inv.addItem(dye8);
        inv.addItem(dye9);
        inv.addItem(dye10);
        inv.addItem(dye11);
        inv.addItem(dye12);
        inv.addItem(dye13);
        inv.addItem(dye14);
        inv.addItem(dye15);
    }
}
