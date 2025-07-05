package com.greenandtasty.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DeleteUserModel {
    private String email;
}
