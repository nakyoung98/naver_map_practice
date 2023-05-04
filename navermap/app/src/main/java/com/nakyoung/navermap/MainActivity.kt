package com.nakyoung.navermap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.nakyoung.navermap.ui.theme.NavermapTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalNaverMapApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavermapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NaverMap(
                        modifier = Modifier.fillMaxSize()
                    )
                    val state = rememberWebViewState(url = "nmap://map?&appname=com.nakyoung.navermap")
                    WebView(state = state)
                }
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Preview
@Composable
fun MapPreview() {
    NaverMap(modifier = Modifier.fillMaxSize())
}