package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListaProdutosActivity : AppCompatActivity() {
    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.getInstance(this)
        db.produtoDao()
    }
    private var produtoLista: List<Produto>? = null
    private var listaNaoOrdenada: List<Produto> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            val produtos = produtoDao.buscaTodos()
            listaNaoOrdenada = produtos
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_lista_produto_nome_desc -> {
                ordenaNomeDesc()
            }
            R.id.menu_lista_produto_nome_asc -> {
                ordenaNomeAsc()
            }
            R.id.menu_lista_produto_description_desc -> {
                ordenaDescricaoDesc()
            }
            R.id.menu_lista_produto_description_asc -> {
                ordenaDescricaoAsc()
            }
            R.id.menu_lista_produto_value_desc -> {
                ordenaValorDesc()
            }
            R.id.menu_lista_produto_value_asc -> {
                ordenaValorAsc()
            }
            R.id.menu_lista_produto_no_order -> {
                semOrdenar()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

    private fun ordenaNomeDesc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorNomeDesc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun ordenaNomeAsc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorNomeAsc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun ordenaDescricaoDesc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorDescricaoDesc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun ordenaDescricaoAsc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorDescricaoAsc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun ordenaValorDesc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorValorDesc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun ordenaValorAsc() {
        lifecycleScope.launch {
            produtoDao.buscaTodosOrdenadoPorValorAsc().collect { listaProdutoOrdenada ->
                produtoLista = listaProdutoOrdenada
                adapter.atualiza(listaProdutoOrdenada)
            }
        }
    }

    private fun semOrdenar() {
        produtoLista = listaNaoOrdenada
        adapter.atualiza(listaNaoOrdenada)
    }
}