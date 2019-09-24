package br.ufrn.eaj.tads.gametetris

import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    val LINHA = 36
    val COLUNA = 20

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }
}