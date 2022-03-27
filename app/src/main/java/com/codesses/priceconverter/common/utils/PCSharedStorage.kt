package com.codesses.priceconverter.common.utils


class PCSharedStorage : SharedPrefHelper(PCSharedConfig.PREF_NAME) {

    companion object {
        private const val RECENT_SCREEN = "recent_screen"
        private const val SLIDER_VISITED = "on_boarding_slider_visited"
        private const val FILTERED_CITY = "filtered_city"
        private const val EVENTS_CREDITS_LEFT = "events_credits_left"
        private const val INVITATION_DAYS_LEFT = "invitation_days_left"
        private const val CURRENT_MONTH_FREE_EVENT = "current_m%s_%s_free_event"

    }

}