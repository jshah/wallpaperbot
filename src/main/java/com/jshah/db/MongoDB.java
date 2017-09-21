package com.jshah.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
