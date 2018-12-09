package lesson5

import lesson5.impl.GraphBuilder
import org.junit.jupiter.api.Tag
import kotlin.test.*

class LongestPathTest {

    @Test
    @Tag("Example")
    fun test1() {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
        }.build()
        val pathMap = graph.longestPath(graph["A"]!!)
        assertEquals(1, pathMap[graph["B"]!!]?.distance)
        assertEquals(2, pathMap[graph["C"]!!]?.distance)
        assertEquals(listOf(graph["A"], graph["B"], graph["C"]), pathMap.unrollLongestPath(graph["C"]!!))

        val graph2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(a, e)
            addConnection(d, k)
            addConnection(e, j)
            addConnection(j, k)
            addConnection(b, f)
            addConnection(c, i)
            addConnection(f, i)
            addConnection(b, g)
            addConnection(g, h)
            addConnection(h, c)
        }.build()
        val pathMap2 = graph2.longestPath(graph2["A"]!!)
        assertEquals(8, pathMap2[graph2["E"]!!]?.distance)
        assertEquals(10, pathMap2[graph2["I"]!!]?.distance)
        assertEquals(listOf(graph2["A"], graph2["E"], graph2["J"], graph2["K"], graph2["D"], graph2["C"],
                graph2["H"], graph2["G"], graph2["B"], graph2["F"], graph2["I"]), pathMap2.unrollLongestPath(graph2["I"]!!))
    }
}