package util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class DateWindow(val startDate: Instant, val endDate: Instant) {
    companion object {
        fun today(zoneId: ZoneId = ZoneId.of("UTC")): DateWindow {
            return DateWindow(
                startDate = LocalDate.now(zoneId).atStartOfDay(zoneId).toInstant(),
                endDate = LocalDate.now(zoneId).atStartOfDay(zoneId).toInstant().plusSeconds(86399))
        }
        fun yesterday(zoneId: ZoneId = ZoneId.of("UTC")): DateWindow {
            return DateWindow(
                startDate = LocalDate.now(zoneId).minusDays(1).atStartOfDay(zoneId).toInstant(),
                endDate = LocalDate.now(zoneId).minusDays(1).atStartOfDay(zoneId).toInstant().plusSeconds(86399))
        }
        fun lastDay(zoneId: ZoneId = ZoneId.of("UTC")): DateWindow {
            return DateWindow(
                startDate = LocalDate.now(zoneId).minusDays(1).atStartOfDay(zoneId).toInstant(),
                endDate = LocalDate.now(zoneId).atStartOfDay(zoneId).toInstant().plusSeconds(86399))
        }
    }
}
