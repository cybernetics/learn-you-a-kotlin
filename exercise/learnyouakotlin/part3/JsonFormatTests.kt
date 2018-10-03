package learnyouakotlin.part3

import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import learnyouakotlin.part1.Presenter
import learnyouakotlin.part1.Session
import learnyouakotlin.part1.Slots
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Rule
import org.junit.Test

class JsonFormatTests {
    @JvmField
    @Rule
    val approval = ApprovalsRule.fileSystemRule("exercise")

    @Test
    fun session_to_json() {
        val session = Session(
            "Learn You a Kotlin For All The Good It Will Do You",
            null,
            Slots(1, 2),
            Presenter("Duncan McGregor"),
            Presenter("Nat Pryce"))

        val json = sessionToJson(session)
        approval.assertApproved(Json.toStableJsonString(json))
    }

    @Test
    fun session_with_subtitle_to_json() {
        val session = Session(
            "Scrapheap Challenge",
            "A Workshop in Postmodern Programming",
            Slots(3, 3),
            Presenter("Ivan Moore"))

        val json = sessionToJson(session)
        approval.assertApproved(Json.toStableJsonString(json))
    }

    @Test
    fun session_to_and_from_json() {
        val original = Session(
            "Working Effectively with Legacy Tests", null,
            Slots(1, 2),
            Presenter("Nat Pryce"),
            Presenter("Duncan McGregor"))

        val parsed = sessionFromJson(sessionToJson(original))
        assertThat(parsed, equalTo(original))
    }
}
