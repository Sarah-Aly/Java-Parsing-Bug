package samplepackage;

import com.google.gson.*;
import com.microsoft.azure.eventgrid.models.EventGridEvent;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import java.lang.reflect.Type;
import org.joda.time.DateTime;

public class Function {

    /**
     * Workaround using a custom deserializer
     */
    private final static Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>() {
        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new DateTime(json.getAsString());
        }
    }).create();
    
    @FunctionName("EventGridTrigger_String")
    public void run(@EventGridTrigger(name = "eventGridEvent") String message, final ExecutionContext context) {

        context.getLogger().info("Java Event Grid trigger (String) function executed.");
        
        EventGridEvent event = gson.fromJson(message, EventGridEvent.class);
        context.getLogger().info("Event content: ");
        context.getLogger().info("Subject: " + event.subject());
        context.getLogger().info("Time: " + event.eventTime().toString());
        context.getLogger().info("Id: " + event.id());
        context.getLogger().info("Data: " + event.data());
    }


    /**
     * Code fails with parsing
     */
   /* private final static Gson gson = new Gson();

    @FunctionName("eventGridMonitorString")
    public void run(@EventGridTrigger(name = "eventGridEvent") String message, final ExecutionContext context) {

        context.getLogger().info("Java Event Grid trigger (String) function executed.");   
        
        EventGridEvent event = gson.fromJson(message, EventGridEvent.class);
        context.getLogger().info("Event content: ");
        context.getLogger().info("Subject: " + event.subject());
        context.getLogger().info("Time: " + event.eventTime().toString());
        context.getLogger().info("Id: " + event.id());
        context.getLogger().info("Data: " + event.data());
    }*/

}
