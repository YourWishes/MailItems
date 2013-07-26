package com.domsplace.Commands;

import com.domsplace.DataManagers.MailItemsMailManager;
import com.domsplace.DataManagers.MailItemsPluginManager;
import com.domsplace.MailItemsBase;
import com.domsplace.Objects.MailItemBox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MailItemsCommandMailbox extends MailItemsBase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("mailbox")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatError + "Only players can have mailboxes.");
            }
            
            Player p = (Player) sender;
            MailItemBox mailBox = getMailBox(p);
            if(args.length < 1) {
                if(mailBox == null) {
                    sender.sendMessage(ChatError + "You don't have a mailbox. Type /" + label + " set");
                    return false;
                }
                
                if(mailBox.isEmpty()) {
                    sender.sendMessage(ChatDefault + "Your mailbox is empty.");
                } else {
                    sender.sendMessage(ChatImportant + "You have items!");
                }
                return true;
            }
            
            if(args[0].equalsIgnoreCase("set")) {
                if(!p.hasPermission("MailItems.mailbox.set")) {
                    sender.sendMessage(ChatError + MailItemsPluginManager.PluginYML.getString("permission"));
                    return true;
                }
                
                /*** Make sure the player is holding a chest ***/
                Block targetedBlock = p.getTargetBlock(null, 100);
                if(targetedBlock == null || !isChest(targetedBlock)) {
                    sender.sendMessage(ChatError + "Please look at a chest.");
                    return true;
                }
                
                if(isMailbox(targetedBlock)) {
                    sender.sendMessage(ChatError + "This mailbox is already claimed!");
                    return true;
                }
                
                Chest chest = getChest(targetedBlock);
                
                MailItemBox newMailBox = new MailItemBox(chest, Bukkit.getOfflinePlayer(p.getName()));
                
                if(mailBox != null) {
                    MailItemBox.mailBoxes.remove(mailBox);
                    mailBox = null;
                }
                
                sender.sendMessage(ChatDefault + "Created mailbox!");
                MailItemsMailManager.SaveMailBoxes();
                return true;
            }
            
            sender.sendMessage(ChatError + "Uknown command " + args[0]);
            return false;
        }
        return false;
    }
}
