package io.pmmp.poggit.kingdom.html.dom

import io.pmmp.poggit.kingdom.Context
import io.pmmp.poggit.kingdom.Renderer
import io.pmmp.poggit.kingdom.html.global.DomElement
import io.pmmp.poggit.kingdom.html.Template

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

class HeadNode<Data : Context> : DomElement<Data, HeadNode<Data>>("head") {
	var title: Template<Data>? = null

	val meta = MetaNodes<Data>()

	override fun renderChildren(r: Renderer<Data>) {
		if (title != null) {
			r.append("<title>")
			title!!.render(r)
			r.appendln("</title>")
		}

		meta.render(r)
		// TODO add CSS links
	}
}
