package com.unipi.large.scale.backend.repositories;

import com.unipi.large.scale.backend.entities.mongodb.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomCommentRepository extends CustomRepository{

    public List<Comment> bulkInsertComments(List<Comment> comments) {

        return mongoTemplate.insert(comments, Comment.class).stream().toList();
    }

    public Comment insertComment(Comment comment) {

        return mongoTemplate.insert(comment);
    }

    public List<Comment> getSongComments(ObjectId id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("song_id").is(id));
        query.with(Sort.by("date").descending().and(Sort.by("id").descending()));
        return mongoTemplate.find(query, Comment.class);
    }

    public boolean deleteSongComments(ObjectId id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("song_id").is(id));
        return mongoTemplate.remove(query, Comment.class).wasAcknowledged();
    }

    public boolean deleteUserComments(ObjectId id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(id));
        return mongoTemplate.remove(query, Comment.class).wasAcknowledged();
    }
}
