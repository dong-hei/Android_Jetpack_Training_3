package com.dk.room_simple_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.dk.room_simple_ex.db.TextDB
import com.dk.room_simple_ex.db.entity.TextEntity
import com.dk.room_simple_ex.db.entity.TextEntity2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * fallback_to_Destructive_Migration에 대해
 * textDao2()를 추가했는데 문제는 이에 따라서 버젼을 v3으로 올릴때 v2에 있던 db가 날아간다.
 * 이 역할을 하는게 fallback_to_Destructive_Migration
 *
 * 근데 대부분 localDB 데이터가 날아가면 안된다.
 *
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = TextDB.getDatabase(this)
        val inputArea = findViewById<EditText>(R.id.textInputArea)

        val insertBtn = findViewById<Button>(R.id.insertBtn)
        val getAllBtn = findViewById<Button>(R.id.getDataBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        insertBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.textDao().insert(TextEntity(0, inputArea.text.toString()))
                db.textDao2().insert(TextEntity2(0, inputArea.text.toString()))
                inputArea.setText("")
            }
        }

        getAllBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("MAIN", db.textDao().getAllData().toString())
                Log.d("MAIN 2", db.textDao2().getAllData().toString())
            }
        }

        deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.textDao().deleteAllData()
                db.textDao2().deleteAllData()
            }
        }

    }
}