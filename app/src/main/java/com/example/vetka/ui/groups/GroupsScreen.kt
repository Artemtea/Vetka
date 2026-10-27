package com.example.vetka.ui.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vetka.model.Group



val groups = listOf(
    Group(
        id = "1",
        name = "Семья",
        ownerId = "user1"
    ),
    Group(
        id = "2",
        name = "Друзья",
        ownerId = "user1"
    )
)
@Composable
fun GroupsScreen(
    onGroupClick : (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(groups){ group ->
                Card(
                    onClick = { onGroupClick(group.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = group.name,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    }
}
