package com.example.atlysimdb.ui.list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.atlysimdb.ui.theme.CustomTheme

@Composable
fun CustomSearchBar(
    value: String, onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp)
            .background(
                color = CustomTheme.colors.White, shape = RoundedCornerShape(8.dp)
            )
            .border(
                BorderStroke(width = 1.dp, color = CustomTheme.colors.Gray200),
                shape = RoundedCornerShape(8.dp)
            ),
        textStyle = CustomTheme.typography.titleMd,
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = CustomTheme.colors.Gray200,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(16.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(start = 8.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "Search Movies",
                            style = CustomTheme.typography.bodyMd,
                            color = CustomTheme.colors.Gray200,
                        )
                    }
                    innerTextField()
                }
            }
        })

}
