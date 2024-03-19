package fr.maxlego08.head.api.enums;

import fr.maxlego08.head.api.Head;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public enum HeadCategory {

    ALPHABET("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZkZTMzMTQzODJiMjEwYjM0ZThjMWI1ZDI1YWU0Yjc1Yjg0OTA0NmU4ZWEwZWYwZTgwMGEzMjhiYWFiNWY3ZSJ9fX0="),
    ANIMALS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFjYzc4YzAxYTQwZDQ1NTc3MTI2YjBmYTg4OTZjZTM2ZWY3MWZmYTI0NDQ0NzQ2MDBmNjU5YzhjYzU1MjVkYSJ9fX0="),
    BLOCKS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVhYTlhYzE1NzU4ZDUxNzdhODk2NjA1OTg1ZTk4YmVhYzhmZWUwZTZiMmM2OGE4ZGMxZjNjOTFjMDc5ZmI4OSJ9fX0="),
    DECORATION("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZmMDQxOTc2YTA5ZGQwNTNlM2QxZDRlNjExYWFjMDk1OTRkNzRmYzcxYTBlYzRkYTAxMTA0MTZkMzE3ZGJhOCJ9fX0="),
    FOOD_DRINKS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJlZTMyZjExNzAwMDJlNWUwMGJmNDZmZTM4YTlhMzU5ZmUwODk5ODY4NmRiYjQzOGFkMjI4ZWI4ZjZmNDRkIn19fQ=="),
    HUMANS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwNDQyZWE3OTEwM2Y2Y2Q4YmQ2OGM3ZmRmNDQ3Yzk1ZGNlYWZhOWY2YzRjNjliZjc5MDZiMzdiZjIxOTE4NiJ9fX0="),
    HUMANOID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY2Y2NkMDEzOWUxZjllZDI3NTU0YTBjMzlhNGVmZmM3MzYxMGI4NmFhMDU3OWU0MDVmNjQxY2QxZDY3OGM0NSJ9fX0="),
    MISCELLANEOUS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3YzU0ZmY3ODYyMTE2ZTY1YTE0MzY2MjBiOTFhZjU4YjUyYWIxNzE1MmExODM3MTgwZjM0NTgwMzJmNTcwMiJ9fX0="),
    MONSTERS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU0ZjJiNGNiNmQyMWQzNTZlYzVjMGNiNmY1MTY2ZmVlMzExOWM3ZGM1OWUyMDgzOWMyMDMzMWNkMTNlNDM5ZCJ9fX0="),
    PLANTS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAwNmQ1YWUwNmM4MWI5OTk2MTkxMjA3ZWVkYWIzMzA1ZTYzYWQ4MTAzY2IxYTU5MjQzZDE0OGUyNDQ0NWFlMCJ9fX0=");

    private final String url;

    HeadCategory(String url) {
        this.url = url;
    }

    public String getName() {
        return name().toLowerCase().replace("_", "-");
    }

    public File getFile(Plugin plugin) {
        File folder = new File(plugin.getDataFolder(), "heads");
        if (!folder.exists()) folder.mkdirs();
        return new File(folder, getName() + ".json");
    }

    public String getUrl() {
        return url;
    }
}
