package io.github.cynergy.authservice.database;

import javax.annotation.PreDestroy;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import io.github.cynergy.authservice.model.User;
import io.github.cynergy.authservice.utils.UserNotFoundException;

import com.mongodb.client.model.Filters;

@Repository("mongo-legacy")
public class MongoDBLegacy implements UserDao {

	MongoClient client;
	MongoDatabase userDb;

	String rollNumberField = "reg_no";
	String passwordField = "password";
	String clearanceField = "clearance";

	@Value("${auth.db.usercollection.name}")
	String userCollectionName;

	@Autowired
	public MongoDBLegacy(@Value("${auth.db.name}") String databaseName, @Value("${auth.db.uri}") String databaseUri) {
		MongoClientURI uri = new MongoClientURI(databaseUri);
		this.client = new MongoClient(uri);
		this.userDb = client.getDatabase(databaseName);
	}

	@Override
	public User getUser(String rollNumber) throws UserNotFoundException {
		// getting reference to the user collection
		MongoCollection<Document> userCollection = this.userDb.getCollection(userCollectionName);

		// finding the document of the given user
		Document userDoc = userCollection.find(Filters.eq(rollNumberField, rollNumber)).first();
		
		if (userDoc == null) {
			throw new UserNotFoundException("User not found in database.");
		}

		// returning the doc
		return docToUser(userDoc);
	}

	@Override
	public String addUser(User user) {
		// getting reference to user collection
		MongoCollection<Document> userCollection = this.userDb.getCollection(userCollectionName);
		
		// converting user object into a document
		Document userDoc = createUserDocument(user);

		// adding user to db
		userCollection.insertOne(userDoc);

		// returning the id
		return userDoc.get("_id").toString();
	}

	/**
	 * Converts an object of User class to a BSON Document.
	 * @param user
	 * @return the document
	 */
	private Document createUserDocument(User user) {
		Document userDoc = new Document();

		userDoc.append(rollNumberField, user.getRollNumber())
			.append(passwordField, user.getPassword())
			.append(clearanceField, user.getClearance());

		return userDoc;
	}

	/**
	 * Converts a BSON Document to an object of the User class.
	 * @param userDoc
	 * @return The object of User class.
	 */
	private User docToUser(Document userDoc) {
		return new User(
			(String) userDoc.get("_id").toString(),
			(String) userDoc.get(rollNumberField),
			(String) userDoc.get(passwordField),
			(int) userDoc.get(clearanceField)
		);
	}

	@PreDestroy
	public void close() {
		client.close();
	}
}