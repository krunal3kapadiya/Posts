package com.common.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Keval on 17-Dec-16.
 * Base fragment is base class for [Fragment]. This handles connection and disconnect of Rx bus.
 * Use this class instead of [AppCompatActivity] through out the application.

 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

open class BaseDialogFragment : DialogFragment() {
    /**
     * [CompositeDisposable] that holds all the subscriptions.
     */
    private val mCompositeDisposable = CompositeDisposable()

    /**
     * Add the subscription to the [CompositeDisposable].

     * @param disposable [Disposable]
     */
    protected fun addSubscription(disposable: Disposable?) {
        if (disposable == null) return
        mCompositeDisposable.add(disposable)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dispose()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind butter knife
        ButterKnife.bind(this, view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    protected fun dispose() = mCompositeDisposable.dispose()
}