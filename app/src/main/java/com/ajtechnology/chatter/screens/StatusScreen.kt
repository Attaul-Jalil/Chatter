package com.ajtechnology.chatter.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ajtechnology.chatter.ChatViewModel

@Composable
fun StatusScreen(navController: NavHostController, vm: ChatViewModel) {

    Text(text = "Status Screen", modifier = Modifier.padding(120.dp))
    BottomNavigationMenu(selectedItem = BottomNavigationItem.STATUSLIST, navController = navController)

}