package workwork.company.worldskillstest.presenter.commons.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import workwork.company.worldskillstest.R

@Composable
fun BoxWithShadow(
    content: @Composable () -> Unit,
    modifier: Modifier=Modifier,
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                ambientColor = colorResource(R.color.shadow_light),
                spotColor = colorResource(R.color.shadow_light),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.background_for_inactive_icons)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        content = {
            content()
        })
}
