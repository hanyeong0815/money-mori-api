package com.side.moneymoriapi.vo.category;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Long id;
    private UUID memberId;
    private String category;
}
