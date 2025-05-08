package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun CustomFieldInBox(
    modifier: Modifier = Modifier,
    verticalPadding: Int,
    horizontalPadding: Int,
    hint: String,
    valueState: MutableState<String>,
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    onLeftIconClick: (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                colorResource(R.color.main_blue).copy(0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = verticalPadding.dp, horizontal = horizontalPadding.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(R.color.white),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.main_blue),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 22.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leftIcon != null) {
                Icon(
                    painter = painterResource(leftIcon),
                    contentDescription = null,
                    tint = colorResource(R.color.main_blue),
                    modifier = Modifier
                        .size(20.dp)
                        .then(
                            if (onLeftIconClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onLeftIconClick()
                                }
                            } else Modifier
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            MainCustomTextField(
                value = valueState.value,
                onValueChange = { valueState.value = it },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = mainFont,
                    color = colorResource(R.color.main_blue)
                ),
                hint = hint,
                cursorColor = colorResource(R.color.main_blue),
                decorationBox = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            it()
                        }
                    }
                }
            )

            if (rightIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(rightIcon),
                    contentDescription = null,
                    tint = colorResource(R.color.main_blue),
                    modifier = Modifier
                        .size(20.dp)
                        .then(
                            if (onRightIconClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onRightIconClick()
                                }
                            } else Modifier
                        )
                )
            }
        }
    }
}