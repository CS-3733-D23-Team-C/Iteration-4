package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class GiftBasket{
    @Getter
    @Setter
    private String giftName;
    @Getter @Setter private String additionalInfo;

    public GiftBasket(String name, String additionalInfo) {
        this.giftName = name;
        this.additionalInfo = additionalInfo;
    }
    @Override
    public String toString() {
        return this.giftName;
    }
}
