package kr.co.lion.memoproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kr.co.lion.memoproject.databinding.ActivityAddMemoBinding
import java.time.LocalDate

class AddMemoActivity : AppCompatActivity() {
    lateinit var activityAddMemoBinding: ActivityAddMemoBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddMemoBinding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(activityAddMemoBinding.root)

        setToolbar()
        setEvent()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setToolbar() {
        activityAddMemoBinding.apply {
            toolbarAddMemo.apply {
                title = "메모 작성"
                inflateMenu(R.menu.menu_add_memo)
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 메모 추가 완료
                        R.id.menuItemAddMemoDone -> {
                            val subtitle = editTextAddSubtitle.text.toString()
                            val date = LocalDate.now().toString()
                            val content = editTextAddContent.text.toString()

                            val memo = MemoClass(subtitle, date, content)
                            val memoIntent = Intent()
                            memoIntent.putExtra("memo", memo)
                            setResult(RESULT_OK, memoIntent)
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