package com.samuel.artspace

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuel.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val archive = ArtArchive()
        archive.addArt(ArtworkRes(title = R.string.artwork_title_0, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_0))
        archive.addArt(ArtworkRes(title = R.string.artwork_title_1, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_1))
        archive.addArt(ArtworkRes(title = R.string.artwork_name_2, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_2))

        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceScreen(archive)
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(artWorkArchive: ArtArchive,modifier: Modifier = Modifier) {
    var art by remember { mutableStateOf(artWorkArchive.firstArt()) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        ArtWorkDisplay(
            artWork = art,
            modifier = Modifier.weight(5f)
        )
        DisplayController(
            onNext = {art = artWorkArchive.nextArt()},
            onPrevious = {art = artWorkArchive.previousArt()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .weight(0.6f)
        )
    }
}

@Composable
fun ArtWorkDisplay(artWork: ArtworkRes, modifier: Modifier = Modifier) {
    Surface (
        shape = RectangleShape,
        shadowElevation = 5.dp,
        modifier = modifier.padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {
            ArtWorkWall(
                image = artWork.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f)
            )
            Spacer(
                Modifier.size(10.dp)
            )
            ArtWorkDescription(
                title = artWork.title,
                artist = artWork.artist,
                year = artWork.year,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ArtWorkWall(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Surface (
        shape = RectangleShape,
        shadowElevation = 10.dp,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable()
fun ArtWorkDescription(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    modifier: Modifier = Modifier
) {
    Surface (
        shape = RectangleShape,
        shadowElevation = 10.dp,
        modifier = modifier
    ){
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ){
            Text(
                text = stringResource(id = title),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(id = artist),
                    fontSize = 18.sp
                )
                Text(
                    text = "(" + stringResource(id = year) + ")",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DisplayController(
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        //Button Previous
        NavigationButton(
            direction = Direction.Previous,
            onClick = onPrevious
        )
        NavigationButton(
            direction = Direction.Next,
            onClick = onNext
        )
    }
}

@Composable
fun NavigationButton(
    onClick: () -> Unit,
    direction: Direction,
    modifier: Modifier = Modifier
) {
    val text: String = when (direction) {
        Direction.Next -> "Next"
        Direction.Previous -> "Previous"
    }

    ElevatedButton(
        onClick = onClick,
        shape = RectangleShape,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 20.dp,
            pressedElevation = 5.dp
        ),
        modifier = modifier.size(width = 120.dp, height = 50.dp)
    ) {
        Text(text = text)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val archive = ArtArchive()
    archive.addArt(ArtworkRes(title = R.string.artwork_title_0, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_0))
    archive.addArt(ArtworkRes(title = R.string.artwork_title_1, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_1))
    archive.addArt(ArtworkRes(title = R.string.artwork_name_2, artist = R.string.artwork_artist_0, year = R.string.artwork_year_0, image = R.drawable.background_2))

    ArtSpaceTheme {
        ArtSpaceScreen(archive)
    }
}

data class ArtworkRes(
    @StringRes val title: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int,
    @DrawableRes val image: Int
)

enum class Direction {
    Next, Previous
}

class ArtArchive(private val arts: MutableList<ArtworkRes> = mutableListOf()) {
    private var curArt: Int = 0

    fun firstArt(): ArtworkRes {
        curArt = 0
        return arts[curArt]
    }
    fun addArt(art: ArtworkRes): Unit {
        arts.add(art)
    }

    fun nextArt(): ArtworkRes {
        curArt = (curArt + 1) % arts.size
        return arts[curArt]
    }

    fun previousArt(): ArtworkRes {
        if (curArt - 1 < 0) {
            curArt = arts.size - 1
        } else {
            curArt -= 1
        }
        return arts[curArt]
    }
}