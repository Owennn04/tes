package com.example.soal1week7.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.example.soal1week7.R

@Composable
fun SearchPage(
    navController: NavHostController
){
    val keyboardController = LocalSoftwareKeyboardController.current

    Box (
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.background_weather),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )

        var searchText by remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                TextField(
                    value = searchText,
                    onValueChange = {searchText = it},
                    placeholder = {
                        Text(
                            "Enter city name...",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0x34FFFFFF),
                        unfocusedContainerColor = Color(0x34FFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (searchText.isNotBlank()) {
                            keyboardController?.hide()
                            navController.navigate("city_weather_page/$searchText")
                        }
                    })
                )
                Button(
                    onClick = {
                        if (searchText.isNotBlank()) {
                            keyboardController?.hide()
                            navController.navigate("city_weather_page/$searchText")
                        }
                    },
                    modifier = Modifier.height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            InitialView()
        }
    }
}

@Composable
fun InitialView(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Icon",
            tint = Color.LightGray,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(60.dp)
        )
        Text(
            text = "Search for a city to get started",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
    }
}