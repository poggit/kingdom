package io.pmmp.poggit.kingdom.html

import io.pmmp.poggit.kingdom.Context
import io.pmmp.poggit.kingdom.Renderer
import io.pmmp.poggit.kingdom.html.global.DomElement

enum class ElementStyle {
	BLOCK {
		override fun <Data : Context> render(r: Renderer<Data>, element: DomElement<Data, *>) {
			renderOpen(r, element)
			r.ln()
			r.indented { element.renderChildren(r) }
			renderClose(r, element)
			r.ln()
		}
	},

	BIG_BLOCK {
		override fun <Data : Context> render(r: Renderer<Data>, element: DomElement<Data, *>) {
			renderOpen(r, element)
			r.ln()
			element.renderChildren(r)
			renderClose(r, element)
			r.ln()
		}
	},
	INLINE {
		override fun <Data : Context> render(r: Renderer<Data>, element: DomElement<Data, *>) {
			renderOpen(r, element)
			element.renderChildren(r)
			renderClose(r, element)
		}
	};

	abstract fun <Data : Context> render(r: Renderer<Data>, element: DomElement<Data, *>)

	fun <Data : Context> renderOpen(r: Renderer<Data>, element: DomElement<Data, *>) {
		r.append("<${element.elementName}")
		for ((name, attr) in element.attributes) {
			r.append(" $name=\"")
			attr.render(r)
			r.append("\"")
		}
		r.append(">")
	}

	fun <Data : Context> renderClose(r: Renderer<Data>, element: DomElement<Data, *>) {
		r.append("</${element.elementName}>")
	}
}
