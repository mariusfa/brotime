package org.fagerland.users.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterUserReqDTO(
    @JsonProperty("username")
    val username: String
)