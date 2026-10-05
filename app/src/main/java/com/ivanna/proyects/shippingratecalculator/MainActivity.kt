package com.ivanna.proyects.shippingratecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ivanna.proyects.shippingratecalculator.shippingRateCalculatorVM.ShippingRateCalculatorVMPage
import com.ivanna.proyects.shippingratecalculator.ui.theme.ShippingRateCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShippingRateCalculatorTheme {
                ShippingRateCalculatorVMPage()
            }
        }
    }
}
