package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
   private final IUserRepository userRepository;
    private final ObjectMapper mapper;
    private static int idCounter = 1;


    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, ObjectMapper mapper) {
        this.postRepository = postRepository;
         this.userRepository = userRepository;
        this.mapper = new ObjectMapper();
    }


    @Override
    public PostSaveDTO addPost(PostDTO post) {
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        Post post1 = mapper.convertValue(post, Post.class);
        post1.setId(postRepository.findNextId());
        postRepository.savePost(post1);

        return new PostSaveDTO("El post fue creado con exito", post1);

    }

    @Override
    public Optional<PostDTO> findPostById(Integer id) {
        Optional<Post> posId = postRepository.findPostById(id);

        return posId.map(post -> mapper.convertValue(post, PostDTO.class));
    }

    @Override
    public List<PostDTO> getAll() {
        List<Post> posts = postRepository.saveAll();
        return posts.stream()
                .map(p -> mapper.convertValue(p, PostDTO.class))
                .toList();


    }

    @Override
    public PromoPostDTO getPromoPostCount(Integer userId) {
        Optional<User> user = userRepository.getUserById(userId);
        List<Post> postList = postRepository.getPromoPostCount(userId);
        long count = 0L;

        if(user.isEmpty()) {
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
