package br.ufrn.eaj.tads.gametetris

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_configuracao.*

class ConfiguracaoActivity : AppCompatActivity() {
    var groupRadio=0
    var escolhido=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracao)


        val settings = getSharedPreferences("Wesley", Context.MODE_PRIVATE)
        groupRadio = settings.getInt("selecionado", 0)
        escolhido = settings.getInt("velocidade", 0)

        Log.i("erro", " NOVO -> grupo 0${groupRadio} escolhido ${escolhido} ")


            when(escolhido){
                500->{
                    radioFacil.isChecked
                }
                400->{
                    radioMedio.isChecked
                }
                200->{
                radioDificil.isChecked
            }
            }


        button.setOnClickListener {
            Toast.makeText(this, "Nível Ajustado", Toast.LENGTH_SHORT).show()
            Log.i("erro", " grupo 0${groupRadio} escolhido ${escolhido} ")

            finish()

        }


    }

    override fun onStop() {
        super.onStop()

        val g = findViewById<RadioGroup>(R.id.radiogroupnivel)

        if(g.checkedRadioButtonId==radioFacil.id){
            groupRadio=g.checkedRadioButtonId
            escolhido=500

        }else if(g.checkedRadioButtonId==radioMedio.id){
            groupRadio=g.checkedRadioButtonId
            escolhido=400

        }else if(g.checkedRadioButtonId==radioDificil.id){
            groupRadio=g.checkedRadioButtonId
            escolhido=200

        }


        val settings = getSharedPreferences("Wesley",Context.MODE_PRIVATE)
        var editor = settings.edit()

        editor.putBoolean("salvar", true)
        editor.putInt("selecionado", groupRadio)
        editor.putInt("velocidade", escolhido)
        editor.commit()




    }
}
