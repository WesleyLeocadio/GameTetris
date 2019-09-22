package br.ufrn.eaj.tads.gametetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnComecarJogo.setOnClickListener(this)
        btnConfiguracoes.setOnClickListener(this)
        btnResultado.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        val id = view.id
        when(id){
            btnComecarJogo.id->{
                var i = Intent(this, MainActivity::class.java)
                startActivity(i)

            }
            btnConfiguracoes.id->{
                var i = Intent(this, ConfiguracaoActivity::class.java)
                startActivity(i)

            }
            btnResultado.id->{
                var i = Intent(this, ResultadoActivity::class.java)
                startActivity(i)

            }


        }

    }
}
