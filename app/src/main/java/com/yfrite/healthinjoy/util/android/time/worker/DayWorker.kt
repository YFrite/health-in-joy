package com.yfrite.healthinjoy.util.android.time.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.ViewModelProvider
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yfrite.healthinjoy.data.eaten.Eaten
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.main.health.viewModel.HealthViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltWorker
class DayWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParam: WorkerParameters,
    private val repository: EatenRepository):
    CoroutineWorker(context, workerParam) {


    override suspend fun doWork(): Result {

        try {
            repository.insert(Eaten())
        } catch (err: Exception){
            return Result.failure()
        }

        return Result.success()
    }

}