package com.unipi.large.scale.backend.dtos;

import com.unipi.large.scale.backend.data.Login;
import com.unipi.large.scale.backend.data.Survey;
import com.unipi.large.scale.backend.entities.mongodb.Comment;
import com.unipi.large.scale.backend.entities.mongodb.MongoSong;
import com.unipi.large.scale.backend.entities.mongodb.MongoUser;
import com.unipi.large.scale.backend.entities.neo4j.Neo4jSong;
import com.unipi.large.scale.backend.entities.neo4j.Neo4jUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record Mapper(ModelMapper modelMapper) {

    public UserDto mongoUserToUserDto(MongoUser mongoUser) {

        return modelMapper.map(mongoUser, UserDto.class);
    }

    public List<UserDto> mongoUsersToUsersDto(List<MongoUser> mongoUsers) {

        return mongoUsers.stream().map(this::mongoUserToUserDto).toList();
    }

    public MongoUser userDtoToMongoUser(UserDto userDto) {

        return modelMapper.map(userDto, MongoUser.class);
    }

    public Login loginDtoToLogin(LoginDto loginDto) {

        return modelMapper.map(loginDto, Login.class);
    }

    public SurveyDto surveyToSurveyDto(Survey survey) {

        return modelMapper.map(survey, SurveyDto.class);
    }

    public InterfaceUserDto neo4jUserToInterfaceUserDto(Neo4jUser neo4jUser) {

        modelMapper.typeMap(Neo4jUser.class, InterfaceUserDto.class).addMapping(
                Neo4jUser::getMongoId,
                InterfaceUserDto::setId
        );

        return modelMapper.map(neo4jUser, InterfaceUserDto.class);
    }

    public List<InterfaceUserDto> neo4jUsersToInterfaceUsersDto(List<Neo4jUser> neo4jUsers) {

        return neo4jUsers.stream().map(this::neo4jUserToInterfaceUserDto).toList();
    }

    public SongDto mongoSongToSongDto(MongoSong song) {

        return modelMapper.map(song, SongDto.class);
    }

    public List<SongDto> mongoSongsToSongsDto(List<MongoSong> songs) {

        return songs.stream().map(this::mongoSongToSongDto).toList();
    }

    public MongoSong songDtoToMongoSong(SongDto songDto) {

        return modelMapper.map(songDto, MongoSong.class);
    }

    public InterfaceSongDto neo4jSongToInterfaceSongDto(Neo4jSong neo4jSong) {

        return modelMapper.typeMap(Neo4jSong.class, InterfaceSongDto.class).addMapping(
                Neo4jSong::getMongoId,
                InterfaceSongDto::setId
        ).map(neo4jSong);
    }

    public List<InterfaceSongDto> neo4jSongsToInterfaceSongsDto(List<Neo4jSong> neo4jSongs) {

        return neo4jSongs.stream().map(this::neo4jSongToInterfaceSongDto).toList();
    }

    public InterfaceUserDto mongoUserToInterfaceUserDto(MongoUser mongoUser) {

        return modelMapper().map(mongoUser, InterfaceUserDto.class);
    }

    public List<InterfaceUserDto> mongoUsersToInterfaceUsersDto(List<MongoUser> mongoUsers) {

        return mongoUsers.stream().map(this::mongoUserToInterfaceUserDto).toList();
    }

    public InterfaceSongDto mongoSongToInterfaceSongDto(MongoSong song) {

        return modelMapper().map(song, InterfaceSongDto.class);
    }

    public List<InterfaceSongDto> mongoSongsToInterfaceSongsDto(List<MongoSong> songs) {

        return songs.stream().map(this::mongoSongToInterfaceSongDto).toList();
    }

    public CommentDto commentToCommentDto(Comment comment) {

        return modelMapper.typeMap(Comment.class, CommentDto.class).addMappings(mapper -> mapper.map(
                Comment::getId,
                CommentDto::setIdSerializer)
        ).addMappings(mapper -> mapper.map(
                Comment::getSongId,
                CommentDto::setSongIdSerializer)
        ).addMappings(mapper -> mapper.map(
                Comment::getUserId,
                CommentDto::setUserIdSerializer)
        ).map(comment);
    }

    public List<CommentDto> commentsToCommentsDto(List<Comment> comments) {

        return comments.stream().map(this::commentToCommentDto).toList();
    }

    public Comment commentDtoToComment(CommentDto commentDto) {

        return modelMapper.map(commentDto, Comment.class);
    }
}
