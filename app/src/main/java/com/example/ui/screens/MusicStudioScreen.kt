package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SandGold
import com.example.ui.theme.SurfBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicStudioScreen(onBack: () -> Unit) {
    var bpm by remember { mutableStateOf(85f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Music Studio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sovereign Beats",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Drop some rhythms for the crew.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tempo (BPM)", fontWeight = FontWeight.Bold)
                        Text("${bpm.toInt()}", style = MaterialTheme.typography.titleLarge, color = SandGold)
                    }
                    Slider(
                        value = bpm,
                        onValueChange = { bpm = it },
                        valueRange = 75f..125f,
                        colors = SliderDefaults.colors(
                            thumbColor = SandGold,
                            activeTrackColor = SandGold
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("75", style = MaterialTheme.typography.labelSmall)
                        Text("125", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(8) { index ->
                    BeatPad(index)
                }
            }
            
            Button(
                onClick = { /* TODO: Save Beat */ },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Register Album")
            }
        }
    }
}

@Composable
fun BeatPad(index: Int) {
    var isPressed by remember { mutableStateOf(false) }
    
    Surface(
        onClick = { isPressed = !isPressed },
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp)),
        color = if (isPressed) SurfBlue else MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = if (isPressed) 8.dp else 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = if (isPressed) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Pad ${index + 1}",
                modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp),
                style = MaterialTheme.typography.labelSmall,
                color = if (isPressed) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}
