package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.ErrorMessages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.User;
import com.bootcamp.be_java_hisp_w29_g07.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g07.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g07.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g07.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
