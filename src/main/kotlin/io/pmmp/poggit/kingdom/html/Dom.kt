package io.pmmp.poggit.kingdom.html

import io.pmmp.poggit.kingdom.Renderer

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

abstract class DomNode<out Self : DomNode<Self>> : Template {
	@Suppress("UNCHECKED_CAST")
	inline operator fun invoke(f: Self.() -> Unit) {
		f(this as Self)
	}
}

abstract class DomElement<out Self : DomNode<Self>>(val elementName: String) : DomNode<Self>() {
	val doRender get() = true
	open val style = ElementStyle.BLOCK
	var id: Template? = null
	val classes = ClassList()
	val data = mutableMapOf<String, Template>()

	open val attributes: MutableMap<String, Template> = mutableMapOf()
	val attributesWithGlobal
		get() = attributes.apply {
			val id = this@DomElement.id
			if (id != null) attributes["id"] = id
			if (classes.classes.isNotEmpty()) attributes["classes"] = classes.classes.joinToString(" ").asHtml
		}

	override fun render(r: Renderer) {
		if (doRender) style.render(r, this)
	}

	abstract fun renderChildren(r: Renderer)

	fun data(name: String, value: Template) {
		data[name] = value
	}
}

enum class ElementStyle {
	BLOCK {
		override fun render(r: Renderer, element: DomElement<*>) {
			renderOpen(r, element)
			r.ln()
			r.indented { element.renderChildren(r) }
			renderClose(r, element)
			r.ln()
		}
	},

	BIG_BLOCK {
		override fun render(r: Renderer, element: DomElement<*>) {
			renderOpen(r, element)
			r.ln()
			element.renderChildren(r)
			renderClose(r, element)
			r.ln()
		}
	},
	INLINE {
		override fun render(r: Renderer, element: DomElement<*>) {
			renderOpen(r, element)
			element.renderChildren(r)
			renderClose(r, element)
		}
	};

	abstract fun render(r: Renderer, element: DomElement<*>)

	fun renderOpen(r: Renderer, element: DomElement<*>) {
		r.append("<${element.elementName}")
		for ((name, attr) in element.attributes) {
			r.append(" $name=\"")
			attr.render(r)
			r.append("\"")
		}
		r.append(">")
	}

	fun renderClose(r: Renderer, element: DomElement<*>) {
		r.append("</${element.elementName}>")
	}
}
