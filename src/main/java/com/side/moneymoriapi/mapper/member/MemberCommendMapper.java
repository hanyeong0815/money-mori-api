package com.side.moneymoriapi.mapper.member;

import com.side.moneymoriapi.vo.member.Member;
import com.side.moneymoriapi.vo.type.RoleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MemberCommendMapper {
    void createMember(Member member);
    void insertRoles(@Param("memberId") UUID memberId, @Param("roles") List<RoleType> roles);
}
