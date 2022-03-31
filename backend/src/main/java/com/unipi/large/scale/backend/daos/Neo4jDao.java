package com.unipi.large.scale.backend.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unipi.large.scale.backend.configs.RecommendedSongsConfigurationProperties;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.summary.SummaryCounters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@EnableConfigurationProperties(RecommendedSongsConfigurationProperties.class)
abstract class Neo4jDao {

    protected final Logger logger = LoggerFactory.getLogger(Neo4jDao.class);

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected Driver driver;

    @Autowired
    protected RecommendedSongsConfigurationProperties recommendedSongsConfigurationProperties;

    SummaryCounters runTransaction(Transaction transaction, String query, Map<String, Object> params) {

        try {
            return transaction.run(query, params).consume().counters();
        } catch (Neo4jException e) {
            logger.error(query + " raised an exception", e);
            throw e;
        }
    }

    int getCount(Transaction transaction, String query, Map<String, Object> params) {

        try {
            Result result = transaction.run(query, params);
            return result.single().get(0).asInt();
        } catch (Neo4jException e) {
            logger.error(query + " raised an exception", e.getMessage());
            throw e;
        }
    }
}
