package com.bootcamp.be_java_hisp_w29_g07.service;

import com.bootcamp.be_java_hisp_w29_g07.dto.request.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.request.PromoPostDTOIn;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PostSaveDTO;
import com.bootcamp.be_java_hisp_w29_g07.Enum.UserType;
import com.bootcamp.be_java_hisp_w29_g07.constants.Messages;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.ListPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoCountPostDTO;
import com.bootcamp.be_java_hisp_w29_g07.dto.response.PromoPostDTOOut;
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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Post service.
 */
@Service
public class PostServiceImpl implements IPostService {

    /**
     * The Post repository.
     */
    private final IPostRepository postRepository;
    /**
     * The User repository.
     */
    private final IUserRepository userRepository;
    /**
     * The Follow repository.
     */
    private final IFollowRepository followRepository;
    /**
     * The Mapper.
     */
    private final ObjectMapper mapper;
    /**
     * The User service.
     */
    private final IUserService userService;

    /**
     * Instantiates a new Post service.
     *
     * @param postRepository   the post repository
     * @param userRepository   the user repository
     * @param followRepository the follow repository
     * @param userService      the user service
     */
    public PostServiceImpl(
            IPostRepository postRepository,
            IUserRepository userRepository,
            IFollowRepository followRepository,
            IUserService userService
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.findAndRegisterModules();
        this.userService = userService;
    }

    /**
     * Add post post save dto.
     *
     * @param post the post
     * @return the post save dto
     */
    @Override
    public PostSaveDTO addPost(PostDTO post) {
        User user = userService.findUserById(post.getUser_id());
        if(user.getUserType().equals(UserType.USER)){
            throw new BadRequestException(Messages.USER_NOT_SELLER_MSG);
        }
        if(Objects.isNull(post.getHas_promo()) || post.getHas_promo()  ){
            throw new BadRequestException(Messages.POST_CANNOT_HAVE_PROMOTION);
        }
        if(Objects.isNull(post.getDiscount()) || post.getDiscount() > 0.0){
            throw new BadRequestException(Messages.POST_CANNOT_HAVE_DISCOUNT);
        }
        Post postCreated = mapper.convertValue(post, Post.class);
        postRepository.savePost(postCreated);
        return new PostSaveDTO(Messages.POST_CREATED_SUCCESSFULLY,
                mapper.convertValue(postCreated, PostDTO.class));
    }

    /**
     * Find post by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<PostDTO> findPostById(Integer id) {
        Optional<Post> posId = postRepository.findPostById(id);
        return posId.map(post -> mapper.convertValue(post, PostDTO.class));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(p -> mapper.convertValue(p, PostDTO.class))
                .toList();
    }

    /**
     * Find list users followed posts by user id list post dto.
     *
     * @param userId the user id
     * @param order  the order
     * @return the list post dto
     */
    @Override
    public ListPostDTO findListUsersFollowedPostsByUserId(Integer userId, String order) {
        // Validates that the user exists
        userService.findUserById(userId);

        List<Integer> userFollowing = followRepository.findFollowedByUserId(userId).stream()
                .map(f -> f.getFollowed().getId()).toList();
        if (userFollowing.isEmpty()) {
            throw new NotFoundException(String.format(Messages.USER_HAS_NOT_FOLLOWED_MSG, userId));
        }

        List<Post> posts = postRepository.findPostsByUserIdsAndLastTwoWeeks(userFollowing);
        if (posts.isEmpty()) {
            throw new NotFoundException(String.format(Messages.USER_HAS_NOT_POSTS_MSG, userId));
        }

        List<Post> orderedPosts = this.applySorting(posts, order);
        List<PostDTO> postDTOS = orderedPosts.stream().map(post -> mapper.convertValue(post, PostDTO.class)).toList();
        return new ListPostDTO(userId, postDTOS);
    }

    /**
     * Apply sorting list.
     *
     * @param posts the posts
     * @param order the order
     * @return the list
     */
    private List<Post> applySorting(List<Post> posts, String order) {
        if (Objects.isNull(order)){
            order = "date_desc";
        }

        return switch (order.toLowerCase()) {
            case "date_asc" -> posts.stream()
                    .sorted(Comparator.comparing(Post::getDate))
                    .toList();
            case "date_desc" -> posts.stream()
                    .sorted(Comparator.comparing(Post::getDate).reversed())
                    .toList();
            default -> posts;
        };
    }

    /**
     * Find promo post count by user id promo count post dto.
     *
     * @param userId the user id
     * @return the promo count post dto
     */
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

    /**
     * Create promo post promo post dto out.
     *
     * @param promoPostDTOIn the promo post dto in
     * @return the promo post dto out
     */
    @Override
    public PromoPostDTOOut createPromoPost(PromoPostDTOIn promoPostDTOIn) {
        User user = userService.findUserById(promoPostDTOIn.getUser_id());
        if(user.getUserType().equals(UserType.USER)){
            throw new BadRequestException(Messages.USER_NOT_SELLER_MSG);
        }
        if (!promoPostDTOIn.getHas_promo()) {
            throw new BadRequestException(Messages.POST_HAS_NO_PROMOTION);
        }
        if (Objects.isNull(promoPostDTOIn.getDiscount()) || promoPostDTOIn.getDiscount() <= 0) {
            throw new BadRequestException(Messages.POST_HAS_NO_DISCOUNT);
        }

        Post postCreated = postRepository.savePost(mapper.convertValue(promoPostDTOIn, Post.class));
        return mapper.convertValue(postCreated, PromoPostDTOOut.class);
    }
}
