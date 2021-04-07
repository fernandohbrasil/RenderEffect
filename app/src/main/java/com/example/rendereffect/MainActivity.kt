package com.example.rendereffect

import android.graphics.*
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.rendereffect.databinding.ActivityMainBinding

@RequiresApi(31)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            btApplyEffecf.setOnClickListener { applyEffects() }
            btClearEffect.setOnClickListener { clearEffects() }
        }

        spinnerBind(R.array.tile_mode, binding.spShaderTileMode)
        spinnerBind(R.array.blend_mode, binding.spBlendMode)
    }

    private fun ActivityMainBinding.applyEffects() {
        when {
            rbImage.isChecked -> ivImage.setRenderEffect(getRenderEffect())
            rbButton.isChecked -> btViewExample.setRenderEffect(getRenderEffect())
            else -> clViewImage.setRenderEffect(getRenderEffect())
        }
    }

    private fun getRenderEffect(): RenderEffect {
        if (binding.rbBlur.isChecked) {
            return RenderEffect.createBlurEffect(getXRadius(), getYRadius(), getShaderTileMode())
        }

        return RenderEffect.createBlendModeEffect(
                RenderEffect.createBlurEffect(getXRadius(), getYRadius(), getShaderTileMode()),
                RenderEffect.createBitmapEffect(BitmapFactory.decodeResource(resources, R.drawable.android)),
                getBlendMode()
        )
    }

    private fun getShaderTileMode(): Shader.TileMode {
        return when (binding.spShaderTileMode.selectedItem) {
            "Shader.TileMode.CLAMP" -> Shader.TileMode.CLAMP
            "Shader.TileMode.REPEAT" -> Shader.TileMode.REPEAT
            "Shader.TileMode.MIRROR" -> Shader.TileMode.MIRROR
            else -> Shader.TileMode.DECAL
        }
    }

    private fun getBlendMode(): BlendMode {
        return when (binding.spBlendMode.selectedItem) {
            "BlendMode.CLEAR" -> BlendMode.CLEAR
            "BlendMode.SRC" -> BlendMode.SRC
            "BlendMode.DST" -> BlendMode.DST
            "BlendMode.SRC_OVER" -> BlendMode.SRC_OVER
            "BlendMode.DST_OVER" -> BlendMode.DST_OVER
            "BlendMode.SRC_IN" -> BlendMode.SRC_IN
            "BlendMode.DST_IN" -> BlendMode.DST_IN
            "BlendMode.SRC_OUT" -> BlendMode.SRC_OUT
            "BlendMode.DST_OUT" -> BlendMode.DST_OUT
            "BlendMode.SRC_ATOP" -> BlendMode.SRC_ATOP
            "BlendMode.DST_ATOP" -> BlendMode.DST_ATOP
            "BlendMode.XOR" -> BlendMode.XOR
            "BlendMode.PLUS" -> BlendMode.PLUS
            "BlendMode.MODULATE" -> BlendMode.MODULATE
            "BlendMode.SCREEN" -> BlendMode.SCREEN
            "BlendMode.OVERLAY" -> BlendMode.OVERLAY
            "BlendMode.DARKEN" -> BlendMode.DARKEN
            "BlendMode.LIGHTEN" -> BlendMode.LIGHTEN
            "BlendMode.COLOR_DODGE" -> BlendMode.COLOR_DODGE
            "BlendMode.COLOR_BURN" -> BlendMode.COLOR_BURN
            "BlendMode.HARD_LIGHT" -> BlendMode.HARD_LIGHT
            "BlendMode.SOFT_LIGHT" -> BlendMode.SOFT_LIGHT
            "BlendMode.DIFFERENCE" -> BlendMode.DIFFERENCE
            "BlendMode.EXCLUSION" -> BlendMode.EXCLUSION
            "BlendMode.MULTIPLY" -> BlendMode.MULTIPLY
            "BlendMode.HUE" -> BlendMode.HUE
            "BlendMode.SATURATION" -> BlendMode.SATURATION
            "BlendMode.COLOR" -> BlendMode.COLOR
            else -> BlendMode.LUMINOSITY
        }
    }

    private fun spinnerBind(textArrayResId: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
                this,
                textArrayResId,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun getXRadius(): Float {
        return try {
            binding.etX.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
    }

    private fun getYRadius(): Float {
        return try {
            binding.etY.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
    }

    private fun ActivityMainBinding.clearEffects() {
        btViewExample.setRenderEffect(null)
        clViewImage.setRenderEffect(null)
        ivImage.setRenderEffect(null)
    }
}