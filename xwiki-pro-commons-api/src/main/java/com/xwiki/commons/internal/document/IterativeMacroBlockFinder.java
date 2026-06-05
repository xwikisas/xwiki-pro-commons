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
package com.xwiki.commons.internal.document;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.MacroBlock;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.block.match.ClassBlockMatcher;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;

import com.xwiki.commons.document.MacroBlockFinder;
import com.xwiki.commons.document.MacroUtils;

/**
 * Implementer of the {@link MacroBlockFinder} that searches for macros iteratively. Additionally, the parsed content of
 * the macros are attached to the macro blocks. This allows further processing of the XDOM if necessary.
 *
 * @version $Id$
 * @since 1.4.0
 */
@Component
@Singleton
@Named("iterative")
public class IterativeMacroBlockFinder implements MacroBlockFinder
{
    @Inject
    private MacroUtils macroUtils;

    @Override
    public XDOM find(XDOM content, Syntax syntax, Function<MacroBlock, Lookup> function) throws MacroExecutionException
    {
        Stack<XDOM> stack = new Stack<>();
        stack.add(content);

        XDOM currentDOM = stack.pop();

        while (currentDOM != null) {
            List<MacroBlock> macros =
                currentDOM.getBlocks(new ClassBlockMatcher(MacroBlock.class), Block.Axes.DESCENDANT);
            for (MacroBlock macro : macros) {
                Lookup lookup = function.apply(macro);
                if (lookup.equals(Lookup.BREAK)) {
                    break;
                } else if (lookup.equals(Lookup.SKIP)) {
                    continue;
                }
                if (macro.getContent() != null && !macro.getContent().isEmpty()
                    && this.macroUtils.isMacroContentParsable(macro.getId()))
                {
                    XDOM macroXDOM = macroUtils.getMacroContentXDOM(macro, syntax);
                    stack.push(macroXDOM);
                    macro.setChildren(macroXDOM.getChildren());
                }
            }
            if (stack.isEmpty()) {
                break;
            }
            currentDOM = stack.pop();
        }

        return content;
    }
}
