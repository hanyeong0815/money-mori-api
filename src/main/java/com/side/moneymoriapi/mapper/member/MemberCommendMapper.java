package com.side.moneymoriapi.mapper.member;

import com.side.moneymoriapi.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCommendMapper {
    void createMember(Member member);
}
