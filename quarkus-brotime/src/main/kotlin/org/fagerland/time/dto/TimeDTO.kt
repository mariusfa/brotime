package org.fagerland.time.dto

import java.math.BigInteger

data class TimeDTO(
    val id: Long,
    val start: BigInteger,
    val end: BigInteger
)