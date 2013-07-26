package com.domsplace.Commands;

import com.domsplace.DataManagers.MailItemsConfigManager;
import com.domsplace.DataManagers.MailItemsMailManager;
import com.domsplace.MailItemsBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MailItemsCommandMailItems extends MailItemsBase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("mailitems")) {
            sender.sendMessage(ChatDefault + "Reloading Config...");
            MailItemsConfigManager.LoadConfig();
            MailItemsMailManager.SaveMailBoxes();
            sender.sendMessage(ChatImportant + "Reloaded Config!");
            return true;
        }
        return false;
    }
}
