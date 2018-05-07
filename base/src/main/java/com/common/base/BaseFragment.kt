/*
 *  Copyright 2017 Keval Patel.
 *
 *  Licensed under the GNU General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.common.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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

open abstract class BaseFragment : Fragment() {
    /**
     * [CompositeDisposable] that holds all the subscriptions.
     */
    private val mCompositeDisposable = CompositeDisposable()
    protected lateinit var mContext: Context       //Instance of the caller

    abstract fun getLayoutId(): Int

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
        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid layout id")
        } else {
            return inflater.inflate(getLayoutId(), container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind butter knife
        ButterKnife.bind(this, view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
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

    protected fun finish() = activity?.finish()

    protected var isNetworkCallRunning = false

    protected fun networkingCallRunning() {
        isNetworkCallRunning = true
    }

    protected fun networkingCallComplete() {
        isNetworkCallRunning = false
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}
