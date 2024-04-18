package fr.maxlego08.head.zcore.enums;

public enum Permission {

    ZHEAD_USE, ZHEAD_RELOAD, ZHEAD_HELP, ZHEAD_GIVE, ZHEAD_SEARCH, ZHEAD_INFO, ZHEAD_RANDOM, ZHEAD_SAVE, ZHEAD_MISCELLANEOUS, ZHEAD_PLANTS, ZHEAD_MONSTERS, ZHEAD_HUMANOID, ZHEAD_HUMANS, ZHEAD_FOOD_DRINKS, ZHEAD_DECORATION, ZHEAD_BLOCKS, ZHEAD_ANIMALS, ZHEAD_ALPHABET, ZHEAD_REFRESH, ZHEAD_PLAYER_HEAD;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
