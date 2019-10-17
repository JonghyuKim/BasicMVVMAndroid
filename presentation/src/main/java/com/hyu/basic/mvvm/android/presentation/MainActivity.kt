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


    private fun moveDetail(){

        val previousFragment = supportFragmentManager.findFragmentById(R.id.fl_frame_layer)

        supportFragmentManager.commit {

            previousFragment?.sharedElementReturnTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.transition_detail_fragment)

            previousFragment?.exitTransition = TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.fade)

            val nextFragment = DetailFragment()

            nextFragment.sharedElementEnterTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.transition_detail_fragment)

            nextFragment.enterTransition = TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.fade)

            replace(R.id.fl_frame_layer, nextFragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addSharedElement(selectViewModel.selectView.value!!, "previewItem${selectViewModel.selectIndex}")
            }
        }
    }
}