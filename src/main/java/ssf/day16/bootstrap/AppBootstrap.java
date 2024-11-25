package ssf.day16.bootstrap;

import java.io.Reader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import ssf.day16.service.HttpbinService;

@Component
public class AppBootstrap implements CommandLineRunner {    // CommandLineRunner - always runs first when app loads
    @Autowired
    private HttpbinService httpBinSvc;

    @Override
    public void run(String... args) {

        //JsonIntroDemo();

        //httpBinSvc.get();

        //httpBinSvc.getJokes();

        httpBinSvc.postForm();

        httpBinSvc.postJson();
    }

    private void JsonIntroDemo() {
        String jsonData = """
            {
                "name": "barney",
                "email": "barney@gmail.com",
                "age": 50,
                "married": true,
                "address": {
                    "street": "1 bedrock ave",
                    "postcode": "abc123"
                },
                "hobbies": ["skiing", "reading", "jogging"]
            } 
        """;

        // Get an object builder
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        objBuilder = objBuilder
                    .add("name", "fred")
                    .add("email", "fred@gmail.com")
                    .add("age", 50)
                    .add("married", true);
                    
        // Mtd 1
        // JsonObjectBuilder addrBuilder = Json.createObjectBuilder();
        // addrBuilder = addrBuilder
        //             .add("street", "1 bedrock ave")
        //             .add("postcode", "abc123");
                    
        // JsonObject addr = addrBuilder.build();  // JsonObjects are immutable - can no longer be changed once built!

        // Mtd 2
        JsonObject addr = Json.createObjectBuilder()
                    .add("street", "1 bedrock ave")
                    .add("postcode", "abc123")
                    .build();

        JsonArray hobbies = Json.createArrayBuilder()
                    .add("skiing")
                    .add("reading")
                    .add("jogging")
                    .build();
        
        JsonObject fred = objBuilder
                    .add("address", addr)
                    .add("hobbies", hobbies)
                    .build();
                
        // JsonObj -> String
        //-------------------
        System.out.printf("Fred as JSON: \n%s\n", fred.toString());
        
        System.out.printf("Name: %s\n", fred.getString("name"));
        System.out.printf("Age: %d\n", fred.getInt("age"));
        // Get obj within JsonObj
        JsonObject tmpObj = fred.getJsonObject("address");
        System.out.printf("Street: %s\n", tmpObj.getString("street"));
        // Get arr within JsonObj
        JsonArray tmpArr = fred.getJsonArray("hobbies");
        System.out.printf("Hobbies[1]: %s\n", tmpArr.getString(1));
        
        // String -> JsonObj
        //-------------------
        Reader reader = new StringReader(jsonData);
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject barney = jsonReader.readObject();

        System.out.printf("Barney: \n%s\n", barney.toString());
        
        String jsonArrdata = "[123, 245, 789]";
        reader = new StringReader(jsonArrdata);
        jsonReader = Json.createReader(reader);

        JsonArray numList = jsonReader.readArray();
        for(int i = 0; i < numList.size(); i++)
            System.out.printf("%d: %s\n", i, numList.get(i));
    }    
}
