package com.unipi.large.scale.backend.service.db;

import com.unipi.large.scale.backend.daos.Neo4jSongDao;
import com.unipi.large.scale.backend.daos.Neo4jUserDao;
import com.unipi.large.scale.backend.service.Utils;
import com.unipi.large.scale.backend.service.clustering.Clustering;
import com.unipi.large.scale.backend.repositories.CustomCommentRepository;
import com.unipi.large.scale.backend.repositories.CustomSongRepository;
import com.unipi.large.scale.backend.repositories.CustomUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


abstract class EntityService {

    protected final Logger logger = LoggerFactory.getLogger(EntityService.class);

    @Autowired
    protected Neo4jUserDao neo4jUserDao;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected Clustering clustering;

    @Autowired
    protected Utils utils;

    @Autowired
    protected Neo4jSongDao neo4jSongDao;

    @Autowired
    protected CustomUserRepository customUserRepository;

    @Autowired
    protected CustomSongRepository customSongRepository;

    @Autowired
    protected CustomCommentRepository customCommentRepository;
}
