package com.side.moneymoriapi.vo.budget;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Budget {
    private Long id;
    private UUID memberId;
    private String month;
    private Integer amount;
    private Instant createdAt;
}
