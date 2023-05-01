package com.rio.videoplayer.data

import androidx.lifecycle.MutableLiveData
import com.rio.videoplayer.data.response.ResultResponse
import com.rio.videoplayer.utils.HttpCode
import com.rio.videoplayer.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainService: MainService) {

    private val compositeDisposable = CompositeDisposable()

    fun search(term: String): MutableLiveData<Resource<MutableList<ResultResponse>>> {
        val data = MutableLiveData<Resource<MutableList<ResultResponse>>>()
        data.value = Resource.loading(null)
        mainService.search(term, "musicVideo")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    data.value = Resource.success(it.results)
                },
                {
                    data.value = Resource.error(null, it.message)
                    it.printStackTrace()
                }
            ).addTo(disposable = compositeDisposable)
        return data
    }

    private fun Disposable.addTo(disposable: CompositeDisposable) {
        disposable.add(this)
    }

    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}