package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.OrderType;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoCountPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IFollowRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IFollowRepository followRepository;
    private final ObjectMapper mapper;
    private final static int idCounter = 1;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, IFollowRepository followRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
    }

    @Override
    public PostSaveDTO addPost(PostDTO post) {
        Post post1 = mapper.convertValue(post, Post.class);
        post1.setId(postRepository.findNextId());
        postRepository.savePost(post1);
        return new PostSaveDTO("Post was created successfully", post1);
    }

    @Override
    public Optional<PostDTO> findPostById(Integer id) {
        Optional<Post> posId = postRepository.findPostById(id);
        return posId.map(post -> mapper.convertValue(post, PostDTO.class));
    }

    @Override
    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(p -> mapper.convertValue(p, PostDTO.class))
                .toList();
    }

    @Override
    public ListPostDTO findListUsersFollowedPostsByUserId(Integer userId, String order) {
        List<Integer> userFollowing = followRepository.findFollowedByUserId(userId).stream()
                .map(f -> f.getFollowed().getId()).toList();
        if (userFollowing.isEmpty()) {
            throw new NotFoundException(String.format(Messages.USER_HAS_NOT_FOLLOWED_MSG, userId));
        }

        List<Post> posts = postRepository.findPostsByUserIdsAndLastTwoWeeks(userFollowing);
        if (posts.isEmpty()) {
            throw new NotFoundException(String.format(Messages.USER_HAS_NOT_POSTS_MSG, userId));
        }

        List<Post> orderedPosts = new ArrayList<>(posts);
        switch (order.toLowerCase()) {
            case "date_asc" -> orderedPosts.sort(Comparator.comparing(Post::getDate));
            case "date_desc" -> orderedPosts.sort(Comparator.comparing(Post::getDate).reversed());
        }

        List<PostDTO> postDTOS = orderedPosts.stream().map(post -> mapper.convertValue(post, PostDTO.class)).toList();
        return new ListPostDTO(userId, postDTOS);
    }

    @Override
    public PromoCountPostDTO findPromoPostCountByUserId(Integer userId) {
        Optional<User> user = userRepository.getUserById(userId);
        long count = postRepository.findPromoPostCountByUserId(userId);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format(Messages.USER_NOT_FOUND_MSG, userId));
        }
        if (user.get().getUserType().getId().equals(UserType.USER.getId())) {
            throw new BadRequestException(Messages.USER_NOT_SELLER_MSG);
        }
        if (count == 0) {
            throw new NotFoundException(String.format(Messages.NO_POST_FOUND, userId));
        }
        return new PromoCountPostDTO(user.get().getId(), user.get().getUsername(), (int) count);
    }
}
