package com.ajtechnology.chatty.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ajtechnology.chatty.DestinationScreens
import com.ajtechnology.chatty.R
import com.ajtechnology.chatty.navigateTo


enum class BottomNavigationItem(val icon: Int, val navDestinationScreens: DestinationScreens) {
    CHATLIST(R.drawable.chat_icon, DestinationScreens.ChatList),
    STATUSLIST(R.drawable.status, DestinationScreens.StatusList),
    PROFILE(R.drawable.user, DestinationScreens.Profile)
}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController) {
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.White)
    ) {

        for (item in BottomNavigationItem.entries){
            Image(painter = painterResource(id = item.icon), contentDescription =null, modifier = Modifier
                .size(40.dp)
                .weight(1f)
                .clickable {
                    navigateTo(navController, item.navDestinationScreens.route)
                }, colorFilter = if (item == selectedItem) ColorFilter.tint(color = Color.Black) else ColorFilter.tint(color = Color.Gray))
        }

    }
}