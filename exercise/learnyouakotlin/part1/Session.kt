package learnyouakotlin.part1


data class Session(val title: String, val subtitle: String?, val slots: Slots, val presenters: List<Presenter>) {

    constructor(title: String, subtitle: String?, slots: Slots, vararg presenters: Presenter) : this(
        title,
        subtitle,
        slots,
        presenters.toList())

    fun withPresenters(newLineUp: List<Presenter>) = Session(title, subtitle, slots, newLineUp)

    fun withTitle(newTitle: String) = Session(newTitle, subtitle, slots, presenters)

    fun withSubtitle(newSubtitle: String?) = Session(title, newSubtitle, slots, presenters)
}
