package com.example.verdejar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unipar.provahorta.R

data class Planta(
    val nome: String,
    val dataPlantio: String,
    val nivelCuidado: String,
    var quantidadeAgua: Int = 0
)

class MainActivity : AppCompatActivity() {

    private lateinit var plantaAdapter: PlantaAdapter
    private val plantas = mutableListOf<Planta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tvTotalPlantas: TextView = findViewById(R.id.tvTotalPlantas)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val etNomePlanta: EditText = findViewById(R.id.etNomePlanta)
        val etDataPlantio: EditText = findViewById(R.id.etDataPlantio)
        val spinnerNivelCuidado: Spinner = findViewById(R.id.spinnerNivelCuidado)
        val btnAdicionarPlanta: Button = findViewById(R.id.btnAdicionarPlanta)


        plantaAdapter = PlantaAdapter(plantas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = plantaAdapter


        val niveisCuidado = arrayOf("Baixo", "Médio", "Alto")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, niveisCuidado)
        spinnerNivelCuidado.adapter = adapterSpinner


        btnAdicionarPlanta.setOnClickListener {
            val nomePlanta = etNomePlanta.text.toString()
            val dataPlantio = etDataPlantio.text.toString()
            val nivelCuidado = spinnerNivelCuidado.selectedItem.toString()


            if (nomePlanta.isNotEmpty() && dataPlantio.isNotEmpty()) {
                val novaPlanta = Planta(nomePlanta, dataPlantio, nivelCuidado)
                plantas.add(novaPlanta)
                plantaAdapter.notifyDataSetChanged()


                tvTotalPlantas.text = "Total de plantas: ${plantas.size}"


                etNomePlanta.text.clear()
                etDataPlantio.text.clear()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    class PlantaAdapter(private val plantas: List<Planta>) : RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

        inner class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nomePlanta: TextView = itemView.findViewById(R.id.tvNome)
            val dataPlantio: TextView = itemView.findViewById(R.id.tvDataPlantio)
            val nivelCuidado: TextView = itemView.findViewById(R.id.tvNivelCuidado)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
            return PlantaViewHolder(view)
        }

        override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
            val planta = plantas[position]
            holder.nomePlanta.text = planta.nome
            holder.dataPlantio.text = "Plantada em: ${planta.dataPlantio}"
            holder.nivelCuidado.text = "Nível de cuidado: ${planta.nivelCuidado}"
        }

        override fun getItemCount(): Int {
            return plantas.size
        }
    }
}