package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.placeDetail.type.DropdownOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun IconDropdownMenu(
    menuItems: ImmutableList<DropdownOption>,
    onMenuItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier.wrapContentSize(Alignment.TopEnd)) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_menu_24),
            contentDescription = null,
            tint = SpoonyAndroidTheme.colors.gray500,
            modifier = Modifier
                .noRippleClickable {
                    if (menuItems.isNotEmpty()) {
                        expanded = !expanded
                    }
                }
        )
        Spacer(modifier = Modifier.height(4.dp))
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(RoundedCornerShape(10.dp))
        ) {
            DropdownMenu(
                expanded = expanded,
                modifier = Modifier
                    .background(
                        color = SpoonyAndroidTheme.colors.white,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(
                        vertical = 2.dp,
                        horizontal = 8.dp
                    ),
                onDismissRequest = { expanded = false }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    menuItems.forEach { menuItem ->
                        key(menuItem) {
                            Box(
                                modifier = Modifier
                                    .widthIn(min = 91.dp)
                                    .noRippleClickable {
                                        onMenuItemClick(menuItem.name)
                                        expanded = false
                                    }
                                    .padding(6.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = menuItem.string,
                                    style = SpoonyAndroidTheme.typography.caption1b,
                                    color = SpoonyAndroidTheme.colors.gray900
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun IconDropdownMenuOnePreview() {
    val menuItems = persistentListOf(DropdownOption.REPORT)
    SpoonyAndroidTheme {
        IconDropdownMenu(
            menuItems = menuItems,
            onMenuItemClick = { selectedItem ->
                // selectedItem
            }
        )
    }
}
