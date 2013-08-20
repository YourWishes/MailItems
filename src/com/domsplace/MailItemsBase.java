package com.domsplace;

import com.domsplace.Objects.MailItemBox;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MailItemsBase {
    public static String ChatError = ChatColor.RED.toString();
    public static String ChatImportant = ChatColor.BLUE.toString();
    public static String ChatDefault = ChatColor.GRAY.toString();
    
    public static boolean ShowFrom = true;
    public static boolean RemoveFrom = true;
    public static boolean BlockCreative = true;
    
    public static String OpenOverride = "MailItems.openmailbox";
    
    public static MailItemsPlugin getPlugin() {
        return MailItemsPlugin.getPlugin();
    }
    
    public static void Error(String reason, Exception cause) {
        Error(reason);
        
        if(cause == null) {
            return;
        }
        
        msgConsole("Show this error to the plugin Admin:");
        cause.printStackTrace();
    }
    
    public static void permBroadcast(String perm, String message) {
        msgConsole(message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!p.hasPermission(perm)) {
                continue;
            }
            
            msgPlayer(p, message);
        }
    }
    
    public static void broadcast(String message) {
        msgConsole(message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            msgPlayer(p, message);
        }
    }
    
    public static void msgConsole(String message) {
        msgPlayer(Bukkit.getConsoleSender(), message);
    }
    
    public static void msgPlayer(CommandSender player, String message) {
        player.sendMessage(ChatDefault + message);
    }
    
    public static void Error(String reason) {
        msgConsole(ChatError + reason);
    }
    
    public static void debug(Object message) {
        broadcast("§bDEBUG: §d: " + message.toString());
    }
    
    public static String stringLocation(Location location) {
        return location.getX() + ", " + location.getY() + ", " + location.getZ();
    }
    
    public static Chest getChest(Block block) {
        if(block == null) {
            return null;
        }
        
        if(block.getType() == null) {
            return null;
        }
        
        if(!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) {
            return null;
        }
        
        if(!(block.getState() instanceof Chest)) {
            return null;
        }
        
        return (Chest) block.getState();
    }
    
    public static boolean isChest(Block block) {
        if(getChest(block) == null) {
            return false;
        }
        
        return true;
    }
    
    public static MailItemBox getMailBox(Block block) {
        return getMailBox(getChest(block));
    }
    
    public static MailItemBox getMailBox(Chest chest) {
        if(chest == null) {
            return null;
        }
        
        for(MailItemBox box : MailItemBox.mailBoxes) {
            if(!box.getChest().equals(chest)) {
                continue;
            }
            
            return box;
        }
        
        return null;
    }
    
    public static MailItemBox getMailBox(OfflinePlayer player) {
        for(MailItemBox box : MailItemBox.mailBoxes) {
            if(box == null) {
                continue;
            }
            
            if(box.getPlayer() == null) {
                continue;
            }
            
            if(player == null) {
                continue;
            }
            
            if(box.getPlayer().getName() == null) {
                continue;
            }
            
            if(player.getName() == null) {
                continue;
            }
            
            if(!box.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                continue;
            }
            
            if(box.getChest() == null) {
                continue;
            }
            
            if(box.getChest().getBlock() == null) {
                continue;
            }
            
            if(!isChest(box.getChest().getBlock())) {
                continue;
            }
            
            return box;
        }
        
        return null;
    }
    
    public static boolean isMailbox(Block block) {
        if(!isChest(block)) {
            return false;
        }
        
        Chest chest = getChest(block);
        
        if(getMailBox(chest) == null) {
            return false;
        }
        
        return true;
    }
    
    public static boolean canSee(OfflinePlayer player, CommandSender getter) {
        if(!(getter instanceof Player)) {
            return true;
        }
        if(!player.isOnline()) {
            return true;
        }
        
        Player sender = (Player) getter;
        Player p = player.getPlayer();
        
        if(!sender.canSee(p)) {
            return false;
        }
        
        return true;
    }
    
    public static OfflinePlayer getOfflinePlayer(String name, CommandSender getter) {
        OfflinePlayer target = Bukkit.getPlayer(name);
        
        if(target != null && !canSee(target, getter)) {
            target = null;
        }
        
        if(target == null) {
            target = Bukkit.getOfflinePlayer(name);
        }
        
        return target;
    }
    
    public static boolean willMakeDoubleChest(Block b) {
        Block t = b.getRelative(BlockFace.NORTH);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.SOUTH);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.EAST);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.WEST);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isDoubleChest(Chest c) {
        Block b = c.getBlock();
        Block t = b.getRelative(BlockFace.NORTH);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.SOUTH);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.EAST);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        t = b.getRelative(BlockFace.WEST);
        if(t != null && t.getType() != null && (t.getType().equals(Material.CHEST) || t.getType().equals(Material.TRAPPED_CHEST))) {
            return true;
        }
        
        return false;
    }
}
