import java.util.*

fun createQaReleaseVersionCode(version: Int): Int {
    with(DateFields.current) {
        return (year * 100 + month) * 100 + version
    }
}

fun createQaReleaseVersionName(version: String): String {
    with(DateFields.current) {
        return "$year.$month.$day-$hours:$minutes-$version"
    }
}

fun createQaReleaseVersionName(version: Int) = createQaReleaseVersionName(version.toString())

private data class DateFields(
    val year: Int,
    val month: Int,
    val day: Int,
    val hours: Int,
    val minutes: Int
) {
    companion object {
        val current: DateFields
            get() = with(Calendar.getInstance()) {
                DateFields(
                    year    = get(Calendar.YEAR),
                    month   = get(Calendar.MONTH),
                    day     = get(Calendar.DAY_OF_MONTH),
                    hours   = get(Calendar.HOUR_OF_DAY),
                    minutes = get(Calendar.MINUTE)
                )
            }
    }
}
