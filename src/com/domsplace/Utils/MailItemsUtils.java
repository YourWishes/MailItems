package com.domsplace.Utils;

import com.domsplace.MailItemsBase;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MailItemsUtils extends MailItemsBase {
    public static net.milkbowl.vault.economy.Economy economy;
    
    public static Boolean setupEconomy() {
        try {
            RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> economyProvider = getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                economy = economyProvider.getProvider();
            }
            return (economy != null);
        } catch(NoClassDefFoundError e) {
            economy = null;
            return false;
        } catch(Exception ex) {
            economy = null;
            return false;
        }
    }
}
