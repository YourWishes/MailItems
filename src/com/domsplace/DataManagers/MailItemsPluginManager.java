package com.domsplace.DataManagers;

import com.domsplace.MailItemsBase;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MailItemsPluginManager extends MailItemsBase {
    
    public static YamlConfiguration PluginYML;
    public static boolean LoadPlugin() {
        InputStream is;
        try {
            
            is = getPlugin().getResource("plugin.yml");
            PluginYML = YamlConfiguration.loadConfiguration(is);
            is.close();
            
            return true;
        } catch(Exception e) {
            Error("Failed to load plugin.yml!", e);
            return false;
        }
    }
    
    public static String getName() {
        return PluginYML.getString("name");
    }
    
    public static String getVersion() {
        return PluginYML.getString("version");
    }
    
    public static List<String> getCommands() {
        List<String> commands = new ArrayList<String>();
        for(String s : ((MemorySection) PluginYML.get("commands")).getKeys(false)) {
            commands.add(s);
        }
        return commands;
    }
}