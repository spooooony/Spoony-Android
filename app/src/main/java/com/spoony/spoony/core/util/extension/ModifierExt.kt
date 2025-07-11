package com.spoony.spoony.core.util.extension

import android.graphics.BlurMaskFilter
import android.graphics.Matrix
import android.graphics.RadialGradient
import android.graphics.Shader
import android.view.HapticFeedbackConstants
import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.black
import com.spoony.spoony.core.designsystem.theme.gray500
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() }
    )
}

fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

@Composable
fun Modifier.spoonyGradient(
    cornerRadius: Dp,
    mainColor: Color = black,
    secondColor: Color = gray500
) = composed {
    this.drawWithContent {
        val roundedCornerPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(Offset.Zero, size),
                    cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
            )
        }

        clipPath(roundedCornerPath) {
            val gradientBrush = Brush.radialGradient(
                0.48f to mainColor,
                1f to secondColor,
                center = Offset(size.width, -size.height),
                radius = size.width * 2
            )

            drawPath(path = roundedCornerPath, brush = gradientBrush)
        }

        drawContent()
    }
}

fun Modifier.advancedImePadding() = composed {
    var consumePadding by remember { mutableStateOf(0) }
    onGloballyPositioned { coordinates ->
        val rootCoordinate = coordinates.findRootCoordinates()
        val bottom = coordinates.positionInWindow().y + coordinates.size.height

        consumePadding = (rootCoordinate.size.height - bottom).toInt().coerceAtLeast(0)
    }
        .consumeWindowInsets(PaddingValues(bottom = (consumePadding / LocalDensity.current.density).dp))
        .imePadding()
}

@Composable
fun Modifier.hapticClick(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit = {}
): Modifier = composed {
    val view = LocalView.current

    this.clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = {
            onClick()
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
    )
}

@Composable
fun Modifier.bounceClick(
    scaleDown: Float = 0.8f,
    scaleUp: Float = 1.2f,
    defaultScale: Float = 1.0f,
    onClick: () -> Unit = {}
): Modifier = composed {
    val scope = rememberCoroutineScope()
    var isClicked by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val durationMillis by remember { mutableIntStateOf(100) }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(150)
            isClicked = false
        }
    }

    val scaleXSate by animateFloatAsState(
        targetValue = if (isClicked) scaleUp else defaultScale,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = LinearEasing
        ),
        label = ""
    )

    val scaleYState by animateFloatAsState(
        targetValue = if (isClicked) scaleDown else defaultScale,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = LinearEasing
        ),
        label = ""
    )

    this
        .graphicsLayer {
            scaleX = scaleXSate
            scaleY = scaleYState
        }
        .hapticClick(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
                onClick()
                scope.launch {
                    // 화면 전환과 동시에 애니메이션이 들어갈 경우 부자연스러워짐을 방지하기 위한 delay
                    delay(100)
                    isClicked = true
                }
            }
        )
}

@Composable
fun Modifier.rotateClick(
    scaleDown: Float = 0.01f,
    rotateMax: Float = 1000f,
    onClick: () -> Unit = {}
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        if (isPressed) scaleDown else 1f,
        label = ""
    )

    val rotation by animateFloatAsState(
        if (isPressed) rotateMax else 1f,
        label = ""
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            rotationX = rotation
            rotationY = rotation
            rotationZ = rotation
        }
        .hapticClick(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

data class ShadowSpread(
    @FloatRange(from = 0.0) val left: Float = 0f,
    @FloatRange(from = 0.0) val top: Float = 0f,
    @FloatRange(from = 0.0) val right: Float = 0f,
    @FloatRange(from = 0.0) val bottom: Float = 0f
)

@Composable
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 1.dp,
    offsetY: Dp = 1.dp,
    offsetX: Dp = 1.dp,
    spread: Dp = 1.dp
) = composed {
    val density = LocalDensity.current

    val paint = remember(color, blur) {
        Paint().apply {
            this.color = color
            val blurPx = with(density) { blur.toPx() }
            if (blurPx > 0f) {
                this.asFrameworkPaint().maskFilter =
                    BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }
        }
    }

    drawBehind {
        val spreadPx = spread.toPx()
        val offsetXPx = offsetX.toPx()
        val offsetYPx = offsetY.toPx()

        val shadowWidth = size.width + spreadPx
        val shadowHeight = size.height + spreadPx

        if (shadowWidth <= 0f || shadowHeight <= 0f) return@drawBehind

        val shadowSize = Size(shadowWidth, shadowHeight)
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.translate(offsetXPx, offsetYPx)
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
        }
    }
}

@Composable
fun Modifier.spoonySliderGradient(
    cornerRadius: Dp = 40.dp,
    mainColor: Color = Color(0xFFFF5235),
    secondColor: Color = Color(0xFFFFCEC6)
) = composed {
    this.drawWithCache {
        val roundPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(Offset.Zero, size),
                    cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
            )
        }

        val cx = size.width
        val cy = 0f
        val radiusX = size.width * 1.4142f
        val radiusY = size.height * 1.4142f
        val scaleY = radiusY / radiusX

        val shader = RadialGradient(
            cx,
            cy,
            radiusX,
            intArrayOf(mainColor.toArgb(), secondColor.toArgb()),
            floatArrayOf(0.545f, 1.0f),
            Shader.TileMode.CLAMP
        ).apply {
            val matrix = Matrix().apply {
                setScale(1f, scaleY, cx, cy)
            }
            setLocalMatrix(matrix)
        }

        val paint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            this.shader = shader
        }

        onDrawWithContent {
            clipPath(roundPath) {
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawPath(roundPath.asAndroidPath(), paint)
                }
            }
            drawContent()
        }
    }
}
