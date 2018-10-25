package io.pmmp.poggit.kingdom

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

class Renderer<out Data : Context>(
	val data: Data,
	val target: (CharSequence) -> Unit,
	val indent: String,
	val eol: String
) : Context {
	private var lineStart = true
	var indents = 0

	inline fun indented(f: () -> Unit) {
		indents++
		f()
		indents--
	}

	fun append(cs: CharSequence): Renderer<Data> {
		if (lineStart) {
			target(indent.repeat(indents))
			lineStart = false
		}
		target(cs)
		return this
	}

	@Suppress("NOTHING_TO_INLINE")
	inline operator fun plusAssign(cs: CharSequence) {
		append(cs)
	}

	fun ln(): Renderer<Data> {
		target(eol)
		lineStart = true
		return this
	}

	fun appendln(cs: CharSequence) = append(cs).ln()
}
