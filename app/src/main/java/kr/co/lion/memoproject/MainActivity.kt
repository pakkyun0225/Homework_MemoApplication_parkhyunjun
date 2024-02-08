package kr.co.lion.memoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.memoproject.databinding.ActivityMainBinding
import kr.co.lion.memoproject.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 메모 리스트 객체
    val memoList = mutableListOf<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

//        setLauncher()
        setToolbar()
        setView()
    }

    override fun onResume() {
        super.onResume()

        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }

//    fun setLauncher() {
//        val contract = ActivityResultContracts.StartActivityForResult()
//        // AddMemoActivity 실행을 위한 런처, 돌아왔을 때의 코드
//        addMemoActivityLauncher = registerForActivityResult(contract) {
//            activityMainBinding.apply {
//                if (it != null) {
//                    // 작업의 결과로 분기한다.
//                    when (it.resultCode) {
//                        RESULT_OK -> {
//                            if (it.data != null) {
//                                val memo =
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                                        it.data!!.getParcelableExtra("memo", Util::class.java)
//                                    } else {
//                                        it.data!!.getParcelableExtra<Util>("memo")
//                                    }
//                                memoList.add(memo!!)
//                                activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        // memoListActivity 계약 등록
//        val contract2 = ActivityResultContracts.StartActivityForResult()
//        memoListActivityLauncher = registerForActivityResult(contract2) {
//            activityMainBinding.apply {
//                if (it != null) {
//                    // 작업의 결과로 분기한다.
//                    when (it.resultCode) {
//                        RESULT_OK -> {
//                            val newSubtitle = it.data!!.getStringExtra("newSubtitle")
//                            val newContent = it.data!!.getStringExtra("newContent")
//                            val position = it.data!!.getIntExtra("position",0)
//                            memoList[position].subtitle = newSubtitle
//                            memoList[position].content = newContent
//                            activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
//                        }
//                    }
//                }
//            }
//        }
//    }


    fun setToolbar() {
        activityMainBinding.apply {
            toolbarMain.apply {
                title = "메모 관리"
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemAddMemo -> {
                            val addIntent =
                                Intent(this@MainActivity, AddMemoActivity::class.java)
                            startActivity(addIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun setView() {
        activityMainBinding.apply {
            recyclerViewMain.apply {
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
                val deco = MaterialDividerItemDecoration(
                    this@MainActivity,
                    MaterialDividerItemDecoration.VERTICAL
                )
                addItemDecoration(deco)
            }
        }
    }

    fun setEvent() {

    }

    inner class RecyclerViewAdapter :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {

        inner class ViewHolderClass(rowMainBinding: RowBinding) :
            RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding: RowBinding

            init {
                this.rowMainBinding = rowMainBinding
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
//                this.rowMainBinding.root.setOnClickListener {
//                    val showIntent = Intent(this@MainActivity, ShowMemoActivity::class.java)
//                    showIntent.putExtra("memo", memoList[adapterPosition].subtitle)
//                    showIntent.putExtra("memo", memoList[adapterPosition].content)
//                    showIntent.putExtra("position", adapterPosition)
//                    startActivity(showIntent)
//                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowMainBinding)

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMainBinding.textViewRowSubtitle.text = "제목: ${memoList[position].subtitle}"
            holder.rowMainBinding.textViewRowDate.text = "날짜: ${memoList[position].date}"
        }
    }
}

