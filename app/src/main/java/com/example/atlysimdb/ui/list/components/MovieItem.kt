package com.example.atlysimdb.ui.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.atlysimdb.R
import com.example.atlysimdb.bl.network.domainData.Movie
import com.example.atlysimdb.ui.theme.AtlysIMDBTheme
import com.example.atlysimdb.ui.theme.CustomTheme

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (image, title) = createRefs()

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("2:3")
                    })

            Text(
                text = movie.title,
                style = CustomTheme.typography.titleMd,
                color = CustomTheme.colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(image.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    AtlysIMDBTheme {
        MovieItem(
            movie = Movie(
                id = 1,
                title = "Inception",
                posterPath = "/pzIddUEMWhWzfvLI3TwxUG2wGoi.jpeg",
                overview = "A mind-bending thriller",
                releaseDate = "",
                voteAverage = 2.7
            ), onClick = {})
    }
}