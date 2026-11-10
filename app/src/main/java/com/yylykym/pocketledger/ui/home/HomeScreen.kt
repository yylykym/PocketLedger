package com.yylykym.pocketledger.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yylykym.pocketledger.ui.components.MonthSelector
import com.yylykym.pocketledger.ui.theme.PocketLedgerTheme
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val datePickerState = rememberDatePickerState()
    MonthSelector(yearMonth = YearMonth.of(2024,5), {})
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewHomeScreen() {
    PocketLedgerTheme {
        HomeScreen()
    }
}