package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun TabRowCustom(
    tabItems: List<String>,
    selectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit,
    colorNotActiveLine: Int = R.color.in_active_icon,
    colorNotActiveText: Color = colorResource( R.color.for_text_on_icons),
) {
    var currentIndex by remember { mutableIntStateOf(selectedIndex) }
    var boxWidthPx by remember { mutableFloatStateOf(0f) }
    val tabWidthFraction = 1f / tabItems.size
    val progress by animateFloatAsState(
        targetValue = currentIndex.toFloat(),
        animationSpec = tween(durationMillis = 500)
    )
    val offsetX = progress * boxWidthPx * tabWidthFraction

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabItems.forEachIndexed { index, text ->
                val textColor by animateColorAsState(
                    targetValue = if (currentIndex == index) colorResource(R.color.main_blue) else colorNotActiveText,
                    animationSpec = tween(durationMillis = 500)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            currentIndex = index
                            onTabSelected(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = mainFont,
                            fontWeight = if (currentIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(colorNotActiveLine))
                .onGloballyPositioned { coordinates ->
                    boxWidthPx = coordinates.size.width.toFloat()
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(tabWidthFraction)
                    .height(1.dp)
                    .offset { IntOffset(offsetX.toInt(), 0) }
                    .background(colorResource(R.color.main_blue))
            )
        }
    }
}