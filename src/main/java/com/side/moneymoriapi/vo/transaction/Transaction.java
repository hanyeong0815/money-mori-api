package com.side.moneymoriapi.vo.transaction;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    private Long id;
    private UUID memberId;
    private Long typeId;
    private Long categoryId;
    private Integer amount;
    private String description;
    private Instant date;
}
