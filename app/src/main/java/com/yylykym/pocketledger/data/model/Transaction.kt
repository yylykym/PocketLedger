package com.yylykym.pocketledger.data.model

import java.time.LocalDate

data class Transaction(
    val id: Long,
    val categoryId: Long,
    val type: TransactionType,
    val amount: Long,
    val note: String,
    val date: LocalDate
)