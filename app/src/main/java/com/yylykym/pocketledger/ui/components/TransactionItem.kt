package com.yylykym.pocketledger.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yylykym.pocketledger.R
import com.yylykym.pocketledger.ui.theme.PocketLedgerTheme

@Composable
fun TransactionItem(
    title: String,
    date: String,
    amount: String,
    icon: Painter,
    isIncome: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = title
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(2f)) {
                Text(text = title, style = MaterialTheme.typography.titleSmall)
                Text(text = date, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "${if (isIncome) "+" else "-"}￥${amount}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTransactionItem() {
    PocketLedgerTheme {
        TransactionItem(
            "午餐",
            "今天 12:30",
            "38",
            painterResource(R.drawable.add),
            isIncome = false,
            onClick = {}
        )
    }
}