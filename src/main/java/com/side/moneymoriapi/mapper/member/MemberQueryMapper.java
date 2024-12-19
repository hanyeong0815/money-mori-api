package com.side.moneymoriapi.mapper.member;

import com.side.moneymoriapi.projection.member.MemberProjection.MemberUsernamePasswordProjection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MemberQueryMapper {
    int countMemberByUsername(String username);
    Optional<MemberUsernamePasswordProjection> findByUsernameAndPassword(@Param("username") String username);
    List<String> findRolesByMemberId(@Param("id") UUID MemberId);
}
