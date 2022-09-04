package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        //Singleton para não depender de instancia para usar este método!
        @Volatile private var db: AppDatabase? = null
        /* @Volatile ->
            notation que quando a properti for acessa em 2 threads ao mesmo tempo
            se uma delas gerar o valor inicialmente a outra também encherga este valor.

            Sendo assim : Evita que aconteça o caso de gerar 2 instâncias ao mesmo tempo.
         */

        fun getInstance(context: Context): AppDatabase {
            //caso meu DB tiver valor retornamos ele mesmo, caso não tiver criamos uma instancia
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).build()
                .also {
                    //also -> função de escopo que retorna a mesma instância
                    db = it
                }
        }
    }
}