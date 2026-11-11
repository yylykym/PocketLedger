package com.yylykym.pocketledger.ui.model

import androidx.annotation.DrawableRes

data class TransactionCategory(
    val id: Long,
    val name: String,
    @param:DrawableRes val iconRes: Int
)
