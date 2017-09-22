package com.jshah.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
        Document myDoc = collection.find(eq("email", email)).first();
        collection.deleteOne(myDoc);
    }

    public Document findUser(String email) {
        return collection.find(eq("email", email)).first();
    }
}
