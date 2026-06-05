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

import org.xwiki.rendering.block.XDOM;

/**
 * Exception that can be used by the {@link MacroBlockFinder} when the
 * {@link com.xwiki.commons.document.MacroBlockFinder.Lookup} that gets returned is BREAK. This can be useful when
 * searching recursively and you want the execution to halt.
 *
 * @version $Id$
 * @since 1.4.0
 */
public class MacroFindBreakException extends Exception
{
    private final XDOM xdom;

    /**
     * Creates an instance that holds a reference to the XDOM at the point where the execution halted.
     *
     * @param xdom the reference to the XDOM at the point of breaking the execution.
     */
    public MacroFindBreakException(XDOM xdom)
    {
        super();
        this.xdom = xdom;
    }

    /**
     * @return a reference to the XDOM that was being searched through.
     */
    public XDOM getXdom()
    {
        return xdom;
    }
}
