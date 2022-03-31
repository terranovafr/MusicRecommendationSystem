package com.unipi.large.scale.backend.repositories;

import com.unipi.large.scale.backend.data.aggregations.Album;
import com.unipi.large.scale.backend.data.aggregations.AverageMusicFeatures;
import com.unipi.large.scale.backend.data.aggregations.Id;
import com.unipi.large.scale.backend.entities.mongodb.MongoSong;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomSongRepository extends CustomRepository{

    public MongoSong findById(ObjectId id) {

        return mongoTemplate.findById(id, MongoSong.class);
    }

    public MongoSong createSong(MongoSong song) {

        return mongoTemplate.insert(song);
    }

    public List<MongoSong> findSongsSortBy(String field, Sort.Direction direction) {

        Query query = new Query();
        query.with(Sort.by(direction, field));
        query.fields().include("id");
        query.limit(1000);
        return mongoTemplate.find(query, MongoSong.class);
    }

    public List<Album> getClusterKHighestRatedAlbums(int cluster, int k) {

        MatchOperation matchCluster = Aggregation.match(Criteria.where("cluster").is(cluster));

        UnwindOperation unwindLikes = Aggregation.unwind("likes");

        ProjectionOperation projectResult = Aggregation.project("_id", "album", "likes")
                .and("cluster").eq("$likes.cluster").as("result");

        MatchOperation matchResult = Aggregation.match(Criteria.where("result").is(true));

        ProjectionOperation projectSum = Aggregation.project("_id", "album")
                .and("$likes.numLikes").minus("$likes.numUnlikes").as("totalLikes");

        GroupOperation groupByAlbum = Aggregation.group("album")
                .sum("totalLikes").as("strength");

        SortOperation sortByStrength = Aggregation.sort(Sort.by(Sort.Direction.DESC, "strength"));

        LimitOperation limit = Aggregation.limit(k);

        Aggregation aggregation = Aggregation.newAggregation(matchCluster, unwindLikes, projectResult, matchResult, projectSum, groupByAlbum, sortByStrength, limit);

        AggregationResults<Album> results = mongoTemplate.aggregate(aggregation, "song", Album.class);

        return results.getMappedResults();
    }

    public Id getMostDanceableCluster() {

        SortOperation sortByDanceability = Aggregation.sort(Sort.by(Sort.Direction.DESC, "danceability"));

        LimitOperation limit = Aggregation.limit(500);

        GroupOperation groupByCluster = Aggregation.group("cluster")
                .count().as("strength");

        SortOperation sortByStrength = Aggregation.sort(Sort.by(Sort.Direction.DESC, "strength"));

        LimitOperation limitToOne = Aggregation.limit(1);

        ProjectionOperation projectCluster = Aggregation.project("_id");

        Aggregation aggregation = Aggregation.newAggregation(sortByDanceability, limit, groupByCluster, sortByStrength, limitToOne, projectCluster);

        AggregationResults<Id> results = mongoTemplate.aggregate(aggregation, "song", Id.class);

        return results.getUniqueMappedResult();
    }

    public List<AverageMusicFeatures> getAverageMusicFeaturesByCluster() {

        GroupOperation groupByCluster = Aggregation.group("cluster")
                .avg("danceability").as("danceability")
                .avg("acousticness").as("acousticness")
                .avg("energy").as("energy")
                .avg("instrumentalness").as("instrumentalness")
                .avg("liveness").as("liveness")
                .avg("valence").as("valence");

        Aggregation aggregation = Aggregation.newAggregation(groupByCluster);

        AggregationResults<AverageMusicFeatures> results = mongoTemplate.aggregate(aggregation, "song", AverageMusicFeatures.class);

        return results.getMappedResults();
    }

    public List<MongoSong> findAllIds() {

        Query query = new Query();
        query.fields().include("id");
        return mongoTemplate.find(query, MongoSong.class);
    }

    public void bulkDeleteSongs(List<MongoSong> songs) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoSong.class);
        for (MongoSong song: songs) {
            Query query = Query.query(Criteria.where("id").is(song.getId()));
            bulkOperations.remove(query);
        }
        System.out.println(bulkOperations.execute());
    }

    public boolean deleteSongById(ObjectId id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, MongoSong.class).wasAcknowledged();
    }

    public List<MongoSong> getSongsStartingWithName(String name) {

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^" + name, "i"));
        query.with(Sort.by(Sort.Direction.ASC, "name"));
        query.fields().include("name").include("album").include("artists");
        query.limit(10);
        return mongoTemplate.find(query, MongoSong.class);
    }

    public List<MongoSong> findToGenerateComments() {

        Query query = new Query();
        query.fields().include("id");
        query.limit(10000);
        return mongoTemplate.find(query, MongoSong.class);
    }

    public void bulkUpdateComments(List<MongoSong> songs) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoSong.class);
        for (MongoSong song: songs) {
            Update update = new Update();
            update.set("comments", song.getComments());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(song.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public boolean updateComments(MongoSong song) {

        Update update = new Update();
        update.set("comments", song.getComments());
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(song.getId())), update, MongoSong.class).wasAcknowledged();
    }

    public void bulkUpdateLikes(List<MongoSong> songs) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoSong.class);
        for (MongoSong song: songs) {
            Update update = new Update();
            update.set("likes", song.getLikes());
            update.set("cluster", song.getCluster());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(song.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public boolean updateLikes(MongoSong song) {

        Update update = new Update();
        update.set("likes", song.getLikes());
        update.set("cluster", song.getCluster());
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(song.getId())), update, MongoSong.class).wasAcknowledged();
    }

}
