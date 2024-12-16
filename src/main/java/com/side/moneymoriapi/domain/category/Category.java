package com.side.moneymoriapi.domain.category;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Long id;
    private String category;
}
