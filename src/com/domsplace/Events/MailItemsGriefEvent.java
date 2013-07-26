package com.domsplace.Events;

import com.domsplace.Objects.MailItemsGriefType;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MailItemsGriefEvent extends MailItemsCustomEventBase {
    private Player player;
    private Block block;
    private MailItemsGriefType type;
    
    public MailItemsGriefEvent (Player griefer, Block mainBlock, List<Block> blocks, MailItemsGriefType type) {
        if(!blocks.contains(mainBlock)) {
            blocks.add(mainBlock);
        }
        
        this.player = griefer;
        this.block = mainBlock;
        this.type = type;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public MailItemsGriefType getType() {
        return this.type;
    }
}
