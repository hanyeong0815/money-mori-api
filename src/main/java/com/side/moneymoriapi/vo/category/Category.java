package com.side.moneymoriapi.vo.category;

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
