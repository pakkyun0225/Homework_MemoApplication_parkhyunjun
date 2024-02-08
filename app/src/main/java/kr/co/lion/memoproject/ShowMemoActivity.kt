package kr.co.lion.memoproject

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.memoproject.databinding.ActivityShowMemoBinding

class ShowMemoActivity : AppCompatActivity() {
    lateinit var activityShowMemoBinding: ActivityShowMemoBinding
    // EditMemoActivity 실행을 위한 런처
    lateinit var editMemoActivityLauncher: ActivityResultLauncher<Intent>

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_memo)
        activityShowMemoBinding = ActivityShowMemoBinding.inflate(layoutInflater)
        setContentView(activityShowMemoBinding.root)

        setView()
        setData()
        setLauncher()
        setToolbar()
    }

    fun setView() {
        activityShowMemoBinding.apply {
            textViewSubtitle.text = Util.memoList[position].subtitle
            textViewDate.text = Util.memoList[position].date
            textViewContent.text = Util.memoList[position].content
        }
    }

    fun setData() {
        activityShowMemoBinding.apply {
            position = intent.getIntExtra("position", 0)
        }
    }

    fun setLauncher() {
        val contract = ActivityResultContracts.StartActivityForResult()
        activityShowMemoBinding.apply {
            editMemoActivityLauncher = registerForActivityResult(contract) {
                if (it != null) {
                    // 작업의 결과로 분기한다.
                    when (it.resultCode) {
                        RESULT_OK -> {
                            setView()
                            Util.toastMessage(this@ShowMemoActivity,"메모가 수정되었습니다.")
                        }
                        RESULT_CANCELED -> {
                            Util.toastMessage(this@ShowMemoActivity,"수정이 취소되었습니다.")
                        }
                    }
                }
            }
        }
    }

    fun setToolbar() {
        activityShowMemoBinding.apply {
            toolbarShowMemo.apply {
                title = "메모 보기"
                inflateMenu(R.menu.menu_show_memo)
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemMemoListEdit -> {
                            val editIntent =
                                Intent(this@ShowMemoActivity, EditMemoActivity::class.java)
                            editIntent.putExtra("subtitle", textViewSubtitle.text)
                            editIntent.putExtra("content", textViewContent.text)
                            editIntent.putExtra("position", position)

                            editMemoActivityLauncher.launch(editIntent)
                        }

                        R.id.menuItemMemoListDelete -> {
                            val dialogBuilder = MaterialAlertDialogBuilder(this@ShowMemoActivity)
                            dialogBuilder.setTitle("메모 삭제")
                            dialogBuilder.setMessage("메모를 삭제하시겠습니까?")
                            dialogBuilder.setNegativeButton("취소", null)
                            dialogBuilder.setPositiveButton("확인"){dialogInterface: DialogInterface, i: Int ->
                                Util.memoList.removeAt(position)
                                finish()
                                Util.toastMessage(this@ShowMemoActivity,"메모가 삭제되었습니다.")
                            }
                            dialogBuilder.show()
                        }
                    }
                    true
                }
            }
        }
    }
}