package com.side.moneymoriapi.mapper.member;

import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.domain.type.RoleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MemberCommendMapper {
    void createMember(Member member);
    void insertRoles(@Param("memberId") UUID memberId, @Param("roles") List<RoleType> roles);
}
