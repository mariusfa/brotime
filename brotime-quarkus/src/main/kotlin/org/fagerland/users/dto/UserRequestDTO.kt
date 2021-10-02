package org.fagerland.users.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequestDTO(
    @JsonProperty("username")
    val username: String
)