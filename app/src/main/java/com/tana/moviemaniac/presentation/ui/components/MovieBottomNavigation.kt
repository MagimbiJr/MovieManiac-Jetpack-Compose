package com.tana.moviemaniac.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tana.moviemaniac.R
import com.tana.moviemaniac.presentation.navigation.BottomNavigation

@Composable
fun MovieBottomNavigation(
    navHostController: NavHostController,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    val items = BottomNavigation.entries
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavigation.Home.route::class.qualifiedName.orEmpty()

    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.bottom_navigation_height))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEach { item ->
                val selected by remember(currentRoute) {
                    derivedStateOf {
                        item.route::class.qualifiedName == currentRoute
                    }
                }
                MovieNavItem(
                    item = item,
                    selected = selected,
                    onItemClick = { onNavigate(item.route::class.qualifiedName.orEmpty()) }
                )
            }
        }
    }
}

@Composable
fun MovieNavItem(
    item: BottomNavigation,
    selected: Boolean,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary.copy(.1f),
    contentColor: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        onClick = onItemClick,
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = if(selected) backgroundColor else Color.Transparent,
            contentColor = contentColor
        )
    ) {
        Row(
            modifier = modifier
                .border(
                    width = if (selected) dimensionResource(id = R.dimen.border_width_small) else 0.dp,
                    color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = CircleShape
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.bottom_navigation_item_padding_vertical),
                    horizontal = dimensionResource(id = R.dimen.bottom_navigation_item_padding_horizontal)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.bottom_navigation_item_spacing))
        ) {
            Icon(
                painter = painterResource(id = item.icon), 
                contentDescription = stringResource(id = item.label),
                modifier = modifier
                    .size(dimensionResource(id = R.dimen.bottom_navigation_icon_size))
            )
            AnimatedVisibility(visible = selected) {
                Text(text = stringResource(id = item.label))
            }
        }
    }
}