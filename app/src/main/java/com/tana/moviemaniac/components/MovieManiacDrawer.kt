package com.tana.moviemaniac.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieManiacNavDrawer(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "I am creating bottom navigation to get rid of Nav Drawer",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}