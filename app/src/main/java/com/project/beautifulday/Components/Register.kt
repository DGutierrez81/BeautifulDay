package com.project.beautifulday.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.R

@Composable
fun Register(navController: NavController, LgViewmodel: LogViewmodel){

    val focusManager = LocalFocusManager.current
    val passwordVisibility = LgViewmodel.passwordVisibility

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = LgViewmodel.userName,
            onValueChange = {LgViewmodel.changeUsername(it)},
            label= { Text(text = "UserName", color = colorResource(id = R.color.paynesGray)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            trailingIcon = { Icon(Icons.Default.Face, contentDescription = "Icon User", tint = colorResource(id = R.color.paynesGray)) },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = LgViewmodel.email,
            onValueChange = { LgViewmodel.changeEmail(it) },
            label = { Text(text = "Email", color = colorResource(id = R.color.paynesGray)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            trailingIcon = { Icon(Icons.Default.Email, contentDescription = "Icon email", tint = colorResource(id = R.color.paynesGray)) },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = LgViewmodel.password,
            onValueChange = { LgViewmodel.changePassword(it) },
            label = { Text(text = "Password", color = colorResource(id = R.color.paynesGray)) },
            visualTransformation = if (!passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(onSend = {
                LgViewmodel.creauteUser(LgViewmodel.email, LgViewmodel.password) {navController.navigate("principal")}
                focusManager.clearFocus()
            }),
            trailingIcon = {
                IconButton(onClick = { LgViewmodel.changePasswordVisibility(!passwordVisibility) }) { LgViewmodel.changeIcon() }

            },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}