/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.xwiki.commons.document;

import java.util.function.Function;

import org.xwiki.component.annotation.Role;
import org.xwiki.rendering.block.MacroBlock;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Searches for xwiki macros inside XDOMs.
 *
 * @since 1.4.0
 * @version $Id$
 */
@Role
public interface MacroBlockFinder
{
    /**
     * An enum defining the actions to be done after finding a macro in the XDOM.
     */
    enum Lookup
    {
        /**
         * The lookup for other macros should halt.
         */
        BREAK,
        /**
         * The lookup for other macros can continue without parsing the content of the processed macro.
         */
        SKIP,
        /**
         * The lookup for other macros can continue while also parsing the content of the processed macro.
         */
        CONTINUE
    }

    /**
     * @param content the content that will be searched recursively for macro blocks.
     * @param syntax the syntax of the content.
     * @param function function the function that is executed when a macro block is found. It receives the found
     *     {@link MacroBlock} and returns a {@link Lookup} value. BREAK if the lookup should stop; SKIP if the content
     *     of the current macro should not be parsed; CONTINUE if the content of the current macro should be parsed.
     * @return the modified content.
     */
    XDOM find(XDOM content, Syntax syntax, Function<MacroBlock, Lookup> function) throws MacroExecutionException;
}
