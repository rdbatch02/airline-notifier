package util

fun String.containsAny(components: List<String>, ignoreCase: Boolean = false): Boolean {
    return components.any { this.contains(it, ignoreCase) }
}