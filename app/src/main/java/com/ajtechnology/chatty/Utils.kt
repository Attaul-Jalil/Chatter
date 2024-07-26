package com.ajtechnology.chatty

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

fun navigateTo(navController: NavController, route: String) {

    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop
    }
}

@Composable
fun CommonProgressbar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CommonDivider(modifier: Modifier = Modifier) {
    Divider(
        color = Color.LightGray, thickness = 1.dp,
        modifier = Modifier
            .alpha(.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )

}

@Composable
fun CommonImage(data:String?,modifier: Modifier=Modifier.wrapContentSize(), contentScale: ContentScale = ContentScale.Crop) {
    val painter = rememberAsyncImagePainter(model = data)
    Image(painter = painter, contentDescription =null,modifier = modifier,contentScale = contentScale )

}


@Composable
fun CheckedSignedIn(vm:ChatViewModel,navController: NavController){
    val alreadySignedIn= remember{
        mutableStateOf(false)
    }
    val signIn = vm.signIn.value
    if (signIn && !alreadySignedIn.value){
        alreadySignedIn.value = true
        navController.navigate(DestinationScreens.ChatList.route){
            popUpTo(0)
        }
    }


}