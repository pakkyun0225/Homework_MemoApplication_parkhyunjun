package kr.co.lion.memoproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kr.co.lion.memoproject.databinding.ActivityAddMemoBinding
import java.time.LocalDate

class AddMemoActivity : AppCompatActivity() {
    lateinit var activityAddMemoBinding: ActivityAddMemoBinding
    val memo = Memo()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddMemoBinding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(activityAddMemoBinding.root)

        setView()
        setToolbar()
    }


    fun setView() {
        Util.showFocusKeyboard(activityAddMemoBinding.editTextAddSubtitle, this@AddMemoActivity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setToolbar() {
        activityAddMemoBinding.apply {
            toolbarAddMemo.apply {
                title = "메모 작성"
                inflateMenu(R.menu.menu_add_memo)
                setNavigationIcon(R.drawable.ic_chevron_left)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    Util.hideFocusKeyboard(this@AddMemoActivity)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemAddMemoDone -> {
                            //입력 유효성 검사 메서드가 반환하는 값을 조건으로 동작하는 코드
                            if (checkData()) {
                                saveData()

                                Util.hideFocusKeyboard(this@AddMemoActivity)
                                setResult(RESULT_OK)
                                finish()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    fun checkData():Boolean {
        activityAddMemoBinding.apply {
            val subtitleText = editTextAddSubtitle.text.toString()
            val contentText = editTextAddContent.text.toString()

            if (subtitleText.trim().isEmpty()) {
                Util.showAlertDialog(
                    this@AddMemoActivity,
                    "제목 입력 오류",
                    "제목이 입력되지 않았습니다."
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showFocusKeyboard(editTextAddSubtitle, this@AddMemoActivity)
                }
                return false
            } else if (contentText.trim().isEmpty()) {
                Util.showAlertDialog(
                    this@AddMemoActivity,
                    "내용 입력 오류",
                    "내용이 입력되지 않았습니다."
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showFocusKeyboard(editTextAddContent, this@AddMemoActivity)
                }
                return false
            } else {
                return true
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveData() {
        activityAddMemoBinding.apply {
            memo.subtitle = editTextAddSubtitle.text.toString()
            memo.date = LocalDate.now().toString()
            memo.content = editTextAddContent.text.toString()

            Util.memoList.add(memo)
        }
    }
}