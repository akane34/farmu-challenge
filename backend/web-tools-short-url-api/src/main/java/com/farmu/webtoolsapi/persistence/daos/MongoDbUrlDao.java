package com.farmu.webtoolsapi.persistence.daos;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import com.farmu.webtoolsapi.domain.UrlDto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.Date;

public class MongoDbUrlDao implements UrlDao{

    private final MongoDatabase mongoDatabase;
    public final static String COLLECTION_NAME = "short_urls";

    public MongoDbUrlDao(MongoDatabase mongoDatabase){
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public Result<UrlDto> find(String hashUrl) {
        Result<UrlDto> result = new Result<>();

        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);

        Document searchQuery = new Document();
        searchQuery.put("hashUrl", hashUrl);
        FindIterable<Document> cursor = collection.find(searchQuery);

        UrlDto urlDto = null;
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                Document item = cursorIterator.next();

                urlDto = new UrlDto();
                urlDto.setUrl(item.getString("url"));
                urlDto.setHashUrl(item.getString("hashUrl"));
                break;
            }
        }

        result.setResult(urlDto);
        if (urlDto == null){
            result.addError("Url not found", ErrorCode.CLIENT_ERROR);
        }

        return result;
    }

    @Override
    public Result<Boolean> save(UrlDto url) {
        Result<Boolean> result = new Result<>(true);

        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);
        Document document = new Document();
        document.put("url", url.getUrl());
        document.put("hashUrl", url.getHashUrl());
        document.put("creationDate", new Date());

        InsertOneResult insertResult = collection.insertOne(document);
        if (!insertResult.wasAcknowledged()){
            result.addError("error saving data", ErrorCode.SERVER_ERROR);
            result.setResult(false);
        }

        return result;
    }

    @Override
    public Result<Boolean> delete(UrlDto url) {
        Result<Boolean> result = new Result<>(true);

        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);

        Document searchQuery = new Document();
        searchQuery.put("hashUrl", url.getHashUrl());

        DeleteResult deleteResult = collection.deleteOne(searchQuery);
        if (!deleteResult.wasAcknowledged()){
            result.addError("Error deleting url", ErrorCode.SERVER_ERROR);
        }

        return result;
    }
}
