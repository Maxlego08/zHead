package fr.maxlego08.head.zcore.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.maxlego08.head.ZHead;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.api.enums.HeadCategory;
import fr.maxlego08.head.zcore.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class HeadAdapter extends TypeAdapter<Head> {

    private static final Type seriType = new TypeToken<Map<String, Object>>() {
    }.getType();

    @Override
    public void write(JsonWriter out, Head value) throws IOException {
        //
    }

    @Override
    public Head read(JsonReader in) throws IOException {
        String name = "";
        String id = "";
        HeadCategory headCategory = HeadCategory.MISCELLANEOUS;
        String value = "";
        String tags = "";

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    name = in.nextString();
                    break;
                case "id":
                    id = in.nextString();
                    break;
                case "category":
                    String categoryName = in.nextString();
                    headCategory = HeadCategory.fromString(categoryName);
                    if (headCategory == null) {
                        Logger.info("The category '" + categoryName + "' cannot be found", Logger.LogType.ERROR);
                        headCategory = HeadCategory.MISCELLANEOUS;
                    }
                    break;
                case "value":
                    value = in.nextString();
                    break;
                case "tags":
                    tags = in.nextString();
                    break;
                default:
                    in.skipValue();
            }
        }
        in.endObject();
        return new ZHead(id, name, value, tags, headCategory);
    }
}
