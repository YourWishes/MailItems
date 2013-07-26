package com.domsplace.Objects;

public class MailItemsGriefType {
    
    public static final MailItemsGriefType INTERACT = new MailItemsGriefType("INTERACT");
    public static final MailItemsGriefType BREAK = new MailItemsGriefType("BREAK");
    public static final MailItemsGriefType PLACE = new MailItemsGriefType("PLACE");
    
    private String type;
            
    public MailItemsGriefType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return this.type;
    }
}
