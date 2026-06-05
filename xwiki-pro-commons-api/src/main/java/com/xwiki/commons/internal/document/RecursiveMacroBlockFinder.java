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
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.MacroBlock;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.block.match.ClassBlockMatcher;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;

import com.xwiki.commons.document.MacroBlockFinder;
import com.xwiki.commons.document.MacroFindBreakException;
import com.xwiki.commons.document.MacroUtils;

/**
 * Default implementation of the {@link MacroBlockFinder}. It searches through the DOM recursively.
 *
 * @version $Id$
 * @since 1.4.0
 */
@Component
@Singleton
public class RecursiveMacroBlockFinder implements MacroBlockFinder
{
    @Inject
    private MacroUtils macroUtils;

    @Inject
    private Logger logger;

    @Override
    public XDOM find(XDOM content, Syntax syntax, Function<MacroBlock, Lookup> function) throws MacroExecutionException
    {
        try {
            return findInternal(content, syntax, function);
        } catch (MacroFindBreakException e) {
            return e.getXdom();
        }
    }

    private XDOM findInternal(XDOM content, Syntax syntax, Function<MacroBlock, Lookup> function)
        throws MacroExecutionException, MacroFindBreakException
    {
        List<MacroBlock> macros = content.getBlocks(new ClassBlockMatcher(MacroBlock.class), Block.Axes.DESCENDANT);
        for (MacroBlock macro : macros) {
            Lookup lookup = function.apply(macro);
            if (lookup.equals(Lookup.BREAK)) {
                throw new MacroFindBreakException(content);
            } else if (lookup.equals(Lookup.SKIP)) {
                continue;
            }

            if (macro.getContent() != null && !macro.getContent().isEmpty() && this.macroUtils.isMacroContentParsable(
                macro.getId()))
            {
                try {
                    XDOM updatedContent = find(this.macroUtils.getMacroContentXDOM(macro, syntax), syntax, function);
                    macroUtils.updateMacroContent(macro,
                        macroUtils.renderMacroContent(updatedContent.getChildren(), syntax));
                } catch (ComponentLookupException e) {
                    logger.warn("Failed to update the content of the macro identified by [{}]. Cause: [{}]",
                        macro.getId(), ExceptionUtils.getRootCauseMessage(e));
                }
            }
        }
        return content;
    }
}
