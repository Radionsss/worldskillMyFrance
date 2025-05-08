package workwork.company.worldskillstest

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.table.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.navigation.Route
import workwork.company.worldskillstest.navigation.toStringRoute
import workwork.company.worldskillstest.presenter.commons.utils.maksShadow
import workwork.company.worldskillstest.ui.theme.WorldSkillsTestTheme
import workwork.company.worldskillstest.ui.theme.mainFont
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()

            val selectedIndex =
                remember { mutableStateOf(0) } // -1 означает, что ничего не выбрано

            val isBottomBarVisible =
                remember { mutableStateOf(true) } // Переменная для управления видимостью панели
            val systemUiController = rememberSystemUiController()

            val colorStatusBarInDefault =
                colorResource(id = R.color.main_color_default)

            systemUiController.setSystemBarsColor(
                color = colorStatusBarInDefault,
                darkIcons = true
            )

            WorldSkillsTestTheme() {
                Scaffold(
                    containerColor = colorResource(R.color.main_color_default),
                    bottomBar = {
                        if (isBottomBarVisible.value) { // Проверяем, нужно ли показывать нижнюю панель
                            Box(modifier = Modifier) {
                                Column(
                                    modifier = Modifier
                                        .maksShadow(
                                            color = Color.Black.copy(alpha = 0.3f),
                                            blurRadius =5.dp
                                        )
                                        .background(Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(52.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        val texts = listOf(
                                            "Events",
                                            "Tickets",
                                            "Records"
                                        )

                                        texts.forEachIndexed { index, text ->
                                            Text(
                                                fontSize = 16.sp,
                                                fontFamily = mainFont,
                                                fontWeight = if (selectedIndex.value == index) FontWeight.Bold else FontWeight.Normal, // Выделение жирным, если выбран
                                                text = text,
                                                color = if (selectedIndex.value == index) colorResource(R.color.main_blue) else Color.Gray, // Цвет текста
                                                modifier = Modifier
                                                    .clickable(
                                                        interactionSource = remember { MutableInteractionSource() },
                                                        indication = null // Убирает ripple-эффект
                                                    ) {
                                                        selectedIndex.value =
                                                            index // Устанавливаем текущий выбранный элемент
                                                        when (selectedIndex.value) {
                                                            0 -> {
                                                                viewModel.setDestination(
                                                                    Route.EventsListScreen
                                                                )
                                                            }

                                                            1 -> {
                                                                viewModel.setDestination(
                                                                    Route.TicketsListScreen
                                                                )
                                                            }

                                                            2 -> {
                                                                viewModel.setDestination(
                                                                    Route.RecordsListScreen
                                                                )
                                                            }

                                                            else -> {}
                                                        }
                                                    }
                                                    .padding(8.dp) // Для удобства взаимодействия
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) {
                    window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .background(color = colorResource(R.color.main_color_default))
                            .fillMaxSize()
                    ) {
                        Navigation(
                            viewModel.startDestination.value,
                        )
                    }
                }
            }
        }
    }
//    fun addDynamicShortcuts(context: Context) {
//        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
//
//        val shortcut = ShortcutInfo.Builder(context, "dynamic_events_shortcut")
//            .setShortLabel("События")
//            .setLongLabel("Открыть список событий")
//            .setIcon(Icon.createWithResource(context, R.drawable.ic_events))
//            .setIntent(Intent(context, EventsActivity::class.java).setAction(Intent.ACTION_VIEW))
//            .build()
//
//        shortcutManager.dynamicShortcuts = listOf(shortcut)
//    }
}
