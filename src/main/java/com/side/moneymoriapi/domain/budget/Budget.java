package com.side.moneymoriapi.domain.budget;

import lombok.*;

import java.time.Instant;
import java.util.Date;
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
    private Date month;
    private Integer amount;
    private Instant createdAt;
}
