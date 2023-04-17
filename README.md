# Jetpack-Compose-Modal-Window
Cheap and easy solution to call dialog view in usual way.

Due to specific of Jetpack Compose, sometimes you need to handle modal state from a different *compose* point. In *compose* you build UI by small elements to cache rendering and unlike of fragments it's not so easy to shared data.
And what if you need to call modal window from child screen, draw it on top hierarchy point and dismiss from activity?

For example: 
after first bottom sheet you call second one and need to collapse on result both of the, or only second if user swipe back,
or you show alert about lost network connection on user action and need to dismiss window on reconnection.

Idea of all this, it to just write an controller like as for SoftwareKeyboard or ModalBottomSheetState.

----

_Just describe your modal and call it from any point of code._

Call `LocalModalHandler.current` and `setContentBuilder` for modal window. Framework will invoke modal at marked point by `Launch` method.
**Don't forget to provide ModalHandler!**

_About of StateValue - it's simple workaround to force recomposition to invoke modal._

There is an sample - cheap, but enough to understand idea.
