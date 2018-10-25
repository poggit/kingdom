package io.pmmp.poggit.kingdom.html.global

import io.pmmp.poggit.kingdom.Context
import io.pmmp.poggit.kingdom.Renderer
import io.pmmp.poggit.kingdom.html.*

/**
 * Superclass of all HTML tags. Global attributes are handled here.
 */
abstract class DomElement<Data : Context, out Self : DomNode<Data, Self>>(val elementName: String) : DomNode<Data, Self>() {
	val doRender get() = true
	open val style = ElementStyle.BLOCK

	// global attributes
	var accessKey: (() -> Char)? = null
	var autoCapitalize: AutoCapitalize? = null
	val classes = ClassList()
	var contentEditable: Boolean? = null
	val data = mutableMapOf<String, Template<Data>>()
	
	var id: Template<Data>? = null

	open val attributes: Map<String, Template<Data>> = emptyMap()
	val attributesWithGlobal: MutableMap<String, Template<Data>>
		get() {
			val attributes = this.attributes.toMutableMap()

			accessKey.nn { attributes["accesskey"] = it().toString().asText() }
			autoCapitalize.nn { attributes["autocapitalize"] = it.string.asHtml() }
			if (classes.classes.isNotEmpty()) attributes["class"] = classes.classes.joinToString(" ").asHtml()
			contentEditable.nn { attributes["contenteditable"] = it.toString().asHtml() }
			data.forEach { name, value -> attributes["data-$name"] = value }

			id.nn { attributes["id"] = it }

			return attributes
		}

	override fun render(r: Renderer<Data>) {
		if (doRender) style.render(r, this)
	}

	abstract fun renderChildren(r: Renderer<Data>)

	fun data(name: String, value: Template<Data>) {
		data[name] = value
	}
}

inline fun <T, R> T?.nn(f: (T) -> R): R? {
	return if (this != null) f(this) else null
}
