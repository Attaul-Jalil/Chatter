package com.ajtechnology.chatty.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ajtechnology.chatty.ChatViewModel

@Composable
fun ChatListScreen(navController: NavHostController, vm: ChatViewModel) {
    
    Text(text = "Chat List Screen", modifier = Modifier.padding(120.dp))
    BottomNavigationMenu(selectedItem = BottomNavigationItem.CHATLIST, navController = navController)

}