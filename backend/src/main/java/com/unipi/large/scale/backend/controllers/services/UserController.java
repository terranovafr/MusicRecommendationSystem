package com.unipi.large.scale.backend.controllers.services;

import com.unipi.large.scale.backend.data.aggregations.Country;
import com.unipi.large.scale.backend.data.Login;
import com.unipi.large.scale.backend.dtos.InterfaceUserDto;
import com.unipi.large.scale.backend.dtos.LoginDto;
import com.unipi.large.scale.backend.dtos.SurveyDto;
import com.unipi.large.scale.backend.dtos.UserDto;
import com.unipi.large.scale.backend.entities.mongodb.MongoUser;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
//http://localhost:8080/users/?options=&
@RequestMapping("users")
public class UserController extends ServiceController {

    @GetMapping("{id}")
    ResponseEntity<UserDto> getById(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.getMongoUserById(new ObjectId(id))),
                HttpStatus.OK
        );
    }

    @Transactional
    @DeleteMapping("{id}")
    ResponseEntity<Object> deleteById(@PathVariable("id") String id) {

        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("register")
    ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        MongoUser mongoUser = mapper.userDtoToMongoUser(userDto);
        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.registerUser(mongoUser)),
                HttpStatus.OK
                );
    }

    @Transactional
    @PutMapping("update")
    ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto) {

        MongoUser mongoUser = mapper.userDtoToMongoUser(userDto);

        userService.updateUser(mongoUser);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PostMapping("login")
    ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {

        Login login = mapper.loginDtoToLogin(loginDto);

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.login(login)),
                HttpStatus.OK
        );
    }

    @GetMapping()
    ResponseEntity<List<UserDto>> getAllUsers() {

        return new ResponseEntity<>(
                mapper.mongoUsersToUsersDto(userService.getAllUsers()),
                HttpStatus.OK
        );
    }

    @GetMapping("similarities/{id}")
    ResponseEntity<List<InterfaceUserDto>> getSimilarUsers(@PathVariable String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToInterfaceUsersDto(userService.getSimilarUsers(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("similar_nearby/{id}")
    ResponseEntity<List<InterfaceUserDto>> getNearbySimilarUsers(@PathVariable String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToInterfaceUsersDto(userService.getNearbySimilarUsers(id)),
                HttpStatus.OK
        );
    }

    @Transactional
    @PostMapping("friend_requests")
    ResponseEntity<Object> addFriendRequest(@RequestParam(value = "from") String fromUserId,
                        @RequestParam(value = "to") String toUserId) {

        userService.addFriendRequest(fromUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("friend_requests")
    ResponseEntity<Object> deleteFriendRequest(@RequestParam(value = "from") String fromUserId,
                                            @RequestParam(value = "to") String toUserId) {

        userService.deleteFriendRequest(fromUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("friend_requests")
    ResponseEntity<Object> updateFriendRequest(@RequestParam(value = "from") String fromUserId,
                                            @RequestParam(value = "to") String toUserId,
                                            @RequestParam(value = "status") @Min(0) @Max(1) int status) {

        userService.updateFriendRequest(fromUserId, toUserId, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("friend_requests/{id}")
    ResponseEntity<List<InterfaceUserDto>> getIncomingFriendRequests(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToInterfaceUsersDto(userService.getIncomingFriendRequests(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("friends/{id}")
    ResponseEntity<List<InterfaceUserDto>> getFriends(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUsersToInterfaceUsersDto(userService.getFriends(id)),
                HttpStatus.OK
        );
    }


    @GetMapping("cluster_values/{id}")
    ResponseEntity<SurveyDto> getUserClusterValues(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.surveyToSurveyDto(userService.getUserClusterValues(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("cluster_cluster_values/{id}")
    ResponseEntity<SurveyDto> getUserClusterClusterValues(@PathVariable("id") int id) {

        return new ResponseEntity<>(
                mapper.surveyToSurveyDto(userService.getUserClusterClusterValues(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("most_similar/{id}")
    ResponseEntity<UserDto> getMostSimilarUser(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.mongoUserToUserDto(userService.getMostSimilarUser(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("neo4j/{id}")
    ResponseEntity<InterfaceUserDto> getNeo4jUser(@PathVariable("id") String id) {

        return new ResponseEntity<>(
                mapper.neo4jUserToInterfaceUserDto(userService.getNeo4jUserByMongoId(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("search/{username}")
    ResponseEntity<List<InterfaceUserDto>> searchUsersByUsername(@PathVariable("username") String username) {

        return new ResponseEntity<>(
                mapper.mongoUsersToInterfaceUsersDto(userService.searchUsersByUsername(username)),
                HttpStatus.OK
        );
    }

    @PutMapping("{id}/quarantine")
    ResponseEntity<Object> quarantineUser(@PathVariable("id") String id) {

        userService.quarantineUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("clusters/highest_variance")
    ResponseEntity<Integer> getClusterWithHighestVariance() {

        return new ResponseEntity<>(
                userService.getClusterWithHighestVariance(),
                HttpStatus.OK
        );
    }

    @GetMapping("top_countries")
    ResponseEntity<List<Country>> getTopKCountries(@RequestParam(value = "k") int k) {

        return new ResponseEntity<>(
                userService.getTopKCountries(k),
                HttpStatus.OK
        );
    }
 }
