package com.drinkscabinet.activity;

import com.ibm.common.activitystreams.ASObject;
import com.ibm.common.activitystreams.IO;
import org.apache.streams.jackson.StreamsJacksonMapper;
import org.apache.streams.pojo.StreamsJacksonMapperConfiguration;
import org.apache.streams.pojo.json.Activity;
import org.apache.streams.pojo.json.ActivityObject;
import org.apache.streams.pojo.json.objectTypes.Note;

import java.io.IOException;

public class ActivityRead {

    private static final String testActivity = "{\"@context\": \"https://www.w3.org/ns/activitystreams#\",\n" +
            " \"type\": \"Note\",\n" +
            " \"to\": [\"https://chatty.example/ben/\"],\n" +
            " \"attributedTo\": \"https://social.example/alyssa/\",\n" +
            " \"content\": \"Say, did you finish reading that book I lent you?\"}";

    private static final String test2 = "{\n" +
            "  \"@context\": {\n" +
            "     \"@vocab\": \"https://www.w3.org/ns/activitystreams\",\n" +
            "     \"ext\": \"https://canine-extension.example/terms/\",\n" +
            "     \"@language\": \"en\"\n" +
            "  },\n" +
            "  \"summary\": \"A note\",\n" +
            "  \"type\": \"Note\",\n" +
            "  \"content\": \"My dog has fleas.\",\n" +
            "  \"ext:nose\": 0,\n" +
            "  \"ext:smell\": \"terrible\"\n" +
            "}";

    public static void main(String[] args) throws IOException {

        // Open a valid json(-ld) input file
////        InputStream inputStream = new FileInputStream("input.json");
//// Read the file into an Object (The type of this object will be a List, Map, String, Boolean,
//// Number or null depending on the root object in the file).
//        Object jsonObject = JsonUtils.fromString(test2);
//        // Create a context JSON map containing prefixes and definitions
//        Map context = new HashMap();
//// Customise context...
//// Create an instance of JsonLdOptions with the standard JSON-LD options
//        JsonLdOptions options = new JsonLdOptions();
//// Customise options...
//// Call whichever JSONLD function you want! (e.g. compact)
//        Object compact = JsonLdProcessor.compact(jsonObject, context, options);
//// Print out the result (or don't, it's your call!)
//        System.out.println(JsonUtils.toPrettyString(compact));
//
//        Object expand = JsonLdProcessor.expand(jsonObject, options);
//        System.out.println(JsonUtils.toPrettyString(expand));
//
//        StreamsJacksonMapper mapper = StreamsJacksonMapper.getInstance();
//        ActivityObject a = mapper.readerFor(ActivityObject.class).readValue(test2);
//        System.out.println(a);

        IO io = IO.makeDefaultPrettyPrint();
        ASObject read = io.read(test2);
        System.out.println(read);
    }
}
