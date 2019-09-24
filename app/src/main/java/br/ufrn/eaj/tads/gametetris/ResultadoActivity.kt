package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resultado.*

class ResultadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        var params = intent.extras
        var texto = params?.getString("PONTUACAO")

        val settings = getSharedPreferences("Wesley", Context.MODE_PRIVATE)
        var editor = settings.edit()


        idPontucao.text="Pontução: ${texto}"

        var score = settings.getInt("SCORE", 0)
        idScore.text="Score: ${score.toString()}"



       if(texto.toString().toInt()>score){
            Log.i("s","entrou")
            idNovoRecord.visibility= View.VISIBLE
            editor.putInt("SCORE", texto.toString().toInt())
            editor.commit()
        }


        btnNovoJogo.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        btnSair.setOnClickListener {
            var i = Intent(this, StartActivity::class.java)
            startActivity(i)
        }

    }
}
