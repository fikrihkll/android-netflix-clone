package com.dagger.netflixclone.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dagger.netflixclone.ui.entity.BottomNavMenu

@Composable
fun NetflixBottomNavigation(
    modifier: Modifier = Modifier,
    menuList: List<BottomNavMenu>,
    onMenuSelected: (BottomNavMenu) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var selectedId by rememberSaveable {
        mutableStateOf("")
    }
    val menuWidth = screenWidth/menuList.size

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
    ) {
        for (menu in menuList) {
            BottomNavigationItem(
                modifier = Modifier
                    .width(menuWidth.dp)
                    .padding(vertical = 4.dp)
                    .clickable {
                       selectedId = menu.id
                    },
                isSelected = selectedId == menu.id,
                menu = menu
            )
        }
    }
}

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    menu: BottomNavMenu,
    isSelected: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        menu.imageEmoji?.let {
            Text(text = it, fontSize = 26.sp)
        } ?: run {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = menu.imageVector ?: Icons.Filled.Build,
                contentDescription = "home",
                tint = if (isSelected) Color.White else Color.Gray
            )
        }

        Text(menu.title, color = if (isSelected) Color.White else Color.Gray)
    }
}

@Preview
@Composable
private fun NetflixBottomNavigationPreview() {
    NetflixBottomNavigation(
        menuList = listOf(
            BottomNavMenu(
                id = "abc",
                title = "test",
                imageVector = Icons.Filled.Home
            ),
            BottomNavMenu(
                id = "abc2",
                title = "test",
                imageVector = Icons.Filled.PlayArrow
            ),
            BottomNavMenu(
                id = "abc3",
                title = "test",
                imageEmoji = "😆"
            ),
        ),
        onMenuSelected = {}
    )
}

@Preview
@Composable
private fun BottomNavigationItemPreview() {
    Row {
        BottomNavigationItem(
            menu = BottomNavMenu(
                id = "abc",
                title = "test",
                imageVector = Icons.Filled.Home
            ),
            isSelected = true,
        )
        BottomNavigationItem(
            menu = BottomNavMenu(
                id = "abc",
                title = "test",
                imageEmoji = "😆"
            ),
            isSelected = true,
        )
    }
}