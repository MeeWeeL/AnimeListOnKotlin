package com.meeweel.anilist.workmanager
//
//import android.content.Context
//import androidx.core.app.NotificationCompat
//import androidx.work.Data
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.meeweel.anilist.R
//import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
//import com.meeweel.anilist.app.App
//import javax.inject.Inject
//
//class SynchronizeWorker(
//    private val context: Context,
//    private val workerParams: WorkerParameters
//) : Worker(context, workerParams) {
//
//    @Inject
//    lateinit var syncer: AnimeSynchronizer
//
//    var builder = NotificationCompat.Builder(context, "CHANNEL_ID")
//        .setSmallIcon(R.drawable.ic_launcher_foreground)
//        .setContentTitle("textTitle")
//        .setContentText("textContent")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//    override fun doWork(): Result {
//
////        App.appInstance.component.inject(this)
//
////        TimeUnit.SECONDS.sleep(10) //  Пауза
//        syncer.synchronize()
////        with(NotificationManagerCompat.from(context)) {
////             notificationId is a unique int for each notification that you must define
////            notify(123, builder.build())
////        }
//        val output: Data = Data.Builder()
//            .putInt(outPutKey, 66)
//            .build()
//
//        return Result.success(output)
//    }
//
//    companion object {
//        const val outPutKey = "Count"
//    }
//}