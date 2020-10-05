package com.backend.dto.liketo;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Liketo {

    private int uid;
    private int store_id;
    private int isLike;

    public Liketo(int uid, int store_id) {
        this.uid = uid;
        this.store_id = store_id;
    }
}
