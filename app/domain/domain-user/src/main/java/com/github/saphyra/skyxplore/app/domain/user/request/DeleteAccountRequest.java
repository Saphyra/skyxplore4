package com.github.saphyra.skyxplore.app.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeleteAccountRequest {
    @NotNull
    private String password;
}
