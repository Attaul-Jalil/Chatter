package com.ajtechnology.chatty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajtechnology.chatty.screens.ChatListScreen
import com.ajtechnology.chatty.screens.LoginScreen
import com.ajtechnology.chatty.screens.ProfileScreen
import com.ajtechnology.chatty.screens.SignUpScreen
import com.ajtechnology.chatty.screens.SingleChatScreen
import com.ajtechnology.chatty.screens.SingleStatusScreen
import com.ajtechnology.chatty.screens.StatusScreen
import com.ajtechnology.chatty.ui.theme.ChattyTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreens(var route: String) {
    object SignUp : DestinationScreens("signeup")
    object Login : DestinationScreens("login")
    object Profile : DestinationScreens("profile")
    object ChatList : DestinationScreens("chatlist")
    object SingleChat : DestinationScreens("singlechat/{chatid}") {
        fun createRoute(id: String) = "singlechat/$id"
    }

    object StatusList : DestinationScreens("StatusList")
    object SingleStatus : DestinationScreens("singleStatus/{userId}") {
        fun createRoute(userId: String) = "singleStatus/$userId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChattyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()

                }
            }
        }
    }

    @Composable
    fun ChatAppNavigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val vm = hiltViewModel<ChatViewModel>()
        NavHost(navController = navController, startDestination = DestinationScreens.SignUp.route) {
            composable(DestinationScreens.SignUp.route) {
                SignUpScreen(navController, vm)
            }
            composable(DestinationScreens.ChatList.route) {
                ChatListScreen(navController, vm)
            }
            composable(DestinationScreens.SingleChat.route) {
                SingleChatScreen(navController, vm)
            }
            composable(DestinationScreens.SingleStatus.route) {
                SingleStatusScreen(navController, vm)
            }
            composable(DestinationScreens.Profile.route) {
                ProfileScreen(navController, vm)
            }
            composable(DestinationScreens.Login.route) {
                LoginScreen(navController, vm)
            }
            composable(DestinationScreens.StatusList.route) {
                StatusScreen(navController, vm)
            }
        }
    }
}

