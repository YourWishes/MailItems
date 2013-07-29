package com.domsplace;

import com.domsplace.Commands.MailItemsCommandMailItems;
import com.domsplace.Commands.MailItemsCommandMailbox;
import com.domsplace.Commands.MailItemsCommandSendItem;
import com.domsplace.DataManagers.MailItemsConfigManager;
import com.domsplace.DataManagers.MailItemsMailManager;
import com.domsplace.DataManagers.MailItemsPluginManager;
import com.domsplace.Listeners.MailItemsAntiBuildListener;
import com.domsplace.Listeners.MailItemsChatListener;
import com.domsplace.Listeners.MailItemsCustomEventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MailItemsPlugin extends JavaPlugin {
    
    public static boolean pluginEnabled = false;
    public static PluginManager pluginManager;
    
    /*** Define Commands ***/
    MailItemsCommandSendItem SendItemCommand = new MailItemsCommandSendItem();
    MailItemsCommandMailItems MailItemCommand = new MailItemsCommandMailItems();
    MailItemsCommandMailbox MailBoxCommand = new MailItemsCommandMailbox();
    
    /*** Define Listeners ***/
    public static MailItemsAntiBuildListener AntiBuildListener;
    public static MailItemsChatListener ChatListener;
    public static MailItemsCustomEventListener CustomEvent;
    
    @Override
    public void onEnable() {
        pluginManager = Bukkit.getPluginManager();
        
        if(!MailItemsPluginManager.LoadPlugin()) {
            Disable();
            return;
        }
        
        if(!MailItemsConfigManager.LoadConfig()) {
            Disable();
            return;
        }
        
        if(!MailItemsMailManager.LoadConfig()) {
            Disable();
            return;
        }
        
        /*** Load Listeners ***/
        AntiBuildListener = new MailItemsAntiBuildListener();
        ChatListener = new MailItemsChatListener();
        CustomEvent = new MailItemsCustomEventListener();
        
        /*** Register Commands ***/
        getCommand("senditem").setExecutor(SendItemCommand);
        getCommand("mailitems").setExecutor(MailItemCommand);
        getCommand("mailbox").setExecutor(MailBoxCommand);
        
        for(String s : MailItemsPluginManager.getCommands()) {
            getCommand(s).setPermissionMessage(MailItemsBase.ChatError + MailItemsPluginManager.PluginYML.getString("permission"));
        }
        
        /*** Register Listeners ***/
        pluginManager.registerEvents(AntiBuildListener, this);
        pluginManager.registerEvents(ChatListener, this);
        pluginManager.registerEvents(CustomEvent, this);
        
        MailItemsBase.permBroadcast("MailItems.*", "§dLoaded " + MailItemsPluginManager.getName() + " v" + MailItemsPluginManager.getVersion() + " successfully!");
        pluginEnabled = true;
    }
    
    @Override
    public void onDisable() {
        if(!pluginEnabled) {
            Bukkit.getLogger().info("Failed to load " + MailItemsPluginManager.getName());
            return;
        }
        
        /*** Stop Threads ***/
        MailItemsMailManager.SaveMailBoxes();
    }
    
    public void Disable() {
        pluginManager.disablePlugin(this);
    }
    
    public static MailItemsPlugin getPlugin() {
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("MailItems");
            if(plugin == null || !(plugin instanceof MailItemsPlugin)) {
                return null;
            }
            
            return (MailItemsPlugin) plugin;
        } catch(NoClassDefFoundError e) {
            return null;
        }
    }
}
