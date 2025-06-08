package workwork.company.worldskillstest.presenter.commons.components
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun BasicInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,

    ) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colorResource(R.color.main_blue) else Color.Transparent,
        label = ""
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isFocused) colorResource(R.color.white) else colorResource(R.color.background_for_inactive_icons),
        label = ""
    )
    Card(modifier = Modifier.shadow(
        elevation = 25.dp,
        ambientColor = colorResource(R.color.shadow_light),
        spotColor = colorResource(R.color.shadow_light),
        shape = RoundedCornerShape(16.dp)
    ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        content = {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = colorResource(R.color.main_blue),
                    fontSize = 15.sp,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                cursorBrush = SolidColor(colorResource(R.color.main_blue)),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor, shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                interactionSource = interactionSource
            ) { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 15.sp,
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.for_text_on_icons)
                        )
                    }
                    innerTextField()
                }
            }
        })
    Spacer(Modifier.height(10.dp))
}

@Composable
fun BasicInputField(
    value: String, placeholder: String, onClick: () -> Unit = {}
) {
    Card(modifier = Modifier.shadow(
            elevation = 25.dp,
            ambientColor = colorResource(R.color.shadow_light),
            spotColor = colorResource(R.color.shadow_light),
            shape = RoundedCornerShape(16.dp)
        ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        content = {
            Box(contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.background_for_inactive_icons),
                        shape = RoundedCornerShape(16.dp)
                    )

                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onClick()
                    }) {
                Text(
                    text = if (value.isNotEmpty()) value else placeholder,
                    fontFamily = mainFont,
                    color = if (value.isNotEmpty()) colorResource(R.color.main_blue) else colorResource(
                        R.color.for_text_on_icons
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        })
    Spacer(Modifier.height(10.dp))
}