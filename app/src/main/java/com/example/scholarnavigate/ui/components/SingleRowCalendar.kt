package com.example.scholarnavigate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scholarnavigate.data.util.DateUtils
import kotlinx.coroutines.launch
import java.util.*

/**
 * A horizontally scrollable single-row calendar strip showing 30 days
 * (15 past + today + 14 future), with left/right navigation arrows.
 *
 * @param dayNumTextColor  Color for the day-number text (e.g. "15")
 * @param dayTextColor     Color for the day-abbreviation text (e.g. "Mon")
 * @param iconsTintColor   Tint for the prev/next chevron icons
 * @param headTextColor    Color for the month+year header text
 * @param onSelectedDayChange Callback fired when the user taps a day
 */
@Composable
fun SingleRowCalendar(
    dayNumTextColor: Color = MaterialTheme.colorScheme.onSurface,
    dayTextColor: Color = MaterialTheme.colorScheme.onSurface,
    iconsTintColor: Color = MaterialTheme.colorScheme.onSurface,
    headTextColor: Color = MaterialTheme.colorScheme.onSurface,
    onSelectedDayChange: (Date) -> Unit = {}
) {
    val today = remember { Calendar.getInstance(Locale.getDefault()).time }
    val dates = remember {
        DateUtils.getDates(pastDays = 15, futureDays = 14, includeCurrentDate = true)
    }

    var selectedDate by remember { mutableStateOf(today) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Start scrolled to today (index 15)
    LaunchedEffect(Unit) {
        listState.scrollToItem(14)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header: Month Year + navigation arrows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                scope.launch {
                    val first = listState.firstVisibleItemIndex
                    if (first > 0) listState.animateScrollToItem((first - 5).coerceAtLeast(0))
                }
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    tint = iconsTintColor
                )
            }

            Text(
                text = "${DateUtils.getMonthName(selectedDate)} ${DateUtils.getYear(selectedDate)}",
                color = headTextColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            IconButton(onClick = {
                scope.launch {
                    val last = listState.firstVisibleItemIndex
                    if (last < dates.size - 1) {
                        listState.animateScrollToItem((last + 5).coerceAtMost(dates.size - 1))
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = iconsTintColor
                )
            }
        }

        // Day strip
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(dates) { date ->
                val isSelected = DateUtils.getDayNumber(date) == DateUtils.getDayNumber(selectedDate) &&
                        DateUtils.getMonthNumber(date) == DateUtils.getMonthNumber(selectedDate)
                val isToday = DateUtils.getDayNumber(date) == DateUtils.getDayNumber(today) &&
                        DateUtils.getMonthNumber(date) == DateUtils.getMonthNumber(today)

                Column(
                    modifier = Modifier
                        .width(44.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isSelected -> MaterialTheme.colorScheme.primary
                                isToday -> MaterialTheme.colorScheme.primaryContainer
                                else -> Color.Transparent
                            }
                        )
                        .clickable {
                            selectedDate = date
                            onSelectedDayChange(date)
                        }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = DateUtils.getDay3LettersName(date),
                        fontSize = 10.sp,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else dayTextColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = DateUtils.getDayNumber(date),
                        fontSize = 14.sp,
                        fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else dayNumTextColor
                    )
                }
            }
        }
    }
}
