import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yogaappadmin.data.YogaClass

@Composable
fun ClassItem(yogaClass: YogaClass, onClick: (YogaClass) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClick(yogaClass) }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F2F9),
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = yogaClass.id.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Yoga class: ${yogaClass.id}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Day of week: ${yogaClass.dayOfWeek}"
                )
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Teacher: ${yogaClass.teacher}"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Type: ${yogaClass.type}"
                )
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Time: ${yogaClass.time}"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Duration: ${yogaClass.duration} minutes"
                )
                Text(
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Capacity: ${yogaClass.capacity}"
                )
            }

            Text(
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp),
                text = "Price: $${yogaClass.price}"
            )

            Text(
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp),
                text = "Description: ${yogaClass.description}"
            )
        }
    }
}
