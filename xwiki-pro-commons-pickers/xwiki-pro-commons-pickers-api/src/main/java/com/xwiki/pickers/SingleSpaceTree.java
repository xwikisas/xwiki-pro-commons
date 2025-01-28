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
package com.xwiki.pickers;

import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.SpaceReferenceResolver;

/**
 * Single space tree picker.
 *
 * @version $Id$
 */
public class SingleSpaceTree
{
    private final String space;

    private final SpaceReferenceResolver spaceReferenceResolver;

    /**
     * @param reference the string value of the reference
     * @param resolver resolver to transform the string in a space reference
     */
    public SingleSpaceTree(String reference, SpaceReferenceResolver resolver)
    {
        space = reference;
        spaceReferenceResolver = resolver;
    }

    /**
     * Get the spaces as a string.
     *
     * @return space as a string
     */
    public String getSpaceAsString()
    {
        return space;
    }

    /**
     * Get the space as a proper reference.
     *
     * @return space as a SpaceReference
     */
    public SpaceReference getSpaceAsReference()
    {
        return spaceReferenceResolver.resolve(space);
    }
}
