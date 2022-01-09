package org.fagerland.time.dto

import java.math.BigInteger

data class TimeRequestDTO(
    val start: BigInteger,
    val end: BigInteger
)
