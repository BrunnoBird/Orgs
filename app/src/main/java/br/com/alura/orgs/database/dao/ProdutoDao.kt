package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun BuscaTodos(): List<Produto>

    //@Insert(onConflict = OnConflictStrategy.REPLACE) -> quero que caso o ID for o mesmo ele faça um update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto: Produto)

    @Delete
    fun remove(produto: Produto)

    //Query de filtro -> @Query("SELECT * FROM tabela_desejada WHERE coluna_a_ser_filtrada = :referencia_do_parametro_a_ser_filtrado")
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Produto?
}