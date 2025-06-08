package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.ui.theme.mainFont


@Composable
fun BackButtonWithTitle(
    text: String,
    onExitClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        if (onExitClick != null) {
            IconButton(onClick = onExitClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Назад",
                    tint = colorResource(R.color.for_text_on_icons),
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = text,
            fontFamily = mainFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource( R.color.text_light_night)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}