package io.pmmp.poggit.kingdom.basic

import io.pmmp.poggit.kingdom.Context
import io.pmmp.poggit.kingdom.KingdomTestData
import io.pmmp.poggit.kingdom.html.asText
import io.pmmp.poggit.kingdom.html.dom.HtmlTree

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

fun simple(html: HtmlTree<KingdomTestData>) = html {
	head {
		title = "Hello world".asText()
	}
}
