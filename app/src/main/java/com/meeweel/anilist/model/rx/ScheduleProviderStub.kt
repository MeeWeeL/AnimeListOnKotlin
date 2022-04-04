package com.meeweel.anilist.model.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}