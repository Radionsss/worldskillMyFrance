package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import workwork.company.worldskillstest.R


@Composable
fun MainCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    cursorColor: Color,
    modifier: Modifier = Modifier,
    decorationBox: @Composable (it: @Composable () -> Unit) -> Unit,
    hint: String,

    ) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        modifier = modifier.fillMaxWidth(),
        cursorBrush = SolidColor(cursorColor),
        decorationBox = {
            decorationBox {
                if (value.isEmpty()) {
                    Text(
                        text = hint ,
                        style = textStyle.copy(color = colorResource(R.color.for_text_on_icons)) // Стиль для текста-заполнителя
                    )
                }
                it()
            }
        }
    )
}