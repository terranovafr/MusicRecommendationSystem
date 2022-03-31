package com.unipi.large.scale.backend.controllers.services;

import com.unipi.large.scale.backend.data.aggregations.Album;
import com.unipi.large.scale.backend.data.aggregations.AverageMusicFeatures;
import com.unipi.large.scale.backend.data.aggregations.Id;
import com.unipi.large.scale.backend.dtos.CommentDto;
import com.unipi.large.scale.backend.dtos.InterfaceSongDto;
import com.unipi.large.scale.backend.dtos.SongDto;
import com.unipi.large.scale.backend.entities.mongodb.Comment;
import com.unipi.large.scale.backend.entities.mongodb.MongoSong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("songs")
public class SongController extends ServiceController{

    @GetMapping("{id}")
    ResponseEntity<SongDto> getById(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.mongoSongToSongDto(songService.getMongoSongById(id)),
                HttpStatus.OK
        );
    }

    @Transactional
    @DeleteMapping("{id}")
    ResponseEntity<Object> deleteById(@PathVariable("id") String id) {

        songService.deleteSongById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("create")
    ResponseEntity<SongDto> createSong(@Valid @RequestBody SongDto songDto) {
        MongoSong mongoSong = mapper.songDtoToMongoSong(songDto);
        return new ResponseEntity<>(
                mapper.mongoSongToSongDto(songService.createSong(mongoSong)),
                HttpStatus.OK
        );
    }

    @GetMapping("neo4j/{id}")
    ResponseEntity<InterfaceSongDto> getNeo4jSong(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jSongToInterfaceSongDto(songService.getNeo4jSongByMongoId(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("search/{name}")
    ResponseEntity<List<InterfaceSongDto>> searchSongsByName(@PathVariable("name") String name) {

        return new ResponseEntity<>(
                mapper.mongoSongsToInterfaceSongsDto(songService.searchSongsByName(name)),
                HttpStatus.OK
        );
    }



    @GetMapping("recommended/{id}")
    ResponseEntity<List<InterfaceSongDto>> getRecommendedSongs(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jSongsToInterfaceSongsDto(songService.getRecommendedSongs(id)),
                HttpStatus.OK
        );
    }

    @Transactional
    @PutMapping("like")
    ResponseEntity<Object> likeSong(@RequestParam(value = "from") String fromUserId,
                                    @RequestParam(value = "to") String toUserId,
                                    @RequestParam(value = "status") @Min(-1) @Max(1) @NotNull int status) {

        songService.likeSong(fromUserId, toUserId, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}/comment")
    ResponseEntity<List<CommentDto>> getSongComments(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.commentsToCommentsDto(songService.getSongComments(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("{id}/comment")
    ResponseEntity<CommentDto> commentSong(@PathVariable("id") String id, @Valid @RequestBody CommentDto commentDto) {

        Comment comment = mapper.commentDtoToComment(commentDto);

        return new ResponseEntity<>(
                mapper.commentToCommentDto(songService.commentSong(id, comment)),
                HttpStatus.OK
        );
    }

    @GetMapping("top_albums")
    ResponseEntity<List<Album>> getClusterKHighestRatedAlbums(@RequestParam("cluster") int cluster,
                                                              @RequestParam("k") int k) {

        return new ResponseEntity<>(
                songService.getClusterKHighestRatedAlbums(cluster, k),
                HttpStatus.OK
        );
    }

    @GetMapping("most_danceable_cluster")
    ResponseEntity<Id> getMostDanceableCluster() {

        return new ResponseEntity<>(
                songService.getMostDanceableCluster(),
                HttpStatus.OK
        );
    }

    @GetMapping("average_music_features")
    ResponseEntity<List<AverageMusicFeatures>> getAverageMusicFeaturesByCluster() {

        return new ResponseEntity<>(
                songService.getAverageMusicFeaturesByCluster(),
                HttpStatus.OK
        );
    }
}
