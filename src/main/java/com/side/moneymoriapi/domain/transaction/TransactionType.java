package com.side.moneymoriapi.domain.transaction;

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
