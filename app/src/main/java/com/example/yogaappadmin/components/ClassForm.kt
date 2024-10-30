import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.yogaappadmin.data.YogaClass
import com.example.yogaappadmin.viewmodel.YogaViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassForm(
    yogaClass: YogaClass?,
    onClose: () -> Unit,
    viewModel: YogaViewModel
) {
    val id by remember { mutableStateOf(yogaClass?.id ?: 0) }
    var dayOfWeek by remember { mutableStateOf(yogaClass?.dayOfWeek ?: "") }
    var time by remember { mutableStateOf(yogaClass?.time ?: "") }
    var teacher by remember { mutableStateOf(yogaClass?.teacher ?: "") }
    var capacity by remember { mutableStateOf(yogaClass?.capacity?.toString() ?: "") }
    var price by remember { mutableStateOf(yogaClass?.price?.toString() ?: "") }
    var duration by remember { mutableStateOf(yogaClass?.duration?.toString() ?: "") }
    var type by remember { mutableStateOf(yogaClass?.type ?: "") }
    var description by remember { mutableStateOf(yogaClass?.description ?: "") }
    var errorMessage by remember { mutableStateOf("") }


    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    var showDeleteDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (yogaClass != null) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }


        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = "Confirm Delete") },
                text = { Text("Are you sure to delete?") },
                confirmButton = {
                    Button(
                        onClick = {
                            yogaClass?.let { viewModel.deleteYogaClass(it.id) }
                            showDeleteDialog = false
                            onClose()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }


        OutlinedTextField(
            value = dayOfWeek,
            onValueChange = { dayOfWeek = it },
            label = { Text("Time of week") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors( // Tùy chỉnh màu sắc khi disable
                disabledTextColor = MaterialTheme.colorScheme.onSurface, // Giữ màu chữ như bình thường
                disabledBorderColor = MaterialTheme.colorScheme.outline, // Giữ màu viền như bình thường
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Giữ màu label như bình thường
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Time of week",
                    modifier = Modifier
                        .clickable {
                            keyboardController?.hide()
                            showDatePickerDialog(context) { selectedDayOfWeek ->
                                dayOfWeek = selectedDayOfWeek
                            }
                        },
                    tint = MaterialTheme.colorScheme.primary  // Thêm tint color cho icon
                )
            }
        )


        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = teacher,
            onValueChange = { teacher = it },
            label = { Text("Teacher") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )


        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }


        Row {
            Button(
                onClick = {
                    keyboardController?.hide()
                    if (dayOfWeek.isEmpty() || time.isEmpty() || teacher.isEmpty() ||
                        capacity.isEmpty() || price.isEmpty() || type.isEmpty() || duration.isEmpty()) {
                        errorMessage = "Please fill all required field!"
                    } else {
                        val newClass = YogaClass(
                            id = id,
                            dayOfWeek = dayOfWeek,
                            time = time,
                            teacher = teacher,
                            capacity = capacity.toInt(),
                            duration = duration.toInt(),
                            price = price.toDouble(),
                            type = type,
                            description = description.ifEmpty { null }
                        )


                        if (yogaClass == null) {
                            viewModel.insertYogaClass(newClass)
                        } else {
                            viewModel.updateYogaClass(newClass)
                        }
                        onClose()
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (yogaClass == null) "Add class" else "Update class")
            }


            Button(
                onClick = {
                    keyboardController?.hide()
                    onClose()
                },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancel")
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun showDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()


    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedLocalDate = LocalDate.of(year, month + 1, dayOfMonth)
            val dayOfWeek = selectedLocalDate.dayOfWeek.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            )
            onDateSelected(dayOfWeek)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

