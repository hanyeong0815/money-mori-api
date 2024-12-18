package com.side.moneymoriapi.mapper.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberQueryMapper {
    int countMemberByUsername(String username);
}
