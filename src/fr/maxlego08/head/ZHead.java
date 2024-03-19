package fr.maxlego08.head;

import fr.maxlego08.head.api.Head;

import java.util.UUID;

public class ZHead implements Head {

    private final String name;
    private final UUID uuid;
    private final String value;
    private final String tags;

    public ZHead(String name, UUID uuid, String value, String tags) {
        this.name = name;
        this.uuid = uuid;
        this.value = value;
        this.tags = tags;
    }


    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getValue() {
        return value;
    }

    public String getTags() {
        return tags;
    }
}
