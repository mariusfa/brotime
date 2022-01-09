package org.fagerland.time.dto

import java.math.BigInteger

data class CreateTimeDTO(
    val start: BigInteger,
    val end: BigInteger
)
