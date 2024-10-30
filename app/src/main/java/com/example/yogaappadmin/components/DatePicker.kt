package com.example.yogaappadmin.components

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerComponent(context: Context, onDateSelected: (String) -> Unit) {
    var selectedDate by remember { mutableStateOf("") }

    val openDatePicker = {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedLocalDate = LocalDate.of(year, month + 1, dayOfMonth)

                val dayOfWeek = selectedLocalDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                selectedDate = "$dayOfMonth/${month + 1}/$year ($dayOfWeek)"

                onDateSelected(dayOfWeek)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = openDatePicker) {
            Text(text = "Chọn ngày")
        }

        if (selectedDate.isNotEmpty()) {
            Text(text = "Ngày đã chọn: $selectedDate", modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DatePickerComponentPreview() {
    DatePickerComponent(context = LocalContext.current) { dayOfWeek ->
        println("Ngày trong tuần: $dayOfWeek")
    }
}
