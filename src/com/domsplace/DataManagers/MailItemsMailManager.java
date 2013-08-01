package com.domsplace.DataManagers;

import com.domsplace.MailItemsBase;
import com.domsplace.Objects.MailItemBox;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MailItemsMailManager extends MailItemsBase {
    
    public static File MailFILE;
    public static YamlConfiguration MailYML;
    
    public static boolean LoadConfig() {
        try {
            MailFILE = new File(getPlugin().getDataFolder(), "mailboxes.yml");
            if(!MailFILE.exists()) {
                MailFILE.createNewFile();
            }
            
            MailYML = YamlConfiguration.loadConfiguration(MailFILE);
            
            LoadMailBoxes();
            
            MailYML.save(MailFILE);
            return true;
        } catch(Exception e) {
            Error("Failed to load mailboxes.yml!", e);
            return false;
        }
    }
    
    public static void LoadMailBoxes() {
        MemorySection ms = (MemorySection) MailYML.get("mailboxes");
        if(ms == null) {
            return;
        }
        for(String s : ms.getKeys(false)) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(MailYML.getString("mailboxes." + s + ".owner"));
            World w = Bukkit.getWorld(MailYML.getString("mailboxes." + s + ".world"));
            if(w == null) {
                continue;
            }
            
            Block b = w.getBlockAt(MailYML.getInt("mailboxes." + s + ".x"), MailYML.getInt("mailboxes." + s + ".y"), MailYML.getInt("mailboxes." + s + ".z"));
            if(!isChest(b)) {
                continue;
            }
            
            Chest c = getChest(b);
            
            MailItemBox mailBox = new MailItemBox(c, p);
        }
    }
    
    public static void SaveMailBoxes() {
        try {
            MailYML = new YamlConfiguration();
            
            int id =0;
            for(MailItemBox mailBox : MailItemBox.mailBoxes) {
                id++;
                MailYML.set("mailboxes." + id + ".owner", mailBox.getPlayer().getName());
                MailYML.set("mailboxes." + id + ".x", mailBox.getChest().getLocation().getBlockX());
                MailYML.set("mailboxes." + id + ".y", mailBox.getChest().getLocation().getBlockY());
                MailYML.set("mailboxes." + id + ".z", mailBox.getChest().getLocation().getBlockZ());
                MailYML.set("mailboxes." + id + ".world", mailBox.getChest().getLocation().getWorld().getName());
            }
            
            MailYML.save(MailFILE);
        } catch(Exception e) {
            Error("Failed to load mailboxes.yml!", e);
        }
    }
}
