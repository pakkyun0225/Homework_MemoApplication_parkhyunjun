package kr.co.lion.memoproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.memoproject.databinding.ActivityMemoListBinding

class MemoListActivity : AppCompatActivity() {
    lateinit var activityMemoListBinding: ActivityMemoListBinding

    // EditMemoActivity 실행을 위한 런처
    lateinit var editMemoActivityLauncher: ActivityResultLauncher<Intent>

    lateinit var subtitle:String
    lateinit var content:String
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_list)
        activityMemoListBinding = ActivityMemoListBinding.inflate(layoutInflater)
        setContentView(activityMemoListBinding.root)

        initView()
        initData()
        setLauncher()
        setToolbar()
    }

    fun initData(){
        activityMemoListBinding.apply {
            position = intent.getIntExtra("position",0)
        }
    }

    fun setLauncher() {
        val contract = ActivityResultContracts.StartActivityForResult()
        // EditMemoActivity 실행을 위한 런처, 돌아왔을 때의 코드
        editMemoActivityLauncher = registerForActivityResult(contract) {
            if (it != null) {
                // 작업의 결과로 분기한다.
                when (it.resultCode) {
                    RESULT_OK -> {
                        // editMemoActivity에서 설정한 Intent 객체로부터 데이터 추출
                        if (it.data != null) {
                            subtitle = it.data!!.getStringExtra("newSubtitle").toString()
                            content = it.data!!.getStringExtra("newContent").toString()

                            activityMemoListBinding.textViewSubtitle.text = "${subtitle}"
                            activityMemoListBinding.textViewContent.text = "${content}"
                        }
                    }
                }
            }
        }
    }

    fun initView() {
        activityMemoListBinding.apply {
            // intent로부터 객체 추출
            val memo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("memo", MemoClass::class.java)
            } else {
                intent?.getParcelableExtra<MemoClass>("memo")
            }
            textViewSubtitle.apply {
                text = "${memo?.subtitle}"
            }
            textViewDate.apply {
                text = "${memo?.date}"
            }
            textViewContent.apply {
                text = "${memo?.content}"
            }
        }
    }

    fun setToolbar() {
        activityMemoListBinding.apply {
            toolbarMemoList.apply {
                title = "메모 보기"
                inflateMenu(R.menu.menu_memo_list)
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    val finishIntent = Intent()
                    finishIntent.putExtra("newSubtitle",subtitle)
                    finishIntent.putExtra("newContent",content)
                    finishIntent.putExtra("position",position)
                    setResult(RESULT_OK,finishIntent)
                    finish()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemMemoListEdit -> {
                            val newIntent =
                                Intent(this@MemoListActivity, EditMemoActivity::class.java)
                            newIntent.putExtra("subtitle", textViewSubtitle.text)
                            newIntent.putExtra("content", textViewContent.text)
                            editMemoActivityLauncher.launch(newIntent)
                        }

                        R.id.menuItemMemoListDelete -> {
                            // 삭제하시겠습니까 다이얼로그같은거 띄우고
                            // 메인액티비티로 돌아가서 리스트에서 해당 항목을 삭제
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