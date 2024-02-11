package kr.co.lion.memoproject

import android.content.DialogInterface
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
                setNavigationIcon(R.drawable.ic_chevron_left)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemEditMemoDone -> {
                            if (checkData()) {
                                Util.memoList[position].subtitle =
                                    editTextEditSubtitle.text.toString()
                                Util.memoList[position].content =
                                    editTextEditContent.text.toString()
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
        activityEditMemoBinding.apply {
            val subtitleText = editTextEditSubtitle.text.toString()
            val contentText = editTextEditContent.text.toString()

            if (subtitleText.trim().isEmpty()) {
                Util.showAlertDialog(
                    this@EditMemoActivity,
                    "제목 입력 오류",
                    "제목이 입력되지 않았습니다."
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showFocusKeyboard(editTextEditSubtitle, this@EditMemoActivity)
                }
                return false
            } else if (contentText.trim().isEmpty()) {
                Util.showAlertDialog(
                    this@EditMemoActivity,
                    "내용 입력 오류",
                    "내용이 입력되지 않았습니다."
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showFocusKeyboard(editTextEditContent, this@EditMemoActivity)
                }
                return false
            } else {
                return true
            }
        }
    }
}