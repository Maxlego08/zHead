package fr.maxlego08.head;

import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.enums.HeadCategory;

public class ZHead implements Head {

    private final String id;
    private final String name;
    private final String value;
    private final String tags;
    private final HeadCategory headCategory;

    public ZHead(String id, String name, String value, String tags, HeadCategory headCategory) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.tags = tags;
        this.headCategory = headCategory;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getTags() {
        return tags;
    }

    @Override
    public HeadCategory getHeadCategory() {
        return headCategory;
    }
}


