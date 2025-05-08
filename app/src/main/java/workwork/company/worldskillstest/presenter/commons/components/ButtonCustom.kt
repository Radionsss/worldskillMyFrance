package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.ui.theme.mainFont

@Composable
fun ButtonCustom(
    text: String, // Текст для кнопки "Отмена"
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.main_color_default)),
        elevation = CardDefaults.cardElevation(
            0.dp
        ),
        content = {
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(), // Равномерное распределение пространства
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(R.color.white), // Цвет текста
                    containerColor = colorResource(R.color.main_blue)  // Фон кнопки
                ),
                elevation = null, // Убираем тень
                shape = RoundedCornerShape(16.dp), // Закругление углов
                contentPadding = PaddingValues(12.dp) // Внутренний отступ
            ) {
                Text(
                    text = text,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = colorResource(R.color.white) // Цвет текста
                )
            }
        })
}