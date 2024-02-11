package kr.co.lion.memoproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.memoproject.databinding.ActivityMainBinding
import kr.co.lion.memoproject.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var addMemoActivityLauncher: ActivityResultLauncher<Intent>

    val memoList = Util.memoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        setView()
    }

    override fun onResume() {
        super.onResume()
        activityMainBinding.apply {
            if (memoList.size == 0) {
                textViewMainEmpty.visibility = View.VISIBLE
            } else {
                textViewMainEmpty.visibility = View.GONE
            }
            recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }

    fun setLauncher() {
        val contract1 = ActivityResultContracts.StartActivityForResult()
        addMemoActivityLauncher = registerForActivityResult(contract1) {
            activityMainBinding.apply {
                if (it != null) {
                    // 작업의 결과로 분기한다.
                    when (it.resultCode) {
                        RESULT_OK -> {
                            Util.toastMessage(this@MainActivity, "메모가 저장되었습니다.")
                            activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                        }

                        RESULT_CANCELED -> {
                            Util.toastMessage(this@MainActivity, "메모 작성이 취소되었습니다.")
                        }
                    }
                }
            }
        }
    }


    fun setToolbar() {
        activityMainBinding.apply {
            toolbarMain.apply {
                title = "Memo App"
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuItemAddMemo -> {
                            val addIntent =
                                Intent(this@MainActivity, AddMemoActivity::class.java)
                            addMemoActivityLauncher.launch(addIntent)
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
            }
        }
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
                this.rowMainBinding.root.setOnClickListener {
                    this.rowMainBinding.rowMainCardView.setBackgroundColor(Color.rgb(200,180,210))
                    val showIntent = Intent(this@MainActivity, ShowMemoActivity::class.java)
                    showIntent.putExtra("position", adapterPosition)
                    startActivity(showIntent)
                    Handler().postDelayed({this.rowMainBinding.rowMainCardView.setBackgroundColor(Color.rgb(241,231,250))}, 80)
                }
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
            holder.rowMainBinding.textViewRowSubtitle.text = "${memoList[position].subtitle}"
            holder.rowMainBinding.textViewRowDate.text = "${memoList[position].date}"
        }
    }
}

