package com.side.moneymoriapi.vo.transaction;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionType {
    private Long id;
    private String type;
}
