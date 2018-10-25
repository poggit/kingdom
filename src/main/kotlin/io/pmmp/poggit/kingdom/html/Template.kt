package io.pmmp.poggit.kingdom.html

import io.pmmp.poggit.kingdom.Context
import io.pmmp.poggit.kingdom.Renderer
import org.apache.commons.text.StringEscapeUtils.escapeHtml4

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

interface Template<in Data : Context> {
	fun render(r: Renderer<Data>)
}

class StaticTemplate<in Data : Context>(val static: String) : Template<Data> {
	override fun render(r: Renderer<Data>) {
		r.append(static)
	}
}

fun <Data : Context> String.asHtml() = StaticTemplate<Data>(this)
fun <Data : Context> String.asText() = StaticTemplate<Data>(escapeHtml4(this))

class FunctionTemplate<in Data : Context>(val f: (r: Data) -> String) : Template<Data> {
	override fun render(r: Renderer<Data>) {
		r.append(f(r.data))
	}

}

fun <Data : Context> dyn(f: (r: Data) -> String) = FunctionTemplate(f)
