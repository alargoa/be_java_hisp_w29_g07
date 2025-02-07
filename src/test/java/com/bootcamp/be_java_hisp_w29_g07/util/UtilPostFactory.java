package com.bootcamp.be_java_hisp_w29_g07.util;

import com.bootcamp.be_java_hisp_w29_g07.entity.Post;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UtilPostFactory {

    public static List<Post> createUnorderedPosts() {
         return Arrays.asList(
                new Post(1, 4, LocalDate.now().minusDays(10), null, 101, 0.0, false, 0.0),
                new Post(2, 4, LocalDate.now().minusDays(2), null, 101, 0.0, false, 0.0),
                new Post(3, 2, LocalDate.now(), null, 101, 0.0, false, 0.0)
        );
    }
}
