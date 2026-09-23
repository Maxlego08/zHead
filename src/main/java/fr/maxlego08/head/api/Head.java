package fr.maxlego08.head.api;

import fr.maxlego08.head.api.enums.HeadCategory;

import java.util.UUID;

public interface Head {

    String getName();

    HeadCategory getHeadCategory();

    String getId();

    String getValue();

    String getTags();
}
