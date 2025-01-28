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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.SpaceReferenceResolver;

/**
 * Multi space picker.
 *
 * @version $Id$
 */
public class SuggestSpacesReference
{
    private final List<String> spaces;

    @Inject
    private final SpaceReferenceResolver spaceReferenceResolver;

    /**
     * @param references the string value of the reference
     * @param resolver resolver to transform the string in a space reference
     */
    public SuggestSpacesReference(String references, SpaceReferenceResolver resolver)
    {
        // Keep the spaces as a string to be more efficient when it comes to space and convert it to the actual
        // space reference only when requested.
        spaces = new ArrayList<>();
        this.spaceReferenceResolver = resolver;
        Collections.addAll(spaces, references.split(","));
    }

    /**
     * Get the spaces as a list of strings.
     *
     * @return list of strings
     */
    public List<String> getSpacesAsString()
    {
        return spaces;
    }

    /**
     * Get the spaces as a list of SpaceReferences.
     *
     * @return list of SpaceReferences
     */
    public List<SpaceReference> getSpacesAsReference()
    {
        return spaces.stream().map(space -> spaceReferenceResolver.resolve(space)).collect(Collectors.toList());
    }
}
