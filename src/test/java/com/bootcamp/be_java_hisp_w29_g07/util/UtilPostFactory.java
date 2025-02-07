package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.dto.PostDTO;
import com.bootcamp.be_java_hisp_w29_g07.entity.Post;
import com.bootcamp.be_java_hisp_w29_g07.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UtilPostFactory {

    private static ObjectMapper mapper;

    public static Post getPostByUser(Integer userId, Integer week) {
        return new Post(1, userId, LocalDate.now().minusWeeks(week), getProduct(userId + 1), 3, 100.0, false, 0.0);
    }

    public static Product getProduct(Integer id) {
        return new Product(id, "Generic Product", "Generic Type", "Generic brand", "white", "N/A");
    }

    public static List<PostDTO> getPostDTOList(Integer userId) {
        List<Post> listPost = Arrays.asList(UtilPostFactory.getPostByUser(userId, 1),
                UtilPostFactory.getPostByUser(userId, 1));

        return listPost.stream()
                .map(post -> getObjectMapper().convertValue(post, PostDTO.class))
                .toList();
    }

    private static ObjectMapper getObjectMapper(){
        if(Objects.isNull(mapper)){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.findAndRegisterModules();
            return mapper;
        }

        return mapper;
    }
}
