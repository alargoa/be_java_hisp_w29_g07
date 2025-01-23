package com.bootcamp.be_java_hisp_w29_g07.repository;

import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    private List<Follow> followList = new ArrayList<>();

    public FollowRepositoryImpl() {
        this.followList.add(new Follow(
                1,
                new User(1, "ancaro", "Andres", "Caro", "andres@gmail.com", UserType.USER),
                new User(2, "jsmith", "John", "Smith", "john.smith@example.com", UserType.SELLER),
                LocalDate.now()));

        this.followList.add(new Follow(
                2,
                new User(3, "mjane", "Mary", "Jane", "mary.jane@example.com", UserType.USER),
                new User(2, "ptaylor", "Paul", "Taylor", "paul.taylor@example.com", UserType.SELLER),
                LocalDate.now()));

        this.followList.add(new Follow(
                3,
                new User(5, "rkhan", "Rashid", "Khan", "rashid.khan@example.com", UserType.USER),
                new User(2, "lgarcia", "Laura", "Garcia", "laura.garcia@example.com", UserType.SELLER),
                LocalDate.now()));

        this.followList.add(new Follow(
                4,
                new User(7, "dlee", "Daniel", "Lee", "daniel.lee@example.com", UserType.USER),
                new User(4, "hwang", "Helen", "Wang", "helen.wang@example.com", UserType.SELLER),
                LocalDate.now()));

        this.followList.add(new Follow(
                5,
                new User(9, "mrobinson", "Michael", "Robinson", "michael.robinson@example.com", UserType.USER),
                new User(4, "ljones", "Lisa", "Jones", "lisa.jones@example.com", UserType.SELLER),
                LocalDate.now()));

        this.followList.add(new Follow(
                6,
                new User(11, "akumar", "Anil", "Kumar", "anil.kumar@example.com", UserType.USER),
                new User(12, "klee", "Karen", "Lee", "karen.lee@example.com", UserType.SELLER),
                LocalDate.now()));
    }

    @Override
    public Follow saveFollow(User user, User userToFollow) {
        Follow follow = new Follow((this.followList.size() + 1), user, userToFollow, LocalDate.now());
        followList.add(follow);
        return follow;
    }

    @Override
    public List<Follow> findAll() {
        return followList;
    }

    @Override
    public Optional<Follow> findFollow(User user, User userToFollow) {
        return findAll()
                .stream()
                .filter(follow -> follow.getFollower().getId().equals(user.getId()))
                .filter(follow -> follow.getFollowed().getId().equals(userToFollow.getId()))
                .findFirst();
    }

    @Override
    public Long countByFollowedId(Integer userId) {
        return this.followList.stream()
                .filter(f -> f.getFollowed().getId().equals(userId))
                .count();
    }

    @Override
    public List<User> iFollow(Integer userId) {
        return this.followList.stream()
                .map(Follow::getFollowed)
                .filter(followed -> followed.getId().equals(userId))
                .toList();
    }

}
