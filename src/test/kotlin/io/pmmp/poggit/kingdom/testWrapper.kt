package io.pmmp.poggit.kingdom

import io.kotlintest.TestContext
import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractFreeSpec
import io.pmmp.poggit.kingdom.html.dom.HtmlTree
import org.apache.commons.io.IOUtils
import java.nio.charset.Charset

/*
 *  Kingdom, the Kotlin DOM template engine
 *  Copyright (C) 2018 SOFe
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

fun kingdomTest(f: KingdomTestScope.() -> Unit): AbstractFreeSpec.() -> Unit {
	val scope = KingdomTestScope("")
	scope.f()
	return { scope.execute(createGroup = { a, b -> a - b }, createTest = { a, b -> a(b) }) }
}

class KingdomTestScope(val dir: String) {
	val scopes = mutableMapOf<String, KingdomTestScope>()
	val files = mutableMapOf<String, KingdomTestFile>()

	operator fun String.div(f: KingdomTestScope.() -> Unit) {
		val scope = KingdomTestScope(dir + this + "/")
		scopes[this] = scope
		scope.f()
	}

	operator fun String.minus(f: (html: HtmlTree<KingdomTestData>) -> Unit): KingdomTestFile {
		val html = HtmlTree<KingdomTestData>()
		f(html)

		val file = KingdomTestFile(html)
		files[this] = file
		return file
	}

	fun execute(createGroup: (String, AbstractFreeSpec.FreeSpecScope.() -> Unit) -> Unit,
	            createTest: (String, TestContext.() -> Unit) -> Unit) {
		for (file in files) {
			createTest(file.key) {
				val expected = IOUtils.resourceToString("expected/$dir${file.key}.html", Charset.defaultCharset(), javaClass.classLoader)
				val builder = StringBuilder()
				file.value.tree.render(Renderer(
					data = file.value.testData,
					target = { builder.append(it) },
					indent = "\t",
					eol = "\n"
				))
				val actual = builder.toString()
				actual shouldBe expected
			}
		}

		for (scope in scopes) {
			createGroup(scope.key) {
				scope.value.execute(createGroup = { a, b -> a - b }, createTest = { a, b -> a(b) })
			}
		}
	}
}

class KingdomTestFile(val tree: HtmlTree<KingdomTestData>) {
	val testData = KingdomTestData()

	operator fun plus(f: KingdomTestData.() -> Unit) = testData.f()
}

class KingdomTestData : Context {
	val map = mutableMapOf<String, () -> String>()

	operator fun get(name: String) = map[name]!!.invoke()

	operator fun String.minus(data: () -> String) {
		map[this] = data
	}

	operator fun String.minus(data: String) = minus { data }
	operator fun String.minus(data: Number) = minus(data.toString())
}
