package com.ivanna.proyects.shippingratecalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaColores = lightColorScheme(
    primary = PrimarioVerde,
    onPrimary = EnPrimarioBlanco,
    primaryContainer = ContenedorPrimario,
    onPrimaryContainer = EnContenedorPrimario,
    secondary = SecundarioVerde,
    background = FondoPagina,
    onBackground = EnContenedorPrimario,
    surface = SuperficieBlanca,
    onSurface = EnContenedorPrimario,
    surfaceVariant = BordeCuadro,
    outline = BordeCuadro,
    error = ColorError
)

@Composable
fun ShippingRateCalculatorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EsquemaColores,
        typography = Typography,
        content = content
    )
}
