package com.example.soal1week7.ui.theme.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.soal1week7.R
import com.example.soal1week7.ui.theme.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherPage(
    cityName: String,
    navController: NavHostController,
    weatherViewModel: WeatherViewModel = viewModel()
){
    LaunchedEffect(key1 = cityName) {
        weatherViewModel.loadWeather(cityName)
    }

    val weatherState by weatherViewModel.weather.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.background_weather),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(cityName, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                 item {
                     if (weatherState.isError){
                         ErrorView(errorMessage = weatherState.errorMessage)
                     } else if (weatherState.cityName.isNotBlank()){
                         WeatherSuccessView(weatherViewModel)
                     }
                 }
            }
        }
    }
}

@Composable
fun WeatherSuccessView(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.weather.collectAsState()

    val condition = weatherState.weatherCondition

    when (condition) {
        "Rain" -> RainPageContent(weatherViewModel)
        "Clouds" -> CloudsPageContent(weatherViewModel)
        "Clear" -> ClearPageContent(weatherViewModel)
        else -> ClearPageContent(weatherViewModel)
    }
}

@Composable
fun ClearPageContent(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.weather.collectAsState()
    val weatherIconUrl by weatherViewModel.weatherIconUrl.collectAsState()
    val currentDate by weatherViewModel.currentDate.collectAsState()
    val currentTime by weatherViewModel.currentTime.collectAsState()
    val listWeatherInfo by weatherViewModel.listWeatherInfo.collectAsState()
    val listSunInfo by weatherViewModel.listSunInfo.collectAsState()
    val sunriseTime by weatherViewModel.sunriseTime.collectAsState()
    val sunsetTime by weatherViewModel.sunsetTime.collectAsState()

    Text(text = weatherState.cityName, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Text(currentDate, fontSize = 16.sp, color = Color.White)
    Text("Updated as of $currentTime", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = R.drawable.panda_clear),
        contentDescription = weatherState.weatherCondition,
        modifier = Modifier.size(150.dp)
    )

    Text(text = "${weatherState.temperature?.toInt() ?: 0}°", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = weatherState.weatherCondition, fontSize = 20.sp, color = Color.White)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(weatherIconUrl).crossfade(true).build(),
            contentDescription = weatherState.weatherCondition,
            modifier = Modifier.size(40.dp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().height(240.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listWeatherInfo) { info ->
            WeatherInfoCard(title = info.first, value = info.second, iconRes = info.third)
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        WeatherInfoCard(title = listSunInfo[0].first, value = sunriseTime, iconRes = listSunInfo[0].third)
        WeatherInfoCard(title = listSunInfo[1].first, value = sunsetTime, iconRes = listSunInfo[1].third)
    }
}

@Composable
fun RainPageContent(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.weather.collectAsState()
    val weatherIconUrl by weatherViewModel.weatherIconUrl.collectAsState()
    val currentDate by weatherViewModel.currentDate.collectAsState()
    val currentTime by weatherViewModel.currentTime.collectAsState()

    Text(text = weatherState.cityName, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Text(currentDate, fontSize = 16.sp, color = Color.White)
    Text("Updated as of $currentTime", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = R.drawable.panda_rain),
        contentDescription = weatherState.weatherCondition,
        modifier = Modifier.size(150.dp)
    )

    Text(text = "${weatherState.temperature?.toInt() ?: 0}°", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = weatherState.weatherCondition, fontSize = 20.sp, color = Color.White)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(weatherIconUrl).crossfade(true).build(),
            contentDescription = weatherState.weatherCondition,
            modifier = Modifier.size(40.dp)
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "Jangan lupa bawa payung!",
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CloudsPageContent(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.weather.collectAsState()
    val weatherIconUrl by weatherViewModel.weatherIconUrl.collectAsState()
    val currentDate by weatherViewModel.currentDate.collectAsState()
    val currentTime by weatherViewModel.currentTime.collectAsState()
    val listWeatherInfo by weatherViewModel.listWeatherInfo.collectAsState()

    Text(
        text = weatherState.cityName,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    Text(
        text = currentDate,
        fontSize = 16.sp,
        color = Color.White)
    Text(
        text = "Updated as of $currentTime",
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.8f)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = R.drawable.panda_cloud),
        contentDescription = weatherState.weatherCondition,
        modifier = Modifier.size(150.dp)
    )

    Text(
        text = "${weatherState.temperature?.toInt() ?: 0}°",
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = weatherState.weatherCondition,
            fontSize = 20.sp,
            color = Color.White
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(weatherIconUrl).crossfade(true).build(),
            contentDescription = weatherState.weatherCondition,
            modifier = Modifier.size(40.dp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().height(120.dp), // Lebih pendek
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cloudInfo = listWeatherInfo.filter {
            it.first == "CLOUDS" || it.first == "WIND" || it.first == "HUMIDITY"
        }
        items(cloudInfo) { info ->
            WeatherInfoCard(title = info.first, value = info.second, iconRes = info.third)
        }
    }
}

@Composable
fun WeatherInfoCard(title: String, value: String, iconRes: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp).width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Bold, color = Color.White)
            Text(title, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun ErrorView(errorMessage: String?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = errorMessage ?: "Oops, Something went Wrong",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFF08080)
        )
    }
}