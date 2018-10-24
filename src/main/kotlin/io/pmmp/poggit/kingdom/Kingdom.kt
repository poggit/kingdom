package io.pmmp.poggit.kingdom

import io.pmmp.poggit.kingdom.css.CssRuleSet
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

class Kingdom{
	val htmlTemplates = mutableMapOf<String, HtmlTree>()

	val cssRuleSets = mutableMapOf<String, Set<CssRuleSet>>()
}
