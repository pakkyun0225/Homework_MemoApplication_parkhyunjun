package kr.co.lion.memoproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import kr.co.lion.memoproject.databinding.ActivityEditMemoBinding

class EditMemoActivity : AppCompatActivity() {
    lateinit var activityEditMemoBinding: ActivityEditMemoBinding

    // EditMemoActivity 실행을 위한 런처
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_memo)
        activityEditMemoBinding = ActivityEditMemoBinding.inflate(layoutInflater)
        setContentView(activityEditMemoBinding.root)

        initData()
        setToolbar()
        setEvent()
    }

    fun initData() {
        activityEditMemoBinding.apply {
            val subtitle = intent.getStringExtra("subtitle")
            val content = intent.getStringExtra("content")

            editTextEditSubtitle.apply {
                text?.append(subtitle)
            }
            editTextEditContent.apply {
                text?.append(content)
            }
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
                            val editIntent = Intent()
                            editIntent.putExtra("newSubtitle", editTextEditSubtitle.text.toString())
                            editIntent.putExtra("newContent", editTextEditContent.text.toString())
                            setResult(RESULT_OK, editIntent)
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }

    fun setEvent() {

    }
}