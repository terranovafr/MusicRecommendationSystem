package com.unipi.large.scale.backend.controllers.services;

import com.unipi.large.scale.backend.dtos.Mapper;
import com.unipi.large.scale.backend.service.clustering.Clustering;
import com.unipi.large.scale.backend.service.db.DbService;
import com.unipi.large.scale.backend.service.db.SongService;
import com.unipi.large.scale.backend.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
abstract class ServiceController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected SongService songService;

    @Autowired
    protected Clustering clustering;

    @Autowired
    protected Mapper mapper;

    @Autowired
    protected DbService dbService;
}
