package com.domsplace.DataManagers;

import com.domsplace.MailItemsBase;
import com.domsplace.Utils.MailItemsUtils;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class MailItemsConfigManager extends MailItemsBase {
    
    public static File ConfigFile;
    public static YamlConfiguration ConfigYML;
    
    public static boolean LoadConfig() {
        try {
            if(!getPlugin().getDataFolder().exists()) {
                getPlugin().getDataFolder().mkdir();
            }

            ConfigFile = new File(getPlugin().getDataFolder(), "config.yml");
            ConfigYML = YamlConfiguration.loadConfiguration(ConfigFile);
            
            if(!ConfigFile.exists()) {
                ConfigFile.createNewFile();
            }
            
            MailItemsUtils.economy = null;
            
            //Load in Default Values
            if(!ConfigYML.contains("showfrom")) {
                ConfigYML.set("showfrom", true);
            }
            if(!ConfigYML.contains("removefrom")) {
                ConfigYML.set("removefrom", true);
            }
            if(!ConfigYML.contains("blockcreative")) {
                ConfigYML.set("blockcreative", true);
            }
            
            if(!ConfigYML.contains("economy.use")) {
                ConfigYML.set("economy.use", false);
            }
            if(!ConfigYML.contains("economy.price.senditem")) {
                ConfigYML.set("economy.price.senditem", 50d);
            }
            
            //Check Values
            ShowFrom = ConfigYML.getBoolean("showfrom");
            RemoveFrom = ConfigYML.getBoolean("removefrom");
            BlockCreative = ConfigYML.getBoolean("blockcreative");
            
            //Update Values
            if(ConfigYML.getBoolean("economy.use")) {
                if(!MailItemsUtils.setupEconomy()) {
                    Error("Failed to hook into economy... Check you have Vault and some Economy plugin loaded.", null);
                    msgConsole("Disabling Economy Support...");
                }
            }
            
            ConfigYML.save(ConfigFile);
            return true;
        } catch(Exception e) {
            Error("Failed to load config.yml!", e);
            return false;
        }
    }
}
