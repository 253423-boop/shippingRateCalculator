package com.ivanna.proyects.shippingratecalculator.shippingRateCalculatorVM

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanna.proyects.shippingratecalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingRateCalculatorVMPage(
    viewModel: shippingRateCalculatorViewModel = viewModel()
) {
    val cantidad by viewModel.cantidad.collectAsStateWithLifecycle()
    val distancia by viewModel.distancia.collectAsStateWithLifecycle()
    val tipoUsuario by viewModel.tipoUsuario.collectAsStateWithLifecycle()
    val tipoEnvio by viewModel.tipoEnvio.collectAsStateWithLifecycle()
    val codigoCupon by viewModel.codigoCupon.collectAsStateWithLifecycle()
    val cuponAplicado by viewModel.cuponAplicado.collectAsStateWithLifecycle()
    val subtotal by viewModel.subtotal.collectAsStateWithLifecycle()
    val descuentoUsuario by viewModel.descuentoUsuario.collectAsStateWithLifecycle()
    val costoEnvio by viewModel.costoEnvio.collectAsStateWithLifecycle()
    val descuentoCupon by viewModel.descuentoCupon.collectAsStateWithLifecycle()
    val total by viewModel.total.collectAsStateWithLifecycle()

    val esCuponValido = codigoCupon == "AHORRO100" && subtotal > 500.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titulo_app)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { valoresRelleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(valoresRelleno)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.subtitulo_app),
                style = MaterialTheme.typography.bodySmall
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = { viewModel.cambiarCantidad(cantidad - 1) },
                            enabled = cantidad > 1
                        ) {
                            Text("-", style = MaterialTheme.typography.titleMedium)
                        }
                        OutlinedTextField(
                            value = cantidad.toString(),
                            onValueChange = { valor ->
                                valor.toIntOrNull()?.let { viewModel.cambiarCantidad(it) }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(50.dp)
                        )
                        TextButton(
                            onClick = { viewModel.cambiarCantidad(cantidad + 1) },
                            enabled = cantidad < 50
                        ) {
                            Text("+", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(R.string.etiqueta_precio_unitario),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.formato_precio_unitario),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.etiqueta_distancia) + ": " + stringResource(R.string.formato_distancia, distancia),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = distancia.toFloat(),
                        onValueChange = { viewModel.cambiarDistancia(it.toInt()) },
                        valueRange = 1f..200f,
                        steps = 198
                    )
                    if (distancia > 50) {
                        Text(
                            text = stringResource(R.string.info_costo_extra),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.etiqueta_tipo_usuario),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BotonOpcion(
                            texto = stringResource(R.string.usuario_estandar),
                            seleccionado = tipoUsuario == TipoUsuario.ESTANDAR,
                            alHacerClic = { viewModel.cambiarTipoUsuario(TipoUsuario.ESTANDAR) },
                            modifier = Modifier.weight(1f)
                        )
                        BotonOpcion(
                            texto = stringResource(R.string.usuario_vip),
                            seleccionado = tipoUsuario == TipoUsuario.VIP,
                            alHacerClic = { viewModel.cambiarTipoUsuario(TipoUsuario.VIP) },
                            modifier = Modifier.weight(1f)
                        )
                        BotonOpcion(
                            texto = stringResource(R.string.usuario_premium),
                            seleccionado = tipoUsuario == TipoUsuario.PREMIUM,
                            alHacerClic = { viewModel.cambiarTipoUsuario(TipoUsuario.PREMIUM) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.etiqueta_tipo_envio),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BotonOpcion(
                            texto = stringResource(R.string.envio_economico),
                            seleccionado = tipoEnvio == TipoEnvio.ECONOMICO,
                            alHacerClic = { viewModel.cambiarTipoEnvio(TipoEnvio.ECONOMICO) },
                            modifier = Modifier.weight(1f)
                        )
                        BotonOpcion(
                            texto = stringResource(R.string.envio_expres),
                            seleccionado = tipoEnvio == TipoEnvio.EXPRES,
                            alHacerClic = { viewModel.cambiarTipoEnvio(TipoEnvio.EXPRES) },
                            modifier = Modifier.weight(1f)
                        )
                        BotonOpcion(
                            texto = stringResource(R.string.envio_mismo_dia),
                            seleccionado = tipoEnvio == TipoEnvio.MISMO_DIA,
                            alHacerClic = { viewModel.cambiarTipoEnvio(TipoEnvio.MISMO_DIA) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.etiqueta_cupon),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = codigoCupon,
                            onValueChange = { viewModel.cambiarCodigoCupon(it) },
                            placeholder = { Text(stringResource(R.string.pista_cupon)) },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(
                            onClick = { viewModel.alternarCupon(!cuponAplicado) },
                            enabled = esCuponValido || cuponAplicado
                        ) {
                            Text(
                                if (cuponAplicado) stringResource(R.string.boton_aplicado)
                                else stringResource(R.string.boton_aplicar)
                            )
                        }
                    }
                    if (cuponAplicado) {
                        Text(
                            text = stringResource(R.string.cupon_aplicado_msj),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (codigoCupon.isNotEmpty() && !esCuponValido) {
                        Text(
                            text = stringResource(R.string.cupon_invalido_msj),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.titulo_resumen),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FilaResumen(stringResource(R.string.etiqueta_subtotal), subtotal)
                    if (descuentoUsuario > 0) {
                        FilaResumen(stringResource(R.string.etiqueta_descuento_usuario), -descuentoUsuario, true)
                    }
                    FilaResumen(stringResource(R.string.etiqueta_costo_envio), costoEnvio)
                    if (descuentoCupon > 0) {
                        FilaResumen(stringResource(R.string.etiqueta_descuento_cupon), -descuentoCupon, true)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.etiqueta_total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.formato_moneda, total),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun BotonOpcion(
    texto: String,
    seleccionado: Boolean,
    alHacerClic: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = alHacerClic,
        modifier = modifier
            .background(
                color = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (seleccionado) 0.dp else 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            color = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun FilaResumen(
    etiqueta: String,
    valor: Double,
    esDescuento: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, style = MaterialTheme.typography.bodySmall)
        Text(
            text = stringResource(R.string.formato_moneda, valor),
            style = MaterialTheme.typography.bodySmall,
            color = if (esDescuento) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}