package com.github.saphyra.skyxplore.app.rest.controller.request.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChangeUsernameRequest {
    public static final int USER_NAME_MAX_LENGTH = 30;
    public static final int USER_NAME_MIN_LENGTH = 3;

    @NotNull
    private String password;

    @NotNull
    @Size(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String userName;
}
