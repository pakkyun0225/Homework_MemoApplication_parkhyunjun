package kr.co.lion.memoproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import kr.co.lion.memoproject.databinding.ActivityEditMemoBinding

class EditMemoActivity : AppCompatActivity() {
    lateinit var activityEditMemoBinding: ActivityEditMemoBinding
    lateinit var oldSubtitle:String
    lateinit var oldContent:String
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_memo)
        activityEditMemoBinding = ActivityEditMemoBinding.inflate(layoutInflater)
        setContentView(activityEditMemoBinding.root)

        setData()
        setView()
        setToolbar()
    }

    fun setData() {
        activityEditMemoBinding.apply {
            oldSubtitle = intent.getStringExtra("subtitle").toString()
            oldContent = intent.getStringExtra("content").toString()
            position = intent.getIntExtra("position", 0)
        }
    }

    fun setView(){
        activityEditMemoBinding.apply {
            editTextEditSubtitle.text?.append(oldSubtitle)
            editTextEditContent.text?.append(oldContent)
        }
    }

    fun setToolbar() {
        activityEditMemoBinding.apply {
            toolbarEditMemo.apply {
                title = "메모 수정"
                inflateMenu(R.menu.menu_edit_memo)
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemEditMemoDone -> {
                            Util.memoList[position].subtitle = editTextEditSubtitle.text.toString()
                            Util.memoList[position].content = editTextEditContent.text.toString()
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }
}