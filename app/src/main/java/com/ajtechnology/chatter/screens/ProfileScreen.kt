package com.ajtechnology.chatter.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ajtechnology.chatter.ChatViewModel
import com.ajtechnology.chatter.CommonDivider
import com.ajtechnology.chatter.CommonProgressbar

@Composable
fun ProfileScreen(navController: NavHostController, vm: ChatViewModel) {


    val inProgress = vm.inProgress.value
    if (inProgress) {
        CommonProgressbar()
    } else {
        Column {
            ProfileContent(vm,onBack = {

            }, onSave = {

            })

        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.PROFILE,
            navController = navController
        )

    }


}


@Composable
fun ProfileContent(vm: ChatViewModel, onBack: () -> Unit, onSave: () -> Unit) {

    val imageUrl = vm.userData.value?.imageUrl
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", Modifier.clickable {
                onBack.invoke()
            })
            Text(text = "Save", Modifier.clickable {
                onSave.invoke()
            })

        }
        CommonDivider()
        imageUrl?.let {
            ProfileImage(imageUrl = it, vm = vm)
        }
        CommonDivider()
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
          //  TextField(value = , onValueChange = )

        }
    }

}


@Composable
fun ProfileImage(imageUrl: String, vm: ChatViewModel) {

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                vm.uploadProfileImage(uri)
            }
        }
            Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {

                Column(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .clickable {
                        launcher.launch("image/*")
                    }, horizontalAlignment = Alignment.CenterHorizontally) {

                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)) {

                    }

                }
            }
}
