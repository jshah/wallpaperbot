package main.java.com.jshah.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.List;

/**
 * Created by jay.shah on 9/20/17.
 */

public class MongoDB {
    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    public MongoDB() {
        this.client = new MongoClient("127.0.0.1", 27017);
        this.db = this.client.getDatabase("wallpaperbot");
        this.collection = db.getCollection("users");
    }

    public void addUser(String user, String email, List<String> resolutions) {
        Document doc = new Document();
        doc.put("user", user);
        doc.put("email", email);
        doc.put("resolutions", resolutions);
        this.collection.insertOne(doc);
    }

    public void deleteUser(String email) {
        Document myDoc = collection.find(Filters.eq("email", email)).first();
        collection.deleteOne(myDoc);
    }

    public Document findUser(String email) {
        return collection.find(Filters.eq("email", email)).first();
    }
}
