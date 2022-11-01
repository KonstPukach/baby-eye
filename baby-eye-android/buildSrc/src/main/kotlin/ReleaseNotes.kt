import org.gradle.api.Project
import org.gradle.process.ExecSpec
import java.io.ByteArrayOutputStream
import java.io.File

private const val RED = "\u001b[31m"
private const val RESET = "\u001b[0m"

private const val POSSIBLE_ANSWERS = "ynb"

private val MergeCommitPattern = Regex(pattern ="Merge pull request #[0-9]+ from [\\w/-]+$")

const val DEFAULT_RELEASE_NOTES_FILE_PATH = "/release-notes.txt"

/**
 * Check whether the [releaseNotesFilePath] file exists and it is not blank. If not the function
 * asks to create release notes by a commit message and puts generated text into
 * [releaseNotesFilePath] file.
 *
 * @param releaseNotesFilePath is a relative file path. The root is [Project.getProjectDir].
 */
fun Project.generateReleaseNotesIfNotExist(
    releaseNotesFilePath: String = DEFAULT_RELEASE_NOTES_FILE_PATH
): Boolean {
    val notesFile = File(projectDir, releaseNotesFilePath)

    if (!releaseNotesAreNotExistOrBlank(releaseNotesFilePath)) {
        notesFile.writeText(notesFile.readText().trim())
    } else {
        println(
            RED + "Error: File ${notesFile.absolutePath} doesn't exist or it doesn't have any text inside. " +
                    RESET + "\nYou can try to put the current commit message into release notes." +
                    "\nCreate release notes and continue? \ny - Yes, \nn - No, " +
                    "\nb - Continue without release notes"
        )

        var answerStr: String
        do {
            answerStr = readLine() ?: ""
        } while (answerStr.length != 1 && !POSSIBLE_ANSWERS.contains(answerStr, true))

        if (answerStr == "n" || answerStr ==  "N")
            return false

        if (answerStr == "b" || answerStr ==  "B")
            return true

        notesFile.writeText(createReleaseNotes())
    }

    return true
}

/**
 * Returns release notes text generated from commits messages and PR's branches
 */
fun Project.createReleaseNotes(): String {
    val notes = executeWithOutput {
        commandLine("git", "log", "-1", "--format=%s")
    }.trim()

    if (notes.matches(MergeCommitPattern)) {
        val mergedBranch = notes
            .split("/")
            .run { subList(1, size).joinToString("/") }

        val childCommitsHashList = getCommitHashes(mergedBranch).split("\n")
        val parentCommitsHashList = getCommitHashes().split("\n")

        val stdout = ByteArrayOutputStream()
        return childCommitsHashList
            .filterNot(parentCommitsHashList::contains)
            .joinToString(separator = "\n") { hash ->
                rootProject.exec {
                    commandLine("git", "log", "--pretty=format:%s", "-n 1", hash)
                    standardOutput = stdout
                }
                stdout.toString().also {
                    stdout.reset()
                }
            }.also {
                stdout.close()
            }
    } else {
        return notes
    }
}

fun Project.releaseNotesAreNotExistOrBlank(
    releaseNotesFilePath: String = DEFAULT_RELEASE_NOTES_FILE_PATH
): Boolean {
    val notesFile = File(projectDir, releaseNotesFilePath)
    return !notesFile.exists() || notesFile.readText().isBlank()
}

private fun Project.getCommitHashes(branch: String? = null) = executeWithOutput {
    commandLine(
        arrayListOf("git", "log", "--first-parent", "--no-merges", "--pretty=format:%h", branch)
            .filterNotNull()
    )
}

private fun Project.executeWithOutput(action: ExecSpec.() -> Unit): String {
    var output = ""
    ByteArrayOutputStream().use { stdout ->
        rootProject.exec {
            action.invoke(this)
            standardOutput = stdout
        }
        output = stdout.toString()
    }
    return output
}
