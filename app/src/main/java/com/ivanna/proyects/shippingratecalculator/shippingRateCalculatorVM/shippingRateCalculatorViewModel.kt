package com.ivanna.proyects.shippingratecalculator.shippingRateCalculatorVM

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class TipoUsuario(val porcentajeDescuento: Double) {
    ESTANDAR(0.0),
    VIP(0.10),
    PREMIUM(0.20)
}

enum class TipoEnvio(val costoAdicional: Double) {
    ECONOMICO(0.0),
    EXPRES(80.0),
    MISMO_DIA(180.0)
}

class shippingRateCalculatorViewModel : ViewModel() {

    private val _cantidad = MutableStateFlow(1)
    val cantidad: StateFlow<Int> = _cantidad.asStateFlow()

    private val _distancia = MutableStateFlow(1)
    val distancia: StateFlow<Int> = _distancia.asStateFlow()

    private val _tipoUsuario = MutableStateFlow(TipoUsuario.ESTANDAR)
    val tipoUsuario: StateFlow<TipoUsuario> = _tipoUsuario.asStateFlow()

    private val _tipoEnvio = MutableStateFlow(TipoEnvio.ECONOMICO)
    val tipoEnvio: StateFlow<TipoEnvio> = _tipoEnvio.asStateFlow()

    private val _codigoCupon = MutableStateFlow("")
    val codigoCupon: StateFlow<String> = _codigoCupon.asStateFlow()

    private val _cuponAplicado = MutableStateFlow(false)
    val cuponAplicado: StateFlow<Boolean> = _cuponAplicado.asStateFlow()

    private val _subtotal = MutableStateFlow(150.0)
    val subtotal: StateFlow<Double> = _subtotal.asStateFlow()

    private val _descuentoUsuario = MutableStateFlow(0.0)
    val descuentoUsuario: StateFlow<Double> = _descuentoUsuario.asStateFlow()

    private val _costoEnvio = MutableStateFlow(0.0)
    val costoEnvio: StateFlow<Double> = _costoEnvio.asStateFlow()

    private val _descuentoCupon = MutableStateFlow(0.0)
    val descuentoCupon: StateFlow<Double> = _descuentoCupon.asStateFlow()

    private val _total = MutableStateFlow(150.0)
    val total: StateFlow<Double> = _total.asStateFlow()

    init {
        calcularCotizacion()
    }

    fun cambiarCantidad(nuevaCantidad: Int) {
        _cantidad.value = nuevaCantidad.coerceIn(1, 50)
        calcularCotizacion()
    }

    fun cambiarDistancia(nuevaDistancia: Int) {
        _distancia.value = nuevaDistancia.coerceIn(1, 200)
        calcularCotizacion()
    }

    fun cambiarTipoUsuario(nuevoTipo: TipoUsuario) {
        _tipoUsuario.value = nuevoTipo
        calcularCotizacion()
    }

    fun cambiarTipoEnvio(nuevoTipo: TipoEnvio) {
        _tipoEnvio.value = nuevoTipo
        calcularCotizacion()
    }

    fun cambiarCodigoCupon(nuevoCodigo: String) {
        _codigoCupon.value = nuevoCodigo
    }

    fun alternarCupon(aplicar: Boolean) {
        val esValido = _codigoCupon.value == "AHORRO100" && _subtotal.value > 500.0
        _cuponAplicado.value = aplicar && esValido
        calcularCotizacion()
    }

    private fun calcularCotizacion() {
        val subtotalCalculado = 150.0 * _cantidad.value
        val descUsuarioCalculado = subtotalCalculado * _tipoUsuario.value.porcentajeDescuento

        val costoBaseEnvio = if (_distancia.value > 50) {
            120.0 + ((_distancia.value - 50) * 2.0)
        } else {
            0.0
        }
        val costoEnvioTotal = costoBaseEnvio + _tipoEnvio.value.costoAdicional

        val esCuponValido = _codigoCupon.value == "AHORRO100" && subtotalCalculado > 500.0
        val descCuponCalculado = if (_cuponAplicado.value && esCuponValido) 100.0 else 0.0

        _subtotal.value = subtotalCalculado
        _descuentoUsuario.value = descUsuarioCalculado
        _costoEnvio.value = costoEnvioTotal
        _descuentoCupon.value = descCuponCalculado
        _total.value = subtotalCalculado - descUsuarioCalculado + costoEnvioTotal - descCuponCalculado
    }
}