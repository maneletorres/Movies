package com.manishsputnikcorporation.movies.data.local

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(value = AndroidJUnit4::class)
class MigrationTest {

    // region Setup
    private companion object {
        const val TEST_DB = "migration-test"
        val MIGRATION_1_2 =
            object : Migration(startVersion = 1, endVersion = 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(sql = "ALTER TABLE movies ADD COLUMN original_language TEXT NOT NULL DEFAULT ''")
                }
            }
        val MIGRATION_2_3 =
            object : Migration(startVersion = 2, endVersion = 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        """
                CREATE TABLE miniseries (
                    id INTEGER PRIMARY KEY NOT NULL,
                    originalName TEXT,
                    overview TEXT NOT NULL DEFAULT ''
                )
                """
                            .trimIndent()
                    )
                    db.execSQL(
                        """
                INSERT INTO miniseries (id, originalName, overview)
                SELECT id, originalName, overview FROM movies
                """
                            .trimIndent()
                    )
                    db.execSQL("DROP TABLE movies")
                    db.execSQL("ALTER TABLE miniseries RENAME TO movies")
                }
            }
        private val ALL_MIGRATIONS = arrayOf(MIGRATION_1_2, MIGRATION_2_3)
    }

    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(instrumentation = InstrumentationRegistry.getInstrumentation(), databaseClass = MediaDatabase::class.java)

    // endregion

    // region Testing
    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        helper.createDatabase(name = TEST_DB, version = 1).apply { close() }

        Room.databaseBuilder(
                context = InstrumentationRegistry.getInstrumentation().targetContext,
                klass = MediaDatabase::class.java,
                name = TEST_DB,
            )
            .addMigrations(*ALL_MIGRATIONS)
            .build()
            .apply { openHelper.writableDatabase.close() }
    }
    // endregion
}
