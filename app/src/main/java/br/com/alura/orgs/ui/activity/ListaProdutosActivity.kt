package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*

class ListaProdutosActivity : AppCompatActivity() {
    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.getInstance(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            //Essa lambda expression é como se fosse o Catch

            Toast.makeText(
                this@ListaProdutosActivity,
                "Ocorreu um erro: ${throwable.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
        val scope = MainScope()

        //vinculo o handle com o escopo que quero que vai ser responsavel por capturar as Exceptions
        scope.launch(handler) {
//            throw Exception("Laçando uma exception de teste")

            //Mudamos o context da coroutines para sair da MAIN para rodar em outra thread e não dar crash
            val produtos = withContext(Dispatchers.IO) {
                produtoDao.buscaTodos()
            }
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenado: List<Produto>? = when (item.itemId) {
            R.id.menu_lista_produto_nome_desc -> {
                produtoDao.buscaTodosOrdenadoPorNomeDesc()
            }
            R.id.menu_lista_produto_nome_asc -> {
                produtoDao.buscaTodosOrdenadoPorNomeAsc()
            }
            R.id.menu_lista_produto_description_desc -> {
                produtoDao.buscaTodosOrdenadoPorDescricaoDesc()
            }
            R.id.menu_lista_produto_description_asc -> {
                produtoDao.buscaTodosOrdenadoPorDescricaoAsc()
            }
            R.id.menu_lista_produto_value_desc -> {
                produtoDao.buscaTodosOrdenadoPorValorDesc()
            }
            R.id.menu_lista_produto_value_asc -> {
                produtoDao.buscaTodosOrdenadoPorValorAsc()
            }
            R.id.menu_lista_produto_no_order -> {
                produtoDao.buscaTodos()
            }
            else -> null
        }

        //Atualizando o adapter com a lista ordenada
        produtosOrdenado?.let {
            adapter.atualiza(it)
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

}