package com.drinkscabinet.activity;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.document.JsonDocument;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import com.ibm.common.activitystreams.ASObject;
import com.ibm.common.activitystreams.IO;
import jakarta.json.JsonArray;
import jakarta.json.JsonStructure;
import org.apache.streams.jackson.StreamsJacksonMapper;
import org.apache.streams.pojo.StreamsJacksonMapperConfiguration;
import org.apache.streams.pojo.json.Activity;
import org.apache.streams.pojo.json.ActivityObject;
import org.apache.streams.pojo.json.objectTypes.Note;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class ActivityRead {

    private static final String testActivity = "{\"@context\": \"https://www.w3.org/ns/activitystreams\",\n" +
            " \"type\": \"Note\",\n" +
            " \"to\": [\"https://chatty.example/ben/\"],\n" +
            " \"attributedTo\": \"https://social.example/alyssa/\",\n" +
            " \"content\": \"Say, did you finish reading that book I lent you?\"}";

    private static final String test2 = "{\n" +
            "  \"@context\": {\n" +
            "     \"@vocab\": \"https://www.w3.org/ns/activitystreams#\",\n" +
            "     \"ext\": \"https://canine-extension.example/terms/\",\n" +
            "     \"@language\": \"en\"\n" +
            "  },\n" +
            "  \"summary\": \"A note\",\n" +
            "  \"type\": \"Note\",\n" +
            "  \"content\": \"My dog has fleas.\",\n" +
            "  \"ext:nose\": 0,\n" +
            "  \"ext:smell\": \"terrible\"\n" +
            "}";

    public static void main(String[] args) throws IOException, JsonLdError {

        // Local document
        Document document = JsonDocument.of(new StringReader(test2));

        JsonStructure jsonStructure = JsonLd.flatten(document).get();
        System.out.println(jsonStructure);

        // Open a valid json(-ld) input file
////        InputStream inputStream = new FileInputStream("input.json");
//// Read the file into an Object (The type of this object will be a List, Map, String, Boolean,
//// Number or null depending on the root object in the file).
        Object jsonObject = JsonUtils.fromString(test2);
        // Create a context JSON map containing prefixes and definitions
        Map context = new HashMap();
// Customise context...
// Create an instance of JsonLdOptions with the standard JSON-LD options
        JsonLdOptions options = new JsonLdOptions();
// Customise options...
// Call whichever JSONLD function you want! (e.g. compact)
        Object compact = JsonLdProcessor.compact(jsonObject, context, options);
// Print out the result (or don't, it's your call!)
        System.out.println(JsonUtils.toPrettyString(compact));

        Object expand = JsonLdProcessor.expand(jsonObject, options);
        System.out.println(JsonUtils.toPrettyString(expand));

        Object flatten = JsonLdProcessor.flatten(jsonObject, context, options);
        System.out.println(flatten);
//
//        StreamsJacksonMapper mapper = StreamsJacksonMapper.getInstance();
//        ActivityObject a = mapper.readerFor(ActivityObject.class).readValue(test2);
//        System.out.println(a);

//        IO io = IO.makeDefaultPrettyPrint();
//        ASObject read = io.read(test2);
//        System.out.println(read);
    }

    private static final String as = "{\n" +
            "  \"@context\": {\n" +
            "    \"@vocab\": \"_:\",\n" +
            "    \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\n" +
            "    \"as\": \"https://www.w3.org/ns/activitystreams#\",\n" +
            "    \"ldp\": \"http://www.w3.org/ns/ldp#\",\n" +
            "    \"vcard\": \"http://www.w3.org/2006/vcard/ns#\",\n" +
            "    \"id\": \"@id\",\n" +
            "    \"type\": \"@type\",\n" +
            "    \"Accept\": \"as:Accept\",\n" +
            "    \"Activity\": \"as:Activity\",\n" +
            "    \"IntransitiveActivity\": \"as:IntransitiveActivity\",\n" +
            "    \"Add\": \"as:Add\",\n" +
            "    \"Announce\": \"as:Announce\",\n" +
            "    \"Application\": \"as:Application\",\n" +
            "    \"Arrive\": \"as:Arrive\",\n" +
            "    \"Article\": \"as:Article\",\n" +
            "    \"Audio\": \"as:Audio\",\n" +
            "    \"Block\": \"as:Block\",\n" +
            "    \"Collection\": \"as:Collection\",\n" +
            "    \"CollectionPage\": \"as:CollectionPage\",\n" +
            "    \"Relationship\": \"as:Relationship\",\n" +
            "    \"Create\": \"as:Create\",\n" +
            "    \"Delete\": \"as:Delete\",\n" +
            "    \"Dislike\": \"as:Dislike\",\n" +
            "    \"Document\": \"as:Document\",\n" +
            "    \"Event\": \"as:Event\",\n" +
            "    \"Follow\": \"as:Follow\",\n" +
            "    \"Flag\": \"as:Flag\",\n" +
            "    \"Group\": \"as:Group\",\n" +
            "    \"Ignore\": \"as:Ignore\",\n" +
            "    \"Image\": \"as:Image\",\n" +
            "    \"Invite\": \"as:Invite\",\n" +
            "    \"Join\": \"as:Join\",\n" +
            "    \"Leave\": \"as:Leave\",\n" +
            "    \"Like\": \"as:Like\",\n" +
            "    \"Link\": \"as:Link\",\n" +
            "    \"Mention\": \"as:Mention\",\n" +
            "    \"Note\": \"as:Note\",\n" +
            "    \"Object\": \"as:Object\",\n" +
            "    \"Offer\": \"as:Offer\",\n" +
            "    \"OrderedCollection\": \"as:OrderedCollection\",\n" +
            "    \"OrderedCollectionPage\": \"as:OrderedCollectionPage\",\n" +
            "    \"Organization\": \"as:Organization\",\n" +
            "    \"Page\": \"as:Page\",\n" +
            "    \"Person\": \"as:Person\",\n" +
            "    \"Place\": \"as:Place\",\n" +
            "    \"Profile\": \"as:Profile\",\n" +
            "    \"Question\": \"as:Question\",\n" +
            "    \"Reject\": \"as:Reject\",\n" +
            "    \"Remove\": \"as:Remove\",\n" +
            "    \"Service\": \"as:Service\",\n" +
            "    \"TentativeAccept\": \"as:TentativeAccept\",\n" +
            "    \"TentativeReject\": \"as:TentativeReject\",\n" +
            "    \"Tombstone\": \"as:Tombstone\",\n" +
            "    \"Undo\": \"as:Undo\",\n" +
            "    \"Update\": \"as:Update\",\n" +
            "    \"Video\": \"as:Video\",\n" +
            "    \"View\": \"as:View\",\n" +
            "    \"Listen\": \"as:Listen\",\n" +
            "    \"Read\": \"as:Read\",\n" +
            "    \"Move\": \"as:Move\",\n" +
            "    \"Travel\": \"as:Travel\",\n" +
            "    \"IsFollowing\": \"as:IsFollowing\",\n" +
            "    \"IsFollowedBy\": \"as:IsFollowedBy\",\n" +
            "    \"IsContact\": \"as:IsContact\",\n" +
            "    \"IsMember\": \"as:IsMember\",\n" +
            "    \"subject\": {\n" +
            "      \"@id\": \"as:subject\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"relationship\": {\n" +
            "      \"@id\": \"as:relationship\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"actor\": {\n" +
            "      \"@id\": \"as:actor\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"attributedTo\": {\n" +
            "      \"@id\": \"as:attributedTo\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"attachment\": {\n" +
            "      \"@id\": \"as:attachment\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"bcc\": {\n" +
            "      \"@id\": \"as:bcc\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"bto\": {\n" +
            "      \"@id\": \"as:bto\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"cc\": {\n" +
            "      \"@id\": \"as:cc\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"context\": {\n" +
            "      \"@id\": \"as:context\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"current\": {\n" +
            "      \"@id\": \"as:current\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"first\": {\n" +
            "      \"@id\": \"as:first\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"generator\": {\n" +
            "      \"@id\": \"as:generator\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"icon\": {\n" +
            "      \"@id\": \"as:icon\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"image\": {\n" +
            "      \"@id\": \"as:image\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"inReplyTo\": {\n" +
            "      \"@id\": \"as:inReplyTo\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"items\": {\n" +
            "      \"@id\": \"as:items\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"instrument\": {\n" +
            "      \"@id\": \"as:instrument\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"orderedItems\": {\n" +
            "      \"@id\": \"as:items\",\n" +
            "      \"@type\": \"@id\",\n" +
            "      \"@container\": \"@list\"\n" +
            "    },\n" +
            "    \"last\": {\n" +
            "      \"@id\": \"as:last\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"location\": {\n" +
            "      \"@id\": \"as:location\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"next\": {\n" +
            "      \"@id\": \"as:next\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"object\": {\n" +
            "      \"@id\": \"as:object\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"oneOf\": {\n" +
            "      \"@id\": \"as:oneOf\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"anyOf\": {\n" +
            "      \"@id\": \"as:anyOf\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"closed\": {\n" +
            "      \"@id\": \"as:closed\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"origin\": {\n" +
            "      \"@id\": \"as:origin\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"accuracy\": {\n" +
            "      \"@id\": \"as:accuracy\",\n" +
            "      \"@type\": \"xsd:float\"\n" +
            "    },\n" +
            "    \"prev\": {\n" +
            "      \"@id\": \"as:prev\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"preview\": {\n" +
            "      \"@id\": \"as:preview\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"replies\": {\n" +
            "      \"@id\": \"as:replies\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"result\": {\n" +
            "      \"@id\": \"as:result\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"audience\": {\n" +
            "      \"@id\": \"as:audience\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"partOf\": {\n" +
            "      \"@id\": \"as:partOf\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"tag\": {\n" +
            "      \"@id\": \"as:tag\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"target\": {\n" +
            "      \"@id\": \"as:target\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"to\": {\n" +
            "      \"@id\": \"as:to\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"url\": {\n" +
            "      \"@id\": \"as:url\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"altitude\": {\n" +
            "      \"@id\": \"as:altitude\",\n" +
            "      \"@type\": \"xsd:float\"\n" +
            "    },\n" +
            "    \"content\": \"as:content\",\n" +
            "    \"contentMap\": {\n" +
            "      \"@id\": \"as:content\",\n" +
            "      \"@container\": \"@language\"\n" +
            "    },\n" +
            "    \"name\": \"as:name\",\n" +
            "    \"nameMap\": {\n" +
            "      \"@id\": \"as:name\",\n" +
            "      \"@container\": \"@language\"\n" +
            "    },\n" +
            "    \"duration\": {\n" +
            "      \"@id\": \"as:duration\",\n" +
            "      \"@type\": \"xsd:duration\"\n" +
            "    },\n" +
            "    \"endTime\": {\n" +
            "      \"@id\": \"as:endTime\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"height\": {\n" +
            "      \"@id\": \"as:height\",\n" +
            "      \"@type\": \"xsd:nonNegativeInteger\"\n" +
            "    },\n" +
            "    \"href\": {\n" +
            "      \"@id\": \"as:href\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"hreflang\": \"as:hreflang\",\n" +
            "    \"latitude\": {\n" +
            "      \"@id\": \"as:latitude\",\n" +
            "      \"@type\": \"xsd:float\"\n" +
            "    },\n" +
            "    \"longitude\": {\n" +
            "      \"@id\": \"as:longitude\",\n" +
            "      \"@type\": \"xsd:float\"\n" +
            "    },\n" +
            "    \"mediaType\": \"as:mediaType\",\n" +
            "    \"published\": {\n" +
            "      \"@id\": \"as:published\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"radius\": {\n" +
            "      \"@id\": \"as:radius\",\n" +
            "      \"@type\": \"xsd:float\"\n" +
            "    },\n" +
            "    \"rel\": \"as:rel\",\n" +
            "    \"startIndex\": {\n" +
            "      \"@id\": \"as:startIndex\",\n" +
            "      \"@type\": \"xsd:nonNegativeInteger\"\n" +
            "    },\n" +
            "    \"startTime\": {\n" +
            "      \"@id\": \"as:startTime\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"summary\": \"as:summary\",\n" +
            "    \"summaryMap\": {\n" +
            "      \"@id\": \"as:summary\",\n" +
            "      \"@container\": \"@language\"\n" +
            "    },\n" +
            "    \"totalItems\": {\n" +
            "      \"@id\": \"as:totalItems\",\n" +
            "      \"@type\": \"xsd:nonNegativeInteger\"\n" +
            "    },\n" +
            "    \"units\": \"as:units\",\n" +
            "    \"updated\": {\n" +
            "      \"@id\": \"as:updated\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"width\": {\n" +
            "      \"@id\": \"as:width\",\n" +
            "      \"@type\": \"xsd:nonNegativeInteger\"\n" +
            "    },\n" +
            "    \"describes\": {\n" +
            "      \"@id\": \"as:describes\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"formerType\": {\n" +
            "      \"@id\": \"as:formerType\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"deleted\": {\n" +
            "      \"@id\": \"as:deleted\",\n" +
            "      \"@type\": \"xsd:dateTime\"\n" +
            "    },\n" +
            "    \"inbox\": {\n" +
            "      \"@id\": \"ldp:inbox\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"outbox\": {\n" +
            "      \"@id\": \"as:outbox\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"following\": {\n" +
            "      \"@id\": \"as:following\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"followers\": {\n" +
            "      \"@id\": \"as:followers\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"streams\": {\n" +
            "      \"@id\": \"as:streams\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"preferredUsername\": \"as:preferredUsername\",\n" +
            "    \"endpoints\": {\n" +
            "      \"@id\": \"as:endpoints\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"uploadMedia\": {\n" +
            "      \"@id\": \"as:uploadMedia\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"proxyUrl\": {\n" +
            "      \"@id\": \"as:proxyUrl\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"liked\": {\n" +
            "      \"@id\": \"as:liked\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"oauthAuthorizationEndpoint\": {\n" +
            "      \"@id\": \"as:oauthAuthorizationEndpoint\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"oauthTokenEndpoint\": {\n" +
            "      \"@id\": \"as:oauthTokenEndpoint\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"provideClientKey\": {\n" +
            "      \"@id\": \"as:provideClientKey\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"signClientKey\": {\n" +
            "      \"@id\": \"as:signClientKey\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"sharedInbox\": {\n" +
            "      \"@id\": \"as:sharedInbox\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"Public\": {\n" +
            "      \"@id\": \"as:Public\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"source\": \"as:source\",\n" +
            "    \"likes\": {\n" +
            "      \"@id\": \"as:likes\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"shares\": {\n" +
            "      \"@id\": \"as:shares\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    },\n" +
            "    \"alsoKnownAs\": {\n" +
            "      \"@id\": \"as:alsoKnownAs\",\n" +
            "      \"@type\": \"@id\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
