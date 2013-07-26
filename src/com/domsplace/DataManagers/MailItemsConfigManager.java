package com.domsplace.DataManagers;

import com.domsplace.MailItemsBase;
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
            
            //Load in Default Values
            if(!ConfigYML.contains("showfrom")) {
                ConfigYML.set("showfrom", true);
            }
            if(!ConfigYML.contains("blockcreative")) {
                ConfigYML.set("blockcreative", true);
            }
            
            //Check Values
            ShowFrom = ConfigYML.getBoolean("showfrom");
            BlockCreative = ConfigYML.getBoolean("blockcreative");
            
            //Update Value
            
            ConfigYML.save(ConfigFile);
            return true;
        } catch(Exception e) {
            Error("Failed to load config.yml!", e);
            return false;
        }
    }
}
