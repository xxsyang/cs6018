# Phase 2 Code Reviews

*Jinyi Zhou | u1424752*

Meet with 1 member of the other team and do the following

- Each person pick a piece of code from your own project (class, method, etc) that you think can be improved. Discuss that code in detail, improve it, and commit those improvements (ie, refactor something with a helper that has fresh eyes)
- Pick a piece of functionality that both of your projects include. Discuss the similarities/differences between your implementations
- Pick part of your code that you don't think is well tested. Work with your partner to add more tests to exercise that part of your project and commit them to your repo

Summarize what you discussed during the code review and the commits you made with your partner in a ~1 page document and add it to your repo.

---

I met with Lydia Yuan.

- *A piece of code from your own project (class, method, etc) that you think can be improved:*

The way I wrote the function for changing the shape of the pen can be improved. Previously, the shape is passed to the function as an integer, where I commented "1 for circle, 2 for squre". I changed the code a little bit by creating a separate class for the shape of pen:

```kotlin
class PenShape {
    companion object {
        const val circle: Int = 1
        const val square: Int = 2
    }
}
```

In this case, the line of code to change the shape becomes:

```kotlin
binding.viewModel?.setShape(PenShape.circle)
```
and
```kotlin
binding.viewModel?.setShape(PenShape.square)
```

This increases the readability of my code.

- *Pick a piece of functionality that both of your projects include. Discuss the similarities/differences between your implementations*

In our project, we used bitmap to implement the drawing. In their project, path is used to implement the drawing. 

In our case, the bitmap is initialized by the following code:

```kotlin
val newBoard = Bitmap.createBitmap(2160, 3840, Bitmap.Config.ARGB_8888)
```

In their project, a separate class named ``PathProperties`` is used to store properties for the path:

```kotlin
class PathProperties(
    var strokeWidth: Float = 10f,
    var color: Color = Color.Black,
    var strokeCap: StrokeCap = StrokeCap.Round,
    var strokeJoin: StrokeJoin = StrokeJoin.Round,
    var eraseMode: Boolean = false
) {

    fun copy(
        strokeWidth: Float = this.strokeWidth,
        color: Color = this.color,
        strokeCap: StrokeCap = this.strokeCap,
        strokeJoin: StrokeJoin = this.strokeJoin,
        eraseMode: Boolean = this.eraseMode
    ) = PathProperties(
        strokeWidth, color, strokeCap, strokeJoin, eraseMode
    )
}
```

where the function ``copy`` is used to reproduce the path.

- *Pick part of your code that you don't think is well tested. Work with your partner to add more tests to exercise that part of your project and commit them to your repo*

After reviewing Lydia's test, I realized that the navigation functionality in our own project is not well tested. I asked the logic in her tests, and then added some new tests for our own project in order to test the navigation between fragments. The code can be viewed in ``NavigationAndViewModelTest.kt``.