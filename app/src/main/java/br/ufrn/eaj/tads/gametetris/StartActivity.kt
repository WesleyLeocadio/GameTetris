package br.ufrn.eaj.tads.gametetris

import android.app.Activity
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
        btnContinuar.setOnClickListener(this)

        var params = intent.extras
        var texto = params?.getBoolean("cod")
        when (texto) {
            true -> {
                btnContinuar.visibility = View.VISIBLE
            }
        }

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
            btnContinuar.id->{
                var i = Intent()
                setResult(Activity.RESULT_OK)
                finish()

            }


        }

    }
}
