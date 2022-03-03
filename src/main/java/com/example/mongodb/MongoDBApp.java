package com.example.mongodb;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MongoDBApp {
	public static MongoClient client = new MongoClient("localhost",27017);
	
    public static void main( String[] args ){	
		try {						
			MongoDatabase infoDB = client.getDatabase("info");
	//		infoDB.createCollection("Personel");   		// collection(tablo) olusturma
			
			MongoCollection<Document> personelCollection = infoDB.getCollection("Personel");
			
		//	createRecord(personelCollection);			
		//	findRecord(personelCollection);
		//	updateRecord(personelCollection);
		//	deleteRecord(personelCollection);
			
			personelCollection.drop();		// collection yani tabloyu siler
			infoDB.drop();					//database i siler
			
		} catch (Exception e) {
			System.out.println("MongoDB connect error : " + e);
		}finally {
			client.close();
		}   	       
    }
    
    
    public static void createRecord(MongoCollection<Document> personelCollection) {
    	BasicDBObject data = new BasicDBObject()
    			.append("name", "Tombul Topaç")
    			.append("date", "2000")
    			.append("country", "Muğla")
    			.append("meslek", "Ogrenci");
    	
    	BasicDBObject data2 = new BasicDBObject()
    			.append("name", "Elon Musk")
    			.append("date", "1971")
    			.append("country", "Africa");

    //	personelCollection.insertOne(Document.parse(data.toJson()));
    	
    	Document parse = Document.parse(data.toJson());
    	Document parse2 =Document.parse(data2.toJson());
    	personelCollection.insertMany(Arrays.asList(parse,parse2));
    }
    
    
    public static void findRecord(MongoCollection<Document> personelCollection) {
    	//All records 
    //	FindIterable<Document> documents = personelCollection.find();
    	
    	//filter records
    	FindIterable<Document> documents = personelCollection.find(new BasicDBObject("date","1960"));
    	for(Document doc: documents) {
    		System.out.println(doc.toJson());
    	}    	
    }
    
    public static void updateRecord(MongoCollection<Document> personelCollection) {
    //	Bson filter = Filters.eq("name", "Ali Gel");  	// eşitse    	
    	Bson filter = Filters.exists("meslek");			// varsa
    	Bson update = Updates.set("not", "4.99");
    	personelCollection.updateOne(filter, update);		// One.. ilk documenti guncelle 
    }
    
    public static void deleteRecord(MongoCollection<Document> personelCollection) {
    	Bson filter = Filters.eq("date", "2010");
    	personelCollection.deleteOne(filter);
    }
}
