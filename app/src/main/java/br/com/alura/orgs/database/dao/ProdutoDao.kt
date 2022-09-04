package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>>

    //@Insert(onConflict = OnConflictStrategy.REPLACE) -> quero que caso o ID for o mesmo ele faça um update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun remove(produto: Produto)

    //Query de filtro -> @Query("SELECT * FROM tabela_desejada WHERE coluna_a_ser_filtrada = :referencia_do_parametro_a_ser_filtrado")
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscaTodosOrdenadoPorNomeAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscaTodosOrdenadoPorNomeDesc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    fun buscaTodosOrdenadoPorDescricaoAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    fun buscaTodosOrdenadoPorDescricaoDesc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun buscaTodosOrdenadoPorValorAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun buscaTodosOrdenadoPorValorDesc(): Flow<List<Produto>>
}