package fr.maxlego08.head.zcore.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.maxlego08.head.ZHead;
import fr.maxlego08.head.api.Head;
import fr.maxlego08.head.zcore.ZPlugin;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

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
        String uuid = "";
        String value = "";
        String tags = "";

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    name = in.nextString();
                    break;
                case "uuid":
                    uuid = in.nextString();
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
        return new ZHead(name, UUID.fromString(uuid), value, tags);
    }
}
