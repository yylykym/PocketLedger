package com.yylykym.pocketledger.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yylykym.pocketledger.R
import com.yylykym.pocketledger.ui.components.IconLabelCard
import com.yylykym.pocketledger.ui.components.MonthSelector
import com.yylykym.pocketledger.ui.components.TransactionItem
import com.yylykym.pocketledger.ui.theme.PocketLedgerTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, onAddTransactionClick: () -> Unit) {

    // 日历回调的月份
    val datePickerState = rememberDatePickerState()

    // 当前选择的月份
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    // 是否显示日历
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_page)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /**
         * 标题
         */
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )

        /**
         * 日历
         */

        MonthSelector(
            date = selectedDate,
            { showDatePicker = true }
        )

        /**
         * 每月统计卡片
         */
        MonthlySummaryCard()

        /**
         * 副标题
         */
        Text(
            text = stringResource(R.string.quick_action_title),
            style = MaterialTheme.typography.titleMedium
        )

        /**
         * 快速操作卡片
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconLabelCard(
                onClick = onAddTransactionClick,
                painterResource(R.drawable.add),
                stringResource(R.string.quick_action_record_income)
            )
            IconLabelCard(
                onClick = onAddTransactionClick,
                painterResource(R.drawable.wallet),
                stringResource(R.string.quick_action_record_expenses)
            )
            IconLabelCard(
                onClick = onAddTransactionClick,
                painterResource(R.drawable.bar_chart),
                stringResource(R.string.quick_action_record_statistics)
            )
            IconLabelCard(
                onClick = onAddTransactionClick,
                painterResource(R.drawable.sync),
                stringResource(R.string.quick_action_record_subscription)
            )
        }

        /**
         * 副标题：最近交易
         */
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.recent_transactions),
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(onClick = {}) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            stringResource(R.string.show_all),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Icon(
                        painter = painterResource(R.drawable.right_pointing_arrow),
                        contentDescription = null,
                        Modifier.size(dimensionResource(R.dimen.icon_size_small))
                    )
                }
            }
        }

        /**
         * TODO: 最近交易
         */
        TransactionItem(
            "午餐",
            "今天 12:30",
            "38",
            painterResource(R.drawable.add),
            isIncome = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = LocalDate.from(
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneOffset.UTC)
                            )
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }


}

@Composable
fun MonthlySummaryCard(modifier: Modifier = Modifier) {
    Card(
        modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Text(text = "本月结余", style = MaterialTheme.typography.titleSmall)
            Text(text = "￥6,580.00", style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()
            Row() {
                Text(
                    text = "本月收入",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "本月支出",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row() {
                Text(
                    text = "￥12,800",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "￥6,220",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewHomeScreen() {
    PocketLedgerTheme {
        HomeScreen(Modifier,{})
    }
}