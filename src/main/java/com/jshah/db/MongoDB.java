package com.jshah.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by jay.shah on 9/20/17.
 */

public class MongoDB {
    public void setupMongoDb() {
        MongoClient client = new MongoClient("127.0.0.1", 27017);
        MongoDatabase database = client.getDatabase("wallpaperbot");
        MongoCollection<Document> collection = database.getCollection("users");

        Document doc = new Document("name", "Jay Shah")
                .append("type", "user")
                .append("size", "1920x1080");
        collection.insertOne(doc);
        System.out.println(collection.count());

        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
}
