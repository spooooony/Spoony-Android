package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

@Composable
fun PlaceDetailImageLazyRow(
    imageList: ImmutableList<String>,
    modifier: Modifier = Modifier,
    isBlurred: Boolean = true
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageSize = if (imageList.size == 1) screenWidth * 1f - 40.dp else screenWidth * (278f / 360f)
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(imageList, key = { index, _ -> index }) { _, imageUrl ->
            AsyncImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpoonyAndroidTheme.colors.gray300)
                    .then(
                        if (isBlurred) Modifier.blur(24.dp) else Modifier
                    ),
                model = ImageRequest.Builder(context)
                    .crossfade(true)
                    .data(imageUrl)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PlaceDetailImageLazyRowPreview() {
    SpoonyAndroidTheme {
        PlaceDetailImageLazyRow(
            imageList = immutableListOf(
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473779988_950264370546560_5341501240589886868_n.jpg?stp=dst-jpg_e35_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_ohc=pHHeQnTQ2MMQ7kNvgFAMpBC&_nc_gid=ade93f1afc274f04a82c92d9b55d5753&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzU0NjA2ODI2MjUyMDI2ODQxNA%3D%3D.3-ccb7-5&oh=00_AYCoKFozJIoZG9Izmc5UtfR5Gg__iKqdIG_lBiKdHBHHoQ&oe=678DA05C&_nc_sid=10d13b",
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473777300_2608644809333752_6829001915967720892_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=104&_nc_ohc=jGWaaI-t3NgQ7kNvgG_G7jW&_nc_gid=ade93f1afc274f04a82c92d9b55d5753&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzU0NjA2ODI2MjkzMTE3ODc4Ng%3D%3D.3-ccb7-5&oh=00_AYA2Yk3eVifWtHRTdKnlzzrSExKSY53mP1SoTgVhRS9WZA&oe=678DA27E&_nc_sid=10d13b",
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473699784_1020999916728583_5213195047254766315_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE0NDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=107&_nc_ohc=Jsa46D-cPKQQ7kNvgEb3Ibg&_nc_gid=43b0cc3eec544f7b82ac6ce4d0931342&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU0NDg1ODAzMzAwMjU2MzI2Nw%3D%3D.3-ccb7-5&oh=00_AYAUoYMY3WAk816Pv1ntEDKsuGiRyiIqIIEtN2n_rNDOzA&oe=678DAEA4&_nc_sid=7a9f4b",
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473668872_631155566092547_2449423066645547426_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=111&_nc_ohc=R493Nwe8XM4Q7kNvgGceBQv&_nc_gid=43b0cc3eec544f7b82ac6ce4d0931342&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzU0NDY0OTE1NzAwNzA2MzU2Mg%3D%3D.3-ccb7-5&oh=00_AYDU1LNSGLa4GKAZQRlX5ABzJQ4qf5h62z257zioGFlesA&oe=678DABD6&_nc_sid=7a9f4b",
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473560284_568265156121387_7921460915182213394_n.jpg?stp=dst-jpegr_e35_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuaGRyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=gKoX2mv1x0QQ7kNvgHd6AjB&_nc_gid=af16ea24a7d04cc9a0d1e86a87ffccac&edm=APoiHPcBAAAA&ccb=7-5&ig_cache_key=MzU0NDU3MjM1MzUwNDM2MDA5Ng%3D%3D.3-ccb7-5&oh=00_AYDc2sbybqTbqQJaVw9HkW-Zt4aaVElCM-ecAUheJdCyGQ&oe=678DAE1A&_nc_sid=22de04"
            ),
            isBlurred = false
        )
    }
}

@Preview
@Composable
private fun PlaceDetailImageLazyRowOnePreview() {
    SpoonyAndroidTheme {
        PlaceDetailImageLazyRow(
            imageList = immutableListOf(
                "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/473779988_950264370546560_5341501240589886868_n.jpg?stp=dst-jpg_e35_tt7&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE4MDAuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=108&_nc_ohc=pHHeQnTQ2MMQ7kNvgFAMpBC&_nc_gid=ade93f1afc274f04a82c92d9b55d5753&edm=APs17CUBAAAA&ccb=7-5&ig_cache_key=MzU0NjA2ODI2MjUyMDI2ODQxNA%3D%3D.3-ccb7-5&oh=00_AYCoKFozJIoZG9Izmc5UtfR5Gg__iKqdIG_lBiKdHBHHoQ&oe=678DA05C&_nc_sid=10d13b"
            ),
            isBlurred = false
        )
    }
}
