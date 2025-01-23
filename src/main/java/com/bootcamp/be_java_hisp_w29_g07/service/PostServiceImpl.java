package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.entity.Follow;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IFollowRepository followRepository;
    private final ObjectMapper mapper;
    private final static int idCounter = 1;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        this.followRepository = new FollowRepositoryImpl();
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
        List<Post> posts = postRepository.saveAll();
        return posts.stream()
                .map(p -> mapper.convertValue(p, PostDTO.class))
                .toList();
        this.followRepository = new FollowRepositoryImpl();
    }

    @Override
    public ListPostDTO listPostByUser(Integer userId) {
        List<Integer> userFollowing = followRepository.userFollowed(userId);
        if (userFollowing.isEmpty()) { throw new NotFoundException(String.format(ErrorMessages.USER_HAS_NOT_FOLLOWED_MSG, userId)); }

        List<Post> posts = postRepository.findPostByUser(userFollowing, userId);
        if (posts.isEmpty()) { throw new NotFoundException(String.format(ErrorMessages.USER_HAS_NOT_POSTS_MSG, userId)); }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        List<PostDTO> postDTOS = posts.stream().map(post -> mapper.convertValue(post, PostDTO.class)).collect(Collectors.toList());
        System.out.println(postDTOS);
        return new ListPostDTO(userId, postDTOS);
    }

    @Override
    public ListPostDTO listPostByUser(Integer userId) {
        List<Integer> userFollowing = followRepository.userFollowed(userId);
        if (userFollowing.isEmpty()) { throw new NotFoundException(String.format(ErrorMessages.USER_HAS_NOT_FOLLOWED_MSG, userId)); }

        List<Post> posts = postRepository.findPostByUser(userFollowing, userId);
        if (posts.isEmpty()) { throw new NotFoundException(String.format(ErrorMessages.USER_HAS_NOT_POSTS_MSG, userId)); }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        List<PostDTO> postDTOS = posts.stream().map(post -> mapper.convertValue(post, PostDTO.class)).collect(Collectors.toList());
        System.out.println(postDTOS);
        return new ListPostDTO(userId, postDTOS);
    }

    @Override
    public PromoPostDTO getPromoPostCount(Integer userId) {
        Optional<User> user = userRepository.getUserById(userId);
        List<Post> postList = postRepository.getPromoPostCount(userId);
        long count = 0L;

        if (user.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MSG, userId));
        }
        if (user.get().getUserType().getId().equals(UserType.USER.getId())) {
            throw new BadRequestException(ErrorMessages.USER_NOT_SELLER_MSG);
        }
        if (postList.isEmpty()) {
            throw new NotFoundException(String.format(ErrorMessages.NO_POST_FOUND, userId));
        }

        count = postList.stream()
                .filter(Post::getHasPromo)
                .count();
        return new PromoPostDTO(user.get().getId(), user.get().getUsername(), (int) count);
    }
}
