package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.viewmodel.DashboardViewModel
import com.example.util.Dragon9ClockEngine
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToVector: () -> Unit,
    onNavigateToMusic: () -> Unit,
    onNavigateToComic: () -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    var d9Time by remember { mutableStateOf(Dragon9ClockEngine.getCurrentDragon9Time()) }
    val profile by viewModel.profile.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        while (true) {
            d9Time = Dragon9ClockEngine.getCurrentDragon9Time()
            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Studio AI", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroBanner()
            }

            item {
                ClockCard(d9Time)
            }

            item {
                StatusRow(
                    harmony = profile?.harmonyNotes ?: 0,
                    shavings = profile?.shavings ?: 0
                )
            }

            item {
                Text(
                    "Creative Workflows",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                WorkflowGrid(onNavigateToVector, onNavigateToMusic, onNavigateToComic)
            }
        }
    }
}

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_hero_banner_1790601103670),
            contentDescription = "Port Waikato Sunset",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                "Welcome to Port Waikato",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Unleash your creativity in the flow.",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ClockCard(time: Dragon9ClockEngine.Dragon9Time) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (time.isApex) CoralAccent else SurfBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (time.isApex) "APEX WEDGE" else "OPERATIONAL HOUR",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val timeText = if (time.isApex) {
                time.apexType?.name ?: "APEX"
            } else {
                "Hour ${time.currentHour?.plus(1)} / 26"
            }
            
            Text(
                timeText,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            if (!time.isApex) {
                Spacer(modifier = Modifier.height(4.dp))
                val minutesLeft = 54 - (time.minuteInHour ?: 0.0)
                Text(
                    "${minutesLeft.toInt()} mins left in flow",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                
                LinearProgressIndicator(
                    progress = { (time.minuteInHour ?: 0.0).toFloat() / 54f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = SandGold,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
            } else {
                Text(
                    "Community Pause Window",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun StatusRow(harmony: Int, shavings: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatusItem(
            modifier = Modifier.weight(1f),
            label = "Harmony",
            value = harmony.toString(),
            icon = Icons.Default.MusicNote,
            color = SandGold
        )
        StatusItem(
            modifier = Modifier.weight(1f),
            label = "Shavings",
            value = shavings.toString(),
            icon = Icons.Default.Token,
            color = CoralAccent
        )
    }
}

@Composable
fun StatusItem(modifier: Modifier, label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = label, tint = color)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelSmall)
                Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun WorkflowGrid(onVector: () -> Unit, onMusic: () -> Unit, onComic: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        WorkflowCard("Vector Studio", "Extract art from your sessions", Icons.Default.Brush, SurfBlue, onVector)
        WorkflowCard("Music Studio", "Drop beats and trip-hop rhythms", Icons.Default.MusicVideo, SandGold, onMusic)
        WorkflowCard("Comic Builder", "Create digital AR stories", Icons.Default.AutoStories, CoralAccent, onComic)
    }
}

@Composable
fun WorkflowCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("workflow_${title.replace(" ", "_").lowercase()}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.2f)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = color
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
