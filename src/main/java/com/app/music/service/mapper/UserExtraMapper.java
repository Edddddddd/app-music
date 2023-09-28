package com.app.music.service.mapper;

import com.app.music.domain.User;
import com.app.music.domain.UserExtra;
import com.app.music.service.dto.UserDTO;
import com.app.music.service.dto.UserExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtra} and its DTO {@link UserExtraDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    UserExtraDTO toDto(UserExtra s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
