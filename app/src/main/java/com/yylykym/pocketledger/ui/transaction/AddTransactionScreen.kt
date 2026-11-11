package com.yylykym.pocketledger.ui.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yylykym.pocketledger.R
import com.yylykym.pocketledger.ui.model.TransactionCategory
import com.yylykym.pocketledger.ui.components.IconLabelCard
import com.yylykym.pocketledger.ui.components.MonthSelector
import com.yylykym.pocketledger.ui.theme.PocketLedgerTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

enum class TransactionType {
    INCOME,
    EXPENSE
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTransactionScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    var transactionType by rememberSaveable {
        mutableStateOf(TransactionType.INCOME)
    }

    // 日历回调的月份
    val datePickerState = rememberDatePickerState()

    val textState = rememberTextFieldState()

    // 当前选择的月份
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    // 是否显示日历
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_page)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "记一笔", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onBackClick) {
                Text(text = "取消", style = MaterialTheme.typography.titleSmall)
            }
        }
        TransactionTypeSelector(
            transactionType = transactionType,
            onTypeSelected = {
                transactionType = it
            })

        AmountInput("", {})

        Text(
            text = stringResource(R.string.choose_category),
            style = MaterialTheme.typography.titleMedium
        )
        val list = listOf<TransactionCategory>(
            TransactionCategory(1, "餐饮", R.drawable.add),
            TransactionCategory(2, "交通", R.drawable.add),
            TransactionCategory(3, "购物", R.drawable.add),
            TransactionCategory(4, "日常", R.drawable.add),
            TransactionCategory(5, "住房", R.drawable.add),
            TransactionCategory(6, "娱乐", R.drawable.add),
            TransactionCategory(7, "订阅", R.drawable.add),
            TransactionCategory(8, "其他", R.drawable.add),
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(list) {
                IconLabelCard({},painterResource(it.iconRes), it.name)
            }
        }
        Text(
            text = stringResource(R.string.additional_information),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            state = textState,
            label = { Text("午餐·可添加备注", style = MaterialTheme.typography.bodySmall) },
            modifier = Modifier.fillMaxWidth()
        )

        MonthSelector(LocalDate.now(), { showDatePicker = true })

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "保存记录")
        }
    }

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

@Composable
fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "¥",
                style = MaterialTheme.typography.headlineMedium
            )

            BasicTextField(
                value = amount,
                onValueChange = onAmountChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
        }

        Spacer(Modifier.height(12.dp))

        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun TransactionTypeSelector(
    modifier: Modifier = Modifier,
    onTypeSelected: (TransactionType) -> Unit,
    transactionType: TransactionType
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = CircleShape,
                onClick = {
                    onTypeSelected(TransactionType.INCOME)
                },
                color = if (transactionType == TransactionType.INCOME) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("收入")
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = CircleShape,
                onClick = {
                    onTypeSelected(TransactionType.EXPENSE)
                },
                color = if (transactionType == TransactionType.EXPENSE) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("支出")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewAddTransactionScreen() {
    PocketLedgerTheme {
        AddTransactionScreen(Modifier, {false})
    }
}