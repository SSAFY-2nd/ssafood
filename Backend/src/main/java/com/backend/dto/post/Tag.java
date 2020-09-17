package com.backend.dto.post;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Long tid;
    private String name;
    private Long hits;

    public Tag(String name) {
        this.name = name;
    }
}
