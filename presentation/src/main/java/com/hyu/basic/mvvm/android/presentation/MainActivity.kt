package com.hyu.basic.mvvm.android.presentation

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import com.hyu.basic.mvvm.android.presentation.detail.DetailFragment
import com.hyu.basic.mvvm.android.presentation.preview.PreviewFragment
import com.hyu.basicmvvmandroid.presentation.R
import com.hyu.basicmvvmandroid.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val selectViewModel : SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            selectModel = selectViewModel
            lifecycleOwner = this@MainActivity
        }

        initLayout()
    }

    private fun initLayout(){
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(supportFragmentManager.findFragmentByTag(PreviewFragment.TAG) == null){
                supportFragmentManager.commit {
                    add(R.id.fl_frame_layer, PreviewFragment(), PreviewFragment.TAG)
                }
            }

            selectViewModel.selectIndex.observe(this, Observer {
                if(it < 0) return@Observer
                moveDetail()
            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(supportFragmentManager.findFragmentByTag(PreviewFragment.TAG) == null){
                selectViewModel.unSelect()
            }
        }
    }


    private fun moveDetail(){
        if(supportFragmentManager.findFragmentByTag("detail") != null) return

        val previousFragment = supportFragmentManager.findFragmentById(R.id.fl_frame_layer)

        supportFragmentManager.commit {

            previousFragment?.sharedElementReturnTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.transition_detail_fragment)

            val nextFragment = DetailFragment()

            nextFragment.sharedElementEnterTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.transition_detail_fragment)

            replace(R.id.fl_frame_layer, nextFragment, "detail")
                .setReorderingAllowed(true)
                .addToBackStack(null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addSharedElement(selectViewModel.selectView.value!!, "previewItem${selectViewModel.selectIndex}")
            }
        }
    }
}