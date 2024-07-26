package com.ajtechnology.chatty.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ajtechnology.chatty.ChatViewModel
import com.ajtechnology.chatty.CheckedSignedIn
import com.ajtechnology.chatty.CommonProgressbar
import com.ajtechnology.chatty.DestinationScreens
import com.ajtechnology.chatty.R
import com.ajtechnology.chatty.navigateTo

@Composable
fun LoginScreen(navController: NavHostController, vm: ChatViewModel) {

     CheckedSignedIn(vm = vm, navController =navController )

     Box(modifier = Modifier.fillMaxSize()) {
          Column(
               modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .verticalScroll(
                         rememberScrollState()
                    ), horizontalAlignment = Alignment.CenterHorizontally
          ) {
               val emailState = remember {
                    mutableStateOf(TextFieldValue())
               }
               val passwordState = remember {
                    mutableStateOf(TextFieldValue())
               }

               val focus  = LocalFocusManager.current
               Image(
                    painter = painterResource(id = R.drawable.signup),
                    contentDescription = null,
                    modifier = Modifier
                         .width(200.dp)
                         .padding(top = 16.dp)
                         .padding(8.dp)
               )
               Text(
                    text = "Sign In", fontSize = 30.sp, fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
               )
               OutlinedTextField(value = emailState.value , onValueChange = {
                    emailState.value = it
               },
                    label = { Text(text = "Email",)}, modifier = Modifier.padding(8.dp)
               )
               OutlinedTextField(value = passwordState.value , onValueChange = {
                    passwordState.value = it
               },
                    label = { Text(text = "Password",)}, modifier = Modifier.padding(8.dp)
               )

               Button(onClick = { vm.loggedIn(emailState.value.text,passwordState.value.text) }, modifier = Modifier.padding(8.dp)) {
                    Text(text = "SIGN IN")

               }

               Text(text = "Already a User ? Go to Login->", color = Color.Blue, modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                         navigateTo(navController, DestinationScreens.Login.route)
                    })

          }
          if(vm.inProgress.value){
               CommonProgressbar()
          }

     }
}