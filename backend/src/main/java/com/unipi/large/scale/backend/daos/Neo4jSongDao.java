package com.unipi.large.scale.backend.daos;

import com.unipi.large.scale.backend.entities.neo4j.Neo4jSong;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.summary.SummaryCounters;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Neo4jSongDao extends Neo4jDao{

    public Optional<Neo4jSong> getByMongoId(String id) {

        try (Session session = driver.session()){

            List<Neo4jSong> songs = session.readTransaction(transaction -> {
                String query = """
                    MATCH (s:Song)
                    WHERE s.mongoId = $mongo_id
                    RETURN s""";

                Map<String, Object> params = Collections.singletonMap("mongo_id", id);
                return getNeo4jSongs(transaction, query, params);
            });
            return songs.stream().findFirst();
        }
    }

    public List<Neo4jSong> getRecommendedSongs(String id) {

        try (Session session = driver.session()){

            return session.readTransaction(transaction -> {
                String query = """
                        MATCH (u:User)-[r:SIMILAR_TO]->(su:User)-[p:LIKES]->(s:Song)
                        WHERE NOT (u)-[:LIKES]->(s) AND u.mongoId = $mongo_id
                        OPTIONAL MATCH (u)-[f:FRIEND_REQUEST {status: "accepted"}]->(su)
                        WITH s AS Song, u AS User, su as SimilarUser, p as Preference,
                            CASE when count(f)>0 then r.weight * 2 else r.weight end AS Weight
                        OPTIONAL MATCH (u:User)-[p:LIKES]->(s:Song)<-[p2:LIKES]-(su:User)
                        WHERE su.mongoId <> u.mongoId AND p.value = p2.value AND u = User AND su = SimilarUser
                        WITH User, Song, SimilarUser, count(p) AS Coherence, Weight, Preference
                        OPTIONAL MATCH (u:User)-[pr:LIKES]->(s:Song)
                        WHERE u = User
                        WITH count(pr) AS numLikes1, Song, SimilarUser, User, Coherence, Weight, Preference
                        OPTIONAL MATCH (u:User)-[pr:LIKES]->(s:Song)
                        WHERE u = SimilarUser
                        WITH count(pr) AS numLikes2, numLikes1, Song, SimilarUser, User, Coherence, Weight, Preference
                        UNWIND [numLikes1,numLikes2] AS numLikes
                        WITH User, SimilarUser, Song, Preference, CASE when min(numLikes) <> 0 then Coherence/(toFloat(min(numLikes))) else 0 end AS BetaStrength, Weight
                        WITH Song, sum($alpha * Weight * Preference.value + $beta * BetaStrength * Preference.value) AS Strength
                        RETURN Song
                        ORDER BY Strength DESC
                        LIMIT 15
                        """;

                Map<String, Object> params = new HashMap<>();
                params.put("mongo_id", id);
                params.put("alpha", recommendedSongsConfigurationProperties.getAlpha());
                params.put("beta", recommendedSongsConfigurationProperties.getBeta());

                return getNeo4jSongs(transaction, query, params);
            });
        }
    }

    public SummaryCounters updateLikeRelationship(String fromId, String toId, int like){

        try (Session session = driver.session()){

            return session.writeTransaction(transaction -> {
                String query = """
                        MATCH(u: User)
                        MATCH(s: Song)
                        WHERE u.mongoId = $from_mongo_id
                        AND s.mongoId = $to_mongo_id
                        MERGE(u)-[r:LIKES]->(s)
                        SET r.value = $like""";

                Map<String, Object> params = new HashMap<>();
                params.put("from_mongo_id", fromId);
                params.put("to_mongo_id", toId);
                params.put("like", like);

                return runTransaction(transaction, query, params);
            });
        }
    }

    public Optional<Record> getLikeValue(String userId, String songId){

        try (Session session = driver.session()){

            return session.readTransaction(transaction -> {
                String query = """
                        MATCH (u:User {mongoId: $user_id})-[r:LIKES]->(s:Song {mongoId: $song_id})
                        RETURN r.value""";

                Map<String, Object> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("song_id", songId);

                try {
                    Result result = transaction.run(query, params);
                    return result.stream().findFirst();
                } catch (Neo4jException e) {
                    logger.error(query + " raised an exception", e.getMessage());
                    throw e;
                }
            });
        }
    }

    public SummaryCounters createSong(Neo4jSong song) {

        try (Session session = driver.session()){

            return session.writeTransaction(transaction -> {
                String query = """
                        CREATE (s: Song {mongoId: $mongo_id, name: $name, artists: $artists, album: $album})""";

                Map<String, Object> params = setCreateUpdateParameters(song);

                return runTransaction(transaction, query, params);
            });
        }
    }

    private List<Neo4jSong> getNeo4jSongs(Transaction transaction, String query, Map<String, Object> params) {

        try {
            Result result = transaction.run(query, params);
            return result.list(record -> objectMapper.convertValue(record.get(0).asMap(), Neo4jSong.class));
        } catch (Neo4jException e) {
            logger.error(query + " raised an exception", e.getMessage());
            throw e;
        }
    }

    private Map<String, Object> setCreateUpdateParameters(Neo4jSong song) {

        Map<String, Object> params = new HashMap<>();
        params.put("mongo_id", song.getMongoId());
        params.put("name", song.getName());
        params.put("artists", song.getArtists());
        params.put("album", song.getAlbum());
        return params;
    }

    public SummaryCounters deleteByMongoId(String id) {

        try (Session session = driver.session()){

            return session.writeTransaction(transaction -> {
                String query = """
                        MATCH (s:Song)
                        WHERE s.mongoId = $mongo_id
                        DETACH DELETE s""";

                Map<String, Object> params = Collections.singletonMap("mongo_id", id);

                return runTransaction(transaction, query, params);
            });
        }
    }
}
