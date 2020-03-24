package com.github.saphyra.skyxplore.app.rest.controller.request.user;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeleteAccountRequest {
    @NotNull
    private String password;
}
