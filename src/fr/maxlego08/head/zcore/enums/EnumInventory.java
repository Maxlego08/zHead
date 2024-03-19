package fr.maxlego08.head.zcore.enums;

public enum EnumInventory {

    HEADS(1),
    HEADS_PAGINATION(2);

    private final int id;

    private EnumInventory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
